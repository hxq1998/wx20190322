package org.hanxq.boot.service;

import java.util.List;

import org.hanxq.boot.dto.WeChartUser;
import com.alibaba.fastjson.JSONObject;

public interface UserService {
	
	List<WeChartUser> getUsers();
	
	JSONObject getUsers(String l1);
	
	WeChartUser getUserByOppenId(String oppenId);
	
	void flushUsers();
	
	WeChartUser getByNickName(String nickName);
	

}
