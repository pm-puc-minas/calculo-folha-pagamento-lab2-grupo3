package br.com.gestaopagamento.Service;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.enums.FGTSEnum;
import org.springframework.stereotype.Service;

@Service
public class CalcularFGTS implements IDesconto{
    @Override
    public BigDecimal calcular(Funcionario funcionario) {
        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal desconto;
        desconto = salarioBruto.multiply(FGTSEnum.ALIQUOTA.getAliquota());
        return desconto;
    }
}
