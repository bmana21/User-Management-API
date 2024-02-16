package org.example.usermanagementapi.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @GetMapping("/auth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Username or Password Format"),
            @ApiResponse(responseCode = "409", description = "Username Already exists")
    })
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        UserResponseDTO userResponseDTO = userService.create(createUserDTO);
        ResponseEntity<?> responseEntity;

        switch (userResponseDTO.getStatus()) {
            case 201:
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
                break;
            case 400:
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Username or Password Format");
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

    @GetMapping("/auth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Password is incorrect"),
            @ApiResponse(responseCode = "404", description = "User with username <username> does not exist"),
            @ApiResponse(responseCode = "500", description = "Error occurred while hashing password")
    })
    public ResponseEntity<?> authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        UserResponseDTO userResponseDTO = userService.authenticate(username, password);
        ResponseEntity<?> responseEntity;

        switch (userResponseDTO.getStatus()) {
            case 200:
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

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AllUsersResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Authentication Error: Api Key is not valid")
    })
    public ResponseEntity<?> getAllUsers(@RequestHeader("X-API-KEY") String token) {

        AllUsersResponseDTO allUsersResponseDTO = userService.retrieveAll(token);
        int status = allUsersResponseDTO.getStatus();

        ResponseEntity<?> responseEntity;
        switch (status) {
            case 200:
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(allUsersResponseDTO);
                break;
            case 401:
                responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Error: token is not valid");
                break;
            default:
                responseEntity = ResponseEntity.ok().build();
                break;
        }

        return responseEntity;
    }

    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Authentication Error: Api Key is not valid"),
            @ApiResponse(responseCode = "404", description = "User with id <userId> does not exist")
    })
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId, @RequestHeader("X-API-KEY") String token) {

        UserResponseDTO userResponseDTO = userService.findById(userId, token);
        int status = userResponseDTO.getStatus();

        ResponseEntity<?> responseEntity;
        switch (status) {
            case 200:
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
                break;
            case 401:
                responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Error: Api Key is not valid");
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