package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Desconto;
import org.springframework.stereotype.Service;

@Service
public class CalcularINSS implements Desconto {
    private static final BigDecimal aliquota1 = new BigDecimal("0.075");
    private static final BigDecimal aliquota2 = new BigDecimal("0.09");
    private static final BigDecimal aliquota3 = new BigDecimal("0.12");
    private static final BigDecimal aliquota4 = new BigDecimal("0.14");

    private static final BigDecimal teto1 = new BigDecimal("1302.0");
    private static final BigDecimal teto2 = new BigDecimal("2571.29");
    private static final BigDecimal teto3 = new BigDecimal("3856.94");
    private static final BigDecimal teto4 = new BigDecimal("7507.49");

    private static final BigDecimal faixa2 = teto2.subtract(teto1);
    private static final BigDecimal faixa3 = teto3.subtract(teto2);

     @Override
    public BigDecimal calcular(Funcionario funcionario){
        if(funcionario == null){
            throw new IllegalArgumentException("O funcionário bruto não pode ser nulo");
        }

        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        if(salarioBruto == null){
            throw new IllegalArgumentException("O salário bruto não pode ser nulo");
        }

    //Lógica de desconto
        BigDecimal desconto;

        if(salarioBruto.compareTo(teto1) <= 0){
            desconto = salarioBruto.multiply(aliquota1); 
        }
   
        else if(salarioBruto.compareTo(teto2) <= 0){
            desconto = teto1.multiply(aliquota1).add(salarioBruto.subtract(teto1).multiply(aliquota2));
        }

        else if(salarioBruto.compareTo(teto3) <= 0){
            desconto = teto1.multiply(aliquota1).add(faixa2.multiply(aliquota2));
            desconto = desconto.add(salarioBruto.subtract(teto2).multiply(aliquota3));
        }

        else if(salarioBruto.compareTo(teto4) <= 0){
            desconto = teto1.multiply(aliquota1).add(faixa2.multiply(aliquota2));
            desconto = desconto.add(faixa3.multiply(aliquota3));
            desconto = desconto.add(salarioBruto.subtract(teto3).multiply(aliquota4));
        }

        else{
            desconto = teto1.multiply(aliquota1).add(faixa2.multiply(aliquota2));
            desconto = desconto.add(faixa3.multiply(aliquota3));
            desconto = desconto.add(teto4.subtract(teto3).multiply(aliquota4));
        }

        return desconto;
    }
}
