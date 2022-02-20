
package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.youyitech.anyview.system.dao.SystemSchoolDao;
import cn.youyitech.anyview.system.entity.SystemSchool;
import cn.youyitech.anyview.system.service.SchoolAreaService;

/**
 * @author TT
 *
 *         2017年8月22日
 */
@Service
public class SystemSchoolServiceIml extends GenericServiceImpl<SystemSchool, Long> implements SchoolAreaService {
	@Autowired
	private SystemSchoolDao systemSchoolDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(systemSchoolDao);
	}

	@Override
	public List<SystemSchool> getSchoolList(SystemSchool school) {
		// TODO Auto-generated method stub
		return systemSchoolDao.getSchoolList(school);
	}

}
