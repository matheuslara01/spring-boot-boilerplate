package br.com.incode.base.infrastructure.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.incode.base.infrastructure.persistence.entities.UserEntity;
import br.com.incode.base.infrastructure.persistence.services.UserRepositoryImpl;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepositoryImpl.findByLogin(username);

        if (user.isPresent()) {
            return (UserDetails) user.get();
        }

        throw new UsernameNotFoundException("Login inválido!");
    }

}
