package com.korit.korit_gov_servlet_study.ch02;

/*
*
* List에 투두 저장
* 저장 요청시 쿼리 파라미터에서 값을 가져와 리스트에 저장
* 저장 전에 3가지 필드가 다 채워져 있는지 확인
* 조회 요청시 쿼리 파라미터가 없으면 전체 조회
* 있으면 title 로 단건 조회
* 해당 title 투두가 없으면 해당되는 contents가 없습니다.
* 빈값이 있으면 map에 "빈 값일 수 없습니다." 넣고 응답.(400)
*
* 있음 200
* 없음 404
* */


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/ch02/todolist")
public class TodoServlet extends HttpServlet {
    private List<Todo> todoList;

    @Override
    public void init() throws ServletException {
        todoList = new ArrayList<>();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String username = req.getParameter("username");

        Todo todo = Todo.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();

        System.out.println(todo.getTitle());
        System.out.println(todo.getContent());
        System.out.println(todo.getUsername());

        Map<String, String> error = validRequest(todo);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        if(!error.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(error);
            return;
        }

        todoList.add(todo);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("text/html");
        resp.getWriter().println("추가되었습니다." + "<br>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html");

        if (req.getParameter("title").isBlank() || req.getQueryString().isEmpty() || req.getQueryString().isBlank()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            for (Todo todo : todoList) resp.getWriter().println(todo + "<br>");
        } else {
            try {
                List<Todo> foundTodo = todoList.stream()
                        .filter(todo -> todo.getTitle().equals(req.getParameter("title")))
                        .toList();
                if (foundTodo.isEmpty()) throw new RuntimeException();
                else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    for (Todo todo : foundTodo) resp.getWriter().println(todo + "<br>");
                }
            } catch (RuntimeException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().println("해당 title의 content를 찾을 수 없습니다.");
            }
        }
    }


//    1. 반환할 맵 생성
//    2. Arrays.stream()을 사용하여 리플렉션 구현
//    3. getClass().getDeclaredFields() -> 클래스에서 선언된 필드들을 가져옴
//    4. (Field).setAccessible(true) -> private필드에 접근 가능하도록 설정
//    5. (Field).getName() -> 필드 네임 가져옴
//    6. (Object) = (Field).get(Object) -> 필드에서 오브젝트를 가져와 비었는지를 판별
    private Map<String, String> validRequest(Todo todo) {
        Map<String,String> error = new HashMap<>();

        Arrays.stream(todo.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            String fName = f.getName();
            try {
                Object fValue = f.get(todo);
                if (fValue == null || fValue.toString().isBlank()) {
                    throw new RuntimeException();
                }
            } catch (IllegalAccessException e) {
                System.out.println("잘못된 필드 접근 입니다.");
            } catch (RuntimeException e) {
                error.put(fName,"빈 값일 수 없습니다.");
            }
        });

        return error;
    }
}
