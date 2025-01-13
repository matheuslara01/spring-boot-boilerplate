package br.com.incode.base.domain.repositories;

import br.com.incode.base.application.usecases.auth.LoginInput;

public interface LoginRepository {
    String login(LoginInput input);
}
