package cn.youyitech.anyview.system.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import cn.youyitech.anyview.system.dto.user.LoginUser;
import cn.youyitech.anyview.system.service.RedisService;
import net.sf.json.JSONObject;

/**
 * RedisService
 */

@Service("redisServiceImpl")
public class RedisServiceImpl implements RedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	

	@Override
	public void delete(String key) {
		if (key == null)
			return;
		stringRedisTemplate.delete(key);
	}

	/**
	 * 添加缓存
	 *
	 * @param key
	 * @param object
	 */
	@Override
	public void save(String key, String object) {
		if (key == null)
			return;
		ValueOperations<String, String> valueops = stringRedisTemplate.opsForValue();
		valueops.set(key, object);
	}

	@Override
	public void save(String key, String object, long timeout) {
		if (key == null)
			return;
		try {
			ValueOperations operations = stringRedisTemplate.opsForValue();
			operations.set(key, object, timeout, TimeUnit.SECONDS);
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String get(String key) {
		ValueOperations<String, String> valueops = stringRedisTemplate.opsForValue();
		return valueops.get(key);
	}

	@Override
	public void save(String key, LoginUser object) {
		if (key == null)
			return;
		ValueOperations<String, String> valueops = stringRedisTemplate.opsForValue();
		valueops.set(key, JSONObject.fromObject(object).toString());
	}

	@Override
	public LoginUser getUser(String key) {
		ValueOperations<String, String> valueops = stringRedisTemplate.opsForValue();
		String values = valueops.get(key);
		if (values == null) {
			return null;
		}
		JSONObject jsonObject = new JSONObject().fromObject(valueops.get(key));// 将json字符串转换为json对象
		LoginUser loginUser = (LoginUser) JSONObject.toBean(jsonObject, LoginUser.class);// 将建json对象转换为Person对象
		return loginUser;
	}
	

}
