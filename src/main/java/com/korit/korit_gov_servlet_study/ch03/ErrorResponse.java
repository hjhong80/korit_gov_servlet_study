package com.korit.korit_gov_servlet_study.ch03;

import lombok.Builder;

import javax.servlet.http.HttpServletResponse;

@Builder
public class ErrorResponse {
    private int status = HttpServletResponse.SC_BAD_REQUEST;
    private String message;
}
