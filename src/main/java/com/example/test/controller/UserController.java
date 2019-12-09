package com.example.test.controller;

import com.example.test.bean.RespBean;
import com.example.test.entity.User;
import com.example.test.service.impl.TestService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mpf
 * @date 2019/12/9
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    TestService testService;

    @GetMapping("/getUser")
    public RespBean test(@RequestParam(value = "id") int id){
        return RespBean.sendSuccessMessage(testService.getUserById(id));
    }
}
