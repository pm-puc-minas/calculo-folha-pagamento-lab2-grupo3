package br.com.gestaopagamento.Controller;

import br.com.gestaopagamento.Models.AuthenticationDTO;
import br.com.gestaopagamento.Models.CadastroDTO;
import br.com.gestaopagamento.Models.User;
import br.com.gestaopagamento.Repository.UserRepository;
import br.com.gestaopagamento.infra.security.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.AuthenticationException;

@Controller
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/login")
    public String loginForm() {
        return "Login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "CadastroFuncionario";
    }

    @PostMapping("/login")
    public String login(@Valid AuthenticationDTO data, HttpServletResponse response) {
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
    
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
    
            return "redirect:/home";
        }catch(AuthenticationException e){
            return "redirect:/auth/login?error=true";
        }
    }

    @PostMapping("/register")
    public String register(@Valid CadastroDTO data, RedirectAttributes redirectAttributes){
        if(repository.findByLogin(data.getLogin())!=null){
            redirectAttributes.addFlashAttribute("errorMessage","Erro: Usuário já existe!");
            return "redirect:/auth/register";
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = new User(data.getLogin(), encryptedPassword, data.getRole());
        this.repository.save(newUser);

        redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
        return "redirect:/auth/register";
    }

}
