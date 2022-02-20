package cn.youyitech.anyview.system.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import cn.youyitech.anyview.system.entity.Student;

/**
 * SERVICE -学生service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface StudentService extends GenericService<Student, Long> {

	// 查询全部学生
	List<Student> findTotal();

	Student findByEntity(Student studentNew);

	void updatePassByUserName(Student student) throws MessagingException, IOException;

	// 根据学生参数查询相应的数据
	List<Student> findByAttribute(Student student);

	void saveOne(Student student) throws MessagingException, IOException;

	void updateInitPass(Student student) throws MessagingException, IOException;

	Student findByUserNameAndSchoolId(Student student);

	void deleteOne(List<String> recordCollege) throws Exception;

	List<Student> findEntityList(Student student);

}
