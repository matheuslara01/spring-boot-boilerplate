package br.com.incode.base.application.controllers.usecases.user.save;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.incode.base.application.usecases.user.get.UserOutput;
import br.com.incode.base.application.usecases.user.save.SaveUserInput;
import br.com.incode.base.application.usecases.user.save.SaveUserUseCase;
import br.com.incode.base.domain.entities.User;
import br.com.incode.base.domain.repositories.UserRepository;

public class SaveUserUseCaseTest {
    
    private SaveUserUseCase saveUserUseCase;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        saveUserUseCase = new SaveUserUseCase(userRepository);
    }

    @Test
    void testExecute_SaveSuccessful() {

        SaveUserInput form = new SaveUserInput("John Doe", "john", "123456", "john@example.com");
        User mockUser = mock(User.class);
        UserOutput expectedOutput = new UserOutput(1L, "John Doe", "john@example.com");

        when(userRepository.saveUser(form)).thenReturn(mockUser);
        when(mockUser.toOutput()).thenReturn(expectedOutput);

        UserOutput result = saveUserUseCase.execute(form);

        assertNotNull(result);
        assertEquals(expectedOutput, result);
        verify(userRepository, times(1)).saveUser(form);
    }

    @Test
    void testExecute_SaveFailure() {

        SaveUserInput form = new SaveUserInput("John Doe", "john", "123456", "john@example.com");
        when(userRepository.saveUser(form)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> saveUserUseCase.execute(form));
        verify(userRepository, times(1)).saveUser(form);
    }
}
