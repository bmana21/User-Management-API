package org.example.usermanagementapi.service.interfaces;

import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    boolean create(CreateUserDTO createUserDTO);

    List<UserDTO> retrieveAll();

    UserDTO findByEmail(String email);

    UserDTO findById(String id);
}
