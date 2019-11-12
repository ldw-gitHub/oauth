package com.framework.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OauthController {

	@RequestMapping(value = "/loginPage",method = RequestMethod.GET)
	public String getLoginPage(ModelAndView view,String code) {
		System.out.println("code = " + code);
		view.addObject("name", "ldw");
		view.addObject("code", code);
		return "login";
	}
	
	@PostMapping("/getSms")
	@ResponseBody
	public Map<String,Object> getSms(String code) {
		System.out.println("code = " + code);
		Map<String,Object> rtMap = new HashMap<String,Object>();
		rtMap.put("name", "ldw");
		return rtMap;
	}

}
