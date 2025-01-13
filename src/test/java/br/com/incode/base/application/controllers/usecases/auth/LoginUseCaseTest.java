package br.com.incode.base.application.controllers.usecases.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.incode.base.application.usecases.auth.LoginInput;
import br.com.incode.base.application.usecases.auth.LoginOutput;
import br.com.incode.base.application.usecases.auth.LoginUseCase;
import br.com.incode.base.domain.repositories.LoginRepository;

public class LoginUseCaseTest {
    
    private LoginUseCase loginUseCase;

    @Mock
    private LoginRepository loginRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginUseCase = new LoginUseCase(loginRepository);
    }

    @Test
    void testExecute_Success() {
        LoginInput form = new LoginInput("user", "password");
        String expectedToken = "mockToken123";
        when(loginRepository.login(form)).thenReturn(expectedToken);

        LoginOutput result = loginUseCase.execute(form);

        assertNotNull(result);
        assertEquals(expectedToken, result.token());
        assertEquals("Bearer", result.tipo());
        verify(loginRepository, times(1)).login(form);
    }
}
