package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.ClassEntity;

/**
 * DAO - 班级表dao层
 * 
 * @author zzq
 * @version 1.0
 */
public interface ClassDao extends GenericDao<ClassEntity, Long> {

	// 获取全部的数据
	List<ClassEntity> findTotal();

	// 根据班级的信息查询对应的数据
	List<ClassEntity> findByAttribute(ClassEntity classEntity);

	ClassEntity findByMajorIdAndClassName(ClassEntity classEntity);

}
