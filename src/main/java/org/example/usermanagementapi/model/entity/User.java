package org.example.usermanagementapi.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username, passwordHash, token;
    private String firstname, surname;

    public User(String username, String passwordHash, String token,String firstname, String surname) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.token = token;
        this.firstname = firstname;
        this.surname = surname;
    }

    protected User() {

    }

}