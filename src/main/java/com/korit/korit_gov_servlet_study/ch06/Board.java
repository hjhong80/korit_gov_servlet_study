package com.korit.korit_gov_servlet_study.ch06;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Board {
    private int boardId;
    private String title;
    private String content;
    private String username;
}
