package org.example.usermanagementapi.service;

import org.example.usermanagementapi.model.dto.AllUsersResponseDTO;
import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserResponseDTO;
import org.example.usermanagementapi.model.entity.User;
import org.example.usermanagementapi.repository.UserRepository;
import org.example.usermanagementapi.util.PasswordHashing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceExceptionsTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_create_same_usernames() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        User user = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");
        when(userRepository.save(user)).thenReturn(null);

        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 201);

        when(userRepository.findByUsername("beso123")).thenReturn(user);

        CreateUserDTO createUserDTO2 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        UserResponseDTO response2 = userService.create(createUserDTO2);
        assertEquals(response2.getStatus(), 409);
    }

    @Test
    public void test_password_too_long() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "passworddddddddddddddddddddddddddddddddddddddddddddddddd", "Beso", "Managadze");
        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 400);
    }

    @Test
    public void test_username_too_long() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso1233333333333333333333333333333", "password", "Beso", "Managadze");
        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 400);
    }

    @Test
    public void test_authentication_wrong_username() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        User user = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");
        when(userRepository.save(user)).thenReturn(null);

        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 201);

        when(userRepository.findByUsername("gio123")).thenReturn(null);
        UserResponseDTO authenticatedUser = userService.authenticate("gio123", "password");
        assertEquals(authenticatedUser.getStatus(), 404);
    }

    @Test
    public void test_authentication_wrong_password() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        User user = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");
        when(userRepository.save(user)).thenReturn(null);

        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 201);

        when(userRepository.findByUsername("beso123")).thenReturn(user);
        UserResponseDTO authenticatedUser = userService.authenticate("beso123", "wrong_password");
        assertEquals(authenticatedUser.getStatus(), 401);
    }

    @Test
    public void test_retrieve_all_wrong_token() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        User user = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");
        when(userRepository.save(user)).thenReturn(null);

        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 201);

        when(userRepository.findByToken("123")).thenReturn(user);
        AllUsersResponseDTO allUsersResponseDTO = userService.retrieveAll("12344444");
        assertEquals(allUsersResponseDTO.getStatus(), 401);
    }

    @Test
    public void test_find_by_id_invalid_id() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        User user = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");
        when(userRepository.save(user)).thenReturn(null);

        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 201);

        when(userRepository.findByToken("123")).thenReturn(user);
        when(userRepository.findByUserId(123L)).thenReturn(user);

        UserResponseDTO userResponseDTO = userService.findById(222L, "123");
        assertEquals(userResponseDTO.getStatus(), 404);
    }


}
