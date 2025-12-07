package br.com.gestaopagamento.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FolhaPagamentoViewController {

    @GetMapping("/folha-de-pagamento") 
    public String  FolhaPagamentoView() {
        return "FolhaPagamento"; 
    }
}