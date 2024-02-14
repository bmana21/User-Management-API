package org.example.usermanagementapi.service;

import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserResponseDTO;
import org.example.usermanagementapi.model.entity.User;
import org.example.usermanagementapi.repository.UserRepository;
import org.example.usermanagementapi.service.interfaces.UserService;
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
        User newUser = new User(createUserDTO.getUsername(), createUserDTO.getPassword(), createUserDTO.getFirstname(), createUserDTO.getSurname());
        userRepository.save(newUser);
        return new UserResponseDTO(201, newUser.getUserId(), createUserDTO.getUsername(), createUserDTO.getFirstname(), createUserDTO.getSurname());
    }

    @Override
    public List<UserResponseDTO> retrieveAll() {
        return null;
    }

    @Override
    public UserResponseDTO findByEmail(String email) {
        return null;
    }

    @Override
    public UserResponseDTO findById(String id) {
        return null;
    }
}
