package br.com.gestaopagamento.Controller;

import br.com.gestaopagamento.Models.*;
import br.com.gestaopagamento.Service.FolhaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RestController
@RequestMapping("/api/folha")
public class FolhaPagamentoController {

    @Autowired
    private FolhaPagamentoService folhaPagamentoService;

    private final List<FolhaPagamento> folhasGeradas = new ArrayList<>();
    private final List<Funcionario> funcionariosRef;

    public FolhaPagamentoController() {
        this.funcionariosRef = new ArrayList<>();
    }

    @PostMapping("/gerar")
    public ResponseEntity<?> gerarFolha(@RequestBody FolhaRequest request) {
        Funcionario funcionario = request.getFuncionario();

        if (funcionario == null) {
            return ResponseEntity.badRequest().body("Funcionario obrigatório no corpo da requisição");
        }

        if (request.getHorasTrabalhadas() == null) {
            return ResponseEntity.badRequest().body("Horas trabalhadas obrigatórias");
        }

        FolhaPagamento folha = folhaPagamentoService.gerarFolha(funcionario, request.getMes(), request.getHorasTrabalhadas());
        folhasGeradas.add(folha);

        return ResponseEntity.ok(folha);
    }

    @GetMapping("/listar")
    public List<FolhaPagamento> listarFolhas() {
        return folhasGeradas;
    }

    @GetMapping("/detalhes/{id}")
    public ResponseEntity<?> detalhesFolha(@PathVariable int id) {
        if (id <= 0) {
        }

        Optional<FolhaPagamento> opt = folhasGeradas.stream().filter(f -> f.getId() == id).findFirst();
        if (!opt.isPresent()) {
            return ResponseEntity.status(404).body(Collections.singletonMap("erro", "Folha de pagamento não encontrada"));
        }

        FolhaPagamento folha = opt.get();
        Funcionario funcionario = folha.getFuncionario();

        BigDecimal salarioBase = funcionario.getSalarioBruto();
        BigDecimal horasTrabalhadas = folha.getHorasTrabalhadas();

        BigDecimal CARGA_HORARIA_MENSAL = new BigDecimal("220");
        BigDecimal SALARIO_MINIMO = new BigDecimal("1412.00");
        BigDecimal PERC_INSAL_MIN = new BigDecimal("0.10");
        BigDecimal PERC_INSAL_MED = new BigDecimal("0.20");
        BigDecimal PERC_INSAL_MAX = new BigDecimal("0.40");
        BigDecimal PERC_PERICULOSIDADE = new BigDecimal("0.30");
        BigDecimal PERC_VALE_TRANSPORTE = new BigDecimal("0.06");
        BigDecimal VALE_ALIMENTACAO = new BigDecimal("300.00");

        BigDecimal salarioProporcional = salarioBase
                .divide(CARGA_HORARIA_MENSAL, 10, RoundingMode.HALF_UP)
                .multiply(horasTrabalhadas);

        BigDecimal adicionalInsalubridade = BigDecimal.ZERO;
        String percInsalubridadeStr = "0%";
        if (funcionario.getGrauInsalubridade() == GrauInsalubridade.MINIMO) {
            adicionalInsalubridade = SALARIO_MINIMO.multiply(PERC_INSAL_MIN);
            percInsalubridadeStr = "10% sobre salário mínimo";
        } else if (funcionario.getGrauInsalubridade() == GrauInsalubridade.MEDIO) {
            adicionalInsalubridade = SALARIO_MINIMO.multiply(PERC_INSAL_MED);
            percInsalubridadeStr = "20% sobre salário mínimo";
        } else if (funcionario.getGrauInsalubridade() == GrauInsalubridade.MAXIMO) {
            adicionalInsalubridade = SALARIO_MINIMO.multiply(PERC_INSAL_MAX);
            percInsalubridadeStr = "40% sobre salário mínimo";
        }

        BigDecimal adicionalPericulosidade = BigDecimal.ZERO;
        String percPericulosidadeStr = "0%";
        if (funcionario.isPericulosidade()) {
            adicionalPericulosidade = salarioBase.multiply(PERC_PERICULOSIDADE);
            percPericulosidadeStr = "30% sobre salário base";
        }

        BigDecimal salarioBrutoComAdicionais = salarioProporcional
                .add(adicionalInsalubridade)
                .add(adicionalPericulosidade);

        BigDecimal descontoIRRF = calcularIRRF(salarioBrutoComAdicionais);

        BigDecimal descontoValeTransporte = salarioBase.multiply(PERC_VALE_TRANSPORTE);

        BigDecimal salarioLiquido = salarioBrutoComAdicionais
                .subtract(descontoIRRF)
                .subtract(descontoValeTransporte)
                .add(VALE_ALIMENTACAO)
                .setScale(2, RoundingMode.HALF_UP);

        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("funcionario", funcionario.getNome());
        resposta.put("mes", folha.getMes());
        resposta.put("horasTrabalhadas", horasTrabalhadas);
        resposta.put("salarioBase", salarioBase);
        resposta.put("salarioProporcional", salarioProporcional);

        Map<String, Object> adicionais = new LinkedHashMap<>();
        adicionais.put("insalubridade", adicionalInsalubridade);
        adicionais.put("percentualInsalubridade", percInsalubridadeStr);
        adicionais.put("periculosidade", adicionalPericulosidade);
        adicionais.put("percentualPericulosidade", percPericulosidadeStr);
        resposta.put("adicionais", adicionais);

        Map<String, Object> descontos = new LinkedHashMap<>();
        descontos.put("IRRF", descontoIRRF);
        descontos.put("valeTransporte", descontoValeTransporte);
        descontos.put("percentualValeTransporte", "6% sobre salário base");
        resposta.put("descontos", descontos);

        Map<String, Object> beneficios = new LinkedHashMap<>();
        beneficios.put("valeAlimentacao", VALE_ALIMENTACAO);
        beneficios.put("descricaoValeAlimentacao", "Valor fixo de R$ 300,00");
        resposta.put("beneficios", beneficios);

        resposta.put("salarioBrutoComAdicionais", salarioBrutoComAdicionais);
        resposta.put("salarioLiquidoFinal", salarioLiquido);

        String resumo = "O funcionário " + funcionario.getNome() + " recebeu R$ " + salarioLiquido.toPlainString() +
                " líquidos, com adicionais: insalubridade (" + percInsalubridadeStr + ") e periculosidade (" + percPericulosidadeStr +
                "), e desconto de 6% de vale-transporte.";
        resposta.put("resumo", resumo);

        return ResponseEntity.ok(resposta);
    }

    private BigDecimal calcularIRRF(BigDecimal baseCalculo) {
        BigDecimal irrf = BigDecimal.ZERO;

        if (baseCalculo.compareTo(new BigDecimal("2112.00")) <= 0) {
            irrf = BigDecimal.ZERO;
        } else if (baseCalculo.compareTo(new BigDecimal("2826.65")) <= 0) {
            irrf = baseCalculo.multiply(new BigDecimal("0.075")).subtract(new BigDecimal("158.40"));
        } else if (baseCalculo.compareTo(new BigDecimal("3751.05")) <= 0) {
            irrf = baseCalculo.multiply(new BigDecimal("0.15")).subtract(new BigDecimal("370.40"));
        } else if (baseCalculo.compareTo(new BigDecimal("4664.68")) <= 0) {
            irrf = baseCalculo.multiply(new BigDecimal("0.225")).subtract(new BigDecimal("651.73"));
        } else {
            irrf = baseCalculo.multiply(new BigDecimal("0.275")).subtract(new BigDecimal("884.96"));
        }

        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = BigDecimal.ZERO;
        }

        return irrf.setScale(2, RoundingMode.HALF_UP);
    }

    public static class FolhaRequest {
        private Funcionario funcionario;
        private int mes;
        private BigDecimal horasTrabalhadas;

        public Funcionario getFuncionario() {
            return funcionario;
        }
        public void setFuncionario(Funcionario funcionario) {
            this.funcionario = funcionario;
        }

        public int getMes() {
            return mes;
        }
        public void setMes(int mes) {
            this.mes = mes;
        }

        public BigDecimal getHorasTrabalhadas() {
            return horasTrabalhadas;
        }
        public void setHorasTrabalhadas(BigDecimal horasTrabalhadas) {
            this.horasTrabalhadas = horasTrabalhadas;
        }
    }
}