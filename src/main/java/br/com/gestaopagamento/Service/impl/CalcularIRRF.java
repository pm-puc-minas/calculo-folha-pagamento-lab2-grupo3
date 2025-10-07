package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Desconto;

public class CalcularIRRF implements Desconto {
    private static final BigDecimal DeducaoPorDependete = new BigDecimal("189,59");

     @Override
    public BigDecimal calcular(Funcionario funcionario){
        //Calcula o salário base
        Desconto calculaInss = new CalcularINSS();
        BigDecimal valorInss = calculaInss.calcular(funcionario);

        BigDecimal salarioBase = funcionario.getSalarioBruto().subtract(valorInss);

        //Calcula a base de cálculo
        BigDecimal qntdDependentes = BigDecimal.valueOf(funcionario.getQntdDependentes());
        BigDecimal pensaoAlimenticia = BigDecimal.valueOf(funcionario.getPensaoAlimenticia());
        BigDecimal outrasDeducoes = BigDecimal.valueOf(funcionario.getOutrasDeducoes());

        BigDecimal baseDeCalculo = salarioBase.subtract(qntdDependentes.multiply(DeducaoPorDependete));
        salarioBase = salarioBase.subtract(pensaoAlimenticia).subtract(outrasDeducoes);





        return BigDecimal.ZERO;
    }
}
