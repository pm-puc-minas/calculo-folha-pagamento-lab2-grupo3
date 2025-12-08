package br.com.gestaopagamento.Models;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CalculoFolhaRequest {
    private String cpfFuncionario;
    private int mes;
    private int ano;
    private BigDecimal horasTrabalhadas;
}