package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import br.com.gestaopagamento.Models.enums.IRRF;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.IDesconto;

@Service("calcularIRRF")
public class CalcularIRRF implements IDesconto {
    private final IDesconto calculaInss; 

    public CalcularIRRF(IDesconto calculaInss) {
        this.calculaInss = calculaInss;
    }

    @Override
    public BigDecimal calcular(Funcionario funcionario){
        if(funcionario == null){
            throw new IllegalArgumentException("O funcionário não pode ser nulo");
        }
        if(funcionario.getSalarioBruto() == null){
            throw new IllegalArgumentException("O salário bruto não pode ser nulo");
        }
        if(funcionario.getDependentes() == null){
            throw new IllegalArgumentException("A quantidade de dependentes não pode ser nula");
        }

        BigDecimal valorInss;
        try{
            valorInss = this.calculaInss.calcular(funcionario);
        }catch (Exception e) {
             throw new RuntimeException("Falha ao calcular IRRF: O cálculo de INSS falhou.", e);
        }

        if(valorInss == null) {
            throw new IllegalStateException("O cálculo dependente do INSS retornou nulo");
        }

        
        BigDecimal salarioBase = funcionario.getSalarioBruto().subtract(valorInss);

        BigDecimal qntdDependentes = new BigDecimal(funcionario.getDependentes().size());

        BigDecimal pensaoAlimenticia = Optional.ofNullable(funcionario.getPensaoAlimenticia()).orElse(BigDecimal.ZERO);
        BigDecimal outrasDeducoes = Optional.ofNullable(funcionario.getOutrasDeducoes()).orElse(BigDecimal.ZERO);


        BigDecimal baseDeCalculo = salarioBase.subtract(qntdDependentes.multiply(IRRF.deducaoPorDependete.getValor()));
        baseDeCalculo = baseDeCalculo.subtract(pensaoAlimenticia).subtract(outrasDeducoes);

         BigDecimal aliquotaEfetiva;

        if(baseDeCalculo.compareTo(IRRF.teto1.getValor()) <= 0){
            aliquotaEfetiva = BigDecimal.ZERO;
        }

        else if(baseDeCalculo.compareTo(IRRF.teto2.getValor()) <= 0 && baseDeCalculo.compareTo(IRRF.teto1.getValor()) > 0){
            aliquotaEfetiva = baseDeCalculo.multiply(IRRF.aliquota2.getValor()).subtract(IRRF.deducaoIrpf2.getValor());
        }

        else if(baseDeCalculo.compareTo(IRRF.teto3.getValor()) <= 0 && baseDeCalculo.compareTo(IRRF.teto2.getValor()) > 0){
            aliquotaEfetiva = baseDeCalculo.multiply(IRRF.aliquota3.getValor()).subtract(IRRF.deducaoIrpf3.getValor());
        }
        
        else if(baseDeCalculo.compareTo(IRRF.teto4.getValor()) <= 0 && baseDeCalculo.compareTo(IRRF.teto3.getValor()) > 0){
            aliquotaEfetiva = baseDeCalculo.multiply(IRRF.aliquota4.getValor()).subtract(IRRF.deducaoIrpf4.getValor());
        }

        else{
            aliquotaEfetiva = baseDeCalculo.multiply(IRRF.aliquota5.getValor()).subtract(IRRF.deducaoIrpf5.getValor());
        }

        if (aliquotaEfetiva.compareTo(BigDecimal.ZERO) < 0) {
            aliquotaEfetiva = BigDecimal.ZERO;
        }

        return aliquotaEfetiva;
    }
}