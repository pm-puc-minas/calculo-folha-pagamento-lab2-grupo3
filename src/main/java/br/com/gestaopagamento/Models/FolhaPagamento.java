package br.com.gestaopagamento.Models;

import java.math.BigDecimal;

public class FolhaPagamento {

    private Funcionario funcionario;
    private int mes;
    private BigDecimal horastrabalhadas;
    private BigDecimal salarioLiquido;


     public FolhaPagamento(Funcionario funcionario, int mes, BigDecimal horastrabalhadas, BigDecimal salarioLiquido){
        this.funcionario = funcionario;
        this.mes = mes;
        this.horastrabalhadas = horastrabalhadas;
        this.salarioLiquido = salarioLiquido;
    }
}
   

