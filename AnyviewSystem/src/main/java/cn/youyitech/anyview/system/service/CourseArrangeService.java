package cn.youyitech.anyview.system.service;

import java.util.List;
import cn.youyitech.anyview.system.entity.CourseArrange;

/**
 * SERVICE -课程编排service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface CourseArrangeService extends GenericService<CourseArrange, Long> {

	// 判断是否存在该课程id
	List<CourseArrange> isExists(long course_id);

	CourseArrange findAddNew(CourseArrange courseArrange);

	// 查询全部的课程编排数据
	List<CourseArrange> findTotal();

	// 通过老师id查询课程编排
	List<CourseArrange> findByTeacher(int id);

	// 通过课程编排获取相应的课程编排数据
	List<CourseArrange> findByAttribute(CourseArrange courseArrange);
	List<CourseArrange>findByIdMany(int class_id);

	void deleteOne(List<String> recordCollege) throws Exception;
}
