package br.com.gestaopagamento.Models;

import java.math.BigDecimal;
//import java.util.ArrayList;

import jakarta.persistence.Entity;

//@Entity - VOLTAR QUANDO FOR USAR BANCO
public class Funcionario {

    private String nome;
    private String cpf;
    private String cargo;
    private BigDecimal salarioBruto;
    private GrauInsalubridade grauInsalubridade;
    private boolean periculosidade;
    private int qntdDependentes;
    private double pensaoAlimenticia;
    private double outrasDeducoes;
    //private ArrayList<FolhaPagamento> pagamentos;  Ainda não estamos usando (não tem nenhum requisito que peça isso)

    public Funcionario() {
       // this.pagamentos = new ArrayList<>();
    }

    public Funcionario(String nome, String cpf, String cargo, BigDecimal salarioBruto, boolean periculosidade, int grauInsalubridade, int qntdDependentes, double pensaoAlimenticia, double outrasDeducoes){
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.salarioBruto = salarioBruto;
        this.periculosidade = periculosidade;
        this.grauInsalubridade = GrauInsalubridade.fromInt(grauInsalubridade);
        //this.pagamentos = new ArrayList<>();
        this.qntdDependentes = qntdDependentes;
        this.pensaoAlimenticia = pensaoAlimenticia;
        this.outrasDeducoes = outrasDeducoes;
    }

    //getters
    public BigDecimal getSalarioBruto() {
        return salarioBruto;
    }

    public GrauInsalubridade getGrauInsalubridade() {
        return grauInsalubridade;
    }
    public boolean getPericulosidade() {
        return periculosidade;
    }

    public String getCpf() {
        return cpf;
    }

    public int getQntdDependentes(){
        return qntdDependentes;
    }
    public double getPensaoAlimenticia(){
        return pensaoAlimenticia;
    }
    public double getOutrasDeducoes(){
        return outrasDeducoes;
    }
    public String getNome() {
        return nome;
    }
    public String getCargo() {
        return cargo;
    }


    //setters

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public void setSalarioBruto(BigDecimal salarioBruto) {
        this.salarioBruto = salarioBruto;
    }
    public void setPericulosidade(boolean periculosidade) {
        this.periculosidade = periculosidade;
    }
    public void setGrauInsalubridade(int grauInsalubridade) {
        this.grauInsalubridade = GrauInsalubridade.fromInt(grauInsalubridade);
    }
    public void setQntdDependentes(int qntdDependentes) {
        this.qntdDependentes = qntdDependentes;
    }
    public void setPensaoAlimenticia(double pensaoAlimenticia) {
        this.pensaoAlimenticia = pensaoAlimenticia;
    }
    public void setOutrasDeducoes(double outrasDeducoes) {
        this.outrasDeducoes = outrasDeducoes;
    }
}
