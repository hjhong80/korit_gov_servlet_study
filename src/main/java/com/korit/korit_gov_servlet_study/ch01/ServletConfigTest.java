package com.korit.korit_gov_servlet_study.ch01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletConfigTest extends HttpServlet {
    @Override
//    모든 서블릿 객체는 각각 독립된 생명주기를 가지고 있다.
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object age = req.getServletContext().getAttribute("age");
        System.out.println(age);
    }
}
