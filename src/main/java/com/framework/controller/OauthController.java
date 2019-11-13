package com.framework.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OauthController {

	@GetMapping("/getSms")
	public Map<String,Object> getSms(String code) {
		Map<String,Object> rtMap = new HashMap<String,Object>();
		rtMap.put("name", "ldw");
		return rtMap;
	}

}
