package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.IAdicional;
@Service
public class CalcularInsalubridade implements IAdicional {

    private static final BigDecimal PORCENTAGEM_BAIXA = new BigDecimal("0.10");
    private static final BigDecimal PORCENTAGEM_MEDIA = new BigDecimal("0.20");
    private static final BigDecimal PORCENTAGEM_ALTA = new BigDecimal("0.40");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1380.60");
    

    @Override
    public BigDecimal calcular(Funcionario funcionario){
        switch (funcionario.getGrauInsalubridade()) {
            case BAIXO:
                return SALARIO_MINIMO.multiply(PORCENTAGEM_BAIXA).setScale(2, RoundingMode.HALF_UP);
            case MEDIO:
                return SALARIO_MINIMO.multiply(PORCENTAGEM_MEDIA).setScale(2, RoundingMode.HALF_UP);
            case ALTO:
                return SALARIO_MINIMO.multiply(PORCENTAGEM_ALTA).setScale(2, RoundingMode.HALF_UP);
            default:
                throw new IllegalArgumentException("Grau de insalubridade inválido");
        }
    }
}
