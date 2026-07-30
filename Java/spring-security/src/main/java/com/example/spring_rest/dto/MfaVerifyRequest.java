package com.example.spring_rest.dto;

public class MfaVerifyRequest {
    private String userName;
    private String code;

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}