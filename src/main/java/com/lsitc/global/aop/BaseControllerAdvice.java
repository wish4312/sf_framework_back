package com.lsitc.global.aop;

import com.lsitc.global.common.BaseResponse;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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
}