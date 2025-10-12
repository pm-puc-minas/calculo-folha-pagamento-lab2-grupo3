package br.com.gestaopagamento.Models;

import java.math.BigDecimal;

public class FolhaPagamento {

    private Funcionario funcionario;
    private int mes;
    private BigDecimal horastrabalhadas;
    private BigDecimal salarioLiquido;
    private BigDecimal ValorValeAlimentacao;
    private BigDecimal ValorValeTransporte;


     public FolhaPagamento(Funcionario funcionario, int mes, BigDecimal horastrabalhadas, BigDecimal salarioLiquido, BigDecimal ValorValeAlimentacao, BigDecimal ValorValeTransporte) {
        this.funcionario = funcionario;
        this.mes = mes;
        this.horastrabalhadas = horastrabalhadas;
        this.salarioLiquido = salarioLiquido;
        this.ValorValeAlimentacao = ValorValeAlimentacao;
        this.ValorValeTransporte = ValorValeTransporte;

    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
    public int getMes() {
        return mes;
    }
    public BigDecimal getHorastrabalhadas() {
        return horastrabalhadas;
    }
    public BigDecimal getSalarioLiquido() {
        return salarioLiquido;
    }
    public BigDecimal getValorValeAlimentacao() {
        return ValorValeAlimentacao;
    }
    public BigDecimal getValorValeTransporte() {
        return ValorValeTransporte;
    }
}
   

