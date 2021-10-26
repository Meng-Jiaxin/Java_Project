package com.sso.login.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import com.sso.login.pojo.User;
import com.sso.login.utils.LoginCacheUtil;
@Controller
@RequestMapping("/view")
public class ViewController {
	@GetMapping("/login")
	public String toLogin(@RequestParam(required = false,value = "target") String  target,
				          HttpSession session,
				          @CookieValue(required = false,value = "TOKEN") Cookie cookie) {
		
		if(StringUtils.isEmpty(target)) {
//			System.out.println("target:"+target);
			target = "http://localhost:9090/view/login";
//			System.out.println("地址错误");
		}
		//已经登陆的用户再次访问，重定向
		if(cookie!=null) {
			String token = cookie.getValue();
			User user = LoginCacheUtil.loginUser.get(token);
			if(user!=null) {
				return "redirect:"+target;  //禁止重复登录
			}
		}
		
		//TODO:校验地址的合法性
		
		//重定向
		session.setAttribute("target", target);
		return "login";
	}
}
