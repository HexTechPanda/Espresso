package com.espresso.filter;

import com.alibaba.fastjson.JSONObject;
import com.espresso.commons.model.UserProfile;
import com.espresso.commons.utils.JwtUtil;
import com.espresso.commons.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class AccessTokenFilter implements GlobalFilter, Ordered {
    Logger logger = LoggerFactory.getLogger(getClass());

    private static final String[] white = { "/public/" };

    @Autowired
    RedisCache redisCache;

    /**
     * Check whether the token in the request header is valid,
     * check whether it exists in redis,
     * if it does not exist, it is invalid jwt.
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().pathWithinApplication().value();

        // Public API interface for release without authentication
        if(StringUtils.indexOfAny(path, white) != -1) {
            return chain.filter(exchange);
        }

        // error messages
        String message = null;

        // get token
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = StringUtils.substringAfter(authorization, "Bearer ");

        if(StringUtils.isEmpty(token)) {
            logger.info("Token not exist");
            message = "No authorization, please login.";
        }else{
            //2 parse token
            String userId;
            try {
                Claims claims = JwtUtil.parseJWT(token);
                userId = claims.getSubject();

                //3 get user profile
                UserProfile value = redisCache.getCacheObject("login:" + userId);
                if(Objects.isNull(value)) {
                    logger.info("Token has expired. {}", token);
                    message = "Your identity has expired, please re-authenticate!";
                }
            } catch (Exception e) {
                logger.error("Failed to parse token {}", token);
                message = "Invalid token.";
            }
        }

        if(message == null) {
            // If the token exists, pass
            return chain.filter(exchange);
        }

        // build error message object
        JSONObject result = new JSONObject();
        result.put("code", 1401);
        result.put("message", message);

        // Convert the response message content object to bytes
        byte[] bits = result.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE,  "application/json;charset=UTF-8");
        // return response object
        return response.writeWith( Mono.just(buffer) );
    }

    @Override
    public int getOrder() {
        // This AccessTokenFilter filter is executed after AuthenticationFilter
        return 10;
    }
}
