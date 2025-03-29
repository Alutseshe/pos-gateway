package com.tijapay.posgateway.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "configs")
public class ApplicationProperties {

    private String mpesaStkEndpoint;
    private String mpesaAuthEndpoint;
    private String mpesaConsumerKey;
    private String mpesaSecretKey;
    private String mpesaBasicAuth;
    private String mpesaGrantType;
    private String mpesaPassword;
    private Long mpesaBusinessShortCode;
    private String mpesaCallBack;

    public String getMpesaStkEndpoint() {
        return mpesaStkEndpoint;
    }

    public void setMpesaStkEndpoint(String mpesaStkEndpoint) {
        this.mpesaStkEndpoint = mpesaStkEndpoint;
    }

    public String getMpesaAuthEndpoint() {
        return mpesaAuthEndpoint;
    }

    public void setMpesaAuthEndpoint(String mpesaAuthEndpoint) {
        this.mpesaAuthEndpoint = mpesaAuthEndpoint;
    }

    public String getMpesaConsumerKey() {
        return mpesaConsumerKey;
    }

    public void setMpesaConsumerKey(String mpesaConsumerKey) {
        this.mpesaConsumerKey = mpesaConsumerKey;
    }

    public String getMpesaSecretKey() {
        return mpesaSecretKey;
    }

    public void setMpesaSecretKey(String mpesaSecretKey) {
        this.mpesaSecretKey = mpesaSecretKey;
    }

    public String getMpesaBasicAuth() {
        return mpesaBasicAuth;
    }

    public void setMpesaBasicAuth(String mpesaBasicAuth) {
        this.mpesaBasicAuth = mpesaBasicAuth;
    }

    public String getMpesaGrantType() {
        return mpesaGrantType;
    }

    public void setMpesaGrantType(String mpesaGrantType) {
        this.mpesaGrantType = mpesaGrantType;
    }

    public String getMpesaPassword() {
        return mpesaPassword;
    }

    public void setMpesaPassword(String mpesaPassword) {
        this.mpesaPassword = mpesaPassword;
    }

    public Long getMpesaBusinessShortCode() {
        return mpesaBusinessShortCode;
    }

    public void setMpesaBusinessShortCode(Long mpesaBusinessShortCode) {
        this.mpesaBusinessShortCode = mpesaBusinessShortCode;
    }

    public String getMpesaCallBack() {
        return mpesaCallBack;
    }

    public void setMpesaCallBack(String mpesaCallBack) {
        this.mpesaCallBack = mpesaCallBack;
    }
}
