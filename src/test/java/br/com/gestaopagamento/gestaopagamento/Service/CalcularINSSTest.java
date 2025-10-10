package br.com.gestaopagamento.gestaopagamento.Service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.impl.CalcularINSS;

public class CalcularINSSTest {

    private CalcularINSS calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalcularINSS();
    }

    @Test
    public void deveCalcularINSSFaixa1() {
        Funcionario funcionario = new Funcionario("Joao", "12345678900", "Dev", new BigDecimal("1302.00"), false, 1);
        BigDecimal esperado = new BigDecimal("97.65"); // 1302.00 * 0.075
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do INSS para faixa 1 deveria ser 97.65");
    }

    @Test
    public void deveCalcularINSSFaixa2() {
        Funcionario funcionario = new Funcionario("Maria", "12345678901", "Dev", new BigDecimal("2000.00"), false, 1);
        BigDecimal esperado = new BigDecimal("151.38"); // 1302*0.075 + (2000-1302)*0.09 = 97.65 + 62.73 = 160.38
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do INSS para faixa 2 deveria ser 151.38");
    }

    @Test
    public void deveCalcularINSSFaixa3() {
        Funcionario funcionario = new Funcionario("Carlos", "12345678902", "Dev", new BigDecimal("3000.00"), false, 1);
        BigDecimal esperado = new BigDecimal("277.39"); // 1302*0.075 + (2571.29-1302)*0.09 + (3000-2571.29)*0.12
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do INSS para faixa 3 deveria ser 277.39");
    }

    @Test
    public void deveCalcularINSSFaixa4() {
        Funcionario funcionario = new Funcionario("Ana", "12345678903", "Dev", new BigDecimal("5000.00"), false, 1);
        BigDecimal esperado = new BigDecimal("551.29"); // 1302*0.075 + (2571.29-1302)*0.09 + (3856.94-2571.29)*0.12 + (5000-3856.94)*0.14
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do INSS para faixa 4 deveria ser 551.29");
    }

    @Test
    public void deveCalcularINSSAcimaDoTeto() {
        Funcionario funcionario = new Funcionario("Pedro", "12345678904", "Dev", new BigDecimal("8000.00"), false, 1);
        BigDecimal esperado = new BigDecimal("877.24"); // Valor máximo do INSS (teto)
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do INSS acima do teto deveria ser 877.24");
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