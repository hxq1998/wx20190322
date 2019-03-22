package org.hanxq.boot.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/main")
public class GatewayController {
	
	@RequestMapping(value = "/wx" ,method = RequestMethod.GET)
	@ResponseBody
	public String mainMethod()throws Exception{
		return "123";
	}

}
