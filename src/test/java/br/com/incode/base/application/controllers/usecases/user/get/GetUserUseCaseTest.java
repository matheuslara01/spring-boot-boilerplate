package br.com.incode.base.application.controllers.usecases.user.get;

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

import br.com.incode.base.application.usecases.user.get.GetUserUseCase;
import br.com.incode.base.application.usecases.user.get.UserOutput;
import br.com.incode.base.domain.entities.User;
import br.com.incode.base.domain.repositories.UserRepository;

public class GetUserUseCaseTest {
    
    private GetUserUseCase getUserUseCase;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getUserUseCase = new GetUserUseCase(userRepository);
    }

    @Test
    void testExecute_UserFound() {

        Long userId = 1L;
        User mockUser = mock(User.class);
        UserOutput expectedOutput = new UserOutput(1L, "John Doe", "john@example.com");

        when(userRepository.findUserById(userId)).thenReturn(mockUser);
        when(mockUser.toOutput()).thenReturn(expectedOutput);

        UserOutput result = getUserUseCase.execute(userId);

        assertNotNull(result);
        assertEquals(expectedOutput, result);
        verify(userRepository, times(1)).findUserById(userId);
    }

    @Test
    void testExecute_UserNotFound() {

        Long userId = 1L;
        when(userRepository.findUserById(userId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> getUserUseCase.execute(userId));
        verify(userRepository, times(1)).findUserById(userId);
    }
}
