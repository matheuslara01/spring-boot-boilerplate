package br.com.incode.base.application.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.incode.base.application.dto.ResponseDTO;
import br.com.incode.base.application.exceptions.InvalidTokenException;
import br.com.incode.base.domain.services.TokenService;
import br.com.incode.base.infrastructure.persistence.entities.User;
import br.com.incode.base.infrastructure.persistence.repositories.UserRepository;

public class AuthenticationFilterToken extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository userRepository;

    public AuthenticationFilterToken(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = recuperateToken(request);

            boolean isValid = tokenService.isTokenValid(token);

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
        Long codUser = tokenService.getIdUser(token);
        User user = userRepository.findById(codUser).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
