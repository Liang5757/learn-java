package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.youyitech.anyview.system.dao.CourseArrangeAndWorkingTableDao;
import cn.youyitech.anyview.system.entity.CourseArrangeAndWorkingTable;
import cn.youyitech.anyview.system.service.CourseArrangeAndWorkingTableService;

@Service("courseArrangeAndWorkingTableServiceImpl")
public class CourseArrangeAndWorkingTableServiceImpl extends GenericServiceImpl<CourseArrangeAndWorkingTable, Long>
		implements CourseArrangeAndWorkingTableService {

	@Autowired
	private CourseArrangeAndWorkingTableDao courseArrangeAndWorkingTableDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(courseArrangeAndWorkingTableDao);
	}

	// 查询全部的课程编排与作业表关系表数据
	@Override
	public List<CourseArrangeAndWorkingTable> findTotal() {
		return courseArrangeAndWorkingTableDao.findTotal();
	}

	@Override
	public List<CourseArrangeAndWorkingTable> findByIdMany(int course_id) {
		return courseArrangeAndWorkingTableDao.findByIdMany(course_id);
	}


	// 通过作业表id删除课程编排与作业表关系表的数据
	@Override
	public void deleteByWorkingTableId(long workingTableId) {

		courseArrangeAndWorkingTableDao.deleteByWorkingTableId(workingTableId);

	}

	@Override
	public void deleteByCourseArrangeId(long courseArrangeId) {

		courseArrangeAndWorkingTableDao.deleteByCourseArrangeId(courseArrangeId);

	}

	@Override
	public void saveOne(CourseArrangeAndWorkingTable courseArrangeAndWorkingTable) {
		courseArrangeAndWorkingTableDao.saveOne(courseArrangeAndWorkingTable);
	}

}
