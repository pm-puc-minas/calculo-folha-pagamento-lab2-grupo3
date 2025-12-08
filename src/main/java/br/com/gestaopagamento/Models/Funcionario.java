package br.com.gestaopagamento.Models;

import br.com.gestaopagamento.Models.enums.GrauInsalubridade;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "salario_bruto", nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioBruto;


    @Enumerated(EnumType.STRING)
    @Column(name = "grau_insalubridade")
    private GrauInsalubridade grauInsalubridade;

    @Column(name = "periculosidade")
    private Boolean periculosidade;

    @Column(name = "pensao_alimenticia", precision = 10, scale = 2)
    private BigDecimal pensaoAlimenticia;

    @Column(name = "outras_deducoes", precision = 10, scale = 2)
    private BigDecimal outrasDeducoes;

   
    @Column(name = "quantidade_dependentes")
    private Integer dependentes = 0;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<FolhaPagamento> folhasPagamento = new ArrayList<>();

    public Funcionario() {
    }
}