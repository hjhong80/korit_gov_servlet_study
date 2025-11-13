package com.korit.korit_gov_servlet_study.ch03;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/ch03/todolist")
public class TodoServlet extends HttpServlet {
    private TodoRepository todoRepository;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        todoRepository =  TodoRepository.getInstance();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

        TodoDto todoDto = gson.fromJson(req.getReader(),TodoDto.class);

        if (todoRepository.findByTitle(todoDto) != null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("동일한 title이 존재 합니다.")
                    .status(HttpServletResponse.SC_BAD_REQUEST)
                    .build();
            String json = gson.toJson(errorResponse);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(json);
        }
        else {
            Todo todo = todoRepository.addTodo(todoDto);
            SuccessResponse<Todo> successResponse = SuccessResponse.<Todo>builder()
                    .status(HttpServletResponse.SC_CREATED)
                    .message("content 추가에 성공하였습니다.")
                    .body(todo)
                    .build();
            String json = gson.toJson(successResponse);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(json);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

        List<Todo> todoList = todoRepository.getAllTodoList();
        if (todoList.isEmpty()) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpServletResponse.SC_BAD_REQUEST)
                    .message("리스트가 비어 있습니다.")
                    .build();
            String json = gson.toJson(errorResponse);
            resp.getWriter().write(json);
        } else {
            SuccessResponse<List<Todo>> successResponse = SuccessResponse.<List<Todo>>builder()
                    .status(HttpServletResponse.SC_OK)
                    .message("조회에 성공하였습니다.")
                    .body(todoList)
                    .build();
            String json = gson.toJson(successResponse);
            resp.getWriter().write(json);
        }
    }
}
