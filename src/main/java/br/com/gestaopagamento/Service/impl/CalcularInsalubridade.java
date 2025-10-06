package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Adicional;
@Service
public class CalcularInsalubridade implements Adicional {

    private static final BigDecimal PORCENTAGEM_BAIXA = new BigDecimal("0.10");
    private static final BigDecimal PORCENTAGEM_MEDIA = new BigDecimal("0.20");
    private static final BigDecimal PORCENTAGEM_ALTA = new BigDecimal("0.40");

    @Override
    public BigDecimal calcular(Funcionario funcionario){
        if (funcionario.getGrauInsalubridade().getGrau()==1) {
       
            return funcionario.getSalarioBruto().multiply(PORCENTAGEM_BAIXA);
       
        }else if (funcionario.getGrauInsalubridade().getGrau()==2) {
           
            return funcionario.getSalarioBruto().multiply(PORCENTAGEM_MEDIA);
        
        }else{
        
            return funcionario.getSalarioBruto().multiply(PORCENTAGEM_ALTA);
        }
    }
}
