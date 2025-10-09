package br.com.gestaopagamento.Service;
import br.com.gestaopagamento.Models.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FolhaPagamentoService {

    private static final BigDecimal CARGA_HORARIA_MENSAL = new BigDecimal("220");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1412.00");
    private static final BigDecimal PERCENTUAL_PERICULOSIDADE = new BigDecimal("0.30");
    private static final BigDecimal DESCONTO_VALE_TRANSPORTE = new BigDecimal("0.06");
    private static final BigDecimal VALE_ALIMENTACAO_FIXO = new BigDecimal("300.00");

    public FolhaPagamento gerarFolha(Funcionario funcionario, int mes, BigDecimal horasTrabalhadas) {
        BigDecimal salarioBase = funcionario.getSalarioBruto();

        BigDecimal salarioProporcional = salarioBase
                .divide(CARGA_HORARIA_MENSAL, 10, RoundingMode.HALF_UP)
                .multiply(horasTrabalhadas);

        BigDecimal adicionalInsalubridade = calcularInsalubridade(funcionario.getGrauInsalubridade());

        BigDecimal adicionalPericulosidade = BigDecimal.ZERO;
        if (funcionario.isPericulosidade()) {
            adicionalPericulosidade = salarioBase.multiply(PERCENTUAL_PERICULOSIDADE);
        } else {
            adicionalPericulosidade = BigDecimal.ZERO;
        }

        BigDecimal salarioBrutoComAdicionais = salarioProporcional
                .add(adicionalInsalubridade)
                .add(adicionalPericulosidade);

        BigDecimal descontoIRRF = calcularIRRF(salarioBrutoComAdicionais);

        BigDecimal descontoValeTransporte = salarioBase.multiply(DESCONTO_VALE_TRANSPORTE);

        BigDecimal beneficioValeAlimentacao = VALE_ALIMENTACAO_FIXO;

        BigDecimal salarioLiquido = salarioBrutoComAdicionais
                .subtract(descontoIRRF)
                .subtract(descontoValeTransporte)
                .add(beneficioValeAlimentacao)
                .setScale(2, RoundingMode.HALF_UP);

        return new FolhaPagamento(funcionario, mes, horasTrabalhadas, salarioLiquido);
    }

    private BigDecimal calcularInsalubridade(GrauInsalubridade grau) {
        BigDecimal adicional = BigDecimal.ZERO;

        if (grau == GrauInsalubridade.MINIMO) {
            adicional = SALARIO_MINIMO.multiply(new BigDecimal("0.10"));
        } else if (grau == GrauInsalubridade.MEDIO) {
            adicional = SALARIO_MINIMO.multiply(new BigDecimal("0.20"));
        } else if (grau == GrauInsalubridade.MAXIMO) {
            adicional = SALARIO_MINIMO.multiply(new BigDecimal("0.40"));
        } else {
            adicional = BigDecimal.ZERO;
        }

        return adicional;
    }

    private BigDecimal calcularIRRF(BigDecimal baseCalculo) {
        BigDecimal irrf = BigDecimal.ZERO;

        if (baseCalculo.compareTo(new BigDecimal("2112.00")) <= 0) {
            irrf = BigDecimal.ZERO;
        } else if (baseCalculo.compareTo(new BigDecimal("2826.65")) <= 0) {
            irrf = baseCalculo.multiply(new BigDecimal("0.075")).subtract(new BigDecimal("158.40"));
        } else if (baseCalculo.compareTo(new BigDecimal("3751.05")) <= 0) {
            irrf = baseCalculo.multiply(new BigDecimal("0.15")).subtract(new BigDecimal("370.40"));
        } else if (baseCalculo.compareTo(new BigDecimal("4664.68")) <= 0) {
            irrf = baseCalculo.multiply(new BigDecimal("0.225")).subtract(new BigDecimal("651.73"));
        } else {
            irrf = baseCalculo.multiply(new BigDecimal("0.275")).subtract(new BigDecimal("884.96"));
        }

        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = BigDecimal.ZERO;
        }

        return irrf.setScale(2, RoundingMode.HALF_UP);
    }
}