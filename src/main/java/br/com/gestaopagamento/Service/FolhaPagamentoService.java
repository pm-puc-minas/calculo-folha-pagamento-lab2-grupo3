package br.com.gestaopagamento.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.gestaopagamento.Models.FolhaPagamento;
import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Repository.IFolhaPagamentoRepository;
import br.com.gestaopagamento.Service.impl.CalcularINSS;
import br.com.gestaopagamento.Service.impl.CalcularIRRF;
import br.com.gestaopagamento.Service.impl.CalcularValeAlimentacao;
import br.com.gestaopagamento.Service.impl.CalcularValeTransporteBeneficio;

@Service
public class FolhaPagamentoService {
    
    private final IFolhaPagamentoRepository folhaPagamentoRepository;
    private final FuncionarioService funcionarioService;
    private final List<IAdicional> regrasAdicionais;
    
    // Instanciamos as calculadoras aqui para usar explicitamente
    private final CalcularINSS calcularINSS = new CalcularINSS();
    private final CalcularIRRF calcularIRRF = new CalcularIRRF(new CalcularINSS());

    public FolhaPagamentoService(IFolhaPagamentoRepository folhaPagamentoRepository, 
                                 List<IAdicional> regrasAdicionais, 
                                 List<IDesconto> regrasDescontos, // Mantido caso use outros descontos
                                 FuncionarioService funcionarioService) {
        this.folhaPagamentoRepository = folhaPagamentoRepository;
        this.regrasAdicionais = regrasAdicionais;
        this.funcionarioService = funcionarioService;
    }

    // MUDANÇA: Adicionei 'int ano' nos parâmetros
    public FolhaPagamento calcularFolha(String cpf, int mes, int ano, BigDecimal horasTrabalhadas) {
        
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário com CPF " + cpf + " não encontrado.");
        }

        // 1. Adicionais
        BigDecimal totalAdicionais = BigDecimal.ZERO;
        for (IAdicional regra : regrasAdicionais) {
            totalAdicionais = totalAdicionais.add(regra.calcular(funcionario));
        }

        // 2. Benefícios
        IBeneficio valeAlimentacao = new CalcularValeAlimentacao(new BigDecimal("30.00"), horasTrabalhadas.intValue());
        IBeneficio valeTransporte = new CalcularValeTransporteBeneficio(new BigDecimal("200.00"));
        
        BigDecimal valorValeAlimentacao = valeAlimentacao.calcular(funcionario);
        BigDecimal valorValeTransporte = valeTransporte.calcular(funcionario);

        // 3. Impostos (Calculados separadamente para salvar no histórico)
        BigDecimal valorINSS = calcularINSS.calcular(funcionario);
        BigDecimal valorIRRF = calcularIRRF.calcular(funcionario);
        
        BigDecimal totalDescontos = valorINSS.add(valorIRRF);

        // 4. Salário Bruto
        BigDecimal salarioBruto = funcionario.getSalarioBruto();

        // 5. Salário Líquido
        BigDecimal salarioLiquido = salarioBruto
                .add(totalAdicionais)
                .add(valorValeAlimentacao)
                .add(valorValeTransporte)
                .subtract(totalDescontos)
                .subtract(Optional.ofNullable(funcionario.getPensaoAlimenticia()).orElse(BigDecimal.ZERO))
                .subtract(Optional.ofNullable(funcionario.getOutrasDeducoes()).orElse(BigDecimal.ZERO));

        salarioLiquido = salarioLiquido.setScale(2, RoundingMode.HALF_UP);

        // 6. Montagem do Objeto
        FolhaPagamento folha = new FolhaPagamento();
        folha.setFuncionario(funcionario);
        folha.setMes(mes);
        folha.setAno(ano); // <-- Salvando o ano
        folha.setHorasTrabalhadas(horasTrabalhadas);
        folha.setSalarioLiquido(salarioLiquido);
        folha.setValorValeAlimentacao(valorValeAlimentacao);
        folha.setValorValeTransporte(valorValeTransporte);
        
        
        folha.setValorINSS(valorINSS);
        folha.setValorIRRF(valorIRRF);

        return folhaPagamentoRepository.save(folha);
    }

    public List<FolhaPagamento> MostrarTodosSalariosFuncionario(String cpf) {
        return folhaPagamentoRepository.findByFuncionarioCpf(cpf);
    }
    public FolhaPagamento buscarPorId(Long id) {
        return folhaPagamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Folha de pagamento não encontrada."));
    }
}