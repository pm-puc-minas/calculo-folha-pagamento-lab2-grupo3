package br.com.gestaopagamento.Service;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;

public interface Adicional {

     BigDecimal calcular(Funcionario funcionario);
}
