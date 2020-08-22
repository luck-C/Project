package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("index")
    public String toIndex(){
        return "login";
    }
    @RequestMapping("showAll")
    public String toMain(){
        return "showAll";
    }
    @GetMapping("register")
    public String toLogin(){
        return "register";
    }
}
