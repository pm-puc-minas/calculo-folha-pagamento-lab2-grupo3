package br.com.gestaopagamento.gestaopagamento.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.impl.CalcularValeAlimentacao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

@SpringBootTest
public class CalcularValeAlimentacaoTest {

    private CalcularValeAlimentacao calcularValeAlimentacao;

    @Test
    public void DeveCalcularVAComMaisDiasTrabalhados()
    {
        Funcionario funcionario = new Funcionario("Carlos Eduardo","98392486378","Garçom",new BigDecimal("1643"),false,1,0,0.0,0.0);
        BigDecimal valorEsperado = new BigDecimal("624.00");

        calcularValeAlimentacao = new CalcularValeAlimentacao(new BigDecimal("24"),26);
        BigDecimal valorAtual = calcularValeAlimentacao.calcular(funcionario);

        assertEquals(valorEsperado,valorAtual);
    }
    @Test
    public void DeveCalcularVAComMenosDiasTrabalhados()
    {
        Funcionario funcionario = new Funcionario("Mariana Silva", "98765432100", "Atendente",new BigDecimal("2000"), false, 1, 0, 0.0, 0.0);

        calcularValeAlimentacao = new CalcularValeAlimentacao(new BigDecimal("25"), 20);

        BigDecimal valorEsperado = new BigDecimal("500.00");
        BigDecimal valorAtual = calcularValeAlimentacao.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }
    @Test
    public void DeveRetornarZeroQuandoFuncionarioNaoTrabalhou()
    {
        Funcionario funcionario = new Funcionario("João Pereira", "11223344556", "Cozinheiro",new BigDecimal("1800"), false, 1, 0, 0.0, 0.0);

        calcularValeAlimentacao = new CalcularValeAlimentacao(new BigDecimal("22"), 0);

        BigDecimal valorEsperado = new BigDecimal("0.00");
        BigDecimal valorAtual = calcularValeAlimentacao.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }
}
