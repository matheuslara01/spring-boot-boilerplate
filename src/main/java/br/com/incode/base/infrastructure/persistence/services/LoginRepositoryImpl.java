package br.com.incode.base.infrastructure.persistence.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.incode.base.application.usecases.auth.LoginInput;
import br.com.incode.base.domain.repositories.LoginRepository;
import br.com.incode.base.infrastructure.persistence.entities.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class LoginRepositoryImpl implements LoginRepository {

    @Value("${projectws.jwt.expiration}")
    private String expiration;

    @Value("${projectws.jwt.secret}")
    private String secret;

    @Autowired
    private AuthenticationManager authManager;

    @Override
    public String login(LoginInput input) {

        Authentication auth = new UsernamePasswordAuthenticationToken(input.login(), input.senha());

        auth = authManager.authenticate(auth);

        if (!auth.isAuthenticated()) {
           throw new RuntimeException("Usuário ou senha inválidos");
        } 

        String token = generateToken(auth);
       
        return token;
    }

    private String generateToken(Authentication authentication) {

        UserEntity logged = (UserEntity) authentication.getPrincipal();
        Date today = new Date();
        Date dateExpiration = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("projectws")
                .setSubject(logged.getId().toString())
                .claim("id", logged.getId())
                .claim("name", logged.getNome())
                .claim("email", logged.getEmail())
                .setIssuedAt(today).setExpiration(dateExpiration).signWith(SignatureAlgorithm.HS256, secret).compact();
    }
}
