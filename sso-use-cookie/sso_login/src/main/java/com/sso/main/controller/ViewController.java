package com.sso.main.controller;

import java.util.Map;

import javax.management.loading.PrivateClassLoader;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/view")
public class ViewController {
	@Autowired
	private RestTemplate restTemplate;
	private final String lOGIN_INFO_ADDRESS = "http://localhost:9090/view/login/info?token=";

	@GetMapping("/index")
	public String toIndex(@CookieValue(required = false, value = "TOKEN") Cookie cookie, HttpSession session) {
		if (cookie != null) {
			String token = cookie.getValue();
			if (!StringUtils.isEmpty(token)) {
				Map result = restTemplate.getForObject(lOGIN_INFO_ADDRESS + token, Map.class);
				session.setAttribute("loginUser", result);
			}
			;
		}

		return "index";
	}
}
