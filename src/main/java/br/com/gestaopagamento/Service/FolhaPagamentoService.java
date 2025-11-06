package br.com.gestaopagamento.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.FolhaPagamento;
import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Repository.IFolhaPagamentoRepository;

import br.com.gestaopagamento.Service.impl.CalcularValeAlimentacao;
import br.com.gestaopagamento.Service.impl.CalcularValeTransporteBeneficio;


@Service
public class FolhaPagamentoService {
    private final IFolhaPagamentoRepository folhaPagamentoRepository;

    private final List<IAdicional> regrasAdicionais;
    private final List<IDesconto> regrasDescontos;

    private final FuncionarioService funcionarioService;

    public FolhaPagamentoService(IFolhaPagamentoRepository folhaPagamentoRepository, List<IAdicional> regrasAdicionais, List<IDesconto> regrasDescontos, FuncionarioService funcionarioService) {
        
        
        this.folhaPagamentoRepository = folhaPagamentoRepository;
        this.regrasAdicionais = regrasAdicionais;
        this.regrasDescontos = regrasDescontos;
        this.funcionarioService = funcionarioService;
    }

    public FolhaPagamento calcularFolha(String cpf, int mes, BigDecimal horasTrabalhadas) {
        
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário com CPF " + cpf + " não encontrado.");
        }
        //Adicionais
        BigDecimal totalAdicionais = BigDecimal.ZERO;
        for (IAdicional regra : regrasAdicionais) {
            totalAdicionais = totalAdicionais.add(regra.calcular(funcionario));
        }

        //Benefícios 
        IBeneficio valeAlimentacao = new CalcularValeAlimentacao(new BigDecimal("30.00"), horasTrabalhadas.intValue());
        IBeneficio valeTransporte = new CalcularValeTransporteBeneficio(new BigDecimal("200.00"));
        BigDecimal valorValeAlimentacao = valeAlimentacao.calcular(funcionario);
        BigDecimal valorValeTransporte = valeTransporte.calcular(funcionario);

        //Descontos
        BigDecimal totalDescontos = BigDecimal.ZERO;
        for (IDesconto regra : regrasDescontos) {
            totalDescontos = totalDescontos.add(regra.calcular(funcionario));
        }

        //Salário Bruto
        BigDecimal salarioBruto = funcionario.getSalarioBruto();

        //Salário Líquido
        BigDecimal salarioLiquido = salarioBruto
                .add(totalAdicionais)
                .add(valorValeAlimentacao)
                .add(valorValeTransporte)
                .subtract(totalDescontos)
                .subtract(Optional.ofNullable(funcionario.getPensaoAlimenticia()).orElse(BigDecimal.ZERO))
                .subtract(Optional.ofNullable(funcionario.getOutrasDeducoes()).orElse(BigDecimal.ZERO));

        salarioLiquido = salarioLiquido.setScale(2, RoundingMode.HALF_UP);

        //Objeto Final
        FolhaPagamento folhaPagamento = new FolhaPagamento(
                funcionario,
                mes,
                horasTrabalhadas,
                salarioLiquido,
                valorValeAlimentacao,
                valorValeTransporte 
        );
        return folhaPagamentoRepository.save(folhaPagamento);
    }

    //mostrar salario de um funcionario e determinado mes
    public FolhaPagamento MostrarSalario(Funcionario funcionario, int mes) {
    
    return folhaPagamentoRepository.findByFuncionarioCpfAndMes(funcionario.getCpf(), mes)
            .orElseThrow(() -> new IllegalArgumentException("Folha de pagamento não encontrada para este funcionário e mês"));
    }

    //listar todas as folhas de pagamento de um funcionario
    public List<FolhaPagamento> MostrarTodosSalariosFuncionario(String cpf) {

        return folhaPagamentoRepository.findByFuncionarioCpf(cpf);
    }
}