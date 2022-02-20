package cn.youyitech.anyview.system.dao;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.Log;

/**
 * DAO - Log
 *
 * @version 1.0
 */

public interface LogDao extends GenericDao<Log, Long> {

	/**
	 * 清除日志
	 */
	void clear();
}
