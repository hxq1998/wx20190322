package org.hanxq.boot.service.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hanxq.boot.controller.accesstoken.AccessToken;
import org.hanxq.boot.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class TemplateServiceImpl implements TemplateService {
	
	@Autowired
	private AccessToken accessToken;

	@Override
	public int push(String templateId, String touser, String first, String keyword1, String keyword2, String keyword3,
			String keyword4, String remark) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken.getAccess_token();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		JSONObject jsonParam = new JSONObject(); 
		
		jsonParam.put("touser", touser); // openid
		jsonParam.put("template_id", "abZXJK8tapct4BWTt7ZaS1wpO-0Gu9oaryIzACz9xQ4" );
		JSONObject data = new JSONObject(); 
		// first
		JSONObject first_ = new JSONObject();
		first_.put("value", first);
		// keyword1
		JSONObject keyword1_ = new JSONObject();
		keyword1_.put("value", keyword1);
		// keyword2
		JSONObject keyword2_ = new JSONObject();
		keyword2_.put("value", keyword2);
		// keyword3
		JSONObject keyword3_ = new JSONObject();
		keyword3_.put("value", keyword3);
		// keyword4
		JSONObject keyword4_ = new JSONObject();
		keyword4_.put("value", keyword4);
		// remark
		JSONObject remark_ = new JSONObject();
		remark_.put("value", remark);
		data.put("first",first_);
		data.put("keyword1",keyword1_);
		data.put("keyword2",keyword2_);
		data.put("keyword3",keyword3_);
		data.put("keyword4",keyword4_);
		data.put("remark",remark_);
		jsonParam.put("data", data);
		//
		StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题    
		entity.setContentEncoding("UTF-8");  
		entity.setContentType("application/json"); 
		httpPost.setEntity(entity);
		System.out.println("发送模板请求00");
		try {
			HttpResponse resp = client.execute(httpPost);
			if(resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				String res = EntityUtils.toString(he,"UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
