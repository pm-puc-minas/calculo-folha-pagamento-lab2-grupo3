package br.com.gestaopagamento.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "folha_pagamento")

public class FolhaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoPagamento;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(name = "mes", nullable = false)
    private int mes;

    @Column(name = "horas_trabalhadas", nullable = false, precision = 5, scale = 2)
    private BigDecimal horastrabalhadas;

    @Column(name = "salario_liquido", nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioLiquido;

    @Column(name = "valor_vale_alimentacao", nullable = false, precision = 10, scale = 2)
    private BigDecimal ValorValeAlimentacao;

    @Column(name = "valor_vale_transporte", nullable = false, precision = 10, scale = 2)
    private BigDecimal ValorValeTransporte;


     public FolhaPagamento(Funcionario funcionario, int mes, BigDecimal horastrabalhadas, BigDecimal salarioLiquido, BigDecimal ValorValeAlimentacao, BigDecimal ValorValeTransporte) {
        this.funcionario = funcionario;
        this.mes = mes;
        this.horastrabalhadas = horastrabalhadas;
        this.salarioLiquido = salarioLiquido;
        this.ValorValeAlimentacao = ValorValeAlimentacao;
        this.ValorValeTransporte = ValorValeTransporte;

    }
    public FolhaPagamento() {
    }
}
   

