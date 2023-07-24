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

import br.com.incode.base.application.controllers.UserController;
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
        // Mock do resultado esperado
        List<User> userList = Arrays.asList(new User(1L, "John"), new User(2L, "Jane"));
        when(userService.findAll()).thenReturn(userList);

        // Chamada ao endpoint
        List<User> response = userController.findAll();

        // Verificações
        assertEquals(userList, response);
    }

    @Test
    public void testFindById() {
        // Mock do resultado esperado
        User user = new User(1L, "John");
        when(userService.findById(1L)).thenReturn(user);

        // Chamada ao endpoint
        User result = userController.findById(1L);

        // Verificações
        assertEquals(user, result);
    }

    @Test
    public void testSave() {
        // Mock do objeto a ser salvo
        User user = new User(1L, "John");

        // Mock do resultado esperado
        when(userService.saveReturnEntity(user, "Erro ao Salvar!")).thenReturn(user);

        // Chamada ao endpoint
        User result = userController.save(user);

        // Verificações
        assertEquals(user, result);
    }

    @Test
    public void testDelete() {
        // Mock do resultado esperado
        ResponseDTO response = new ResponseDTO(true, "Excluido com sucesso!");
        when(userService.deleteById(1L, "Erro ao Deletar!")).thenReturn(response);

        // Chamada ao endpoint
        ResponseDTO result = userController.delete(1L);

        // Verificações
        assertEquals(response, result);
    }

    @Test
    public void testFindAll_EmptyList() {
        // Mock do resultado esperado - lista vazia
        List<User> userList = Collections.emptyList();
        when(userService.findAll()).thenReturn(userList);

        // Chamada ao endpoint
        List<User> response = userController.findAll();

        // Verificações
        assertEquals(userList, response);
    }

    @Test
    public void testFindById_UserNotFound() {
        // Mock do resultado esperado - usuário não encontrado
        when(userService.findById(1L)).thenReturn(null);

        // Chamada ao endpoint
        User result = userController.findById(1L);

        // Verificações
        assertNull(result);
    }

    @Test
    public void testSave_NullUser() {
        // Chamada ao endpoint com usuário nulo
        User result = userController.save(null);

        // Verificações
        assertNull(result);
    }

    @Test
    public void testDelete_NonexistentUser() {
        // Mock do resultado esperado - usuário não encontrado para exclusão
        ResponseDTO response = new ResponseDTO(false, "Usuário não encontrado");
        when(userService.deleteById(1L, "Erro ao Deletar!")).thenReturn(response);

        // Chamada ao endpoint
        ResponseDTO result = userController.delete(1L);

        // Verificações
        assertEquals(response, result);
    }

}
