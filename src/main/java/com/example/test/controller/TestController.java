package com.example.test.controller;

import com.example.test.entity.User;
import com.example.test.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    UserService userService;

    @GetMapping("index")
    public String index(Model model){
        User user = userService.getUserById(1);
        model.addAttribute("user",user);
        return "index";
    }

    @GetMapping("login")
    public String login(Model model){
        User user = userService.getUserById(1);
        model.addAttribute("user",user);
        return "login";
    }

}
