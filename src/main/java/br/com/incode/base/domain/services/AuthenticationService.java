package br.com.incode.base.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.incode.base.application.dto.LoginDTO;
import br.com.incode.base.application.dto.TokenDTO;
import br.com.incode.base.infrastructure.persistence.entities.User;
import br.com.incode.base.infrastructure.persistence.repositories.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);

		if (user.isPresent()) {
			return (UserDetails) user.get();
		}

		throw new UsernameNotFoundException("Login inválido!");
    }
    

    public TokenDTO login(LoginDTO form) throws AuthenticationException {
		Authentication dadosLogin = form.converter();

		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.generateToken(authentication);
			return new TokenDTO(token, "Bearer");

		} catch (AuthenticationException e) {
			throw e;
		}
	}
}
