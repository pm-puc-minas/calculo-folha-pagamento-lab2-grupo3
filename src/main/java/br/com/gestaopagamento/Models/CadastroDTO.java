package br.com.gestaopagamento.Models;

import br.com.gestaopagamento.Models.enums.UserRole;

public class CadastroDTO {
    String login;
    String password;
    UserRole role;

    public CadastroDTO() {}

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }


}
