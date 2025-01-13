package br.com.incode.base.infrastructure.persistence.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.incode.base.application.usecases.user.save.SaveUserInput;
import br.com.incode.base.domain.entities.User;
import br.com.incode.base.domain.repositories.UserRepository;
import br.com.incode.base.infrastructure.exceptions.ResourceNotFoundException;
import br.com.incode.base.infrastructure.persistence.entities.UserEntity;
import br.com.incode.base.infrastructure.persistence.repositories.UserJpaRepository;
import br.com.incode.base.infrastructure.util.BaseRepositoryImpl;

@Service
public class UserRepositoryImpl extends BaseRepositoryImpl<UserEntity, Long> implements UserRepository {

    @Autowired
    private UserJpaRepository repository;

    public Optional<UserEntity> findByLogin(String username) {
        return repository.findByLogin(username);
    }

    @Override
    public User findUserById(Long id) {
        return repository.findById(id).map(UserEntity::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"));
    }

    @Override
    public User saveUser(SaveUserInput form) {
        User user = form.toDomain();
        return saveReturnEntity(UserEntity.fromDomain(user), "Erro ao salvar").toDomain();
    }

}
