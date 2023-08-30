package br.com.incode.base.application.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.incode.base.application.dto.ResponseDTO;
import br.com.incode.base.infrastructure.persistence.entities.User;
import br.com.incode.base.infrastructure.util.BaseRepositoryImpl;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private BaseRepositoryImpl<User, Long> userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {

        List<User> userList = Arrays.asList(new User(1L, "John"), new User(2L, "Jane"));
        when(userService.findAll()).thenReturn(userList);

        List<User> response = userController.findAll();

        assertEquals(userList, response);
    }

    @Test
    public void testFindById() {

        User user = new User(1L, "John");
        when(userService.findById(1L)).thenReturn(user);

        User result = userController.findById(1L);

        assertEquals(user, result);
    }

    @Test
    public void testSave() {

        User user = new User(1L, "John");

        when(userService.saveReturnEntity(user, "Erro ao Salvar!")).thenReturn(user);

        User result = userController.save(user);

        assertEquals(user, result);
    }

    @Test
    public void testDelete() {

        ResponseDTO response = new ResponseDTO(true, "Excluido com sucesso!");
        when(userService.deleteById(1L, "Erro ao Deletar!")).thenReturn(response);

        ResponseDTO result = userController.delete(1L);

        assertEquals(response, result);
    }

    @Test
    public void testFindAll_EmptyList() {

        List<User> userList = Collections.emptyList();
        when(userService.findAll()).thenReturn(userList);

        List<User> response = userController.findAll();

        assertEquals(userList, response);
    }

    @Test
    public void testFindById_UserNotFound() {

        when(userService.findById(1L)).thenReturn(null);

        User result = userController.findById(1L);

        assertNull(result);
    }

    @Test
    public void testSave_NullUser() {

        User result = userController.save(null);

        assertNull(result);
    }

    @Test
    public void testDelete_NonexistentUser() {

        ResponseDTO response = new ResponseDTO(false, "Usuário não encontrado");
        when(userService.deleteById(1L, "Erro ao Deletar!")).thenReturn(response);

        ResponseDTO result = userController.delete(1L);

        assertEquals(response, result);
    }

}
