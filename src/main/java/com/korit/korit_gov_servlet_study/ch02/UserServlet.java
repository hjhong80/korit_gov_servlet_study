package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/ch02/users")
public class UserServlet extends HttpServlet {
    private List<User> users;

    @Override
    public void init() throws ServletException {
        users = new ArrayList<>();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        User user = User.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .build();
        Map<String,String> error = validUser(user);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

//        에러가 발생하였을때의 응답
        if (!error.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(error);
            System.out.println("=============================================");
            System.out.println(error);
            System.out.println("=============================================");
            return;
        }

//        정상 입력일때의 응답
        users.add(user);

        System.out.println("=============================================");
        users.forEach(System.out::println);
        System.out.println("=============================================");

        resp.setStatus(201);
        resp.setContentType("text/plain");
        resp.getWriter().println("추가 성공!!!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        username으로 user 찾기
//        찾으면 유저 객체 응답(200), 없으면 "username은 존재하지 않습니다(404)."

        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        List<User> userList = users.stream()
                .filter(user -> user.getUsername().equals(req.getParameter("username")))
                .toList();

        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html");

        if (userList.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("username은 존재하지 않습니다.");
        } else {
            userList.forEach(System.out::println);
            resp.setStatus(HttpServletResponse.SC_OK);
            userList.forEach(user -> {
                try {
                    resp.getWriter().println(user + "<br>");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private Map<String,String> validUser(User user) {
        Map<String,String> error = new HashMap<>();

//        User 객체의 선언된 모든 필드(접근 제어자 무관)을 스트림으로 순회
        Arrays.stream(user.getClass().getDeclaredFields()).forEach(f -> {
//            private 필드에 접근 가능하도록 강제 허용
            f.setAccessible(true);
            String fieldName = f.getName();
            System.out.print(fieldName + "  :  ");

            try {
//                리플렉션으로 user인스턴스의 해당 필드값 꺼내기
                Object fieldValue = f.get(user);
                System.out.println(fieldValue);
//                필드가 빈칸이거나 null 이면 검증 실패로 간주하여 runtime exception 발생
                if (fieldValue == null) {
                    throw new RuntimeException();
                }
                if (fieldValue.toString().isBlank()) {
                    throw new RuntimeException();
                }
            } catch (IllegalAccessException e) {
//                필드 접근 권한 문제 (드물게 발생)
                System.out.println("잘못된 필드 접근 입니다.");
            } catch (RuntimeException e) {
//                예외를 받아서 해당 필드에 대한 에러 메세지 추가
                error.put(fieldName,"빈 값 일 수 없습니다.");
            }
        });

        return error;
    }
}
