package cn.youyitech.anyview.system.utils;

import java.util.UUID;

public class UUIDCode {

	public static String getUUIDCode() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
