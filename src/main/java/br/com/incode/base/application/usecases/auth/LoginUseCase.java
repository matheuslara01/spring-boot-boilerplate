package br.com.incode.base.application.usecases.auth;

import br.com.incode.base.domain.repositories.LoginRepository;

public class LoginUseCase {

    private final LoginRepository loginRepository;

    public LoginUseCase(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginOutput execute(LoginInput form) {
        String token = loginRepository.login(form);
        return new LoginOutput(token, "Bearer");
    }

}
