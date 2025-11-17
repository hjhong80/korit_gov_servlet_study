package com.korit.korit_gov_servlet_study.ch07.repository;

import com.korit.korit_gov_servlet_study.ch07.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch07.entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserRepository {
    private static UserRepository instance;
    private int UserId = 0;
    private List<User> userList;

    private UserRepository() {
        this.userList = new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User addUser(User user) {
        user.setUserId(UserId++);
        userList.add(user);
        return user;
    }

    public User findUserByUsername(String username) {
        return userList.stream().filter(i -> username.equals(i.getUsername())).findFirst().orElse(null);
    }

}
