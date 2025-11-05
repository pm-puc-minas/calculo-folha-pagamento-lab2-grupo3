package br.com.gestaopagamento.Service.impl;

import java.math.BigDecimal;
import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.Beneficio;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class CalcularValeTransporteBeneficio implements Beneficio {

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
