package br.com.gestaopagamento.gestaopagamento.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.gestaopagamento.Models.enums.GrauInsalubridade;
import br.com.gestaopagamento.Models.Funcionario;// <-- IMPORTAR O ENUM
import br.com.gestaopagamento.Service.impl.CalcularInsalubridade;

public class CalcularInsalubridadeTest {
    
    private CalcularInsalubridade calculadora;
    
    @BeforeEach
    void setUp() {
        calculadora = new CalcularInsalubridade();
    }

    @Test
    public void deveCalcularValorBaixo() {
        Funcionario funcionarioBaixa = new Funcionario();
        funcionarioBaixa.setNome("Diogo");
        funcionarioBaixa.setCpf("12345678900");
        funcionarioBaixa.setCargo("Dev");
        funcionarioBaixa.setSalarioBruto(new BigDecimal("1518.00"));
        funcionarioBaixa.setPericulosidade(false);
        funcionarioBaixa.setGrauInsalubridade(GrauInsalubridade.fromInt(1));
        funcionarioBaixa.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionarioBaixa.setOutrasDeducoes(new BigDecimal("0.0"));
    
        BigDecimal valoresperado = new BigDecimal("138.06");
        BigDecimal valorCalculado = calculadora.calcular(funcionarioBaixa);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 151.80");
    }       
    
    @Test
    public void deveCalcularValorMedio() {
        Funcionario funcionarioMedio = new Funcionario();
        funcionarioMedio.setNome("Diogo");
        funcionarioMedio.setCpf("12345678900");
        funcionarioMedio.setCargo("Dev");
        funcionarioMedio.setSalarioBruto(new BigDecimal("3000.00"));
        funcionarioMedio.setPericulosidade(false);
        funcionarioMedio.setGrauInsalubridade(GrauInsalubridade.fromInt(2));
        funcionarioMedio.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionarioMedio.setOutrasDeducoes(new BigDecimal("0.0"));
        
        BigDecimal valoresperado = new BigDecimal("276.12");
        BigDecimal valorCalculado = calculadora.calcular(funcionarioMedio);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 276.12");
    }

    @Test
    public void deveCalcularValorAlto() {
        Funcionario funcionarioAlto = new Funcionario();
        funcionarioAlto.setNome("Diogo");
        funcionarioAlto.setCpf("12345678900");
        funcionarioAlto.setCargo("Dev");
        funcionarioAlto.setSalarioBruto(new BigDecimal("1518.00"));
        funcionarioAlto.setPericulosidade(false);
        funcionarioAlto.setGrauInsalubridade(GrauInsalubridade.fromInt(3));
        funcionarioAlto.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionarioAlto.setOutrasDeducoes(new BigDecimal("0.0"));

        BigDecimal valoresperado = new BigDecimal("552.24");
        BigDecimal valorCalculado = calculadora.calcular(funcionarioAlto);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 607.20");
    }

    @Test
    public void deveErrarValorBaixo() {
        Funcionario funcionarioBaixa = new Funcionario();
        funcionarioBaixa.setNome("Diogo");
        funcionarioBaixa.setCpf("12345678900");
        funcionarioBaixa.setCargo("Dev");
        funcionarioBaixa.setSalarioBruto(new BigDecimal("3000.00"));
        funcionarioBaixa.setPericulosidade(false);
        funcionarioBaixa.setGrauInsalubridade(GrauInsalubridade.fromInt(2)); // Grau 2
        funcionarioBaixa.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionarioBaixa.setOutrasDeducoes(new BigDecimal("0.0"));

        BigDecimal valoresperado = new BigDecimal("138.06"); // Esperado para Grau 1
        BigDecimal valorCalculado = calculadora.calcular(funcionarioBaixa);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 138.06");
    }       
    
    @Test
    public void deveErrarValorMedio() {
        Funcionario funcionarioMedio = new Funcionario();
        funcionarioMedio.setNome("Diogo");
        funcionarioMedio.setCpf("12345678900");
        funcionarioMedio.setCargo("Dev");
        funcionarioMedio.setSalarioBruto(new BigDecimal("3000.00"));
        funcionarioMedio.setPericulosidade(false);
        funcionarioMedio.setGrauInsalubridade(GrauInsalubridade.fromInt(3)); // Grau 3
        funcionarioMedio.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionarioMedio.setOutrasDeducoes(new BigDecimal("0.0"));

        BigDecimal valoresperado = new BigDecimal("276.12"); // Esperado para Grau 2
        BigDecimal valorCalculado = calculadora.calcular(funcionarioMedio);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 276.12");
    }

    @Test
    public void deveErrarValorAlto() {
        Funcionario funcionarioAlto = new Funcionario();
        funcionarioAlto.setNome("Diogo");
        funcionarioAlto.setCpf("12345678900");
        funcionarioAlto.setCargo("Dev");
        funcionarioAlto.setSalarioBruto(new BigDecimal("3000.00"));
        funcionarioAlto.setPericulosidade(false);
        funcionarioAlto.setGrauInsalubridade(GrauInsalubridade.fromInt(1)); // Grau 1
        funcionarioAlto.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionarioAlto.setOutrasDeducoes(new BigDecimal("0.0"));

        BigDecimal valoresperado = new BigDecimal("552.24"); // Esperado para Grau 3
        BigDecimal valorCalculado = calculadora.calcular(funcionarioAlto);
        assertEquals(valoresperado, valorCalculado,"o  valor deveria ser 552.24");
    }
}