package com.tyroguide.main.service;

import java.util.HashMap;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubService {

	@GetMapping("/sub")
	public HashMap<String,Integer> subNumbers(@PathParam("num1") int num1, @PathParam("num2") int num2) {
		HashMap<String,Integer> result = new HashMap<String,Integer>();
		result.put("result",num1 - num2); 
		return result;
	}
	
}
