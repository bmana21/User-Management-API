package org.example.usermanagementapi.service.interfaces;

import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO create(CreateUserDTO createUserDTO);

    List<UserResponseDTO> retrieveAll();

    UserResponseDTO findByEmail(String email);

    UserResponseDTO findById(String id);
}
