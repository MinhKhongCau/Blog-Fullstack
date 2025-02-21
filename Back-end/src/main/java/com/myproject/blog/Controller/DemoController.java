package com.myproject.blog.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.blog.Model.Account;
import com.myproject.blog.Service.AccountService;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DemoController {
    @Autowired
    private AccountService accountService;

    @ResponseBody
    @GetMapping("demo")
    public String demoMethod() {
        return "hello world";
    }
    
}
