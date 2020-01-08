package com.example.test.controller;

import com.example.test.bean.RespBean;
import com.example.test.entity.User;
import com.example.test.service.impl.UserService;
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
    UserService userService;

    @PostMapping("/login")
    public RespBean login(@RequestBody User user) {
        User res = userService.loginUser(user);
        if (res == null) {
            return RespBean.sendErrorMessage();
        }
        return RespBean.sendSuccessMessage(res);
    }

    @PostMapping("/logout")
    public RespBean logout(@RequestBody User user) {
        User res = userService.logoutUser(user);
        if (res == null) {
            return RespBean.sendErrorMessage();
        }
        return RespBean.sendSuccessMessage(res);
    }

    @PostMapping("/register")
    public RespBean register(@RequestBody User user) {
        User isExist = userService.getUserByName(user.getUsername());
        if (isExist != null) {
            return RespBean.sendErrorMessage("用户名已存在");
        }
        User res = userService.registerUser(user);
        if (res == null) {
            return RespBean.sendErrorMessage("注册失败");
        }
        return RespBean.sendSuccessMessage("注册成功", res);
    }
}
