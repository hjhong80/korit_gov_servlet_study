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

@WebServlet("/ch03/users")
public class UserServlet extends HttpServlet {
    private UserRepository userRepository;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        userRepository = UserRepository.getInstance();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html");

        UserDto userDto = gson.fromJson(req.getReader(),UserDto.class);
//        System.out.println(userDto);

        User foundUser = userRepository.findByUsername(userDto);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

        if (foundUser != null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpServletResponse.SC_BAD_REQUEST)
                    .message("이미 존재하는 username 입니다.")
                    .build();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            String json = gson.toJson(errorResponse);
            resp.getWriter().write(json);
            return;
        }

        User user = userRepository.addUser(userDto.toEntity());

        SuccessResponse<User> successResponse = SuccessResponse.<User>builder()
                .status(HttpServletResponse.SC_OK)
                .message("사용자 등록이 성공하였습니다.")
                .body(user)
                .build();
        String json = gson.toJson(successResponse);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

//        StringBuilder sb = new StringBuilder();
//        BufferedReader reader = req.getReader();
//        String line;
//        while ((line = reader.readLine()) != null) sb.append(line).append("\n");
//
//        System.out.println(sb);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

        List<User> users = userRepository.getAllUser();

        SuccessResponse<List<User>> successResponse = SuccessResponse.<List<User>>builder()
                .status(HttpServletResponse.SC_OK)
                .message("조회에 성공하였습니다.")
                .body(users)
                .build();

        String json = gson.toJson(successResponse);
        resp.getWriter().write(json);

//        List<User> users = userRepository.getAllUser();
//        StringBuilder sb = new StringBuilder();
//        users.forEach(user -> sb.append(gson.toJson(user)));
//        resp.getWriter().write(json);
    }
}
