
package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import cn.youyitech.anyview.system.utils.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.youyitech.anyview.system.dao.CourseDao;
import cn.youyitech.anyview.system.entity.Course;
import cn.youyitech.anyview.system.entity.CourseArrange;
import cn.youyitech.anyview.system.entity.ExamPlan;
import cn.youyitech.anyview.system.service.CourseArrangeService;
import cn.youyitech.anyview.system.service.CourseService;
import cn.youyitech.anyview.system.service.ExamPlanService;
import cn.youyitech.anyview.system.service.WorkingTableService;
import cn.youyitech.anyview.system.support.Message;

import com.framework.loippi.utils.Paramap;

/**
 * @author TT
 *
 *         2017年8月22日
 */
@Service
public class CourseServiceIml extends GenericServiceImpl<Course, Long> implements CourseService {
	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseArrangeService courseArrangeService;

	@Autowired
	private WorkingTableService workingTableService;

	@Autowired
	private ExamPlanService examPlanService;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(courseDao);
	}

	@Override
	public List<Course> courseNameExists(String courseName) {
		List<Course> courseList = courseDao.findByParams(Paramap.create().put("courseName", courseName));
		return courseList;
	}

	@Override
	public List<Course> findByInMany(int collegeId) {
		List<Course>courseList=courseDao.findByIdMany(collegeId);
		SortUtil.sort(courseList,true,"courseName");
		return courseList;
	}

	@Override
	public List<Course> findNotIdList(Course course) {
		// TODO Auto-generated method stub
		List<Course>courseList=courseDao.findNotIdList(course);
		SortUtil.sort(courseList,true,"courseName");
		return courseList;
	}

	@Override
	public Course findByCourseName(String courseName) {
		return courseDao.findByCourseName(courseName);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(Long[] ids) throws Exception {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			// long id = Long.parseLong(id_string);
			List<CourseArrange> courseArrangeList = courseArrangeService.findList("course_id", id);
			List<ExamPlan> examPlanList = examPlanService.findList("courseId", id);
			if (examPlanList.size() > 0) {
				throw new RuntimeException("请先删除该课程的所有考试编排！");
			} else if (workingTableService.findList("courseId", id).size() > 0) {
				throw new RuntimeException("请先删除该课程的所有作业表！");
			} else if (courseArrangeList.size() > 0) {
				throw new RuntimeException("请先删除该课程的所有课程编排！");
			} else {
				courseDao.delete(id);
			}
		}
	}
}
