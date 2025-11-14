package com.korit.korit_gov_servlet_study.ch06;

import java.util.ArrayList;
import java.util.List;

public class BoardRepository {
    private static BoardRepository instance;
    private List<Board> boardList = new ArrayList<>();
    private int boardId = 1;

    private BoardRepository() {};

    public static BoardRepository getInstance() {
        if (instance == null) {
            instance = new BoardRepository();
            System.out.println(">>>>> 인스턴스 생성 <<<<<");
        }
        return instance;
    }

    public Board addBoard(BoardDto boardDto) {
        Board board = boardDto.toEntity();
        board.setBoardId(this.boardId++);
        boardList.add(board);
        boardList.forEach(System.out::println);
        return board;
    }

    public List<Board> getAllBoardList() {
        return this.boardList;
    }


}
