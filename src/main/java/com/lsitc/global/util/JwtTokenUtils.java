package com.lsitc.global.util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lsitc.global.common.SessionVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenUtils {
	
    private static String SECRET_KEY = "secretForFems";
    private static long TOKEN_VALID_TIME = 30 * 60 * 1000L;
    private static String COOKIE_ID = "FEMS_SESSION";
    private static int MAX_AGE;
    
	@Value("${jwt.secretKey:secretKeysecretKeysecretKeysecretKeysecretKeysecretKey}")
	private void setSecretKey(String secretKey) {
		SECRET_KEY = secretKey;
	};
	
	@Value("${jwt.tokenValidTime:30}")
	private void setTokenValidTime(int tokenValidTime) {
	    if(tokenValidTime < 0) {
	        //무제한일 경우 10년정도로 처리
            TOKEN_VALID_TIME =  10 * 365 * 24 * 60 * 60 * 1000L;
	    }else {
	        TOKEN_VALID_TIME = tokenValidTime * 60 * 1000L;
	    }
	};

	@Value("${jwt.cookieId:FEMS_SESSION}")
	private void setCookieId(String cookieId) {
		COOKIE_ID = cookieId;
	};

    @Value("${jwt.tokenValidTime:30}")
    private void setMaxAge(int tokenValidTime) {
        MAX_AGE = tokenValidTime * 60;
    };

    /**
     * @methodName  : createToken
     * @date        : 2021.02.19
     * @desc        : JWT 생성 후 반환
     * @param claims : JWT에 담을 claims
     * @return
     * @throws UnsupportedEncodingException 
     * @throws InvalidKeyException 
     */
    public static String createToken(Map<String, Object> claims) throws InvalidKeyException, UnsupportedEncodingException {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        
        Date now = Calendar.getInstance().getTime();
        return Jwts.builder()
                .setHeader(header) //header추가
                .setClaims(claims)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME)) // set Expire Time
                .setIssuedAt(now) // 토큰 발행 시간 정보
                // .signWith(SignatureAlgorithm.HS256, base64Key)
                .signWith(SignatureAlgorithm.HS256,// 사용할 암호화 알고리즘과
                        SECRET_KEY.getBytes("UTF-8")) // signature 에 들어갈 secret값 세팅
                .compact();
    }
    
    /**
     * @methodName  : getClaims
     * @date        : 2021.02.19
     * @desc        : token으로부터 claim을 추출한다.
     * @param token
     * @return
     * @throws UnsupportedEncodingException 
     * @throws IllegalArgumentException 
     * @throws MalformedJwtException 
     * @throws UnsupportedJwtException 
     * @throws ExpiredJwtException 
     * @throws SignatureException 
     */
    public static Claims getClaims(String token) throws SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, UnsupportedEncodingException {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes("UTF-8"))
                .parseClaimsJws(token)  //JWS = Signature 가 포함된 내용
                .getBody();          
        return claims;
    }
    
    /**
     * @methodName  : resolveToken
     * @date        : 2021.02.19
     * @desc        : Cookie의 token값을 가져오고, 로그인 하지 않은 경우 공백("")을 반환한다. 
     * @param request : HttpServletRequest
     * @return
     */
    public static String resolveToken(HttpServletRequest request) {
        String token = "";
        try {
        	// header에서 Authkey 값에서 Token 가져오기
        	String authKey = request.getHeader("AUTHENTICATION_" + COOKIE_ID); //authentication key
        	
        	if(authKey == null || "".equals(authKey)) {
	        	// 
	            Cookie[] cookies = request.getCookies();
	            for (Cookie cookie : cookies) {
	                if ( COOKIE_ID.equals(cookie.getName()) ) {
	                    token = cookie.getValue();
	                }
	            }
        	}else {
        		token = authKey;
        	}
        } catch ( NullPointerException e ) {
          //로그인 하지 않은 경우
            return "";
        }
        return token;
    }
    
    /**
     * @methodName  : setTokenOnResponse
     * @date        : 2021.02.19
     * @desc        : token을 response에 추가한다.
     * @param token : jwt token
     * @param response : HttpServletResponse
     */
    public static void setTokenOnResponse(String token, HttpServletResponse response) {
        //response에 쿠키 셋팅
        Cookie cookie = new Cookie(COOKIE_ID, token);
        cookie.setMaxAge(MAX_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /**
     * @methodName  : get
     * @date        : 2021.02.19
     * @desc        : session에 있는 key 객체를 반환한다.
     * @param key   : key값(userNo, comId, ...) 
     * @return
     */
    public static Object getSessionValue(String key) {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = resolveToken(servletRequest);

        Claims claims = null;
        try {
            claims = getClaims(token);
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | IllegalArgumentException | UnsupportedEncodingException e) {
            //로그인 하지 않은 경우
            e.printStackTrace();
        }
        
        return claims.getOrDefault(key, null);
    }
    
    /**
     * @methodName  : getMap
     * @date        : 2021.02.19
     * @desc        : session정보를 Map형태로 반환한다.
     * @return
     */
    public static Map<String, Object> getSessionMap() {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = resolveToken(servletRequest);
        Claims claims = null;
        Map<String, Object> sessionMap = new HashMap<>();
        try {
            claims = getClaims(token);
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | IllegalArgumentException | UnsupportedEncodingException e) {
            //로그인 하지 않은 경우
            return sessionMap;
        }
        
        for(String key : claims.keySet()) {
            sessionMap.put(key, claims.getOrDefault(key, null));
        }
        
        return sessionMap;
    }
    
    /**
     * @methodName  : getSessionVo
     * @date        : 2021.02.19
     * @desc        : 세션정보를 SessionVo형태로 반환한다.
     * @return
     */
    public static SessionVo getSessionVo() {
        try {
            return CommonUtils.convertMapToObject(getSessionMap(), SessionVo.class);
        } catch (Exception e) {
            // 로그인 하지 않은 경우
            return new SessionVo();
        }
        
    }
}
