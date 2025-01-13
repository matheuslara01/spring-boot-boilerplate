package br.com.incode.base.infrastructure.persistence.repositories;

import java.util.Optional;

import br.com.incode.base.infrastructure.persistence.entities.UserEntity;
import br.com.incode.base.infrastructure.util.BaseRepository;

public interface UserJpaRepository extends BaseRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String username);

}
