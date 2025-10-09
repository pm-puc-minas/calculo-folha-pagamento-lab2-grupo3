package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Adicional;
@Service
public class CalcularInsalubridade implements Adicional {

    private static final BigDecimal PORCENTAGEM_BAIXA = new BigDecimal("0.10");
    private static final BigDecimal PORCENTAGEM_MEDIA = new BigDecimal("0.20");
    private static final BigDecimal PORCENTAGEM_ALTA = new BigDecimal("0.40");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1380.60");
    

    @Override
    public BigDecimal calcular(Funcionario funcionario){
        if (funcionario.getGrauInsalubridade().getGrau()==1) {
       
            return SALARIO_MINIMO.multiply(PORCENTAGEM_BAIXA).setScale(2, RoundingMode.HALF_UP);
       
        }else if (funcionario.getGrauInsalubridade().getGrau()==2) {
           
            return SALARIO_MINIMO.multiply(PORCENTAGEM_MEDIA).setScale(2, RoundingMode.HALF_UP);
        
        }else{
        
            return SALARIO_MINIMO.multiply(PORCENTAGEM_ALTA).setScale(2, RoundingMode.HALF_UP);
        }
    }
}
