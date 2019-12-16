package com.example.test.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CommonFile {
    private Integer id;
    private String filename;
    private String filepath;
    private Date uploadtime;
    private String creator;
}
