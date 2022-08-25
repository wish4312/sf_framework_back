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
package com.lsitc.global.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.lsitc.global.interceptor.RestLoggingInterceptor;

@Component
public class RestUtils {

    protected final static Logger logger = LoggerFactory.getLogger(RestUtils.class);

    //timeout(초)
    private static int TIMEOUT = 30;
    //G/W URL
    private static String GATEWAY_URL = "";
    
    @Value("${spring.gatewayUrl}")
    private void setGatewayUrl(String gatewayUrl) {
        GATEWAY_URL = gatewayUrl;
    };
    
    
    /**
     * @methodName  : getDefaultHttHeaders
     * @date        : 2021.02.24
     * @desc        : 기본 헤더를 만들어 반환한다.
     * @return
     */
    public static HttpHeaders getDefaultHttHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
    
    public static <T> ResponseEntity<T> requestRestCallResponse(String url, HttpMethod httpMethod, Map<String, String> bodyMap, Class<T> clazz) throws Exception{
        return requestRestCallResponse(url, httpMethod, getDefaultHttHeaders(), bodyMap, TIMEOUT, clazz);
    }
    
    public static <T> ResponseEntity<T> requestRestCallResponse(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Map<String, String> bodyMap ,int timeOutSec, Class<T> clazz) throws Exception{
        //restTemplate
        RestTemplate restTemplate = makeRestTemplate(timeOutSec);
        //requestEntity
        HttpEntity<?> requestEntity = new HttpEntity<>(bodyMap, httpHeaders);
        //response
        ResponseEntity<T> response = restTemplate.exchange(setDefaultUrl(url), httpMethod, requestEntity, clazz);

        return response;
    }
    
    /**
     * @methodName  : makeRestTemplate
     * @date        : 2021.02.24
     * @desc        : restTeplate를 반환한다.
     * @param timeOutSec
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    private static RestTemplate makeRestTemplate(int timeOutSec) throws NoSuchAlgorithmException, KeyManagementException {
        if( timeOutSec == 0 ) {
            timeOutSec = TIMEOUT;
        }
        
        //SSL위해 추가
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;//new X509Certificate[0];
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };  
        
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom()); 
        
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();   
        
        //factory
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        //https무시를 위해 추가
        factory.setHttpClient(httpClient);
        
        //setTimeout
        factory.setConnectTimeout(timeOutSec*1000);
        factory.setReadTimeout(timeOutSec*1000);
        
        //restTemplate
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
        restTemplate.setInterceptors(Collections.singletonList(new RestLoggingInterceptor()));
        return restTemplate;
    }
    

    public static <T> T  requestRestCall(String url, HttpMethod httpMethod, Map<String, Object> bodyMap, Class<T> clazz) throws Exception{
        return requestRestCall(url, httpMethod, getDefaultHttHeaders(), bodyMap, TIMEOUT, clazz);
    }
    
    public static <T> T  requestRestCall(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Map<String, Object> bodyMap, Class<T> clazz) throws Exception{
        return requestRestCall(url, httpMethod, httpHeaders, bodyMap, TIMEOUT, clazz);
    }
    
    /**
     * @methodName  : requestRestCall
     * @date        : 2021.02.24
     * @desc        : Object형태로 결과를 리터한다.
     * @param <T>
     * @param url
     * @param httpMethod HttpMethod.POST, HttpMethod.GET etc..
     * @param httpHeaders header정보를 세팅한다. 쿠키, contentType etc..
     * @param bodyMap Map<String, String>을 requestBody로 만들어준다.(nullable)  
     * @param timeOutSec 타임아웃
     * @param clazz : 변환될 Object
     * @return
     * @throws Exception
     */
    public static <T> T requestRestCall(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Map<String, Object> bodyMap ,int timeOutSec, Class<T> clazz) throws Exception{
        RestTemplate restTemplate = makeRestTemplate(timeOutSec);
        
        //requestEntity
        HttpEntity<?> requestEntity = new HttpEntity<>(bodyMap, httpHeaders);
        
        //response
        ResponseEntity<T> response = restTemplate.exchange(setDefaultUrl(url), httpMethod,requestEntity, clazz);

        return response.getBody();
    }
    

    public static <T> List<T> requestRestCallAsList(String url, HttpMethod httpMethod, Map<String, String> bodyMap, Class<T> clazz) throws Exception{
        return requestRestCallAsList(url, httpMethod, getDefaultHttHeaders(), bodyMap, TIMEOUT, clazz);
    }

    public static <T> List<T> requestRestCallAsList(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Map<String, String> bodyMap, Class<T> clazz) throws Exception{
        return requestRestCallAsList(url, httpMethod, httpHeaders, bodyMap, TIMEOUT, clazz);
    }
    
    /**
     * 
     * @methodName  : requestRestCallAsList
     * @date        : 2021.02.24
     * @desc        : List<Object>형태로 결과를 리터한다.
     *                https://luvstudy.tistory.com/103 참조
     * @param <T>
     * @param url
     * @param httpMethod
     * @param httpHeaders
     * @param bodyMap
     * @param timeOutSec
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> List<T> requestRestCallAsList(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Map<String, String> bodyMap ,int timeOutSec, Class<T> clazz) throws Exception{
        RestTemplate restTemplate = makeRestTemplate(timeOutSec);
        
        //requestEntity
        HttpEntity<?> requestEntity = new HttpEntity<>(bodyMap, httpHeaders);
        
        //GenericType을 변환하기 위함..
        Type type = (Class<T>) clazz; 
        ParameterizedType parameterizedType = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { type };
            }

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
        //response
        ResponseEntity<List<T>> response = restTemplate.exchange(
            setDefaultUrl(url), httpMethod, requestEntity, 
            new ParameterizedTypeReference<List<T>>() { @Override public Type getType() { return parameterizedType; } }
        );
        //FIXME 빈값으로 할까?
        return response != null ? response.getBody() : Collections.emptyList(); //null
    }
    
    private static String setDefaultUrl(String url) {
        if(!StringUtils.lowerCase(url).startsWith("http")) {
            //url이 http로 시작하지 않으면 API G/W를 바라보도록 수정.
            return GATEWAY_URL + url;
        } else {
            return url;
        }
    }
    
//    public static WebClient getWebclient(int connectionTimeout, int readTimeout, int writeTimeout) {
        
//        return WebClient
//                .builder()
//                    .clientConnector(
//                        new ReactorClientHttpConnector(
//                            HttpClient.create()
//                                      .secure(
//                                          ThrowingConsumer.unchecked(
//                                              sslContextSpec -> sslContextSpec.sslContext(
//                                                      SslContextBuilder.forClient()
//                                                                       .trustManager(InsecureTrustManagerFactory.INSTANCE)
//                                                                       .build()
//                                              )
//                                          )
//                        )
//                        .tcpConfiguration(
//                                client -> client.option(
//                                                ChannelOption.CONNECT_TIMEOUT_MILLIS, 120_000).doOnConnected(
//                                                        conn -> conn.addHandlerLast(new ReadTimeoutHandler(180))
//                                                                    .addHandlerLast(new WriteTimeoutHandler(180))
//                                                )
//                                )
//                        )
//                    )
//          );
        
        
//        TcpClient tcpClient = TcpClient.create()
//            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout) // Connection Timeout
//            .doOnConnected(connection ->
//                connection.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)) // Read Timeout
//                    .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS))); // Write Timeout
//
//        ClientHttpConnector connector = new ReactorClientHttpConnector(HttpClient.from(tcpClient));
//        
//        return WebClient.builder().clientConnector(connector)
//                .build();
        
        
//        TcpClient tcpClient = TcpClient.create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout) // Connection Timeout
//                .doOnConnected(connection ->
//                    connection.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)) // Read Timeout
//                        .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS))); // Write Timeout
//
//            ClientHttpConnector connector = new ReactorClientHttpConnector(HttpClient.from(tcpClient));
//            
//            return WebClient.builder().clientConnector(connector)
//                    .build();
        
//        HttpClient httpClient = HttpClient.create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
//                .responseTimeout(Duration.ofMillis(5000))
//                .doOnConnected(conn -> 
//                  conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
//                    .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)))
////                .wiretap(true)
//                ;
//                
//        return WebClient.builder()
//          .clientConnector(new ReactorClientHttpConnector(httpClient))
//          .build();
//
//        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
//                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50)).build();
//        exchangeStrategies.messageWriters().stream().filter(LoggingCodecSupport.class::isInstance)
//                .forEach(writer -> ((LoggingCodecSupport) writer).setEnableLoggingRequestDetails(true));
//        
//
//        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.create()
////                .secure(ThrowingConsumer.unchecked(sslContextSpec -> sslContextSpec.sslContext(
////                        SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build())))
//                .tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 120_000)
//                        .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(180))
//                                .addHandlerLast(new WriteTimeoutHandler(180))))))
//                .exchangeStrategies(exchangeStrategies)
//                  .filter(RestUtils.logRequest())
//                  .filter(RestUtils.logResponse())
////                .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
////                    logger.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
////                    clientRequest.headers()
////                            .forEach((name, values) -> values.forEach(value -> logger.debug("{} : {}", name, value)));
////                    return Mono.just(clientRequest);
////                })).filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
////                    clientResponse.headers().asHttpHeaders()
////                            .forEach((name, values) -> values.forEach(value -> logger.debug("{} : {}", name, value)));
////                    return Mono.just(clientResponse);
////                }))
//                .defaultHeader("user-agent",
//                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3")
//                .baseUrl("http://localhost:8080").build();
//    }
    
//    public static ExchangeFilterFunction logRequest() {
//        return (clientRequest, next) -> {
//            logger.debug("===========================request begin================================================");
//            logger.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
//            clientRequest.headers()
//                    .forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
//            logger.debug("==========================request end================================================");
//            return next.exchange(clientRequest);
//        };
//    }
//    public static ExchangeFilterFunction logResponse() {
//        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
//            logger.debug("============================response begin==========================================");
////            logger.debug("Response: {}", clientResponse.headers().asHttpHeaders().get("property-header"));
//            clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> logger.debug("{} : {}", name, value)));
////            logger.debug("Response: {}", clientResponse.bodyToMono(String.class));
////            logger.debug("Response body: {}", StreamUtils.copyToString(clientResponse., Charset.defaultCharset()));
//            Mono<ClientResponse> t = Mono.just(clientResponse);
//            logger.debug("=======================response end=================================================");
////            return Mono.just(clientResponse);
//            return t;
//          
//        });
//    }
}
