package cn.youyitech.anyview.system.service;

import cn.youyitech.anyview.system.entity.ClassAndStudent;

import java.util.List;

/**
 * SERVICE -学生与班级关联表service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface ClassAndStudentService extends GenericService<ClassAndStudent, Long> {
	// 通过学生id找学生与班级关联表数据
	public ClassAndStudent findByStudentID(Long studentId);

	// 通过学生id删除学生与班级关联表数据
	public void deleteByStudentID(Long studentId);

	// 查询全部的班级
	List<ClassAndStudent> findTotal();
	
	//通过班级id找一个班有多少人
	public int howMany(int cid);

	//通过班级id找关系表数据
	public List<ClassAndStudent> findByClassID(int cid);
}
