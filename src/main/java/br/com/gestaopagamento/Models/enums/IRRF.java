package br.com.gestaopagamento.Models.enums;

import java.math.BigDecimal;

public enum IRRF {
    ducaoPorDependete(new BigDecimal("189.59")),

    aliquota2(new BigDecimal("0.075")),
    aliquota3(new BigDecimal("0.15")),
    aliquota4(new BigDecimal("0.225")),
    aliquota5(new BigDecimal("0.275")),
    
    deducaoIrpf2(new BigDecimal("142.80")),
    deducaoIrpf3(new BigDecimal("354.80")),
    deducaoIrpf4(new BigDecimal("636.13")),
    deducaoIrpf5(new BigDecimal("869.36")),

    teto1(new BigDecimal("1903.98")),
    teto2(new BigDecimal("2826.65")),
    teto3(new BigDecimal("3751.05")),
    teto4(new BigDecimal("4664.68"));

    private final BigDecimal valor;

    IRRF(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return this.valor;
    }
}
