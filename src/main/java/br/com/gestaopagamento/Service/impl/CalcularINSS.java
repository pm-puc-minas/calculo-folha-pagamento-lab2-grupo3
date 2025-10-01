package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Desconto;

public class CalcularINSS implements Desconto {

     @Override
    public BigDecimal calcular(Funcionario funcionario){
        //retornando zero apenas para não dar erro
        return BigDecimal.ZERO;
    }
}
