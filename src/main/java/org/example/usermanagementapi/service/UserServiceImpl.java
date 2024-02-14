package org.example.usermanagementapi.service;

import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserResponseDTO;
import org.example.usermanagementapi.model.entity.User;
import org.example.usermanagementapi.repository.UserRepository;
import org.example.usermanagementapi.service.interfaces.UserService;
import org.example.usermanagementapi.util.PasswordHashing;
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
        User newUser = new User(createUserDTO.getUsername(), passwordHash, createUserDTO.getFirstname(), createUserDTO.getSurname());
        userRepository.save(newUser);
        return new UserResponseDTO(201, newUser.getUserId(), createUserDTO.getUsername(), createUserDTO.getFirstname(), createUserDTO.getSurname());
    }

    @Override
    public UserResponseDTO authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new UserResponseDTO(404);
        }
        String passwordHash = PasswordHashing.hashPassword(password);
        if (!passwordHash.equals(user.getPasswordHash())) {
            return new UserResponseDTO(401);
        }
        return new UserResponseDTO(200, user.getUserId(), user.getUsername(), user.getFirstname(), user.getSurname());
    }

    @Override
    public List<UserResponseDTO> retrieveAll() {
        return null;
    }


    @Override
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findByUserId(id);
        if (user == null) {
            return new UserResponseDTO(404);
        }
        return new UserResponseDTO(200, user.getUserId(), user.getUsername(), user.getFirstname(), user.getSurname());
    }
}
