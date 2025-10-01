package br.com.gestaopagamento.Service;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;

public interface Desconto {

     BigDecimal calcular(Funcionario funcionario);
}
