package com.sparta.userapi.controller;

import com.sparta.userapi.entity.UserRoleEnum;
import com.sparta.userapi.jwtutil.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class AuthController {

    public static final String AUTHORIZATION_HEADER = "Authorization";      //AUTHORIZATION_HEADER  이름은 상수로 만들어 놓음

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/create-cookie")
    public String createCookie(HttpServletResponse res) {       //쿠키를 만드는 메서드
        addCookie("Robbie Auth", res);      //저장할 cookievalue? //띄어쓰기=공백이 있으면 쿠키에서 저장할 수 없다고 오류가 남

        return "createCookie";
    }

    @GetMapping("/get-cookie")
    public String getCookie(@CookieValue(AUTHORIZATION_HEADER) String value) {
        // HttpServletRequest에 들어있는 쿠키 중에서 Authorization이라는 이름으로 된 쿠키를
        // @CookieValue(AUTHORIZATION_HEADER)이 애노테이션을 통해서 가지고 온다!
        //@CookieValue 에 우리가 가지고 오고 싶은 (AUTHORIZATION_HEADER) 그 쿠키의 이름을 넣으면
        //String value 이 변수에 해당하는 그 쿠키의 값이 들어감 -> 예상값 "Robbie Auth"
        //Robbie Auth를  cookieValue쪽에 넣었으니깐! 그러면 (@CookieValue(AUTHORIZATION_HEADER) String value)
        //이걸 통해서 value에 해당하는 Robbie Auth 이 값이 들어와야함
        System.out.println("value = " + value);

        return "getCookie : " + value;

    }

    @GetMapping("/create-session")
    public String createSession(HttpServletRequest req) {
        // 세션이 존재할 경우 세션 반환, 없을 경우 새로운 세션을 생성한 후 반환
        HttpSession session = req.getSession(true);

        // 세션에 저장될 정보 Name - Value 를 추가합니다.
        session.setAttribute(AUTHORIZATION_HEADER, "Robbie Auth");

        return "createSession";
    }

    @GetMapping("/get-session")
    public String getSession(HttpServletRequest req) {
        // 세션이 존재할 경우 세션 반환, 없을 경우 null 반환
        HttpSession session = req.getSession(false);

        String value = (String) session.getAttribute(AUTHORIZATION_HEADER); // 가져온 세션에 저장된 Value 를 Name 을 사용하여 가져옵니다.
        System.out.println("value = " + value);

        return "getSession : " + value;
    }

    @GetMapping("/create-jwt")
    public String createJwt(HttpServletResponse res) {
        // Jwt 생성
        String token = jwtUtil.createToken("Robbie", UserRoleEnum.USER);

        // Jwt 쿠키 저장
        jwtUtil.addJwtToCookie(token, res);

        return "createJwt : " + token;
    }

    @GetMapping("/get-jwt")
    public String getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);

        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        String username = info.getSubject();
        System.out.println("username = " + username);
        // 사용자 권한
        String authority = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
        System.out.println("authority = " + authority);

        return "getJwt : " + username + ", " + authority;
    }

    public static void addCookie(String cookieValue, HttpServletResponse res) {     //쿠키를 저장하는 메서드
        try {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
            //URLEncoder.encode (첫번째에 encode할 값 넣어주고, 타입은 utf-8) replaceAll =전부 다 바꿔준다(공백을 = "//+" 이렇게 표헌하나..? 공백을 "%20"이렇게 바꿔준다)
            //spring에서 cookie 클래스를 만들수 있게끔  Cookie cookie .. 쿠키 클래스!
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, cookieValue); // (Name-Value)
            cookie.setPath("/");        //set 메서드 사용해서 Path 넣어주고,, MaxAge도..
            cookie.setMaxAge(30 * 60);

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

//HttpServletResponse // 서블릿에서 만들어준 Response 객체에 어떤 데이터를 담으면, 클라이언트로 자연스럽게 반환이 됨.
//우리가 만든 쿠키를 Reponse 객체에 담아야 됨.. , 근데 addCookie 라는 게 이미 존재한다.(쿠키를 만들 공간이 이미 만들어져 있음)
//HttpServletResponse res -> res! // res.addCookie 해서 거기에 우리가 만든 쿠키를 넣어주면 됨 (cookie) -> 그러면 쿠키가 잘 담김.
//우리가 만든 쿠키    Cookie cookie = new Cookie(AUTHORIZATION_HEADER, cookieValue);
//우리가 만든 쿠키    cookie.setPath("/");
//우리가 만든 쿠키    cookie.setMaxAge(30 * 60);