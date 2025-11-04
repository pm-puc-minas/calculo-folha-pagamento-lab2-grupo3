package br.com.gestaopagamento.Models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // <-- ANOTAÇÃO ATIVADA
@Table(name = "funcionario") // Define o nome da tabela
public class Funcionario {

    @Id // <-- Chave Primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <-- ID Auto-incrementado
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "salario_bruto", nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioBruto;

    // Diz ao JPA para salvar o nome do Enum (ex: "BAIXO", "MEDIO")
    @Enumerated(EnumType.STRING)
    @Column(name = "grau_insalubridade")
    private GrauInsalubridade grauInsalubridade;

    @Column(name = "periculosidade")
    private boolean periculosidade;

    // MUDANÇA: 'double' para 'BigDecimal'
    @Column(name = "pensao_alimenticia", precision = 10, scale = 2)
    private BigDecimal pensaoAlimenticia;

    // MUDANÇA: 'double' para 'BigDecimal'
    @Column(name = "outras_deducoes", precision = 10, scale = 2)
    private BigDecimal outrasDeducoes;

    // MUDANÇA: Trocamos 'qntdDependentes' pela lista real
    @OneToMany(
            mappedBy = "funcionario",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Dependente> dependentes = new ArrayList<>();

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<FolhaPagamento> folhasPagamento = new ArrayList<>();

    // Construtor vazio é obrigatório para o JPA
    public Funcionario() {
    }

    // Você pode manter seu construtor completo se precisar,
    // apenas ajuste os tipos de 'double' para 'BigDecimal'
}