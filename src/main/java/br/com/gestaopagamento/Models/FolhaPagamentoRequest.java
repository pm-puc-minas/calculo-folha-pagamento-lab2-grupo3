package br.com.gestaopagamento.Dto;

import java.math.BigDecimal;
import br.com.gestaopagamento.Models.Funcionario;

public class FolhaPagamentoRequest {
    private Funcionario funcionario;
    private int mes;
    private BigDecimal horasTrabalhadas;

    // getters e setters
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }

    public BigDecimal getHorasTrabalhadas() { return horasTrabalhadas; }
    public void setHorasTrabalhadas(BigDecimal horasTrabalhadas) { this.horasTrabalhadas = horasTrabalhadas; }
}
