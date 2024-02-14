package org.example.usermanagementapi.service;

import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserDTO;
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
    public boolean create(CreateUserDTO createUserDTO) {
        userRepository.save(new User(createUserDTO.getUsername(), createUserDTO.getPassword(), createUserDTO.getFirstname(), createUserDTO.getSurname()));
        return true;
    }

    @Override
    public List<UserDTO> retrieveAll() {
        return null;
    }

    @Override
    public UserDTO findByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO findById(String id) {
        return null;
    }
}
