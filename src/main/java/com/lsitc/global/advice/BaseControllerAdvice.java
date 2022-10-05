package com.lsitc.global.advice;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.lsitc.global.common.BaseResponse;
import com.lsitc.global.error.exception.BisiExcp;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

/**
 * ExcpHandler
 */
//@ControllerAdvice
//@ConfigurationProperties
public class BaseControllerAdvice {
    //최대 허용 파일 사이즈
    private static String MAX_FILE_SIZE;
    //최대 허용 리퀘스트 사이즈
    private static String MAX_REQUEST_SIZE;
    
    @SuppressWarnings("static-access")
    public BaseControllerAdvice(
         @Value("${spring.servlet.multipart.max-file-size}") String maxFileSize
        ,@Value("${spring.servlet.multipart.max-request-size}") String maxRequestSize
    ) {
        this.MAX_FILE_SIZE = maxFileSize;
        this.MAX_REQUEST_SIZE = maxRequestSize;
    }
    
        
    @Autowired
    private Environment environment;
    
    Logger logger = LoggerFactory.getLogger(this.getClass()); 
    
    /**
     * 
     * @methodName  : BisiExcpProc
     * @date        : 2021.02.19
     * @desc        : BisiExcp.class 예외처리
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BisiExcp.class)
    @ResponseBody
    public Object BisiExcpProc(HttpServletRequest request, BisiExcp e) {      
        BaseResponse result = new BaseResponse();
        result.setRetnCd(e.getErrorCd());
        result.setRetnMsg(e.getErrorMsg());
        logger.error(e.toString());
        return result;
    }
    
    /**
     * 
     * @methodName  : expiredJwtExcpProc
     * @date        : 2021.02.19
     * @desc        : ExpiredJwtException.class 예외처리
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public Object expiredJwtExcpProc(HttpServletRequest request, ExpiredJwtException e) {      
        BaseResponse result = new BaseResponse();
        result.setRetnCd(-1);
        // FIXME 다국어처리
        result.setRetnMsg("토큰이 만료되었습니다.");
        logger.error(e.toString());
        return result;
    }
        
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public Object handleFileSizeLimitExceeded(Exception e) {
        BaseResponse result = new BaseResponse();
        result.setRetnCd(-1);
        // FIXME 다국어처리
        result.setRetnMsg("허용 파일 사이즈(" + MAX_FILE_SIZE + ")를 초과했습니다.");
        logger.error(e.toString());
        return result;
    }
    
    //FIXME max request size 초과할 때, 안되는것 같음
    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseBody
    public Object handleSizeLimitExceededExcpProc(SizeLimitExceededException e) {
        BaseResponse result = new BaseResponse();
        result.setRetnCd(-1);
        // FIXME 다국어처리
        result.setRetnMsg("허용 리퀘스트 사이즈(" + MAX_REQUEST_SIZE + ")를 초과했습니다.");
        logger.error(e.toString());
        return result;
    }
    
    /**
     * 
     * @methodName  : jwtExcpProc
     * @date        : 2021.02.19
     * @desc        : JwtException.class 예외처리
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(JwtException.class)
    @ResponseBody
    public Object jwtExcpProc(HttpServletRequest request, JwtException e) {      
        BaseResponse result = new BaseResponse();
        result.setRetnCd(-1);
        // FIXME 다국어처리
        result.setRetnMsg("토큰이 변조되었습니다.");
        logger.error(e.toString());
        return result;
    }
    
    /**
     * 
     * @methodName  : ExcpProc
     * @date        : 2021.02.19
     * @desc        : Exception.class 예외처리
     *                로컬일때 예외를 리턴하고, 운영에서는 메시지만 리턴한다. 
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object ExcpProc(HttpServletRequest request, Exception e) {   
        BaseResponse result = new BaseResponse();
        result.setRetnCd(-1);
        
        //현재 프로파일
        String[] profiles = environment.getActiveProfiles();

        if( ArrayUtils.contains(profiles, "local") ){
            //local일 경우만 메시지 보이게끔..
            result.setRetnMsg(e.getMessage());
            logger.error(e.getMessage(), e);
        } else {
            //FIXME 다국어 처리해주세요.
            result.setRetnMsg("에러가 발생하였습니다.");
            logger.error(e.getMessage());
        }
        
        return result;
    }
    
}