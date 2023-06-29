package br.com.incode.base.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.incode.base.common.exceptions.InvalidTokenException;
import br.com.incode.base.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenService {
    
    @Value("${projectws.jwt.expiration}")
	private String expiration;

	@Value("${projectws.jwt.secret}")
	private String secret;

	public String generateToken(Authentication authentication) {

		User logged = (User) authentication.getPrincipal();
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

	public boolean isTokenValid(String token) {

		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			System.out.println("Token mal formado");
			throw new InvalidTokenException("Token mal formado");
		} catch (UnsupportedJwtException e) {
			System.out.println("Token não suportado");
			throw new InvalidTokenException("Token não suportado");
		} catch (ExpiredJwtException e) {
			System.out.println("Token expirado");
			throw new InvalidTokenException("Token expirado");
		} catch (IllegalArgumentException e) {
			System.out.println("Token nulo");
			return false;
		} catch (SignatureException e) {
			System.out.println("Erro de secret no token");
			throw new InvalidTokenException("Erro de secret no token");
		}
		
	}

    public Long getIdUser(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
}
