package br.com.gestaopagamento.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditarPerfilController {

    @GetMapping("/editar-perfil") 
    public String EditarPerfilPagina() {
        return "EditarPerfil"; 
    }
}