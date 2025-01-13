package br.com.incode.base.infrastructure.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.incode.base.infrastructure.exceptions.InvalidTokenException;
import br.com.incode.base.infrastructure.persistence.entities.UserEntity;
import br.com.incode.base.infrastructure.persistence.services.UserRepositoryImpl;
import br.com.incode.base.infrastructure.util.ResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilterToken extends OncePerRequestFilter {

    private final String secret;
    private UserRepositoryImpl userRepositoryImpl;

    public AuthenticationFilterToken(UserRepositoryImpl userRepositoryImpl, String secret) {
        this.userRepositoryImpl = userRepositoryImpl;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = recuperateToken(request);

            boolean isValid = isTokenValid(token);

            if (isValid) {
                authUser(token);
            }

            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {

            ObjectMapper objectMapper = new ObjectMapper();

            ResponseDTO responseApi = new ResponseDTO(false, e.getMessage());

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.println(objectMapper.writeValueAsString(responseApi));

        }

    }

    private String recuperateToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            String requestURI = request.getRequestURI();

            if (requestURI.contains("auth") || requestURI.contains("swagger") || requestURI.contains("api-docs")) {
                return null;
            } else {
                throw new InvalidTokenException("Token não está presente no header da requisição!");
            }

        }
        return token.substring(7);
    }

    private void authUser(String token) {
        Long codUser = getIdUser(token);
        UserEntity user = userRepositoryImpl.findById(codUser);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean isTokenValid(String token) {

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
        } catch (io.jsonwebtoken.SignatureException e) {
            System.out.println("Erro de secret no token");
            throw new InvalidTokenException("Erro de secret no token");
        }

    }

    private Long getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
