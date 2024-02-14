package org.example.usermanagementapi.controller;

import org.example.usermanagementapi.model.dto.AllUsersResponseDTO;
import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserResponseDTO;
import org.example.usermanagementapi.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        UserResponseDTO userResponseDTO = userService.create(createUserDTO);
        return switch (userResponseDTO.getStatus()) {
            case 201 -> ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
            case 400 -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Username or Password");
            case 409 -> ResponseEntity.status(HttpStatus.CONFLICT).body("Username Already exists");
            default -> ResponseEntity.ok().build();
        };

    }

    @GetMapping(params = {"username", "password"})
    public ResponseEntity<?> authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        UserResponseDTO userResponseDTO = userService.authenticate(username, password);
        return switch (userResponseDTO.getStatus()) {
            case 200 -> ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
            case 404 ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with username %s does not exist", username));
            case 401 -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password is incorrect");
            default -> ResponseEntity.ok().build();
        };
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        AllUsersResponseDTO allUsersResponseDTO = userService.retrieveAll();
        return switch (allUsersResponseDTO.getStatus()) {
            case 200 -> ResponseEntity.status(HttpStatus.OK).body(allUsersResponseDTO);
            default -> ResponseEntity.ok().build();
        };
    }

    @GetMapping(params = "userId")
    public ResponseEntity<?> getUserById(@RequestParam("userId") Long userId) {
        UserResponseDTO userResponseDTO = userService.findById(userId);
        return switch (userResponseDTO.getStatus()) {
            case 200 -> ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
            case 404 ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with id %d does not exist", userId));
            default -> ResponseEntity.ok().build();
        };
    }
}