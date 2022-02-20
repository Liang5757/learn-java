package cn.youyitech.anyview.system.service.impl;

import cn.youyitech.anyview.system.dao.ClassAndStudentDao;
import cn.youyitech.anyview.system.entity.ClassAndStudent;
import cn.youyitech.anyview.system.service.ClassAndStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classAndStudentServiceImpl")
public class ClassAndStudentServiceImpl extends GenericServiceImpl<ClassAndStudent, Long>
		implements ClassAndStudentService {

	@Autowired
	private ClassAndStudentDao classAndStudentDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(classAndStudentDao);
	}

	// 通过学生id删除学生与班级关联表数据
	@Override
	public void deleteByStudentID(Long studentId) {

		classAndStudentDao.deleteByStudentID(studentId);

	}

	// 通过学生id找学生与班级关联表数据
	@Override
	public ClassAndStudent findByStudentID(Long studentId) {
		return classAndStudentDao.findByStudentID(studentId);
	}

	// 查询全部的班级
	@Override
	public List<ClassAndStudent> findTotal() {
		return classAndStudentDao.findTotal();
	}
	
	//通过班级id找一个班有多少人
	@Override
	public int howMany(int cid) {
		return classAndStudentDao.howMany(cid);
	}
	
	//通过班级id找关系表数据
	@Override
	public List<ClassAndStudent> findByClassID(int cid){
		return classAndStudentDao.findByClassID(cid);
	}
}
