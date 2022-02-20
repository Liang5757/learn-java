package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import cn.youyitech.anyview.system.utils.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.loippi.utils.Paramap;

import cn.youyitech.anyview.system.dao.CourseArrangeDao;
import cn.youyitech.anyview.system.entity.CourseArrange;
import cn.youyitech.anyview.system.entity.CourseArrangeAndWorkingTable;
import cn.youyitech.anyview.system.service.CourseArrangeAndWorkingTableService;
import cn.youyitech.anyview.system.service.CourseArrangeService;

@Service("courseArrangeServiceImpl")
public class CourseArrangeServiceImpl extends GenericServiceImpl<CourseArrange, Long> implements CourseArrangeService {

	@Autowired
	private CourseArrangeDao courseArrangeDao;

	@Autowired
	private CourseArrangeAndWorkingTableService courseArrangeAndWorkingTableService;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(courseArrangeDao);
	}

	// 判断是否存在该课程id
	@Override
	public List<CourseArrange> isExists(long course_id) {
		List<CourseArrange> courseList = courseArrangeDao.findByParams(Paramap.create().put("course_id", course_id));

		return courseList;
	}

	@Override
	public CourseArrange findAddNew(CourseArrange courseArrange) {
		return courseArrangeDao.findAddNew(courseArrange);
	}

	// 查询全部的课程编排数据
	@Override
	public List<CourseArrange> findTotal() {
		return courseArrangeDao.findTotal();
	}

	// 通过老师id查询课程编排
	@Override
	public List<CourseArrange> findByTeacher(int id) {
		return courseArrangeDao.findByTeacher(id);
	}

	@Override
	public List<CourseArrange> findByAttribute(CourseArrange courseArrange) {
		return courseArrangeDao.findByAttribute(courseArrange);
	}

	@Override
	public List<CourseArrange> findByIdMany(int class_id) {
		return courseArrangeDao.findByIdMany(class_id);
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(List<String> recordCourseArrange) throws Exception {
		// TODO Auto-generated method stub
		for (String id_string : recordCourseArrange) {
			long id = Long.parseLong(id_string);
			// 删除课程编排
			courseArrangeDao.delete(id);
			// 删除该课程编排与作业表的关系表中的数据
			List<CourseArrangeAndWorkingTable> cAndwList = courseArrangeAndWorkingTableService
					.findList("courseArrangeId", id);
			for (int i = 0; i < cAndwList.size(); i++) {
				courseArrangeAndWorkingTableService.delete(cAndwList.get(i).getId());
			}
		}
	}

}
