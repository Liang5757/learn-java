package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.SystemSchool;

/**
 * @author TT
 *
 *         2017年8月22日
 */
public interface SystemSchoolDao extends GenericDao<SystemSchool, Long> {

	public List<SystemSchool> getSchoolList(SystemSchool school);
}
