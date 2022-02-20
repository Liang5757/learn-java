package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.Teacher;

/**
 * DAO - 教师表dao层
 * 
 * @author zzq
 * @version 1.0
 */
public interface TeacherDao extends GenericDao<Teacher, Long> {

	// 通过用户名查找教师
	Teacher findByUserName(Teacher teacher);

	// 获取全部的教师数据
	List<Teacher> findTotal();

	// 根据教师参数查询相应的数据
	List<Teacher> findByAttribute(Teacher teacher);

	Teacher findByEntity(Teacher teacherNew);
}
