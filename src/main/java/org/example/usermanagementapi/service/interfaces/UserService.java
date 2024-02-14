package org.example.usermanagementapi.service.interfaces;

import org.example.usermanagementapi.model.dto.AllUsersResponseDTO;
import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO create(CreateUserDTO createUserDTO);

    UserResponseDTO authenticate(String username, String password);

    AllUsersResponseDTO retrieveAll();

    UserResponseDTO findById(Long id);
}
