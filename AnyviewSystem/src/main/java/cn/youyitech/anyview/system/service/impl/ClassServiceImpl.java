package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.youyitech.anyview.system.utils.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.youyitech.anyview.system.dao.ClassDao;
import cn.youyitech.anyview.system.entity.ClassAndStudent;
import cn.youyitech.anyview.system.entity.ClassEntity;
import cn.youyitech.anyview.system.service.ClassAndStudentService;
import cn.youyitech.anyview.system.service.ClassService;

@Service("classServiceImpl")
public class ClassServiceImpl extends GenericServiceImpl<ClassEntity, Long> implements ClassService {

	@Autowired
	private ClassDao classSystemDao;

	@Resource
	private ClassAndStudentService classAndStudentService;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(classSystemDao);
	}

	// 查询全部的班级
	@Override
	public List<ClassEntity> findTotal() {

		List<ClassEntity> classEntityList=classSystemDao.findTotal();
		SortUtil.sort(classEntityList,true,"className");
		return classEntityList;
	}

	@Override
	public List<ClassEntity> findByAttribute(ClassEntity classEntity) {
		List<ClassEntity> classEntityList=classSystemDao.findByAttribute(classEntity);
		SortUtil.sort(classEntityList,true,"className");
		return classEntityList;
	}

	@Override

	public ClassEntity findByMajorIdAndClassName(ClassEntity classEntity) {
		return classSystemDao.findByMajorIdAndClassName(classEntity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(List<String> recordClass) throws Exception {
		// TODO Auto-generated method stub
		for (String id_string : recordClass) {
			long id = Long.parseLong(id_string);
			ClassEntity classSystem = classSystemDao.find(id);
			List<ClassAndStudent> classAndStudentList = classAndStudentService.findList("student_class_id", id);
			// 删除判断
			if (classSystem.getClassCourseArrangeList().size() > 0) {

				throw new RuntimeException("请先删除该班级的所有课程编排！");

			} else if (classAndStudentList.size() > 0) {

				throw new RuntimeException("请先删除该班级的所有学生！");

			} else {
				// 删除班级
				classSystemDao.delete(id);
			}

		}

	}

}
