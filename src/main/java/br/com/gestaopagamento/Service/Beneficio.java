package br.com.gestaopagamento.Service;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;

public interface Beneficio {

     BigDecimal calcular(Funcionario funcionario);
}
