package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Beneficio;

public class CalcularValeTransporte implements Beneficio {
    private final BigDecimal PORCENTAGEM_MAX = new BigDecimal("0.06");
    private BigDecimal valorVT;
    private BigDecimal valorDesconto;

    public CalcularValeTransporte(BigDecimal valorvt) {
        this.valorVT = valorvt;
    }

     @Override
    public BigDecimal calcular(Funcionario funcionario){
         valorDesconto = funcionario.getSalarioBruto().multiply(valorVT);

         if (valorVT.compareTo(valorDesconto) < 0) {
             valorDesconto = valorVT;
             return valorVT;
         }

         return valorDesconto;
    }
}
