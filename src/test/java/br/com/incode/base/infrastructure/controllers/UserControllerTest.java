package br.com.incode.base.infrastructure.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.incode.base.application.usecases.user.get.GetUserUseCase;
import br.com.incode.base.application.usecases.user.get.UserOutput;
import br.com.incode.base.application.usecases.user.save.SaveUserUseCase;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GetUserUseCase getUserUseCase;

    @Mock
    private SaveUserUseCase saveUserUseCase;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(null);
        userController.getUserUseCase = getUserUseCase;
        userController.saveUserUseCase = saveUserUseCase;
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetUserById_Success() throws Exception {

        UserOutput userOutput = new UserOutput(1L, "John Doe", "johndoe@example.com");
        when(getUserUseCase.execute(1L)).thenReturn(userOutput);

        mockMvc.perform(get("/user/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("John Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
    }

}
