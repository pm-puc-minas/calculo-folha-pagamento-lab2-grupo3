package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Beneficio;
import org.springframework.stereotype.Service;

@Service
public class CalcularValeAlimentacao implements Beneficio {
    private BigDecimal valorDiario;
    private int diasTrabalhados;
    private BigDecimal valorTotal;

    public CalcularValeAlimentacao(BigDecimal valorDiario, int diasTrabalhados) {
        this.valorDiario = valorDiario;
        this.diasTrabalhados = diasTrabalhados;
    }
     @Override
    public BigDecimal calcular(Funcionario funcionario){
        valorTotal = valorDiario.multiply(BigDecimal.valueOf(diasTrabalhados));
        return valorTotal;
    }
}
