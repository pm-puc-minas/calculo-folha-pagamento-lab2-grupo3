package br.com.gestaopagamento.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gestaopagamento.Models.FolhaPagamento;
import br.com.gestaopagamento.Service.FolhaPagamentoService;
import br.com.gestaopagamento.Dto.FolhaPagamentoRequest;

@RestController
@RequestMapping("/folha")
public class FolhaPagamentoController {

    private final FolhaPagamentoService folhaPagamentoService;

    public FolhaPagamentoController(FolhaPagamentoService folhaPagamentoService) {
        this.folhaPagamentoService = folhaPagamentoService;
    }

    @PostMapping("/calcular")
    public ResponseEntity<FolhaPagamento> calcularFolha(@RequestBody FolhaPagamentoRequest request) {
        FolhaPagamento folha = folhaPagamentoService.calcular(
                request.getFuncionario(),
                request.getMes(),
                request.getHorasTrabalhadas()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(folha);
    }
}
