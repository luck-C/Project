package com.luck.controller;

import com.luck.entity.User;
import com.luck.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public String login(User user, HttpSession session){
        //User login = userService.login(user);
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(user.getUsername(),user.getPassword()));

            return "redirect:/file/findAll";
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名错误！");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误！");
        }
        return "redirect:/index";

    }
    @PostMapping("register")
    public String login(User user){
        userService.register(user);
        return "redirect:/index";
    }
    @GetMapping("logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/index";
    }

}
