package org.example.usermanagementapi.model.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AllUsersResponseDTO {
    public static class SingleUser {
        public Long userId;
        public String username;
        public String firstname;
        public String surname;

        public SingleUser(Long userId, String username, String firstname, String surname) {
            this.userId = userId;
            this.username = username;
            this.firstname = firstname;
            this.surname = surname;
        }
    }

    private final int status;
    private final List<SingleUser> users;

    public AllUsersResponseDTO(int status) {
        this.status = status;
        users = new ArrayList<>();
    }

    public void addUser(Long userId, String username, String firstname, String surname) {
        this.users.add(new SingleUser(userId, username, firstname, surname));
    }

}
