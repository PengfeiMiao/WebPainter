package com.example.test.controller;

import com.example.test.service.impl.TestService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {

    @Autowired
    TestService testService;

    @GetMapping("/get")
    public String test(){
        return new Gson().toJson(testService.getUserById(20151601));
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

}
