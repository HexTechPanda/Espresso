package com.espresso.commons.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * JWT Tools
 */
public class JwtUtil {

    // Valid for
    public static final Long JWT_TTL = 30 * 60 * 1000L;
    // Set key plaintext
    public static final String JWT_KEY = "926D96C90030DD58429D2751AC1BDBBC";

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }
    
    /**
     * create JWT
     * @param subject The data to be stored in the token (json format)
     * @return
     */
    public static String createJWT(String subject, Map<String, Object> claims) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID(), claims);// 设置过期时间
        return builder.compact();
    }

    /**
     * createJWT
     * @param subject The data to be stored in the token (json format)
     * @param ttlMillis token timeout
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis, Map<String, Object> claims) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID(), claims);
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid, Map<String, Object> claims ) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)   // Subject can be JSON data
                .setIssuer("espresso")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, JWT_KEY) // Sign using HS256 symmetric encryption algorithm, the second parameter is the secret key
                .setExpiration(expDate)
                .addClaims(claims);
    }

    /**
     * create token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis, Map<String, Object> claims) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id, claims); // Set expiration time
        return builder.compact();
    }


    /**
     * Generate encrypted secret key secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
    
    /**
     * parseJWT
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
//        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(JWT_KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }


}