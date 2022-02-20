package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.Course;

public interface CourseService extends GenericService<Course, Long> {

	List<Course> courseNameExists(String courseName);

	List<Course> findByInMany(int collegeId);

	List<Course> findNotIdList(Course course);

	Course findByCourseName(String courseName);

	void deleteOne(Long[] ids) throws Exception;

}
