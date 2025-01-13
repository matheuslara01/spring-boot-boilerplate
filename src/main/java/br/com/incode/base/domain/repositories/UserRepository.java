package br.com.incode.base.domain.repositories;

import br.com.incode.base.application.usecases.user.save.SaveUserInput;
import br.com.incode.base.domain.entities.User;

public interface UserRepository {

    User findUserById(Long id);
    User saveUser(SaveUserInput form);
}
