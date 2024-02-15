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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceSuccessTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_create_Success() {
        CreateUserDTO createUserDTO = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        User user = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");

        when(userRepository.save(user)).thenReturn(null);

        UserResponseDTO response = userService.create(createUserDTO);

        assertEquals(response.getStatus(), 201);
        assertEquals(response.getUsername(), "beso123");
        assertEquals(response.getFirstname(), "Beso");
        assertEquals(response.getSurname(), "Managadze");
    }

    @Test
    public void test_create_two_users() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        User user = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");
        when(userRepository.save(user)).thenReturn(null);

        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 201);

        CreateUserDTO createUserDTO2 = new CreateUserDTO("123beso", "password", "Beso", "Managadze");
        UserResponseDTO response2 = userService.create(createUserDTO2);
        assertEquals(response2.getStatus(), 201);
    }

    @Test
    public void test_authenticate_valid_credentials() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        UserResponseDTO response = userService.create(createUserDTO1);
        assertEquals(response.getStatus(), 201);

        User user = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");
        when(userRepository.findByUsername("beso123")).thenReturn(user);

        UserResponseDTO authenticatedUser = userService.authenticate("beso123", "password");
        assertEquals(authenticatedUser.getStatus(), 200);
        assertEquals(authenticatedUser.getUsername(), "beso123");
        assertEquals(authenticatedUser.getFirstname(), "Beso");
        assertEquals(authenticatedUser.getSurname(), "Managadze");
    }

    @Test
    public void test_retrieve_all_users_authenticated() {
        // Create 2 users
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 201);
        CreateUserDTO createUserDTO2 = new CreateUserDTO("giorgi123", "password", "Giorgi", "Managadze");
        UserResponseDTO response2 = userService.create(createUserDTO2);
        assertEquals(response2.getStatus(), 201);

        // Mock user lists
        User user1 = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");
        User user2 = new User("giorgi123", PasswordHashing.hashPassword("password"), "456", "Giorgi", "Managadze");
        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userRepository.findByToken("123")).thenReturn(user1);
        when(userRepository.findAll()).thenReturn(users);

        AllUsersResponseDTO allUsersResponseDTO = userService.retrieveAll("123");

        assertEquals(allUsersResponseDTO.getStatus(), 200);
        assertEquals(allUsersResponseDTO.getUsers().size(), 2);
    }

    @Test
    public void test_find_by_user_id_authenticated() {
        CreateUserDTO createUserDTO1 = new CreateUserDTO("beso123", "password", "Beso", "Managadze");
        User user = new User("beso123", PasswordHashing.hashPassword("password"), "123", "Beso", "Managadze");
        when(userRepository.save(user)).thenReturn(null);

        UserResponseDTO response1 = userService.create(createUserDTO1);
        assertEquals(response1.getStatus(), 201);

        when(userRepository.findByToken("123")).thenReturn(user);
        when(userRepository.findByUserId(123L)).thenReturn(user);

        UserResponseDTO userResponseDTO = userService.findById(123L, "123");
        assertEquals(userResponseDTO.getStatus(), 200);
    }
}
