package br.com.gestaopagamento.gestaopagamento.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.gestaopagamento.Models.enums.GrauInsalubridade;
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
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Diogo");
        funcionario.setCpf("12345678900");
        funcionario.setCargo("Dev");
        funcionario.setSalarioBruto(new BigDecimal("4000.00"));
        funcionario.setPericulosidade(true); // <- Marcado como true
        funcionario.setGrauInsalubridade(GrauInsalubridade.fromInt(1));
        funcionario.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionario.setOutrasDeducoes(new BigDecimal("0.0"));
        // --- FIM DA CORREÇÃO ---
        
        // O cálculo de 30% de 4000.00 é 1200.00. 
        // O seu código original estava "new BigDecimal("1200")"
        // Vamos usar "1200.00" para ser mais preciso com escala monetária.
        BigDecimal valorEsperado = new BigDecimal("1200.00"); 
        BigDecimal valorCalculado = calculadora.calcular(funcionario);
        assertEquals(valorEsperado, valorCalculado, "O valor deveria ser 1200.00");
    }

    @Test
    public void deveRetornarZeroQuandoNaoTemPericulosidade() {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Diogo");
        funcionario.setCpf("12345678900");
        funcionario.setCargo("Dev");
        funcionario.setSalarioBruto(new BigDecimal("3000.00"));
        funcionario.setPericulosidade(false); // <- Marcado como false
        funcionario.setGrauInsalubridade(GrauInsalubridade.fromInt(1));
        funcionario.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionario.setOutrasDeducoes(new BigDecimal("0.0"));
        // --- FIM DA CORREÇÃO ---
        
        BigDecimal valorEsperado = BigDecimal.ZERO;
        BigDecimal valorCalculado = calculadora.calcular(funcionario);
        assertEquals(valorEsperado, valorCalculado, "O valor deveria ser 0.00");
    }
}