package br.com.gestaopagamento.Service;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.FolhaPagamento;
import br.com.gestaopagamento.Service.impl.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FolhaPagamentoService {
    private final Map<String, FolhaPagamento> folhaPagamentoDB = new HashMap<>();

    public FolhaPagamento calcularFolha(Funcionario funcionario, int mes, BigDecimal horasTrabalhadas) {
        // Adicionais
        IAdicional adicionalInsalubridade = new CalcularInsalubridade();
        IAdicional adicionalPericulosidade = new CalcularPericulosidade();
        BigDecimal valorInsalubridade = adicionalInsalubridade.calcular(funcionario);
        BigDecimal valorPericulosidade = adicionalPericulosidade.calcular(funcionario);

        // Benefícios
        IBeneficio valeAlimentacao = new CalcularValeAlimentacao(new BigDecimal("30.00"), horasTrabalhadas.intValue());
        IBeneficio valeTransporte = new CalcularValeTransporteBeneficio(new BigDecimal("200.00"));
        BigDecimal valorValeAlimentacao = valeAlimentacao.calcular(funcionario);
        BigDecimal valorValeTransporte = valeTransporte.calcular(funcionario);

        // Descontos
        IDesconto descontoINSS = new CalcularINSS();
        IDesconto descontoIRRF = new CalcularIRRF();
        IDesconto descontoValeTransporte = new CalcularValeTransporteDesconto(valorValeTransporte);
        BigDecimal valorINSS = descontoINSS.calcular(funcionario);
        BigDecimal valorIRRF = descontoIRRF.calcular(funcionario);
        BigDecimal valorDescontoVT = descontoValeTransporte.calcular(funcionario);

        // Salário bruto
        BigDecimal salarioBruto = funcionario.getSalarioBruto();

        // Salário líquido
        BigDecimal salarioLiquido = salarioBruto
            .add(valorInsalubridade)
            .add(valorPericulosidade)
            .add(valorValeAlimentacao)
            .add(valorValeTransporte)
            .subtract(valorINSS)
            .subtract(valorIRRF)
            .subtract(valorDescontoVT)
            .subtract(BigDecimal.valueOf(funcionario.getPensaoAlimenticia()))
            .subtract(BigDecimal.valueOf(funcionario.getOutrasDeducoes()));

        salarioLiquido = salarioLiquido.setScale(2, RoundingMode.HALF_UP);

        FolhaPagamento folhaPagamento = new FolhaPagamento(
            funcionario,
            mes,
            horasTrabalhadas,
            salarioLiquido,
            valorValeAlimentacao,
            valorValeTransporte
        );

        folhaPagamentoDB.put(funcionario+ "-" + mes, folhaPagamento);
        return folhaPagamento;
    }

    public List<FolhaPagamento> listarTodos(){
        return new ArrayList<>(folhaPagamentoDB.values());
    }

}