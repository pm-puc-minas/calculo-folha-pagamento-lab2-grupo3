// O 'package' deve ser o seu, que está correto
package br.com.gestaopagamento.gestaopagamento.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.GrauInsalubridade;
import br.com.gestaopagamento.Service.impl.CalcularINSS;

public class CalcularINSSTest {

    private CalcularINSS calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalcularINSS();
    }

    // Função auxiliar para criar funcionário (evita repetição)
    private Funcionario criarFuncionario(String nome, String cpf, BigDecimal salario, int grau) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setCargo("Dev");
        funcionario.setSalarioBruto(salario);
        funcionario.setPericulosidade(false);
        
        // Esta linha precisa do import 'GrauInsalubridade'
        funcionario.setGrauInsalubridade(GrauInsalubridade.fromInt(grau)); 
        
        // Estas linhas precisam do import 'BigDecimal'
        funcionario.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionario.setOutrasDeducoes(new BigDecimal("0.0"));
        return funcionario;
    }

    @Test
    public void deveCalcularINSSFaixa1() {
        Funcionario funcionario = criarFuncionario("Joao", "12345678900", new BigDecimal("1302.00"), 1);
        
        BigDecimal esperado = new BigDecimal("97.65"); 
        BigDecimal calculado = calculadora.calcular(funcionario).setScale(2, RoundingMode.HALF_UP);
        assertEquals(esperado, calculado, "O valor do INSS para faixa 1 deveria ser 97.65");
    }

    @Test
    public void deveCalcularINSSFaixa2() {
        Funcionario funcionario = criarFuncionario("Maria", "12345678901", new BigDecimal("2000.00"), 1);
        BigDecimal esperado = new BigDecimal("151.38"); 
        BigDecimal calculado = calculadora.calcular(funcionario).setScale(2, RoundingMode.HALF_UP);
        assertEquals(esperado, calculado, "O valor do INSS para faixa 2 deveria ser 151.38");
    }

    @Test
    public void deveCalcularINSSFaixa3() {
        Funcionario funcionario = criarFuncionario("Carlos", "12345678902", new BigDecimal("3000.00"), 1);
        BigDecimal esperado = new BigDecimal("277.39"); 
        BigDecimal calculado = calculadora.calcular(funcionario).setScale(2, RoundingMode.HALF_UP);
        assertEquals(esperado, calculado, "O valor do INSS para faixa 3 deveria ser 277.39");
    }

    @Test
    public void deveCalcularINSSFaixa4() {
        Funcionario funcionario = criarFuncionario("Ana", "12345678903", new BigDecimal("5000.00"), 1);
        BigDecimal esperado = new BigDecimal("551.29"); 
        BigDecimal calculado = calculadora.calcular(funcionario).setScale(2, RoundingMode.HALF_UP);
        assertEquals(esperado, calculado, "O valor do INSS para faixa 4 deveria ser 551.29");
    }

    @Test
    public void deveCalcularINSSAcimaDoTeto() {
        Funcionario funcionario = criarFuncionario("Pedro", "12345678904", new BigDecimal("8000.00"), 1);
        BigDecimal esperado = new BigDecimal("877.24"); 
        BigDecimal calculado = calculadora.calcular(funcionario).setScale(2, RoundingMode.HALF_UP);
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
        Funcionario funcionario = criarFuncionario("Lucas", "12345678905", null, 1); // Salário nulo
        assertThrows(IllegalArgumentException.class, () -> {
            calculadora.calcular(funcionario);
        });
    }
}