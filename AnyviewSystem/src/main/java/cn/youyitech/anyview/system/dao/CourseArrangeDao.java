package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.CourseArrange;

/**
 * DAO - 课程编排表dao层
 * 
 * @author zzq
 * @version 1.0
 */
public interface CourseArrangeDao extends GenericDao<CourseArrange, Long> {

	CourseArrange findAddNew(CourseArrange courseArrange);

	// 获取全部的课程编排数据
	List<CourseArrange> findTotal();

	// 通过教师id获取相应的课程编排数据
	List<CourseArrange> findByTeacher(int id);

	// 通过课程编排获取相应的课程编排数据
	List<CourseArrange> findByAttribute(CourseArrange courseArrange);
	List<CourseArrange>findByIdMany(int class_id);

}
