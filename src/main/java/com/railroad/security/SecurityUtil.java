package com.railroad.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class SecurityUtil {
    public static final String BEARER = "Bearer";
    @Getter
    private SecretKey securityKey;
    
    @PostConstruct
    private void init(){
        securityKey = generateKey();
    }

    private SecretKey generateKey() {
        return MacProvider.generateKey(SignatureAlgorithm.HS512);
    }

    public Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
