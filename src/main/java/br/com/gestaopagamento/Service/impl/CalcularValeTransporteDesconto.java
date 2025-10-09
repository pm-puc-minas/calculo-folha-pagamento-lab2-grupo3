package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Desconto;
import lombok.Getter;

public class CalcularValeTransporteDesconto implements Desconto {
    private final BigDecimal PORCENTAGEM_MAX = new BigDecimal("0.06");

    private BigDecimal valorVT;

    public CalcularValeTransporteDesconto(BigDecimal valorVT) {
        this.valorVT = valorVT;
    }

    @Override
    public BigDecimal calcular(Funcionario funcionario) {
        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal descontoCalculado = salarioBruto.multiply(PORCENTAGEM_MAX);

        if (descontoCalculado.compareTo(valorVT) > 0) {
            return valorVT;
        }

        return descontoCalculado;
    }
}
