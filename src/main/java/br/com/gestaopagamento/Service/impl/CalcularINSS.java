package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.enums.INSS;
import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.IDesconto;

@Service("calcularINSS")
public class CalcularINSS implements IDesconto {

     @Override
    public BigDecimal calcular(Funcionario funcionario){
        if(funcionario == null){
            throw new IllegalArgumentException("O funcionário não pode ser nulo");
        }

        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        if(salarioBruto == null){
            throw new IllegalArgumentException("O salário bruto não pode ser nulo");
        }

    //Lógica de desconto
        BigDecimal desconto;
        BigDecimal multiply = INSS.TETO1.getValor().multiply(INSS.ALIQUOTA1.getValor());

        if(salarioBruto.compareTo(INSS.TETO1.getValor()) <= 0){
            desconto = salarioBruto.multiply(INSS.ALIQUOTA1.getValor());
        }
   
        else {

            if(salarioBruto.compareTo(INSS.TETO2.getValor()) <= 0){
                desconto = multiply.add(salarioBruto.subtract(INSS.TETO1.getValor()).multiply(INSS.ALIQUOTA2.getValor()));
            }

            else if(salarioBruto.compareTo(INSS.TETO3.getValor()) <= 0){
                desconto = multiply.add(INSS.FAIXA2.getValor().multiply(INSS.ALIQUOTA2.getValor()));
                desconto = desconto.add(salarioBruto.subtract(INSS.TETO2.getValor()).multiply(INSS.ALIQUOTA3.getValor()));
            }

            else if(salarioBruto.compareTo(INSS.TETO4.getValor()) <= 0){
                desconto = multiply.add(INSS.FAIXA2.getValor().multiply(INSS.ALIQUOTA2.getValor()));
                desconto = desconto.add(INSS.FAIXA3.getValor().multiply(INSS.ALIQUOTA3.getValor()));
                desconto = desconto.add(salarioBruto.subtract(INSS.TETO3.getValor()).multiply(INSS.ALIQUOTA4.getValor()));
            }

            else{
                desconto = multiply.add(INSS.FAIXA2.getValor().multiply(INSS.ALIQUOTA2.getValor()));
                desconto = desconto.add(INSS.FAIXA3.getValor().multiply(INSS.ALIQUOTA3.getValor()));
                desconto = desconto.add(INSS.TETO4.getValor().subtract(INSS.TETO3.getValor()).multiply(INSS.ALIQUOTA4.getValor()));
            }
        }

        return desconto;
    }
}
