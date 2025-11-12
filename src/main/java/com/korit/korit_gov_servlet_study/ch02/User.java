package com.korit.korit_gov_servlet_study.ch02;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String username;
    private String password;
    private String name;
    private String email;
}
