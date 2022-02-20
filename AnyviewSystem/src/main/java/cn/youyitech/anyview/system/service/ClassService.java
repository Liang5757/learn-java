package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.ClassEntity;

/**
 * SERVICE -班级表service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface ClassService extends GenericService<ClassEntity, Long> {

	// 查询全部的班级
	List<ClassEntity> findTotal();

	// 根据班级的信息查询对应的数据
	List<ClassEntity> findByAttribute(ClassEntity classEntity);

	ClassEntity findByMajorIdAndClassName(ClassEntity classEntity);

	void deleteOne(List<String> recordCollege) throws Exception;

}
