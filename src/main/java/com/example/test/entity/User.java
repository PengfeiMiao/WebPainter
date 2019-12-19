package com.example.test.entity;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private int login;
    private String loginHost;
    private String auth;
}
