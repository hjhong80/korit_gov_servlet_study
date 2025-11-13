package com.korit.korit_gov_servlet_study.ch03;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TodoDto {
    public String title;
    public String content;
    public String username;

    public Todo toEntity() {
        return Todo.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();
    }
}
