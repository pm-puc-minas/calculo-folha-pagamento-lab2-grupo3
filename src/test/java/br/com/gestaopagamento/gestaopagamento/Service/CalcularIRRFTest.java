package br.com.gestaopagamento.gestaopagamento.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import br.com.gestaopagamento.Models.GrauInsalubridade;
import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.GrauInsalubridade; // <-- IMPORTAR O ENUM
import br.com.gestaopagamento.Service.impl.CalcularIRRF;

public class CalcularIRRFTest {

    private CalcularIRRF calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalcularIRRF();
    }

    // Função auxiliar para criar funcionário (evita repetição)
    private Funcionario criarFuncionario(String nome, String cpf, BigDecimal salario, int grau) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setCargo("Dev");
        funcionario.setSalarioBruto(salario);
        funcionario.setPericulosidade(false);
        funcionario.setGrauInsalubridade(GrauInsalubridade.fromInt(grau));
        funcionario.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionario.setOutrasDeducoes(new BigDecimal("0.0"));
        return funcionario;
    }


    @Test
    public void deveCalcularIRRFFaixaIsenta() {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("Joao", "12345678900", new BigDecimal("1900.00"), 1);
        // --- FIM DA CORREÇÃO ---

        BigDecimal esperado = BigDecimal.ZERO; // Faixa isenta
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do IRRF para faixa isenta deveria ser 0");
    }

    @Test
    public void deveCalcularIRRFFaixa1() {
        Funcionario funcionario = criarFuncionario("Maria", "12345678901", new BigDecimal("2500.00"), 1);
        BigDecimal esperado = new BigDecimal("67.80"); //(2500 - deducao) * aliquota - parcela deduzir
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do IRRF para faixa 1 deveria ser 67.80");
    }

    @Test
    public void deveCalcularIRRFFaixa2() {
        Funcionario funcionario = criarFuncionario("Carlos", "12345678902", new BigDecimal("3500.00"), 1);
        BigDecimal esperado = new BigDecimal("192"); //(3500 - deducao) * aliquota - parcela deduzir
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do IRRF para faixa 2 deveria ser 192.00");
    }

    @Test
    public void deveCalcularIRRFFaixa3() {
        Funcionario funcionario = criarFuncionario("Ana", "12345678903", new BigDecimal("5000.00"), 1);
        BigDecimal esperado = new BigDecimal("505.64"); //(5000 - deducao) * aliquota - parcela deduzir
        BigDecimal calculado = calculadora.calcular(funcionario);
        assertEquals(esperado, calculado, "O valor do IRRF para faixa 3 deveria ser 505.64");
    }

    @Test
    public void deveCalcularIRRFFaixa4() {
        Funcionario funcionario = criarFuncionario("Pedro", "12345678904", new BigDecimal("8000.00"), 1);
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
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("Lucas", "12345678905", null, 1);
        // --- FIM DA CORREÇÃO ---

        assertThrows(IllegalArgumentException.class, () -> {
            calculadora.calcular(funcionario);
        });
    }
}