package com.example.srpexample.jwt;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CLoginResponse {

    private String salt;
    private String B;

    public CLoginResponse(String salt, String b) {
        this.salt = salt;
        this.B = b;
    }
}
