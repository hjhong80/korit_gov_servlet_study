package com.korit.korit_gov_servlet_study.ch07;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.korit.korit_gov_servlet_study.ch07.dto.ResponseDto;
import com.korit.korit_gov_servlet_study.ch07.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch07.entity.User;
import com.korit.korit_gov_servlet_study.ch07.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch07/users")
public class UserServlet extends HttpServlet {
    UserService userService;
    Gson gson;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("[UserServlet] 초기 생성 성공");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST 작업 시작");
        resp.setContentType("application/json");
        SignupReqDto signupReqDto = gson.fromJson(req.getReader(),SignupReqDto.class);
        User user = userService.signin(signupReqDto);
        System.out.println(signupReqDto);
        System.out.println(user);
        if (user == null) {
            System.out.println("사용자 추가에 실패하였습니다.");
            ResponseDto<User> responseDto = ResponseDto.<User>builder()
                    .status(HttpServletResponse.SC_BAD_REQUEST)
                    .message("사용자 추가에 실패하였습니다.")
                    .body(null)
                    .build();
            resp.setStatus(responseDto.getStatus());
            resp.getWriter().write(gson.toJson(responseDto));
        } else {
            System.out.println("사용자가 추가되었습니다.");
            ResponseDto<User> responseDto = ResponseDto.<User>builder()
                    .status(HttpServletResponse.SC_OK)
                    .message("가입에 성공하였습니다.")
                    .body(user)
                    .build();
            resp.setStatus(responseDto.getStatus());
            resp.getWriter().write(gson.toJson(responseDto));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String username = req.getParameter("username");
        String json;

        if (username != null) {
            User foundUser = userService.getUserByUsername(req.getParameter("username"));
            ResponseDto<User> responseDto;
            if (foundUser == null) {
                System.out.println("사용자 검색에 실패하였습니다.");
                responseDto = ResponseDto.<User>builder()
                        .status(HttpServletResponse.SC_BAD_REQUEST)
                        .message("사용자 추가에 실패하였습니다.")
                        .body(null)
                        .build();
            } else {
                System.out.println("사용자가 검색되었습니다.");
                responseDto = ResponseDto.<User>builder()
                        .status(HttpServletResponse.SC_OK)
                        .message("검색에 성공하였습니다.")
                        .body(foundUser)
                        .build();
            }
            resp.setStatus(responseDto.getStatus());
            json = gson.toJson(responseDto);
            resp.getWriter().write(json);
            return;
        }

        List<User> allUser = userService.getUserList();
        ResponseDto<List<User>> responseDto;
        System.out.println("전체 사용자 검색");
        if (allUser.isEmpty()){
            responseDto = ResponseDto.<List<User>>builder()
                    .status(HttpServletResponse.SC_NOT_FOUND)
                    .message("리스트가 없습니다.")
                    .body(null)
                    .build();
        } else {
            responseDto = ResponseDto.<List<User>>builder()
                    .status(HttpServletResponse.SC_OK)
                    .message("전체 검색 성공")
                    .body(allUser)
                    .build();
        }
        json = gson.toJson(responseDto);
        resp.setStatus(responseDto.getStatus());
        resp.getWriter().write(json);
    }
}
