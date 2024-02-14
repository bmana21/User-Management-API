package org.example.usermanagementapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username, passwordHash;
    private String firstname, surname;

    public User(String username, String passwordHash, String firstname, String surname) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstname = firstname;
        this.surname = surname;
    }

    protected User() {

    }

}