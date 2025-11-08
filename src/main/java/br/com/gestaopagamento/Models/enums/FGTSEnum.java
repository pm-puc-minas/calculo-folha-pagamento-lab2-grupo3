package br.com.gestaopagamento.Models.enums;

import java.math.BigDecimal;

public enum FGTSEnum {
    ALIQUOTA;

    public BigDecimal getAliquota() {
        return BigDecimal.valueOf(0.08);
    }
}
