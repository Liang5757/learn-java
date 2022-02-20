package cn.youyitech.anyview.system.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import cn.youyitech.anyview.system.entity.Teacher;

/**
 * SERVICE -教师service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface TeacherService extends GenericService<Teacher, Long> {

	// 通过用户名查询教师
	Teacher findByUserName(Teacher teacher);

	// 查询全部的教师
	List<Teacher> findTotal();

	void updatePassByUserName(Teacher teacher) throws MessagingException, IOException;

	Teacher findByEntity(Teacher teacherNew);

	// 根据教师参数查询相应的数据
	List<Teacher> findByAttribute(Teacher teacher);

	void saveOne(Teacher teacher) throws MessagingException, IOException;

	void updateInitPass(Teacher teacher) throws MessagingException, IOException;

	void deleteOne(List<String> recordCollege) throws Exception;

}
