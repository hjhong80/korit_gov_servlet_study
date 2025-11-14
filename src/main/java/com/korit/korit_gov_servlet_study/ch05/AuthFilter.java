package com.korit.korit_gov_servlet_study.ch05;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

//@WebFilter("/ch05/mypage/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;

        System.out.println("[AuthFilter] 전처리 - 로그인 검증");

        Object principal = httpReq.getSession().getAttribute("principal");
        if (principal == null) {
            System.out.println("[AuthFilter] 로그인 안됨 -> 리디렉트");
            httpResp.sendRedirect(httpReq.getContextPath() + "/ch05/login");
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("[AuthFilter] 후처리 - 로그인 된 사용자의 응답 처리");
    }
}
