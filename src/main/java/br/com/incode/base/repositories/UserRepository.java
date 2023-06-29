package br.com.incode.base.repositories;

import java.util.Optional;

import br.com.incode.base.common.generics.BaseRepository;
import br.com.incode.base.models.User;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByLogin(String username);

}
