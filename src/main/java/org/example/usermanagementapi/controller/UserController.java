package org.example.usermanagementapi.controller;

import jakarta.servlet.http.HttpSession;
import org.example.usermanagementapi.model.dto.AllUsersResponseDTO;
import org.example.usermanagementapi.model.dto.CreateUserDTO;
import org.example.usermanagementapi.model.dto.UserResponseDTO;
import org.example.usermanagementapi.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        UserResponseDTO userResponseDTO = userService.create(createUserDTO);
        ResponseEntity<?> responseEntity;

        switch (userResponseDTO.getStatus()) {
            case 201:
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
                break;
            case 400:
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Username or Password");
                break;
            case 409:
                responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body("Username Already exists");
                break;
            default:
                responseEntity = ResponseEntity.ok().build();
                break;
        }

        return responseEntity;

    }

    @GetMapping(params = {"username", "password"})
    public ResponseEntity<?> authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        UserResponseDTO userResponseDTO = userService.authenticate(username, password);
        ResponseEntity<?> responseEntity;

        switch (userResponseDTO.getStatus()) {
            case 200:
                session.setAttribute("user", userResponseDTO);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
                break;
            case 401:
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password is incorrect");
                break;
            case 404:
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with username %s does not exist", username));
                break;
            case 500:
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while hashing password");
                break;
            default:
                responseEntity = ResponseEntity.ok().build();
                break;
        }

        return responseEntity;
    }

    @GetMapping(params = "token")
    public ResponseEntity<?> getAllUsers(@RequestParam("token") String token) {

        AllUsersResponseDTO allUsersResponseDTO = userService.retrieveAll(token);
        int status = allUsersResponseDTO.getStatus();

        ResponseEntity<?> responseEntity;
        switch (status) {
            case 200:
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(allUsersResponseDTO);
                break;
            case 401:
                responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Error: Token is not valid");
                break;
            default:
                responseEntity = ResponseEntity.ok().build();
                break;
        }

        return responseEntity;
    }

    @GetMapping(params = {"userId", "token"})
    public ResponseEntity<?> getUserById(@RequestParam("userId") Long userId, @RequestParam("token") String token) {

        UserResponseDTO userResponseDTO = userService.findById(userId, token);
        int status = userResponseDTO.getStatus();

        ResponseEntity<?> responseEntity;
        switch (status) {
            case 200:
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
                break;
            case 401:
                responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Error: Token is not valid");
                break;
            case 404:
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with id %d does not exist", userId));
                break;
            default:
                responseEntity = ResponseEntity.ok().build();
                break;
        }
        return responseEntity;
    }
}