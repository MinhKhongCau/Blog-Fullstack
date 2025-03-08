package com.myproject.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.blog.Service.AccountService;
import com.myproject.blog.Service.AuthorityService;
import com.myproject.blog.Service.PostService;
import com.myproject.blog.Until.Constants.Json;
import com.myproject.blog.Until.Constants.ReturnStatus;

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
	public String demoMethod() {
		System.out.println(accountService.getAll());

		System.out.println("Result Query: " + postService.getAll());

		System.out.println(authorityService.getAll());

		ReturnStatus returnStatus = ReturnStatus.SENT;
		returnStatus.chargeMessage("This is demo");
		return Json.toJson(ReturnStatus.SENT.chargeMessage("this is demo"));
	}

}
