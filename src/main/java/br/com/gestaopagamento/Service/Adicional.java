package br.com.gestaopagamento.Service;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;

//Intefaces em Java precisam começar com I: IAdicional
public interface Adicional {

     BigDecimal calcular(Funcionario funcionario);
}
