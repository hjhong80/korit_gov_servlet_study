package com.korit.korit_gov_servlet_study.ch01;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/*
*
* // Servlet //
* 클라이언트의 요청을 처리 하고 그 결과를 반환하는 servlet class의 구현 규칙을 지킨 자바 웹 프로그래밍
*
* // 작업의 대략적인 흐름도 //
* 생성자(객체 생성) -> (최초 1회)init() 호출 -> (요청마다) service() 호출
* -> (요청된 http 메소드에 따라) doGet(), doPost()... 등등 호출
* -> (tomcat 서버 종료시) destroy() 호출
*
* - "컨테이너" : 톰캣 서버
* 1. init() -> servlet 초기화 메소드, "컨테이너"가 맨 처음 1번만 호출
*
* */

public class FirstServlet extends HttpServlet {
    public FirstServlet() {
        System.out.println("FirstServlet 생성자 호출");
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init() 메소드 호출 초기화");
        config.getServletContext().setAttribute("age", 27);
    }

    @Override
    public void destroy() {
        System.out.println("destroy() 메소드 호출 소멸");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
        System.out.println("service() 메소드 호출 요청 발생");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

