package br.com.gestaopagamento.gestaopagamento.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.impl.CalcularValeTransporteBeneficio;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalcularValeTransporteBeneficioTest {

    private CalcularValeTransporteBeneficio calcularValeTransporteBeneficio;

    @Test
    public void DeveRetornarValorDoValeTransporte() {
        Funcionario funcionario = new Funcionario("Mariana Souza", "12345678900", "Auxiliar", new BigDecimal("1800.00"), false, 1, 0, 0.0, 0.0);

        calcularValeTransporteBeneficio = new CalcularValeTransporteBeneficio(new BigDecimal("200.00"));

        BigDecimal valorEsperado = new BigDecimal("200.00");
        BigDecimal valorAtual = calcularValeTransporteBeneficio.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }

    @Test
    public void DeveRetornarZeroQuandoValorDoValeForZero() {
        Funcionario funcionario = new Funcionario("Carlos Eduardo", "98765432100", "Garçom", new BigDecimal("2500.00"), false, 1, 0, 0.0, 0.0);

        calcularValeTransporteBeneficio = new CalcularValeTransporteBeneficio(BigDecimal.ZERO);

        BigDecimal valorEsperado = BigDecimal.ZERO;
        BigDecimal valorAtual = calcularValeTransporteBeneficio.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }
}
