package br.com.gestaopagamento.gestaopagamento.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.GrauInsalubridade; // <-- IMPORTAR O ENUM
import br.com.gestaopagamento.Service.impl.CalcularValeTransporteDesconto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigDecimal;
import br.com.gestaopagamento.Models.GrauInsalubridade;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalcularValeTransporteDescontoTest {

    private CalcularValeTransporteDesconto calcularValeTransporteDesconto;

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
    public void DeveCalcularDescontoQuandoMenorQueValorVT() {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("Ana Beatriz", "12345678900", "Recepcionista", "2000.00");
        // --- FIM DA CORREÇÃO ---

        calcularValeTransporteDesconto = new CalcularValeTransporteDesconto(new BigDecimal("150.00"));
        
        // 6% de 2000.00 = 120.00
        BigDecimal valorEsperado = new BigDecimal("120.00"); // Use .00
        BigDecimal valorAtual = calcularValeTransporteDesconto.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }

    @Test
    public void DeveAplicarTetoQuandoDescontoMaiorQueValorVT() {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("Carlos Eduardo", "98765432100", "Garçom", "4000.00");
        // --- FIM DA CORREÇÃO ---

        calcularValeTransporteDesconto = new CalcularValeTransporteDesconto(new BigDecimal("200.00"));

        // 6% de 4000.00 = 240.00.
        // Como 240.00 > 200.00 (teto do benefício), o desconto deve ser 200.00
        BigDecimal valorEsperado = new BigDecimal("200.00"); // Use .00
        BigDecimal valorAtual = calcularValeTransporteDesconto.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }

    @Test
    public void DeveRetornarZeroQuandoSalarioForZero() {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("João Pedro", "11122233344", "Estagiário", "0.00");
        // --- FIM DA CORREÇÃO ---

        calcularValeTransporteDesconto = new CalcularValeTransporteDesconto(new BigDecimal("180.00"));

        BigDecimal valorEsperado = new BigDecimal("0.00"); // Use .00
        BigDecimal valorAtual = calcularValeTransporteDesconto.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }
}