package cn.youyitech.anyview.system.utils;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class ReturnJson {
	public static String jsonString(String message, Object data, int status)

	{
		Map returnMap = new HashMap();
		returnMap.put("message", message);
		returnMap.put("data", data);
		returnMap.put("status", status);

		JSONObject json = JSONObject.fromObject(returnMap);
		return json.toString();
	}

	public static String jsonString(String message, Object data, int status, String[] excludes)

	{
		Map returnMap = new HashMap();
		returnMap.put("message", message);
		returnMap.put("data", data);
		returnMap.put("status", status);
		JsonConfig jsonConfig = new JsonConfig();

		// 排除,避免循环引用 There is a cycle in the hierarchy!
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.setIgnoreDefaultExcludes(true);
		jsonConfig.setAllowNonStringKeys(true);

		if (excludes != null && excludes.length > 0) {
			jsonConfig.setExcludes(excludes);
		}

		JSONObject json = JSONObject.fromObject(returnMap);
		return json.toString();
	}

	public static String jsonString(String message, int status)

	{
		Map returnMap = new HashMap();
		returnMap.put("message", message);
		returnMap.put("status", status);
		JSONObject json = JSONObject.fromObject(returnMap);
		return json.toString();
	}

	public static String jsonStringError(String message, int status)

	{
		Map returnMap = new HashMap();
		returnMap.put("message", message);
		returnMap.put("status", status);
		JSONObject json = JSONObject.fromObject(returnMap);
		return json.toString();
	}
}
