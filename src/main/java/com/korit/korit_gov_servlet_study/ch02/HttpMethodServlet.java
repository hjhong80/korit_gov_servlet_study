package com.korit.korit_gov_servlet_study.ch02;

/*
*
* // 멱등성 : 같은 작업을 수행하면 같은 결과를 도출하는 성질 //
*
* HTTP 프로토콜 메소드
* 1. GET
*  -. 용도 : 메소드 조회
*  -. 특징 : 서버로부터 데이터를 요청만 하고 수정하지 않음
*           요청 데이터(파라미터)가 url 에 포함됨
*           멱등성 있음
*
* 2. POST
*  -. 용도 : 새로운 리소스 생성 (데이터 수정, 변경 등등)
*  -. 특징 : 서버의 데이터를 전송하여 새로운 리소스를 생성
*           요청 데이터가 HTTP Body에 포함
*           멱등성 없음
*
* 3. PUT
*  -. 용도 : 리소스 전체의 수정 및 생성
*  -. 특징 : 리소스가 존재하면 리소스 전체를 교체, 없으면 신규 생성
*           전체 데이터를 전송해야 함
*           멱등성 있음
*
* 4. PATCH
*  -. 용도 : 리소스 부분 수정
*  -. 특징 : 리소스의 일부만 수정
*           변경할 필드만 전송하면 되므로 PUT보다 효율적
*           멱등성 없음
*
* 5. DELETE
*  -. 용도 : 리소스 삭제
*  -. 특징 : 지정된 리소스를 삭제
*           멱등성 있음
*
* 6. HEAD
*  -. 용도 : 리소스 존재 여부 또는 메타 데이터 확인
*
* // 프리플라이트 : 서버에 실제 요청하기 전 사전 점검 요청 //
* 7. OPTIONS
*  -. 용도 : HTTP 메소드의 존재 여부 또는 CORS 프리플라이트 요청에 사용
*
* 8. CONNECT
*  -. 용도 : 프록시 서버를 통한 터널링에 사용, SSL(인증서) 연결에 활용.
*
* 9. TRACE
*  -. 용도 : 디버깅
*
* */

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ch02/method")
public class HttpMethodServlet extends HttpServlet {
    Map<String,String> datas = new HashMap<>(Map.of(
            "name", "aaa",
            "age", "50",
            "address", "earth"
    ));


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET 요청 들어옴");
        System.out.println("요청 메소드 : " + req.getMethod());
        System.out.println("요청 쿼리 파라미터 (datasKey) : " + req.getParameter("datasKey"));
        String datasKey = req.getParameter("datasKey");

        System.out.println(datas.get(datasKey));

        System.out.println("요청 쿼리 파라미터 (datasKey2) : " + req.getParameter("datasKey2"));
        String datasKey2 = req.getParameter("datasKey2");

        System.out.println(datas.get(datasKey2));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST 요청 들어옴");
    }
}
