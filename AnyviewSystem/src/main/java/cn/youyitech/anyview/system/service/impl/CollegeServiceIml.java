
package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import cn.youyitech.anyview.system.utils.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.youyitech.anyview.system.dao.CollegeDao;
import cn.youyitech.anyview.system.entity.College;
import cn.youyitech.anyview.system.service.CollegeService;
import cn.youyitech.anyview.system.support.Message;

import com.framework.loippi.utils.Paramap;

@Service
public class CollegeServiceIml extends GenericServiceImpl<College, Long> implements CollegeService {
	@Autowired
	private CollegeDao collegeDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(collegeDao);
	}

	@Override
	public List<College> collegeNameExists(String collegeName) {
		List<College> collegeList = collegeDao.findByParams(Paramap.create().put("collegeName", collegeName));
		SortUtil.sort(collegeList,true,"collegeName");
		return collegeList;
	}

	@Override
	public List<College> findByIdMany(int schoolId) {
		List<College> collegeList=collegeDao.findByIdMany(schoolId);
		SortUtil.sort(collegeList,true,"collegeName");
		System.out.println(collegeList);
		return collegeList;
	}

	@Override
	public List<College> findNotIdList(College college) {
		List<College> collegeList=collegeDao.findNotIdList(college);
		SortUtil.sort(collegeList,true,"collegeName");
		return collegeList;
	}

	@Override

	public College findByCollegeNameAndSchoolId(College college) {
		return collegeDao.findByCollegeNameAndSchoolId(college);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(Long[] ids) throws Exception {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			College college = collegeDao.find(id);
			if (college.getCollegeCourseList().size() > 0) {
				throw new RuntimeException("请先删除该学院的所有课程!");
			} else if (college.getCollegeMajorList().size() > 0) {
				throw new RuntimeException("请先删除该学院的所有专业!");
			} else if (college.getCollegeTeacherList().size() > 0) {
				throw new RuntimeException("请先删除该学院的所有教师!");
			} else {
				collegeDao.delete(id);
			}
		}

	}

}
