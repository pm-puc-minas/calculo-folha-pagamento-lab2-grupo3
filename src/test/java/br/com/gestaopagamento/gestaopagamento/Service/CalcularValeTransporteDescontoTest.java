package br.com.gestaopagamento.gestaopagamento.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.impl.CalcularValeTransporteDesconto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalcularValeTransporteDescontoTest {

    private CalcularValeTransporteDesconto calcularValeTransporteDesconto;

    @Test
    public void DeveCalcularDescontoQuandoMenorQueValorVT() {
        Funcionario funcionario = new Funcionario("Ana Beatriz", "12345678900", "Recepcionista", new BigDecimal("2000"), false, 1, 0, 0.0, 0.0);

        calcularValeTransporteDesconto = new CalcularValeTransporteDesconto(new BigDecimal("150.00"));

        BigDecimal valorEsperado = new BigDecimal("120");
        BigDecimal valorAtual = calcularValeTransporteDesconto.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }

    @Test
    public void DeveAplicarTetoQuandoDescontoMaiorQueValorVT() {
        Funcionario funcionario = new Funcionario("Carlos Eduardo", "98765432100", "Garçom", new BigDecimal("4000"), false, 1, 0, 0.0, 0.0);

        calcularValeTransporteDesconto = new CalcularValeTransporteDesconto(new BigDecimal("200.00"));

        BigDecimal valorEsperado = new BigDecimal("200");
        BigDecimal valorAtual = calcularValeTransporteDesconto.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }

    @Test
    public void DeveRetornarZeroQuandoSalarioForZero() {
        Funcionario funcionario = new Funcionario("João Pedro", "11122233344", "Estagiário", new BigDecimal("0.00"), false, 1, 0, 0.0, 0.0);

        calcularValeTransporteDesconto = new CalcularValeTransporteDesconto(new BigDecimal("180.00"));

        BigDecimal valorEsperado = new BigDecimal("0");
        BigDecimal valorAtual = calcularValeTransporteDesconto.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }
}
