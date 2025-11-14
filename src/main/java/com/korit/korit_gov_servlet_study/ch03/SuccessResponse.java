package com.korit.korit_gov_servlet_study.ch03;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;

@Data
@Builder
public class SuccessResponse<T> {
    private int status = HttpServletResponse.SC_OK;
    private String message;
    private T body;
}
