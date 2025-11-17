package com.korit.korit_gov_servlet_study.ch07.service;

import com.korit.korit_gov_servlet_study.ch07.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch07.entity.User;
import com.korit.korit_gov_servlet_study.ch07.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    private static UserService instance;

    private UserService() {
        this.userRepository = UserRepository.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User signin(SignupReqDto signupReqDto) {
        if (getUserByUsername(signupReqDto.getUsername()) != null) return null;
        return userRepository.addUser(signupReqDto.toEntity());
    }

    public List<User> getUserList() {
        List<User> userList = userRepository.getUserList();
        userList.forEach(System.out::println);
        return userList;
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
