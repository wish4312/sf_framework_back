package com.lsitc.global.interceptor;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsitc.global.filter.ReadableHttpServletRequestWrapper;
import com.lsitc.global.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ControllerLoggingInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HandlerInterceptor.super.preHandle(request, response, handler);
    log.debug(
        "\n=== Request-{} ====\n" 
       +    "{} {}\n"
       +    "Headers : {}\n"
       +    "RequestParam : {}\n"
       +    "RequestBody : {}\n"
       + "==================================================\n",
        request.getRequestedSessionId(),
        request.getMethod(), request.getRequestURI(),
        getHeaders(request),
        getRequestParam(request),
        getRequestBody(request)
    );

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    log.debug(
        "\n=== Response-{} ====\n" 
       +    "HttpStatus : {}\n"
       +    "Headers : {}\n"
       +    "ResponseBody : {}\n"
       + "==================================================\n",
        request.getRequestedSessionId(),
        response.getStatus(),
        getHeaders(response),
        getResponseBody(response)
    );
  }

  private String getHeaders(HttpServletRequest request) {
    Map<String, String> headerMap = new HashMap<>();

    Enumeration<String> headerArray = request.getHeaderNames();
    while (headerArray.hasMoreElements()) {
      String headerName = headerArray.nextElement();
      headerMap.put(headerName, request.getHeader(headerName));
    }
    return JsonUtils.objectToJson(headerMap);
  }

  private String getHeaders(HttpServletResponse response) {
    Map<String, String> headerMap = new HashMap<>();

    Collection<String> headerArray = response.getHeaderNames();
    for (String headerName : headerArray) {
      headerMap.put(headerName, response.getHeader(headerName));
    }
    return JsonUtils.objectToJson(headerMap);
  }

  private String getRequestParam(HttpServletRequest request) throws IOException {
    ReadableHttpServletRequestWrapper cachingRequest = (ReadableHttpServletRequestWrapper) request;
    return JsonUtils.objectToJson(cachingRequest.getParameterMap());
  }

  private String getRequestBody(HttpServletRequest request) throws IOException {
    ReadableHttpServletRequestWrapper cachingRequest = (ReadableHttpServletRequestWrapper) request;
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readTree(cachingRequest.getContentAsByteArray()).toString();
  }

  private String getResponseBody(HttpServletResponse response) throws IOException {
    ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readTree(cachingResponse.getContentAsByteArray()).toString();
  }
}
