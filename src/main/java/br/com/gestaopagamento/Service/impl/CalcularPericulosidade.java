package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Adicional;

@Service
public class CalcularPericulosidade implements Adicional {

    private static final BigDecimal PORCENTAGEM = new BigDecimal("0.30");

    @Override
    public BigDecimal calcular(Funcionario funcionario){
        
        // --- CORREÇÃO AQUI ---
        // O getter para um 'boolean periculosidade' gerado pelo Lombok é 'isPericulosidade()'
        if (funcionario.isPericulosidade()) { 
            return funcionario.getSalarioBruto().multiply(PORCENTAGEM).setScale(2, RoundingMode.HALF_UP);      
        }
        // --- FIM DA CORREÇÃO ---

        return BigDecimal.ZERO;
    }
}