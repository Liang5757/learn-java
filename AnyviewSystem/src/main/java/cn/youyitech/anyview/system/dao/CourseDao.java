package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.Course;

/**
 * @author TT
 *
 *         2017年8月22日
 */
public interface CourseDao extends GenericDao<Course, Long> {

	List<Course> findByIdMany(int collegeId);

	List<Course> findNotIdList(Course course);

	Course findByCourseName(String courseName);
}
