package br.com.incode.base.application.usecases.user.get;

import br.com.incode.base.domain.entities.User;
import br.com.incode.base.domain.repositories.UserRepository;

public class GetUserUseCase {

    private final UserRepository userRepository;

    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserOutput execute(Long id) {
        User user = userRepository.findUserById(id);
        return user.toOutput();
    }

}
