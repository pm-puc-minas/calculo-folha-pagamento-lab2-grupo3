package br.com.gestaopagamento.Service;

import java.math.BigDecimal;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.enums.FGTSEnum;
import org.springframework.stereotype.Service;

@Service
public class CalcularFGTS implements IDesconto{
    @Override
    public BigDecimal calcular(Funcionario funcionario) {
        if(funcionario == null){
            throw new IllegalArgumentException("O funcionário não pode ser nulo!");
        }
        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal desconto = BigDecimal.ZERO;
        desconto = salarioBruto.multiply(FGTSEnum.ALIQUOTA.getAliquota());
        return desconto;
    }
}
