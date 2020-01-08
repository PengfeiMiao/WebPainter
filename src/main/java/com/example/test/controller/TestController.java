package com.example.test.controller;

import com.example.test.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    UserService userService;

    @GetMapping("index")
    public String index(){
        return "index";
    }

    @GetMapping("login")
    public String login(Model model){
        model.addAttribute("user","value");
        return "login";
    }

}
