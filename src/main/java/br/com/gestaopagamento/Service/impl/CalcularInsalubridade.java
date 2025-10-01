package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Adicional;
@Service
public class CalcularInsalubridade implements Adicional {

    private static final int PORCENTAGEM_BAIXA = 10;
    private static final int PORCENTAGEM_MEDIA = 10;
    private static final int PORCENTAGEM_ALTA = 10;

    @Override
    public BigDecimal calcular(Funcionario funcionario){
        //retornando zero apenas para não dar erro
        return BigDecimal.ZERO;
    }
}
