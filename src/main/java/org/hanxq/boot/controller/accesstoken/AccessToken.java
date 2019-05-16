package org.hanxq.boot.controller.accesstoken;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hanxq.boot.constant.SystemConstant;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 特点为2小时刷新一次，这里使用1小时50分钟刷新策略
 * 
 * @author 学琦 appid wxa3938e5eb2a9e659
 */
@Component
public class AccessToken {
	
	private int hour = 0;
	
	private int day = 0;

	private String token = "grundfoswlcxwx123";

	/**
	 * 凭证
	 */
	private String access_token = "";

	/**
	 * 启动时候构造方法
	 */
	public AccessToken() {
		build();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * 构建access_token
	 */
	public void build() {
		CloseableHttpClient client = HttpClients.createDefault();

		CloseableHttpResponse response = null;
		String url = SystemConstant.ACCESS_TOKEN;
		
		HttpGet get = new HttpGet(url);
		
		try {
			CloseableHttpResponse res = client.execute(get);
			
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				// 
				String result = EntityUtils.toString(res.getEntity());// 返回json格式：
				JSONObject json = JSONObject.parseObject(result);
				System.out.println("========================");
				System.out.println(json.toString());
				System.out.println("========================");
				
				// set token
				this.setAccess_token(json.getString("access_token"));
				// 设置获取到的小时
				this.setHour(this.getBeforeHourTime());
				this.setDay(this.getBeforeDayTime());
				System.out.println("************************");
				System.out.println(this.getAccess_token());
				System.out.println("************************");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	/**
	 * 获得第几个小时
	 * @return
	 */
	public int getBeforeHourTime() {
		long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
		Date date = new Date(currentTime);
		DateFormat df = new SimpleDateFormat("HH");
		String nowTime="";
		nowTime= df.format(date);System.out.println(nowTime);
		return Integer.parseInt(nowTime);
	}
	
	/**
	 * 获得天
	 * @return
	 */
	public int getBeforeDayTime() {
		long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
		Date date = new Date(currentTime);
		DateFormat df = new SimpleDateFormat("HH");
		String day = "";
		df = new SimpleDateFormat("dd");
		day = df.format(date);
	
		return Integer.parseInt(day);
	}
	
	public String compareAccessToken() {
		long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
		Date date = new Date(currentTime);
		DateFormat df = new SimpleDateFormat("dd");
		int day1 = Integer.parseInt(df.format(date));
		// day不等直接重新获取
		df = new SimpleDateFormat("HH");
		int hour1 = Integer.parseInt(df.format(date));;
		if (day1 != this.getDay()) {
			this.build();
		}
		// hour不等直接获取
		if ( hour1 != this.getHour() && hour1 - this.getHour() > 1) {
			this.build();
			
		}
		return this.access_token;
		
	}
	
	
	
}
