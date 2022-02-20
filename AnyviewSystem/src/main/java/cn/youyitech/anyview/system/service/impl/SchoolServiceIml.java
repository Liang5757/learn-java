
package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import cn.youyitech.anyview.system.utils.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.loippi.utils.Paramap;

import cn.youyitech.anyview.system.dao.SchoolDao;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;

/**
 * @author TT
 *
 *         2017年8月22日
 */
@Service("schoolServiceIml")
public class SchoolServiceIml extends GenericServiceImpl<School, Long> implements SchoolService {
	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	SystemUserService systemUserService;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(schoolDao);
	}

	@Override
	public List<School> schoolNameExists(String schoolName) {
		List<School>schoolList= schoolDao.findByParams(Paramap.create().put("schoolName", schoolName));
		SortUtil.sort(schoolList,true,"schoolName");
		return schoolList;
	}

	@Override
	public List<School> findByDeleteId(Long id) {
		return schoolDao.findByDeleteId(id);
	}

	@Override
	public List<School> findListByName(School school) {
		List<School> schoolList=schoolDao.findListByName(school);
		SortUtil.sort(schoolList,true,"schoolName");
		return schoolList;
	}

	@Override
	public List<School> findNotIdList(String string, int id) {
		List<School> schoolList=schoolDao.findNotIdList(Paramap.create().put(string,id));
		SortUtil.sort(schoolList,true,"schoolName");
		return schoolList;
	}

	@Override

	public School findBySchoolName(String schoolName) {
		return schoolDao.findBySchoolName(schoolName);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(Long[] ids) throws Exception {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			School school = schoolDao.find(id);
			if (school.getSchoolCollegeList().size() > 0) {
				throw new RuntimeException("请先删除该学校的所有学院！");
			} else if (systemUserService.find("schoolId", id) != null) {
				throw new RuntimeException("请先删除该学校的所有管理员！");
			} else {
				schoolDao.delete(id);
			}
		}
	}

}
