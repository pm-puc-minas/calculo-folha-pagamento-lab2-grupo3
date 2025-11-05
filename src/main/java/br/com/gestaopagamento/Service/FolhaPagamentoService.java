package br.com.gestaopagamento.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

import br.com.gestaopagamento.Models.FolhaPagamento;
import br.com.gestaopagamento.Service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
@Service
public class FolhaPagamentoService {

    private final CalcularINSS calcularINSS;
    private final CalcularIRRF calcularIRRF;
    private final CalcularFGTS calcularFGTS;
    private final CalcularValeTransporteDesconto calcularVTDesconto;
    private final CalcularValeAlimentacao calcularVA;
    private final CalcularInsalubridade calcularInsalubridade;

    @Autowired
    public FolhaPagamentoService(
            CalcularINSS calcularINSS,
            CalcularIRRF calcularIRRF,
            CalcularFGTS calcularFGTS,
            CalcularValeTransporteDesconto calcularVTDesconto,
            CalcularValeAlimentacao calcularVA,
            CalcularInsalubridade calcularInsalubridade
    ) {
        this.calcularINSS = calcularINSS;
        this.calcularIRRF = calcularIRRF;
        this.calcularFGTS = calcularFGTS;
        this.calcularVTDesconto = calcularVTDesconto;
        this.calcularVA = calcularVA;
        this.calcularInsalubridade = calcularInsalubridade;
    }

    public FolhaPagamento calcular(Funcionario funcionario, int mes, BigDecimal horasTrabalhadas) {
        BigDecimal inss = calcularINSS.calcular(funcionario);
        BigDecimal irrf = calcularIRRF.calcular(funcionario);
        //BigDecimal fgts = calcularFGTS.calcular(funcionario);
        BigDecimal vtDesconto = calcularVTDesconto.calcular(funcionario);
        BigDecimal va = calcularVA.calcular(funcionario);
        BigDecimal insalubridade = calcularInsalubridade.calcular(funcionario);

        BigDecimal salarioLiquido = funcionario.getSalarioBruto()
                .add(insalubridade)
                .add(va)
                .subtract(inss)
                .subtract(irrf)
                .subtract(vtDesconto);

        return new FolhaPagamento(funcionario, mes, horasTrabalhadas, salarioLiquido);
    }


}
