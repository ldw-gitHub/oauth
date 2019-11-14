package com.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String getLoginPage() {
		return "login";
	}
	@RequestMapping(value = {"","/","/index"},method = RequestMethod.POST)
	public String getIndexPage(ModelAndView view,String code) {
		view.addObject("name", "ldw");
		view.addObject("code", code);
		return "index";
	}
	
	@RequestMapping(value = "/indexs")
	public String getIndexsPage() {
		return "index";
	}
	
}
