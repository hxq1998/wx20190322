package org.hanxq.boot.controller;

import java.util.HashMap;
import java.util.Map;

import org.hanxq.boot.dto.WeChartUser;
import org.hanxq.boot.service.TemplateService;
import org.hanxq.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/main")
public class GatewayController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TemplateService templateService;
	
	@RequestMapping(value = "/wx.do" ,method = RequestMethod.GET)
	@ResponseBody
	public String mainMethod(String signature,String timestamp,String nonce,String echostr)throws Exception{
		System.out.println("微信访问了!");
		return echostr;
	}
	
	
	@RequestMapping(value = "/opt.do",method = RequestMethod.GET )
	public String main() {
		System.out.println("进入操作页面");
		return "main";
	}
	
	@RequestMapping(value = "/users.do",method = RequestMethod.GET )
	@ResponseBody
	public Object getUserList() {
		
		JSONObject users = userService.getUsers("user");
		return users;
	}
	
	
	@RequestMapping(value = "/user.do",method = RequestMethod.GET )
	@ResponseBody
	public Object getUser(String oppenId) {
		
		WeChartUser user = userService.getUserByOppenId(oppenId);
		return user;
	}
	
	@RequestMapping(value="notice.do",method = RequestMethod.GET)
	@ResponseBody
	public Object notice(String user,String first,String keyword1,String keyword2,String keyword3,String keyword4,String remark) {
		System.out.println(user + ":" + first + ":" + keyword1 + ":" +  keyword2 + ":" + keyword3 + ":" + keyword4 + ":" +  remark);
		Map<String, String> result = new HashMap<String, String>();
		// 通过id获取所有员工的信息
		// 先通过user 获得openid
		WeChartUser u = userService.getByNickName(user);
		if ( null != u && null != u.getOppenId()) {
			// 推送消息 "货物派送通知"
			System.out.println(String.format("发送给%s用户", u.getOppenId()));
			templateService.push("", u.getOppenId(), first, keyword1, keyword2, keyword3, keyword4, remark);
			// id abZXJK8tapct4BWTt7ZaS1wpO-0Gu9oaryIzACz9xQ4
		}
		result.put("success", "0054");
		return result;
	}
	
	@RequestMapping(value = "/getUserByNickName.do",method = RequestMethod.GET )
	@ResponseBody
	public Object getUserByNickName(String nickName) {
		
		WeChartUser user = userService.getByNickName(nickName);
		if ( null != user && null != user.getOppenId()) {
			// 推送消息 "货物派送通知"
			
			// id abZXJK8tapct4BWTt7ZaS1wpO-0Gu9oaryIzACz9xQ4
		}
		return user;
	}
	

}
