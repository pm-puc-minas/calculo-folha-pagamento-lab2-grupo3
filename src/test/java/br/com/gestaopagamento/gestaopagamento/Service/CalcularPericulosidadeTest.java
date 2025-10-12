package br.com.gestaopagamento.gestaopagamento.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.impl.CalcularPericulosidade;

public class CalcularPericulosidadeTest {
    private CalcularPericulosidade calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalcularPericulosidade();
    }

    @Test
    public void deveCalcularPericulosidadeCorretamente() {
        Funcionario funcionario = new Funcionario("Diogo", "12345678900", "Dev", new BigDecimal("4000.00"), true, 1, 0, 0.0, 0.0);
        BigDecimal valorEsperado = new BigDecimal("1200.00");
        BigDecimal valorCalculado = calculadora.calcular(funcionario);
        assertEquals(valorEsperado, valorCalculado, "O valor deveria ser 1200.00");
    }

    @Test
    public void deveRetornarZeroQuandoNaoTemPericulosidade() {
        Funcionario funcionario = new Funcionario("Diogo", "12345678900", "Dev", new BigDecimal("3000.00"), false, 1, 0, 0.0, 0.0);
        BigDecimal valorEsperado = BigDecimal.ZERO;
        BigDecimal valorCalculado = calculadora.calcular(funcionario);
        assertEquals(valorEsperado, valorCalculado, "O valor deveria ser 0.00");
    }
}
