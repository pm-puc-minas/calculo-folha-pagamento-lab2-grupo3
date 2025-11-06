package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.IBeneficio;
import lombok.Getter;

public class CalcularValeTransporteBeneficio implements IBeneficio {

    @Getter
    private BigDecimal valorVT;

    public CalcularValeTransporteBeneficio(BigDecimal valorVT) {
        this.valorVT = valorVT;
    }

    @Override
    public BigDecimal calcular(Funcionario funcionario) {
        return valorVT;
    }
}
