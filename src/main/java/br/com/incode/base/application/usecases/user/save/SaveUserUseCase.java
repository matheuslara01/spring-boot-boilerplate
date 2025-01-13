package br.com.incode.base.application.usecases.user.save;

import br.com.incode.base.application.usecases.user.get.UserOutput;
import br.com.incode.base.domain.entities.User;
import br.com.incode.base.domain.repositories.UserRepository;

public class SaveUserUseCase {
    
    private final UserRepository userRepository;

    public SaveUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserOutput execute(SaveUserInput form) {
        User user = userRepository.saveUser(form);
        return user.toOutput();
    }
}
