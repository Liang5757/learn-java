package cn.youyitech.anyview.system.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientDownloadUtil {
	//客户端下载的版本id
	private static AtomicInteger versionNumber=new AtomicInteger(0);
	
	//保存的兼容版本号
	private static List<String> list=new ArrayList<>();
	
	public static String getVersionId() {
		String versionId=Integer.toString(versionNumber.incrementAndGet());
		return versionId;
	}
	
	public static List<String> getList() {
		return list; 
	}
}
