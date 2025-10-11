package br.com.gestaopagamento.gestaopagamento.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.impl.CalcularIRRF;

public class CalcularIRRFTest {

    private CalcularIRRF calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalcularIRRF();
    }

    @Test
    public void deveCalcularIRRFFaixaIsenta() {
        Funcionario funcionario = new Funcionario("Joao", "12345678900", "Dev", new BigDecimal("1900.00"), false, 1);
        BigDecimal esperado = BigDecimal.ZERO; // Faixa isenta
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do IRRF para faixa isenta deveria ser 0");
    }

    @Test
    public void deveCalcularIRRFFaixa1() {
        Funcionario funcionario = new Funcionario("Maria", "12345678901", "Dev", new BigDecimal("2500.00"), false, 1);
        BigDecimal esperado = new BigDecimal("67.80"); //(2500 - deducao) * aliquota - parcela deduzir
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do IRRF para faixa 1 deveria ser 67.80");
    }

    @Test
    public void deveCalcularIRRFFaixa2() {
        Funcionario funcionario = new Funcionario("Carlos", "12345678902", "Dev", new BigDecimal("3500.00"), false, 1);
        BigDecimal esperado = new BigDecimal("192.00"); //(3500 - deducao) * aliquota - parcela deduzir
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do IRRF para faixa 2 deveria ser 192.00");
    }

    @Test
    public void deveCalcularIRRFFaixa3() {
        Funcionario funcionario = new Funcionario("Ana", "12345678903", "Dev", new BigDecimal("5000.00"), false, 1);
        BigDecimal esperado = new BigDecimal("505.64"); //(5000 - deducao) * aliquota - parcela deduzir
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do IRRF para faixa 3 deveria ser 505.64");
    }

    @Test
    public void deveCalcularIRRFFaixa4() {
        Funcionario funcionario = new Funcionario("Pedro", "12345678904", "Dev", new BigDecimal("8000.00"), false, 1);
        BigDecimal esperado = new BigDecimal("1426.64"); //(8000 - deducao) * aliquota - parcela deduzir
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do IRRF para faixa 4 deveria ser 1426.64");
    }

    @Test
    public void deveLancarExcecaoFuncionarioNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculadora.calcular(null);
        });
    }

    @Test
    public void deveLancarExcecaoSalarioNulo() {
        Funcionario funcionario = new Funcionario("Lucas", "12345678905", "Dev", null, false, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            calculadora.calcular(funcionario);
        });
    }
}