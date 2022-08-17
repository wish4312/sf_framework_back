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
* Created   : 2021.02.24
*/
package com.lsitc.core.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class RestLoggingInterceptor implements ClientHttpRequestInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * @methodName  : intercept
     * @date        : 2021.02.24
     * @desc        : REST API 호출 interceptor로 pre/post logging을 처리한다.
     * @param request
     * @param body
     * @param execution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        
        //preLogging
        requestLogging(request, body);
        //execute
        ClientHttpResponse response = execution.execute(request, body);
        //postLogging
        logResponse(response);
        return response;
    }
    
    /**
     * @methodName  : requestLogging
     * @date        : 2021.02.24
     * @desc        : RequestLogging처리
     * @param request
     * @param body
     * @throws IOException
     */
    private void requestLogging(HttpRequest request, byte[] body) throws IOException {
        //FIXME 이 부분을 수정해야 합니다.
        logger.debug("===========================request begin================================================");
        logger.debug("URI         : {}", request.getURI());
        logger.debug("Method      : {}", request.getMethod());
        logger.debug("Headers     : {}", request.getHeaders());
        logger.debug("Request body: {}", new String(body, Charset.defaultCharset()));
        logger.debug("==========================request end================================================");
    }

    /**
     * @methodName  : logResponse
     * @date        : 2021.02.24
     * @desc        : desc
     * @param response
     * @throws IOException
     */
    private void logResponse(ClientHttpResponse response) throws IOException {
        //FIXME 이 부분을 수정해야 합니다.
        logger.debug("============================response begin==========================================");
        logger.debug("Status code  : {}", response.getStatusCode());
        logger.debug("Status text  : {}", response.getStatusText());
        logger.debug("Headers      : {}", response.getHeaders());
        logger.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        logger.debug("=======================response end=================================================");
    }
}
