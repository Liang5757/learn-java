package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.dto.user.LoginUser;

public interface RedisService {

	void delete(String key);

	void save(String key, String object);

	void save(String key, String object, long timeout);

	String get(String key);

	void save(String key, LoginUser object);

	LoginUser getUser(String key);

}
