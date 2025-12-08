package br.com.gestaopagamento.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "ano", nullable = false)
    private int ano;

    // Ajustei para horasTrabalhadas (CamelCase)
    @Column(name = "horas_trabalhadas", nullable = false, precision = 10, scale = 2)
    private BigDecimal horasTrabalhadas;

    @Column(name = "salario_liquido", nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioLiquido;

    // Ajustei para letra minúscula no começo
    @Column(name = "valor_vale_alimentacao", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorValeAlimentacao;

    // Ajustei para letra minúscula no começo
    @Column(name = "valor_vale_transporte", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorValeTransporte;
    
    @Column(name = "valor_inss", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorINSS;
    
    @Column(name = "valor_irrf", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorIRRF;

    // Construtor Vazio (Obrigatório para o JPA/Hibernate)
    public FolhaPagamento() {
    }

    // Construtor Completo (Atualizado com todos os campos novos)
    public FolhaPagamento(Funcionario funcionario, int mes, int ano, BigDecimal horasTrabalhadas, 
                          BigDecimal salarioLiquido, BigDecimal valorValeAlimentacao, 
                          BigDecimal valorValeTransporte, BigDecimal valorINSS, BigDecimal valorIRRF) {
        this.funcionario = funcionario;
        this.mes = mes;
        this.ano = ano;
        this.horasTrabalhadas = horasTrabalhadas;
        this.salarioLiquido = salarioLiquido;
        this.valorValeAlimentacao = valorValeAlimentacao;
        this.valorValeTransporte = valorValeTransporte;
        this.valorINSS = valorINSS;
        this.valorIRRF = valorIRRF;
    }
}