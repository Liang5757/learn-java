package cn.youyitech.anyview.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.youyitech.anyview.system.dao.LogDao;
import cn.youyitech.anyview.system.entity.Log;
import cn.youyitech.anyview.system.service.LogService;

/**
 * Service - 日志
 * 
 * @version 1.0
 */

@Service("logServiceImpl")
public class LogServiceImpl extends GenericServiceImpl<Log, Long> implements LogService {

	@Autowired
	private LogDao logDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(logDao);
	}

	public void clear() {
		logDao.clear();
	}
}