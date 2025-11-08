package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.IDesconto;

public class CalcularIRRF implements IDesconto {
    private final IDesconto calculaInss; 

    public CalcularIRRF(IDesconto calculaInss) {
        this.calculaInss = calculaInss;
    }

    @Override
    public BigDecimal calcular(Funcionario funcionario){
        BigDecimal valorInss = this.calculaInss.calcular(funcionario);

        BigDecimal salarioBase = funcionario.getSalarioBruto().subtract(valorInss);


        BigDecimal qntdDependentes = new BigDecimal(funcionario.getDependentes().size());

        BigDecimal pensaoAlimenticia = Optional.ofNullable(funcionario.getPensaoAlimenticia()).orElse(BigDecimal.ZERO);
        BigDecimal outrasDeducoes = Optional.ofNullable(funcionario.getOutrasDeducoes()).orElse(BigDecimal.ZERO);


        BigDecimal baseDeCalculo = salarioBase.subtract(qntdDependentes.multiply(DeducaoPorDependete));
        baseDeCalculo = baseDeCalculo.subtract(pensaoAlimenticia).subtract(outrasDeducoes);

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