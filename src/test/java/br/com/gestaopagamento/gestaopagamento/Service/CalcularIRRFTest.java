package br.com.gestaopagamento.gestaopagamento.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode; 
// Removi o import de ArrayList pois não é mais necessário

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.enums.GrauInsalubridade;
import br.com.gestaopagamento.Service.impl.CalcularINSS; 
import br.com.gestaopagamento.Service.impl.CalcularIRRF;

public class CalcularIRRFTest {

    private CalcularIRRF calculadora;
    private CalcularINSS calculadoraINSS;

    @BeforeEach
    void setUp() {
        calculadoraINSS = new CalcularINSS();
        calculadora = new CalcularIRRF(calculadoraINSS);
    }

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
        
        // --- MUDANÇA AQUI ---
        // Antes: funcionario.setDependentes(new ArrayList<>());
        // Agora: Passamos o número 0 (inteiro)
        funcionario.setDependentes(0); 
        
        return funcionario;
    }

    private BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    @Test
    public void deveCalcularIRRFFaixaIsenta() {
        Funcionario funcionario = criarFuncionario("Joao", "12345678900", new BigDecimal("1900.00"), 1);

        BigDecimal esperado = new BigDecimal("0.00"); 
        BigDecimal calculado = calculadora.calcular(funcionario);
        
        assertEquals(esperado, round(calculado), "O valor do IRRF para faixa isenta deveria ser 0.00");
    }

    @Test
    public void deveCalcularIRRFFaixa1() {
        Funcionario funcionario = criarFuncionario("Maria", "12345678901", new BigDecimal("2500.00"), 1);
        
        BigDecimal esperado = new BigDecimal("29.29");
        BigDecimal calculado = calculadora.calcular(funcionario);
        
        assertEquals(esperado, round(calculado), "O valor do IRRF para faixa 1 estava incorreto.");
    }

    @Test
    public void deveCalcularIRRFFaixa2() {
        Funcionario funcionario = criarFuncionario("Carlos", "12345678902", new BigDecimal("3500.00"), 1);
        
        BigDecimal esperado = new BigDecimal("121.70");
        BigDecimal calculado = calculadora.calcular(funcionario);
        
        assertEquals(esperado, round(calculado), "O valor do IRRF para faixa 2 estava incorreto.");
    }

    @Test
    public void deveCalcularIRRFFaixa3() {
        Funcionario funcionario = criarFuncionario("Ana", "12345678903", new BigDecimal("5000.00"), 1);
        
        BigDecimal esperado = new BigDecimal("370.48");
        BigDecimal calculado = calculadora.calcular(funcionario);
        
        assertEquals(esperado, round(calculado), "O valor do IRRF para faixa 3 estava incorreto.");
    }

    @Test
    public void deveCalcularIRRFFaixa4() {
        Funcionario funcionario = criarFuncionario("Pedro", "12345678904", new BigDecimal("8000.00"), 1);
        
        BigDecimal esperado = new BigDecimal("1089.40");
        BigDecimal calculado = calculadora.calcular(funcionario);
        
        assertEquals(esperado, round(calculado), "O valor do IRRF para faixa 4 estava incorreto.");
    }

    @Test
    public void deveLancarExcecaoFuncionarioNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculadora.calcular(null);
        });
    }

    @Test
    public void deveLancarExcecaoSalarioNulo() {
        Funcionario funcionario = criarFuncionario("Lucas", "12345678905", null, 1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            calculadora.calcular(funcionario);
        });
    }
}