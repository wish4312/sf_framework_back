/*
* 상기 프로그램에 대한 저작권을 포함한 지적재산권은 K-FEMS 사업단에 있으며,
* LS와 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
* LS의 지적재산권 침해에 해당됩니다.
* (Copyright ⓒ 2020 K-FEMS 사업단. All Rights Reserved| Confidential)               
* 
* You are strictly prohibited to copy, disclose, distribute, modify, or use
* this program in part or as a whole without the prior written consent of
* LS. LS owns the intellectual property rights in
* and to this program.
* (Copyright ⓒ 2020 LS. All Rights Reserved| Confidential)
* Created   : 2021.02.26
*/
package com.lsitc.global.error.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lsitc.global.common.BaseResponse;

@Controller
@ConfigurationProperties
public class CustomErrorCtr implements ErrorController {
    private static final String PATH = "/error";
    
    @RequestMapping(PATH)
    public Object error(HttpServletRequest request) {
        //404일때만 이곳에 올 것이다.
        if ( MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType()) ) {
            BaseResponse result = new BaseResponse();
            result.setRetnCd(-1);
            //FIXME 다국어 처리
            result.setRetnMsg("문제가 발생하였습니다." + PATH);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
//            return "error/404.html";
            //return new ModelAndView("/static/error/404.html");
            return "redirect:http://localhost:9999/error/404.html";
        }
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}