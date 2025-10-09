package br.com.gestaopagamento.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.Funcionario;
@Service
public class FolhaPagamentoService {

    private Funcionario funcionario;
    private int mes;
    private BigDecimal horastrabalhadas;
    private BigDecimal salarioLiquido;
    private ArrayList<Adicional> adicionais = new ArrayList<>();

    public FolhaPagamentoService(Funcionario funcionario, int mes, BigDecimal horastrabalhadas){
        this.funcionario = funcionario;
        this.mes = mes;
        this.horastrabalhadas = horastrabalhadas;
        for (Adicional adicional : adicionais) {
            salarioLiquido = adicional.calcular(funcionario);
        }

    }
}