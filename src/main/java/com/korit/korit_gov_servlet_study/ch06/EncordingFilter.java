package com.korit.korit_gov_servlet_study.ch06;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter("/ch06/*")
public class EncordingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[EncodingFilter] 초기 생성");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("[EncodingFilter] 필터 입력");
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("[EncodingFilter] 필터 출력");
    }

    @Override
    public void destroy() {
        System.out.println("[EncodingFilter] 소멸");
    }
}
