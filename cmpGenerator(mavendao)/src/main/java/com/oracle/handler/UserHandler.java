package com.oracle.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.oracle.service.UserService;
import com.oracle.vo.User;

@Controller
@SessionAttributes(names="user")
public class UserHandler {

	@Autowired
	UserService userService;
	
	@RequestMapping("/")
	public String defaultAction() {
		return "login";
	}
	
	
	@RequestMapping("/{path}")
	public String defaultPath(@PathVariable("path") String path) {		
		return path;
	}
	
	
	@RequestMapping("/login")
	public String login(User user,Map<String,Object> map) {
		
		User u=userService.login(user.getLoginname(), user.getLoginpwd());
		System.out.println("开始登录: "+u);
		if(u!=null) {
			System.out.println("登录成功； ");
			map.put("user",u);
			return "index";
		}else {
			return "redirect:/";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(User u) {
		System.out.println("已注销.."+u);
		//注销session;
		return "redirect:/";
	}
}
