/*
* 상기 프로그램에 대한 저작권을 포함한 지적재산권은 LS ITC에 있으며,
* LS ITC가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
* LS ITC의 지적재산권 침해에 해당됩니다.
* (Copyright ⓒ 2021 LS ITC. All Rights Reserved| Confidential)               
* 
* You are strictly prohibited to copy, disclose, distribute, modify, or use
* this program in part or as a whole without the prior written consent of
* LS ITC Business unit. LS ITC Business unit., owns the intellectual property rights in
* and to this program.
* (Copyright ⓒ 2021 LS ITC Business unit. All Rights Reserved| Confidential)
* Author    : LS ITC
* Created   : 2021-04-23 17:58:11
*/

package com.lsitc.domain.auth.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lsitc.domain.common.menu.MenuMngSvc;
import com.lsitc.domain.base.service.SystemLogReadSvc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseResponse;
import com.lsitc.global.util.JwtTokenUtils;
import com.lsitc.global.util.CryptoUtils;
import com.lsitc.domain.auth.service.LoginInfoSvc;

import io.jsonwebtoken.security.InvalidKeyException;

@Controller
public class LoginInfoCtr{
    @Autowired
    private MenuMngSvc commMenuSvc;
    
    @Autowired
    private Environment env;
    private static String COOKIE_ID = "";
    private static String REDIRECT_URL = "";
    
    @Value("${jwt.cookieId:FEMS_SESSION}")
    private void setCookieId(String cookieId) {
        COOKIE_ID = cookieId;
    };
    
    
    @Value("${spring.gatewayUrl:http://apigw:9999}")
    private void setGatewayUrl(String gatewayUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("redirect:");
        sb.append(gatewayUrl);
        REDIRECT_URL = sb.toString();
    };

	@Autowired
	private LoginInfoSvc loginInfoSvc;
    @Autowired
    private SystemLogReadSvc systemLogReadSvc;

    @GetMapping("/login")
    public Object login(ModelAndView mav) {
        // profile 정보
        ArrayList<String> activeProfileArr = new ArrayList<>(Arrays.asList(env.getActiveProfiles()));
        if( activeProfileArr.indexOf("prod") >= 0 ) {
            //운영일때 profile 넣어준다.
            mav.addObject("profile", "prod");
        }
        mav.setViewName("login");
        return mav;
//        return "login";
    }
    
    @GetMapping("/sso")
    public String sso(HttpServletRequest request, RedirectAttributes redirect) {
        String userId = request.getHeader("SM_USER");
        //System.out.println("SSO로 들어오고" );
        //System.out.println(userId);
        if(StringUtils.isNotBlank(userId)) {
            HttpSession session = request.getSession();
            session.setAttribute("comId", "COM01");
            session.setAttribute("userId", userId);//userId
            session.setAttribute("flag", "AD");
            return REDIRECT_URL + "/login";
        } else {
            return REDIRECT_URL + "/login";
        }
    }

    @PostMapping("/login")
    //public Object loginCheck(@RequestBody Map<String, String> inputUserInfo, Response response) {
    public Object loginCheck(@RequestParam(defaultValue = "ko") String locale, 
                             @RequestParam String comId, @RequestParam String userId, 
                             @RequestParam(required = false) String userPswd, 
                             HttpServletResponse response, 
                             ModelAndView mav,
                             RedirectAttributes ra,
                             HttpSession session) throws InvalidKeyException, UnsupportedEncodingException {
        Map<String, String> inputUserInfo = new HashMap<String, String>();
        
        comId = session.getAttribute("comId") != null ? (String) session.getAttribute("comId") : comId;
        userId = session.getAttribute("userId") != null ? (String) session.getAttribute("userId") : userId;
        String flag = session.getAttribute("flag") != null ? (String) session.getAttribute("flag") : "";
        
        inputUserInfo.put("locale", locale);
        inputUserInfo.put("comId", comId);
        inputUserInfo.put("userId", userId);
        inputUserInfo.put("userPswd", userPswd);
       
        //clear
        session.setAttribute("comId", null);
        session.setAttribute("userId", null);
        session.setAttribute("flag", null);
        
        //사용자 조회
        Map<String, Object> loginInfo = loginInfoSvc.loginCheck(inputUserInfo);
        
        // profile 정보
        ArrayList<String> activeProfileArr = new ArrayList<>(Arrays.asList(env.getActiveProfiles()));
        
        //운영이 아닐때 SSO 미적용
    	
        if ( loginInfo == null ) {
            //로그인 실패
            Cookie cookie = new Cookie(COOKIE_ID, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            mav.addObject("resultCode", "N");
            mav.addObject("resultDesc", "로그인에 실패하였습니다.");
            mav.setViewName("login");
            return mav;
        }

        String userPswdChk= loginInfo.get("userPswd").toString();
        String userPswdCrypted= CryptoUtils.getSHA512(inputUserInfo.get("userPswd").toString());
        
        if ( loginInfo.get("userPswd").equals(userPswdCrypted) ){
            //비밀번호 삭제!
            loginInfo.remove("userPswd");
            //로그인 성공
            String token = JwtTokenUtils.createToken(loginInfo);
            //토큰을 쿠키에 셋팅
            JwtTokenUtils.setTokenOnResponse(token, response);
            //로그인 이력 저장
            systemLogReadSvc.saveLoginLog(loginInfo);
            return REDIRECT_URL;
        } else {
            //로그인 실패
            Cookie cookie = new Cookie(COOKIE_ID, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            mav.addObject("resultDesc", "로그인에 실패하였습니다.");
            mav.setViewName("login");
            return mav;
        }
    }
    @PostMapping("/loginProc")
    //public Object loginCheck(@RequestBody Map<String, String> inputUserInfo, Response response) {
    public Object loginProc(@RequestParam(defaultValue = "ko") String locale, 
                             @RequestParam String comId, @RequestParam String userId, 
                             @RequestParam(required = false) String userPswd, 
                             HttpServletResponse response, 
                             ModelAndView mav,
                             RedirectAttributes ra,
                             HttpSession session) throws InvalidKeyException, UnsupportedEncodingException {
        Map<String, String> inputUserInfo = new HashMap<String, String>();
        
        comId = session.getAttribute("comId") != null ? (String) session.getAttribute("comId") : comId;
        userId = session.getAttribute("userId") != null ? (String) session.getAttribute("userId") : userId;
        String flag = session.getAttribute("flag") != null ? (String) session.getAttribute("flag") : "";
        
        inputUserInfo.put("locale", locale);
        inputUserInfo.put("comId", comId);
        inputUserInfo.put("userId", userId);
        inputUserInfo.put("userPswd", userPswd);
       
        //clear
        session.setAttribute("comId", null);
        session.setAttribute("userId", null);
        session.setAttribute("flag", null);
        
        //사용자 조회
        Map<String, Object> loginInfo = loginInfoSvc.loginCheck(inputUserInfo);
        
        // profile 정보
        ArrayList<String> activeProfileArr = new ArrayList<>(Arrays.asList(env.getActiveProfiles()));
        
        //운영이 아닐때 SSO 미적용
    	
        if ( loginInfo == null ) {
            //로그인 실패
            Cookie cookie = new Cookie(COOKIE_ID, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            mav.addObject("resultCode", "N");
            mav.addObject("resultDesc", "로그인에 실패하였습니다.");
            mav.setViewName("login");
//            return mav;
            return REDIRECT_URL + "/login";
        }

        String userPswdChk= loginInfo.get("userPswd").toString();
        String userPswdCrypted= CryptoUtils.getSHA512(inputUserInfo.get("userPswd").toString());
        
        if ( loginInfo.get("userPswd").equals(userPswdCrypted) ){
            //비밀번호 삭제!
            loginInfo.remove("userPswd");
            //로그인 성공
            String token = JwtTokenUtils.createToken(loginInfo);
            //토큰을 쿠키에 셋팅
            JwtTokenUtils.setTokenOnResponse(token, response);
            //로그인 이력 저장
            systemLogReadSvc.saveLoginLog(loginInfo);
            return REDIRECT_URL;
        } else {
            //로그인 실패
            Cookie cookie = new Cookie(COOKIE_ID, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            mav.addObject("resultDesc", "로그인에 실패하였습니다.");
            mav.setViewName("login");
            return REDIRECT_URL + "/login";
        }
    }
    
    @RequestMapping("/loginChk")
    @ResponseBody
    public Object loginChk (HttpServletRequest request, HttpServletResponse response, @RequestBody BaseParam paramMap, HttpSession session)  throws InvalidKeyException, UnsupportedEncodingException {
        BaseResponse result = new BaseResponse();
        
        HashMap<String, Object> loingInfo = paramMap.getParams();
        Map<String, String> inputUserInfo = new HashMap<String, String>();

        inputUserInfo.put("locale", loingInfo.get("local") == null ? "ko" : loingInfo.get("local").toString());
        inputUserInfo.put("comId", loingInfo.get("comId").toString());
        inputUserInfo.put("userId", loingInfo.get("userId").toString());
        inputUserInfo.put("userPswd", loingInfo.get("userPswd").toString());
       
        //clear
        session.setAttribute("comId", null);
        session.setAttribute("userId", null);
        session.setAttribute("flag", null);
        
        //사용자 조회
        Map<String, Object> loginInfo = loginInfoSvc.loginCheck(inputUserInfo);
        
        if ( loginInfo == null ) {
            //로그인 실패
            Cookie cookie = new Cookie(COOKIE_ID, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            
            result.setRetnCd(-9001);
            result.setRetnMsg("로그인에 실패하였습니다.");
            return result;
        }

        String userPswdCrypted= CryptoUtils.getSHA512(inputUserInfo.get("userPswd").toString());

        if ( loginInfo.get("userPswd").equals(userPswdCrypted) ){
            // Spring Security ContextHolder 에 인증된 사용자 정보를 넣어줌.
            // 사용자에게 전달해 줄 session key 에 자동으로 userInfo를 맵핑해주어서 이후 sessionkey로 사용자 식별 가능.
            // TODO JwtToken으로 구성된 인증과정은 제거할 예정.
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                loginInfo, null, Collections.singletonList(new SimpleGrantedAuthority("admin")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //비밀번호 삭제!
            loginInfo.remove("userPswd");
            //로그인 성공
            String token = JwtTokenUtils.createToken(loginInfo);
            //토큰을 쿠키에 셋팅
            JwtTokenUtils.setTokenOnResponse(token, response);
            //로그인 이력 저장
            systemLogReadSvc.saveLoginLog(loginInfo);
            result.add(COOKIE_ID, token);
            result.add("userInfo", loginInfo);
            return result;
        } else {
            //로그인 실패
            Cookie cookie = new Cookie(COOKIE_ID, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            
            result.setRetnCd(-9001);
            result.setRetnMsg("로그인에 실패하였습니다.");
            return result;
        }
    }
    @RequestMapping("/isValidToken")
    @ResponseBody
    public Object isValidToken (HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> param)  throws InvalidKeyException, UnsupportedEncodingException {
        BaseResponse ret = new BaseResponse();
      //Cookies에서 JWT token을 받아온다.
        String token = JwtTokenUtils.resolveToken(request);
        if( "".equals(token) ) {
            //로그인 안되었고
            //FIXME 다국어 처리
//            throw new BisiExcp("로그인하지 않았습니다.");
//            return response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
        } else {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.(유효하지 않은 경우 Excetion발생..)
            String newToken;
            try {
                newToken = JwtTokenUtils.createToken(JwtTokenUtils.getClaims(token));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
            }
            
            if("/".equals(param.get("requestURI")) || loginInfoSvc.selectMenuAuth(param)) {
                //최초 페이지거나 유효한 권한이면..
                // 토큰이 유효하면 새로운 토큰을 발급합니다.
                JwtTokenUtils.setTokenOnResponse(newToken, response);
            } else {
                //유효하지 않으면..
                return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
            }
            
            return ret;
        }
    }
    
    @RequestMapping("/logout")
    public Object logout (HttpServletRequest request, HttpServletResponse response) throws IOException {
        //쿠키 삭제..
        Cookie cookie = new Cookie(COOKIE_ID, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return REDIRECT_URL + "/login";
    }
    
    @PostMapping("/saveChangePasswordBGR")
    @ResponseBody
    public Object loginCheck(@RequestBody Map<String, Object> paramMap) {
        BaseResponse ret = new BaseResponse();
        
        Map<String ,Object> resultMap   = new HashMap<String, Object>();
        try {
            ret.add("resultCode", resultMap.get("resultCode"));
            ret.add("resultDesc", resultMap.get("resultDesc"));
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            ret.add("resultCode", "N");
            ret.add("resultDesc", "처리도중 에러가 발생했습니다");
            System.out.println("NoClassDefFoundError ERROR CATCHED");
        }
        return ret;
    }
    

    @RequestMapping("/menuLogCont")
    @ResponseBody
    public Object menuLogCont(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> param)  throws InvalidKeyException, UnsupportedEncodingException {
        BaseResponse ret = new BaseResponse();
      //Cookies에서 JWT token을 받아온다.
        String token = JwtTokenUtils.resolveToken(request);
        if( "".equals(token) ) {
            //로그인 안되었고
            //FIXME 다국어 처리
//            throw new BisiExcp("로그인하지 않았습니다.");
//            return response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
        } else {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.(유효하지 않은 경우 Excetion발생..)
            String newToken;
            try {
                newToken = JwtTokenUtils.createToken(JwtTokenUtils.getClaims(token));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
            }
            
            if("/".equals(param.get("requestURI")) || loginInfoSvc.selectMenuAuth(param)) {
                //최초 페이지거나 유효한 권한이면..
                // 토큰이 유효하면 새로운 토큰을 발급합니다.
                JwtTokenUtils.setTokenOnResponse(newToken, response);
                systemLogReadSvc.saveMenuCnctLog(param.get("menuId").toString());
            } else {
                //유효하지 않으면..
                return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
            }
            
            return ret;
        }
    }
}