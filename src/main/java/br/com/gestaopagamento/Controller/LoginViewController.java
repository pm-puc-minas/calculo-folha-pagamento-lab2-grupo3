package br.com.gestaopagamento.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {
    @GetMapping("/login")
    public String paginaLogin() {
        return "Login"; // O nome do arquivo HTML (sem .html)
    }
}