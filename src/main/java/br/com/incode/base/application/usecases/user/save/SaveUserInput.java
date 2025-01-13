package br.com.incode.base.application.usecases.user.save;

import br.com.incode.base.domain.entities.User;

public record SaveUserInput(String nome, String login, String senha, String email) {

    public User toDomain() {
        return new User(this.nome, this.login, this.senha, this.email);
    }
}
