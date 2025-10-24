package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import java.util.Optional; // <-- IMPORTANTE ADICIONAR

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Desconto;

public class CalcularIRRF implements Desconto {
    private static final BigDecimal DeducaoPorDependete = new BigDecimal("189.59");

    // ... (Suas constantes de teto e alíquota estão perfeitas) ...
    private static final BigDecimal aliquota2 = new BigDecimal("0.075");
    private static final BigDecimal aliquota3 = new BigDecimal("0.15");
    private static final BigDecimal aliquota4 = new BigDecimal("0.225");
    private static final BigDecimal aliquota5 = new BigDecimal("0.275");
    private static final BigDecimal deducaoIrpf2 = new BigDecimal("142.80");
    private static final BigDecimal deducaoIrpf3 = new BigDecimal("354.80");
    private static final BigDecimal deducaoIrpf4 = new BigDecimal("636.13");
    private static final BigDecimal deducaoIrpf5 = new BigDecimal("869.36");
    private static final BigDecimal teto1 = new BigDecimal("1903.98");
    private static final BigDecimal teto2 = new BigDecimal("2826.65");
    private static final BigDecimal teto3 = new BigDecimal("3751.05");
    private static final BigDecimal teto4 = new BigDecimal("4664.68");


    @Override
    public BigDecimal calcular(Funcionario funcionario){
        //Calcula o salário base
        Desconto calculaInss = new CalcularINSS();
        BigDecimal valorInss = calculaInss.calcular(funcionario);

        BigDecimal salarioBase = funcionario.getSalarioBruto().subtract(valorInss);

        // --- CORREÇÃO AQUI ---

        // 1. Trocamos 'getQntdDependentes()' por 'getDependentes().size()'
        //    O 'Funcionario' novo tem uma LISTA de dependentes.
        BigDecimal qntdDependentes = new BigDecimal(funcionario.getDependentes().size());

        // 2. Trocamos 'BigDecimal.valueOf(getDouble())' por 'getBigDecimal()'
        //    Os campos já são BigDecimal, não precisamos converter.
        //    Usamos 'Optional...orElse' para evitar erro se o valor for nulo no banco.
        BigDecimal pensaoAlimenticia = Optional.ofNullable(funcionario.getPensaoAlimenticia()).orElse(BigDecimal.ZERO);
        BigDecimal outrasDeducoes = Optional.ofNullable(funcionario.getOutrasDeducoes()).orElse(BigDecimal.ZERO);

        // --- FIM DA CORREÇÃO ---

        BigDecimal baseDeCalculo = salarioBase.subtract(qntdDependentes.multiply(DeducaoPorDependete));
        baseDeCalculo = baseDeCalculo.subtract(pensaoAlimenticia).subtract(outrasDeducoes);

        //Calcula aliquota Efetiva
         BigDecimal aliquotaEfetiva;

        if(baseDeCalculo.compareTo(teto1) <= 0){
            aliquotaEfetiva = BigDecimal.ZERO;
        }

        else if(baseDeCalculo.compareTo(teto2) <= 0 && baseDeCalculo.compareTo(teto1) > 0){
            aliquotaEfetiva = baseDeCalculo.multiply(aliquota2).subtract(deducaoIrpf2);
        }

        else if(baseDeCalculo.compareTo(teto3) <= 0 && baseDeCalculo.compareTo(teto2) > 0){
            aliquotaEfetiva = baseDeCalculo.multiply(aliquota3).subtract(deducaoIrpf3);
        }
        
        else if(baseDeCalculo.compareTo(teto4) <= 0 && baseDeCalculo.compareTo(teto3) > 0){
            aliquotaEfetiva = baseDeCalculo.multiply(aliquota4).subtract(deducaoIrpf4);
        }

        else{
            aliquotaEfetiva = baseDeCalculo.multiply(aliquota5).subtract(deducaoIrpf5);
        }

        if (aliquotaEfetiva.compareTo(BigDecimal.ZERO) < 0) {
            aliquotaEfetiva = BigDecimal.ZERO;
        }

        return aliquotaEfetiva;
    }
}