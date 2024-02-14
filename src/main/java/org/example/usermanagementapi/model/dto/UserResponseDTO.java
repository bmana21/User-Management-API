package org.example.usermanagementapi.model.dto;


import lombok.Getter;

@Getter
public class UserResponseDTO {
    private final int status;
    private final Long userId;
    private final String username;
    private final String firstname;
    private final String surname;

    public UserResponseDTO(int status) {
        this.status = status;
        this.userId = 0L;
        this.username = "";
        this.firstname = "";
        this.surname = "";
    }

    public UserResponseDTO(int status, Long id, String username, String firstname, String surname) {
        this.status = status;
        this.userId = id;
        this.username = username;
        this.firstname = firstname;
        this.surname = surname;
    }

}