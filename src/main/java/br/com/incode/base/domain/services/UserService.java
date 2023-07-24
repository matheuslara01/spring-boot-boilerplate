package br.com.incode.base.domain.services;

import org.springframework.stereotype.Service;

import br.com.incode.base.infrastructure.persistence.entities.User;
import br.com.incode.base.infrastructure.util.BaseRepositoryImpl;

@Service
public class UserService extends BaseRepositoryImpl<User, Long> {

}
