package cn.youyitech.anyview.system.service;

import cn.youyitech.anyview.system.entity.Log;

/**
 * Service - 日志
 * 
 * @version 1.0
 */

public interface LogService extends GenericService<Log, Long> {

	/**
	 * 清空日志
	 */
	void clear();
}