package br.com.incode.base.application.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.incode.base.application.dto.LoginDTO;
import br.com.incode.base.application.dto.TokenDTO;
import br.com.incode.base.application.exceptions.ExceptionHandlerGeneric;
import br.com.incode.base.domain.services.AuthenticationService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private TokenDTO tokenDTO;
    private LoginDTO validLoginForm;
    private LoginDTO invalidLoginForm;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        tokenDTO = new TokenDTO("token", "Bearer");
        validLoginForm = new LoginDTO("testUser", "testPassword");
        invalidLoginForm = new LoginDTO("invalidUser", "invalidPassword");
    }

    @Test
    public void testAuthenticatorSuccess() throws Exception {

        when(authenticationService.login(validLoginForm)).thenReturn(tokenDTO);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        String jsonContent = objectMapper.writeValueAsString(validLoginForm);

        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"))
                .andExpect(jsonPath("$.tipo").value("Bearer"));
    }

    @Test
    public void testAuthenticatorInvalidCredentials() throws Exception {

        when(authenticationService.login(invalidLoginForm))
                .thenThrow(new BadCredentialsException("Usuário ou senha inválidos"));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new ExceptionHandlerGeneric())
                .build();

        String jsonContent = objectMapper.writeValueAsString(invalidLoginForm);

        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Login ou senha inválidos!"));
    }

    @Test
    public void testAuthenticatorException() throws Exception {
        
        when(authenticationService.login(validLoginForm)).thenThrow(new RuntimeException("Erro interno"));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new ExceptionHandlerGeneric())
                .build();

        String jsonContent = objectMapper.writeValueAsString(validLoginForm);

        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Erro interno no servidor. Contate o administrador do sistema."));
    }
}
