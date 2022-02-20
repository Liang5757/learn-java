package cn.youyitech.anyview.system.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import cn.youyitech.anyview.system.entity.ClassAndStudent;
import cn.youyitech.anyview.system.entity.Student;

import com.framework.loippi.mybatis.eitity.GenericEntity;

/**
 * Entity - 学生模板导入
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAndUser implements GenericEntity {

	private static final long serialVersionUID = 1L;
	// 学生集合
	private List<Student> lists_student;
	// 学生与班级关联集合
	private List<ClassAndStudent> lists_classAndStudent;
	// 一个学生多少个班级集合
	private List<Integer> lists_number;

}
