package org.example.usermanagementapi.model.dto;


import lombok.Getter;

@Getter
public class UserDTO {
    private final String username;
    private final String passwordHash;
    private final String firstname;
    private final String surname;

    public UserDTO(String username, String passwordHash, String firstname, String surname) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstname = firstname;
        this.surname = surname;
    }

}