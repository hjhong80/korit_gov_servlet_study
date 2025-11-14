package com.korit.korit_gov_servlet_study.ch06;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.korit.korit_gov_servlet_study.ch03.SuccessResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch06/boards")
public class BoardServlet extends HttpServlet {
    private BoardRepository boardRepository = BoardRepository.getInstance();
    private Gson gson;

    @Override
    public void init() throws ServletException {
        boardRepository = BoardRepository.getInstance();
        gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(">>> 초기 접근 성공 <<<");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        List<Board> boardList = boardRepository.getAllBoardList();

        if (boardList.isEmpty()) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpServletResponse.SC_NOT_FOUND)
                    .message("게시물이 없습니다.")
                    .build();
            String json = gson.toJson(errorResponse);
            resp.setStatus(errorResponse.getStatus());
            resp.getWriter().write(json);
            return;
        } else {
            SuccessResponse<List<Board>> successResponse = SuccessResponse.<List<Board>>builder()
                    .status(HttpServletResponse.SC_OK)
                    .message("조회에 성공하였습니다.")
                    .body(boardList)
                    .build();
            String json = gson.toJson(successResponse);
            resp.setStatus(successResponse.getStatus());
            resp.getWriter().write(json);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        BoardDto boardDto = gson.fromJson(req.getReader(), BoardDto.class);
        System.out.println(boardDto);

        Board board = boardRepository.addBoard(boardDto);
//        System.out.println(board);
        if (board == null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpServletResponse.SC_BAD_REQUEST)
                    .message("잘못된 입력 입니다.")
                    .build();
            resp.setStatus(errorResponse.getStatus());
            String json = gson.toJson(errorResponse);
            System.out.println(json);
            resp.getWriter().write(json);
            return;
        } else {
            SuccessResponse<Board> successResponse = SuccessResponse.<Board>builder()
                    .status(HttpServletResponse.SC_OK)
                    .message("게시물 추가에 성공하였습니다.")
                    .body(board)
                    .build();
            resp.setStatus(successResponse.getStatus());
            String json = gson.toJson(successResponse);
            resp.getWriter().write(json);
        }
    }
}
