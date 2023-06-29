package br.com.incode.base.services;

import org.springframework.stereotype.Service;

import br.com.incode.base.common.generics.BaseRepositoryImpl;
import br.com.incode.base.models.entities.User;

@Service
public class UserService extends BaseRepositoryImpl<User, Long> {

}
