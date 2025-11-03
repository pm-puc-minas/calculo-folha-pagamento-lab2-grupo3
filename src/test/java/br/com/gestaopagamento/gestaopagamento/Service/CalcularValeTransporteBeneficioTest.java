package br.com.gestaopagamento.gestaopagamento.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.GrauInsalubridade; // <-- IMPORTAR O ENUM
import br.com.gestaopagamento.Service.impl.CalcularValeTransporteBeneficio;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigDecimal;
import br.com.gestaopagamento.Models.GrauInsalubridade;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalcularValeTransporteBeneficioTest {

    private CalcularValeTransporteBeneficio calcularValeTransporteBeneficio;
    
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
    public void DeveRetornarValorDoValeTransporte() {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("Mariana Souza", "12345678900", "Auxiliar", "1800.00");
        // --- FIM DA CORREÇÃO ---

        calcularValeTransporteBeneficio = new CalcularValeTransporteBeneficio(new BigDecimal("200.00"));

        BigDecimal valorEsperado = new BigDecimal("200.00"); // Use .00
        BigDecimal valorAtual = calcularValeTransporteBeneficio.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }

    @Test
    public void DeveRetornarZeroQuandoValorDoValeForZero() {
        // --- CORREÇÃO AQUI ---
        Funcionario funcionario = criarFuncionario("Carlos Eduardo", "98765432100", "Garçom", "2500.00");
        // --- FIM DA CORREÇÃO ---

        calcularValeTransporteBeneficio = new CalcularValeTransporteBeneficio(BigDecimal.ZERO);

        BigDecimal valorEsperado = BigDecimal.ZERO;
        BigDecimal valorAtual = calcularValeTransporteBeneficio.calcular(funcionario);

        assertEquals(valorEsperado, valorAtual);
    }
}