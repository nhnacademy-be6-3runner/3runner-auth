package com.nhnacademy.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class TokenController {
	@ResponseBody
	@GetMapping("/test")
	public String test() {
		return "hello";
	}
}
