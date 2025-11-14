package com.korit.korit_gov_servlet_study.ch04;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/ch04/*")
public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        필터를 처음 호출 되었을때 생성
        System.out.println("필터 초기화");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        본체
        System.out.println("필터(전처리) : 요청 입력");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("필터(후처리) : 응답 출력");
    }

    @Override
    public void destroy() {
//        호출되면 필터가 사라짐
        System.out.println("필터 소멸");
    }
}
