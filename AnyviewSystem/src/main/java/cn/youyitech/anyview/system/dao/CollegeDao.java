package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.College;

/**
 * @author TT
 *
 *         2017年8月22日
 */
public interface CollegeDao extends GenericDao<College, Long> {

	List<College> findByIdMany(int schoolId);

	List<College> findNotIdList(College college);

	College findByCollegeNameAndSchoolId(College college);

}
