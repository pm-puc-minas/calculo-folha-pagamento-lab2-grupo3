package br.com.gestaopagamento.Models;

import br.com.gestaopagamento.Models.enums.UserRole;

public record CadastroDTO(String login, String password, UserRole role) {
}
