package com.vikas.airline.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {


    //Secret key used to sign JWT tokens.
    private String secret;

    //Access token expiration in milliseconds.
    private long accessTokenExpiration;

    //Refresh token expiration in milliseconds.
    private long refreshTokenExpiration;

}