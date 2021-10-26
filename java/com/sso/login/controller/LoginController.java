package com.sso.login.controller;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import com.sso.login.pojo.User;
import com.sso.login.utils.LoginCacheUtil;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static Set<User> dbUsers;
	static {
		dbUsers = new HashSet<>();
		dbUsers.add(new User(0,"zhangsan","12345"));
		dbUsers.add(new User(1,"lisi","123456"));
		dbUsers.add(new User(2,"wangwu","1234567"));
	}
	@PostMapping
	public String doLogin(@RequestParam("username") String username,
						  @RequestParam("password") String password,
						  HttpSession session,
						  HttpServletResponse response) {
		// TODO Auto-generated method stub
		String target = (String) session.getAttribute("target");
		System.out.println("target:"+target);
		Optional<User> firstOptional = dbUsers.stream().filter(
				dbUser -> dbUser.getUsername().equals(username)
				&& dbUser.getPassword().equals(password)).findFirst();
		if(firstOptional.isPresent()) {
			//保存登录信息
			String token =  UUID.randomUUID().toString();
			System.out.println("token:"+token);
			Cookie cookie = new Cookie("TOKEN",token);
			cookie.setDomain("");
			response.addCookie(cookie);
			LoginCacheUtil.loginUser.put(token, firstOptional.get());
			
		}else {
			//登陆失败，返回登陆界面
			session.setAttribute("msg", "用户名或密码错误");
			return "/login";
		}
		
		return "redirect:"+target;
	}
	@GetMapping("info")
	@ResponseBody
	public ResponseEntity<User> getUserInfo(@RequestParam("token") String token){
		if(!StringUtils.isEmpty(token)) {
			User user = LoginCacheUtil.loginUser.get(token);
			return ResponseEntity.ok(user);
		}else {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		
	}

}
