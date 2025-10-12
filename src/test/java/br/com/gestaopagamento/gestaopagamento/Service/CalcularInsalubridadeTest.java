package br.com.gestaopagamento.gestaopagamento.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.impl.CalcularInsalubridade;

public class CalcularInsalubridadeTest {
    
        private CalcularInsalubridade calculadora;
        
@BeforeEach
        void setUp() {
        calculadora = new CalcularInsalubridade();
        }

@Test
        public void deveCalcularValorBaixo() {
        Funcionario funcionarioBaixa = new Funcionario("Diogo", "12345678900", "Dev", new BigDecimal("1518.00"), false, 1, 0, 0.0, 0.0);
        BigDecimal valoresperado = new BigDecimal("138.06");
        BigDecimal valorCalculado = calculadora.calcular(funcionarioBaixa);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 151.80");
        }     
        
@Test
        public void deveCalcularValorMedio() {
        Funcionario funcionarioMedio = new Funcionario("Diogo", "12345678900", "Dev", new BigDecimal("3000.00"), false, 2, 0, 0.0, 0.0);
        BigDecimal valoresperado = new BigDecimal("276.12");
        BigDecimal valorCalculado = calculadora.calcular(funcionarioMedio);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 276.12");
        }

@Test
        public void deveCalcularValorAlto() {
        Funcionario funcionarioAlto = new Funcionario("Diogo", "12345678900", "Dev", new BigDecimal("1518.00"), false, 3, 0, 0.0, 0.0);
        BigDecimal valoresperado = new BigDecimal("552.24");
        BigDecimal valorCalculado = calculadora.calcular(funcionarioAlto);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 607.20");
        }
        @Test
        public void deveErrarValorBaixo() {
        Funcionario funcionarioBaixa = new Funcionario("Diogo", "12345678900", "Dev", new BigDecimal("3000.00"), false, 2, 0, 0.0, 0.0);
        BigDecimal valoresperado = new BigDecimal("138.06");
        BigDecimal valorCalculado = calculadora.calcular(funcionarioBaixa);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 138.06");
        }     
        
@Test
        public void deveErrarValorMedio() {
        Funcionario funcionarioMedio = new Funcionario("Diogo", "12345678900", "Dev", new BigDecimal("3000.00"), false, 3, 0, 0.0, 0.0);
        BigDecimal valoresperado = new BigDecimal("276.12");
        BigDecimal valorCalculado = calculadora.calcular(funcionarioMedio);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 276.12");
        }

@Test
        public void deveErrarValorAlto() {
        Funcionario funcionarioAlto = new Funcionario("Diogo", "12345678900", "Dev", new BigDecimal("3000.00"), false, 1, 0, 0.0, 0.0);
        BigDecimal valoresperado = new BigDecimal("552.24");
        BigDecimal valorCalculado = calculadora.calcular(funcionarioAlto);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 552.24");
        }

}
