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
* Created   : 2021.03.12
*/
package com.lsitc.global.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsitc.global.annotation.Loggable;
import com.lsitc.global.util.JsonUtils;

@Component
public class ControllerLoggingInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * @methodName  : afterCompletion
     * @date        : 2021.03.12
     * @desc        : api호출 후 reqeust에 대한 로그를 남긴다.
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerMethod hm = (HandlerMethod)handler; 
        Method method = hm.getMethod();
        
        Loggable loggableMethod = method.getAnnotation(Loggable.class);
        
        if ( loggableMethod != null ) {
            //@Loggable annotaion이 있는 경우에만 로그를 남긴다.
            logger.debug("\n==================================================\n" +
                            "[{}]\n" +
                            "{} - {} {}\n" +
                            "Headers : {}\n" +
                            "Request : {}\n" +
                            "Response : {}\n"+
                            "==================================================\n",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    getHeaders(request),
                    getRequestBody(request),
                    getResponseBody(response));
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * @methodName  : getHeaders
     * @date        : 2021.03.12
     * @desc        : requestHeader를 json형태로 반환한다.
     * @param request
     * @return
     */
    private String getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        
        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return JsonUtils.objectToJson(headerMap);
    }

    /**
     * @methodName  : getRequestBody
     * @date        : 2021.03.12
     * @desc        : GET일때는 queryString, 이외에는 ReqeustBody를 JSON형태로 반환한다.
     * @param request
     * @return
     * @throws IOException
     */
    private String getRequestBody(HttpServletRequest request) throws IOException {
        ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        ObjectMapper objectMapper = new ObjectMapper();
        if( HttpMethod.GET.toString().equals(request.getMethod())) {
            //GET Method
            return JsonUtils.objectToJson(cachingRequest.getParameterMap());
        } else {
            //Other
            return objectMapper.readTree(cachingRequest.getContentAsByteArray()).toString();
        }
    }

    /**
     * @methodName  : getResponseBody
     * @date        : 2021.03.12
     * @desc        : 반환된 response를 리턴한다.
     * @param response
     * @return
     * @throws IOException
     */
    private String getResponseBody(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        ObjectMapper objectMapper = new ObjectMapper();
        //FIXME 향후 이 부분을 어떻게 정의할지..(특히 개인정보)
        return objectMapper.readTree(cachingResponse.getContentAsByteArray()).toString();
    }
}
