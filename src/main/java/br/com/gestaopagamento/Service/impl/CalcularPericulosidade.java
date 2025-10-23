package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.IAdicional;

@Service
public class CalcularPericulosidade implements IAdicional {

    private static final BigDecimal PORCENTAGEM = new BigDecimal("0.30");

    @Override
    public BigDecimal calcular(Funcionario funcionario){
        if (funcionario.getPericulosidade()) {
        return funcionario.getSalarioBruto().multiply(PORCENTAGEM).setScale(2, RoundingMode.HALF_UP);    
        }
        return BigDecimal.ZERO;
    }

}













