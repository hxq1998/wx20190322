package org.hanxq.boot.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hanxq.boot.controller.accesstoken.AccessToken;
import org.hanxq.boot.dto.WeChartUser;
import org.hanxq.boot.service.UserService;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class UserServiceImpl implements UserService {
	
	private String URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";
	
	private volatile List<WeChartUser> users = new ArrayList<WeChartUser>();
	
	/**
	 * 构造方法来刷新user
	 */
	public UserServiceImpl() {
		// flushUsers();
	}

	/**
	 * 刷新user对象
	 */
	public void flushUsers() {
		this.setUsers(buildAllUsers());
	}

	@Resource
	private AccessToken token;
	
	public void setUsers(List<WeChartUser> users) {
		this.users = users;
	}

	/**
	 * 获取用户列表
	 */
	@Override
	public List<WeChartUser> getUsers() {
		// TODO Auto-generated method stub
		String access_token = token.getAccess_token();
		String url_ = URL + access_token;
		
		return null;
	}

	@Override
	public JSONObject getUsers(String l1) {
		String access_token = token.getAccess_token();
		String url_ = URL + access_token;
		
		// httpclient post
		HttpPost httpPost = new HttpPost(url_);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		JSONObject obj = null;
		// json方式
		try {
			CloseableHttpResponse resp = client.execute(httpPost);
			if(resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				respContent = EntityUtils.toString(he,"UTF-8");
				// System.out.println(String.format("输出的结果%s", respContent));
				obj = JSONArray.parseObject(respContent);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	private JSONObject buildParam() {
		
		JSONObject json =  new JSONObject();
		
		return null;
	}

	@Override
	public WeChartUser getUserByOppenId(String oppenId) {
		// List<WeChartUser> users = buildAllUsers();
		System.out.println("进入getUserByOppenId");
		if ( null == users || users.isEmpty()) {
			// 调用刷新方法
			System.out.println("users is empty");
			flushUsers();
		}
		String demoId = "owDIy1Vi2BL8IBO9gaWL92swCBx4"; // 小如如如如_
		//WeChartUser user = getOne(oppenId);
		
		for (WeChartUser u : users) {
			if(u.getOppenId().equals(oppenId))
				return u;
		}
		
		return getOne(oppenId);
	}

	private WeChartUser getOne(String oppenId) {
		WeChartUser user = new WeChartUser();
		
		String url_ = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" +token.getAccess_token() + "&openid=" + oppenId + "&lang=zh_CN";
		HttpPost httpPost = new HttpPost(url_);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		JSONObject obj = null;
		// json方式
		try {
			CloseableHttpResponse resp = client.execute(httpPost);
			if(resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				respContent = EntityUtils.toString(he,"UTF-8");
				// System.out.println(String.format("输出的结果%s", respContent));
				obj = JSONArray.parseObject(respContent);
				// 或许信息
				user.setNickName(obj.getString("nickname"));
				user.setOppenId(obj.getString("openid"));
				user.setCity(obj.getString("city"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

	private List<WeChartUser> buildAllUsers() {
		List<WeChartUser> userList = new ArrayList<WeChartUser>();
		JSONObject users = getUsers("0");
		String data = users.getString("data");
		Integer total = users.getInteger("total");
		//System.out.println(String.format("解析的用户数组是data:--%s", data.toString()));
		JSONObject openIds = (JSONObject) JSONObject.parse(data);
		String openIdList = openIds.getString("openid");
		JSONArray parse = (JSONArray) JSONArray.parse(openIdList);
		//System.out.println(String.format("解析的用户数组是:%s", parse.toString()));
		for (int i=0;i<total;i++) {
			String opId = (String) parse.get(i);
			//System.out.println(String.format("获得的openid是:%s", opId.toString()));
			WeChartUser user = getOne(opId);
			userList.add(user);
		}
		return userList;
	}

	@Override
	public WeChartUser getByNickName(String nickName) {
		if ( null == users || users.isEmpty()) {
			// 调用刷新方法
			System.out.println("users is empty");
			flushUsers();
		}
		for (WeChartUser u : users) {
			if(u.getNickName().equals(nickName))
				return u;
		}
		return new WeChartUser();
	}


}
