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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginInfoCtr{

    private static String REDIRECT_URL = "";

    @Value("${spring.gatewayUrl:http://apigw:9999}")
    private void setGatewayUrl(String gatewayUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("redirect:");
        sb.append(gatewayUrl);
        REDIRECT_URL = sb.toString();
    };
    
    @RequestMapping("/logout")
    public Object logout (HttpServletRequest request, HttpServletResponse response) throws IOException {
        //쿠키 삭제..
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return REDIRECT_URL + "/signin";
    }
}