package com.korit.korit_gov_servlet_study.ch03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private List<User> users;
    private static UserRepository instance;
    private Integer userId = 1;

    private UserRepository() {
        users = new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User findByUsername(UserDto userDto) {
        return users.stream()
                .filter(user -> user.getUsername().equals(userDto.getUsername()))
                .findFirst()
                .orElse(null);
    }

    public User addUser(User user) {
        user.setUserId(this.userId++);
        users.add(user);
        return user;
    }

    public List<User> getAllUser() {
        return users;
    }
}
