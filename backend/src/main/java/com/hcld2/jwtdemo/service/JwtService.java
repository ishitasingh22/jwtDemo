package com.hcld2.jwtdemo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final long expireMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expireMs) {

        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).build();
        this.expireMs = expireMs;
    }

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireMs))
                .sign(algorithm);
    }

    public String verifyAndGetUsername(String token) {
        try {
            return verifier.verify(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String validateToken(String token) {
        return verifyAndGetUsername(token);
    }
}