package br.com.incode.base.domain.entities;

import br.com.incode.base.application.usecases.user.get.UserOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String nome;
    private String login;
    private String senha;
    private String email;

    public User(String nome, String login, String senha, String email) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
    }

    public UserOutput toOutput() {
        return new UserOutput(this.id, this.nome, this.email);
    }
}
