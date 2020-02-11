package com.example.srpexample.models;

import lombok.Data;

import java.math.BigInteger;


@Data
public class CReg {
    private String nick;
    private String salt;
    private String verifier;
}
