package org.example.usermanagementapi.service;

import org.example.usermanagementapi.model.dto.AllUsersResponseDTO;
import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserResponseDTO;
import org.example.usermanagementapi.model.entity.User;
import org.example.usermanagementapi.repository.UserRepository;
import org.example.usermanagementapi.service.interfaces.UserService;
import org.example.usermanagementapi.util.PasswordHashing;
import org.example.usermanagementapi.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDTO create(CreateUserDTO createUserDTO) {
        if (userRepository.findByUsername(createUserDTO.getUsername()) != null) {
            return new UserResponseDTO(409);
        }
        if (createUserDTO.getUsername().isEmpty() || createUserDTO.getUsername().length() > 16) {
            return new UserResponseDTO(400);
        }
        if (createUserDTO.getPassword().isEmpty() || createUserDTO.getPassword().length() > 32) {
            return new UserResponseDTO(400);
        }
        String passwordHash = PasswordHashing.hashPassword(createUserDTO.getPassword());
        String token = TokenGenerator.generateToken();
        User newUser = new User(createUserDTO.getUsername(), passwordHash, token, createUserDTO.getFirstname(), createUserDTO.getSurname());
        userRepository.save(newUser);
        return new UserResponseDTO(201, newUser.getUserId(), createUserDTO.getUsername(), token, createUserDTO.getFirstname(), createUserDTO.getSurname());
    }

    @Override
    public UserResponseDTO authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new UserResponseDTO(404);
        }
        String passwordHash = PasswordHashing.hashPassword(password);
        if (passwordHash == null) {
            return new UserResponseDTO(500);
        }
        if (!passwordHash.equals(user.getPasswordHash())) {
            return new UserResponseDTO(401);
        }
        return new UserResponseDTO(200, user.getUserId(), user.getUsername(), user.getToken(), user.getFirstname(), user.getSurname());
    }

    @Override
    public AllUsersResponseDTO retrieveAll(String token) {
        User requesterUser = userRepository.findByToken(token);
        if (requesterUser == null) {
            return new AllUsersResponseDTO(401);
        }
        List<User> allUsers = userRepository.findAll();
        AllUsersResponseDTO allUsersResponseDTO = new AllUsersResponseDTO(200);
        for (User user : allUsers) {
            allUsersResponseDTO.addUser(user.getUserId(), user.getUsername(), user.getFirstname(), user.getSurname());
        }
        return allUsersResponseDTO;
    }


    @Override
    public UserResponseDTO findById(Long id, String token) {
        User requesterUser = userRepository.findByToken(token);
        if (requesterUser == null) {
            return new UserResponseDTO(401);
        }
        User user = userRepository.findByUserId(id);
        if (user == null) {
            return new UserResponseDTO(404);
        }
        return new UserResponseDTO(200, user.getUserId(), user.getUsername(), user.getToken(), user.getFirstname(), user.getSurname());
    }
}
