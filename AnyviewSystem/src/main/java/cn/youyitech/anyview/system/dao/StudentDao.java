package cn.youyitech.anyview.system.dao;

import java.util.List;

import cn.youyitech.anyview.system.entity.ClassAndStudent;
import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.Student;

/**
 * DAO - 学生表dao层
 * 
 * @author zzq
 * @version 1.0
 */
public interface StudentDao extends GenericDao<Student, Long> {

	// 获取全部的学生数据
	List<Student> findTotal();

	// 根据学生参数查询相应的数据
	List<Student> findByAttribute(Student student);

	Student findByEntity(Student studentNew);

	Student findByUserNameAndSchoolId(Student student);

	List<Student> findEntityList(Student student);


}
