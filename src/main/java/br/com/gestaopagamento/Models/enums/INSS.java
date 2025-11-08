package br.com.gestaopagamento.Models.enums;

import java.math.BigDecimal;

public enum INSS {
    ALIQUOTA1(new BigDecimal("0.075")),
    ALIQUOTA2(new BigDecimal("0.09")),
    ALIQUOTA3(new BigDecimal("0.12")),
    ALIQUOTA4(new BigDecimal("0.14")),
    TETO1(new BigDecimal("1302.0")),
    TETO2(new BigDecimal("2571.29")),
    TETO3(new BigDecimal("3856.94")),
    TETO4(new BigDecimal("7507.49")),
    FAIXA2(TETO2.getValor().subtract(TETO1.getValor())),
    FAIXA3(TETO3.getValor().subtract(TETO2.getValor())),;

    private final BigDecimal valor;

   INSS (BigDecimal valor) {
        this.valor = valor;
   }
   public BigDecimal getValor() {
       return valor;
   }

}
