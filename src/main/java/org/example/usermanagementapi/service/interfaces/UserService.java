package org.example.usermanagementapi.service.interfaces;

import org.example.usermanagementapi.model.dto.AllUsersResponseDTO;
import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO create(CreateUserDTO createUserDTO);

    UserResponseDTO authenticate(String username, String password);

    AllUsersResponseDTO retrieveAll(String token);

    UserResponseDTO findById(Long id, String token);
}
