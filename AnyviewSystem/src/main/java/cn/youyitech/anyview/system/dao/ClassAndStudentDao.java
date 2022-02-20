package cn.youyitech.anyview.system.dao;

import java.util.List;

import cn.youyitech.anyview.system.entity.ClassAndStudent;
import cn.youyitech.anyview.system.entity.Student;

import com.framework.loippi.mybatis.dao.GenericDao;

/**
 * DAO - 学生与班级关系表dao层
 * 
 * @author zzq
 * @version 1.0
 */
public interface ClassAndStudentDao extends GenericDao<ClassAndStudent, Long> {

	// 通过学生id获取到关系表数据
	ClassAndStudent findByStudentID(Long studentId);

	// 通过学生id删除关系表数据
	void deleteByStudentID(Long studentId);

	// 获取全部的关系表数据
	List<ClassAndStudent> findTotal();

	//通过班级id找一个班有多少人
	int howMany(int cid);
	
	//通过班级id找关系表数据
	List<ClassAndStudent> findByClassID(int cid);
}
