package br.com.gestaopagamento.Models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Funcionario {

    private String nome;
    private String cpf;
    private String cargo;
    private BigDecimal salarioBruto;
    private GrauInsalubridade grauInsalubridade;
    private boolean periculosidade;
    private ArrayList<FolhaPagamento> pagamentos;

    public Funcionario(String nome, String cpf, String cargo, BigDecimal salarioBruto, boolean periculosidade, GrauInsalubridade grauInsalubridade){
            this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.salarioBruto = salarioBruto;
        this.periculosidade = periculosidade;
        this.grauInsalubridade = grauInsalubridade;
           
        this.pagamentos = new ArrayList<>();
    }

}
