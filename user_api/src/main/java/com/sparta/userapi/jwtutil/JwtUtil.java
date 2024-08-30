package com.sparta.userapi.jwtutil;

import com.sparta.userapi.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.security.Key;
import java.util.Date;

@Component      //bean으로 등록
public class JwtUtil {  //다른 객체에 의존하지 않고, 하나의 모듈로서 동작하는 클래스(util)
    //jwt 데이터

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    /*값 앞에 붙어있으면 해당하는 값의 토큰'이라는 규칙,
    그리고 Bearer  -> 다음에 한 칸 띄움, 왜냐 bearer 다음 한 칸 띄우고, 토큰 값을 줘서 , 구별!! */

    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    //jwt 생성 = 토큰 생성
/*
1. jwt 토큰을 response header에 담아서 내 보낼수도 있고,
-> 쿠키를 따로 만들 필요 x , 그냥 토큰 자체를 헤더에 넣으면 됨.
2. 쿠키 객체를 만들어서 그 쿠키를 response 객체에 담는 방법
-> 쿠키 자체에 완료 기한을 줄 수 있음, header에 setCookie 라는 이름으로 넘어가면서, 자동으로 쿠키가 저장된다
*/
    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }


    //생성된 jwt를 cookie에 저장

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    //cookie에 들어있던 jwt 토큰을 substring

    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7); //bearer_ 이 공백까지하면 7글자니깐 이렇게 자르면 순수한 토큰값만 나옴
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    //jwt 검증

    // 토큰 검증
/*
substring 을 사용해서 자른 순수한 토큰을 받아온 다음
1번 한줄이면, 토큰이 만료되었는지, 위변조 되었는 지 검증가능
try catch문을 사용해서 세세하게 토큰이 어떠한 오류가 발생했는지  세세한 오류를 잡아주려고 아래와 같이 작성됨.
* */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); //1
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }


    // 토큰에서 사용자 정보 가져오기
/*
getBody() -> body 부분의  claims를 가져올 수 잇다?
* */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}

