package org.example.usermanagementapi.model.dto;


import lombok.Getter;

@Getter
public class CreateUserDTO {
    private final String username;
    private final String password;
    private final String firstname;
    private final String surname;

    public CreateUserDTO(String username, String password, String firstname, String surname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surname = surname;
    }

}