package br.com.gestaopagamento.Models;

import java.math.BigDecimal;

public class ValeAlimentacao {
    private BigDecimal valorDiario;
    private int diasTrabalhados;
    private BigDecimal valorTotal;
    private final BigDecimal porcentagemDesconto = new BigDecimal("0.2");

    public ValeAlimentacao(BigDecimal valorDiario, int diasTrabalhados) {
        this.valorDiario = valorDiario;
        this.diasTrabalhados = diasTrabalhados;
    }
}
