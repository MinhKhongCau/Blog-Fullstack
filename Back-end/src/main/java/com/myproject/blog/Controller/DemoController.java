package com.myproject.blog.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.blog.Model.Account;
import com.myproject.blog.Model.Authority;
import com.myproject.blog.Model.Post;
import com.myproject.blog.Service.AccountService;
import com.myproject.blog.Service.AuthorityService;
import com.myproject.blog.Service.PostService;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DemoController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthorityService authorityService;

    @ResponseBody
    @GetMapping("demo")
    public List<Authority> demoMethod() {
        System.out.println(accountService.getAll());

        System.out.println("Result Query: "+postService.getAll());

        System.out.println(authorityService.getAll());
        return authorityService.getAll();
    }
    
}
