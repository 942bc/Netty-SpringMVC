package org.kurt.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
public class TesController {
	
	@RequestMapping("/test")
	@ResponseBody
	public Object test(){
		Map map = new HashMap();
		map.put("hello", "world");
		return map;
	}
}
