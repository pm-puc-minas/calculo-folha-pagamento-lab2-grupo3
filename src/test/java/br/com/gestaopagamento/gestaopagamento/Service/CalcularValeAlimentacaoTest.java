package br.com.gestaopagamento.gestaopagamento.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.enums.GrauInsalubridade; // <-- IMPORTAR O ENUM
import br.com.gestaopagamento.Service.impl.CalcularValeAlimentacao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

@SpringBootTest
public class CalcularValeAlimentacaoTest {

    private CalcularValeAlimentacao calcularValeAlimentacao;

    // Função auxiliar para criar o funcionário
    private Funcionario criarFuncionario(String nome, String cpf, String cargo, String salario) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setCargo(cargo);
        funcionario.setSalarioBruto(new BigDecimal(salario));
        funcionario.setPericulosidade(false);
        funcionario.setGrauInsalubridade(GrauInsalubridade.fromInt(1));
        funcionario.setPensaoAlimenticia(new BigDecimal("0.0"));
        funcionario.setOutrasDeducoes(new BigDecimal("0.0"));
        return funcionario;
    }

    @Test
    public void DeveCalcularVAComMaisDiasTrabalhados()
    {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("Carlos Eduardo","98392486378","Garçom", "1643.00");
        // --- FIM DA CORREÇÃO ---
        
        BigDecimal valorEsperado = new BigDecimal("624.00"); // Use .00 para escala

        calcularValeAlimentacao = new CalcularValeAlimentacao(new BigDecimal("24.00"),26);
        BigDecimal valorAtual = calcularValeAlimentacao.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }
    @Test
    public void DeveCalcularVAComMenosDiasTrabalhados()
    {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("Mariana Silva", "98765432100", "Atendente", "2000.00");
        // --- FIM DA CORREÇÃO ---

        calcularValeAlimentacao = new CalcularValeAlimentacao(new BigDecimal("25.00"), 20);

        BigDecimal valorEsperado = new BigDecimal("500.00"); // Use .00 para escala
        BigDecimal valorAtual = calcularValeAlimentacao.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }
    @Test
    public void DeveRetornarZeroQuandoFuncionarioNaoTrabalhou()
    {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("João Pereira", "11223344556", "Cozinheiro", "1800.00");
        // --- FIM DA CORREÇÃO ---

        calcularValeAlimentacao = new CalcularValeAlimentacao(new BigDecimal("22.00"), 0);

        BigDecimal valorEsperado = new BigDecimal("0.00"); // Use .00 para escala
        BigDecimal valorAtual = calcularValeAlimentacao.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }
}