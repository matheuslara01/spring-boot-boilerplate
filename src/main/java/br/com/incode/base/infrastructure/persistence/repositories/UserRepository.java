package br.com.incode.base.infrastructure.persistence.repositories;

import java.util.Optional;

import br.com.incode.base.infrastructure.persistence.entities.User;
import br.com.incode.base.infrastructure.util.BaseRepository;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByLogin(String username);

}
