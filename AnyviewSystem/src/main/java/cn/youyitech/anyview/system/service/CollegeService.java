package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.College;

public interface CollegeService extends GenericService<College, Long> {

	List<College> collegeNameExists(String collegeName);

	List<College> findByIdMany(int schoolId);

	List<College> findNotIdList(College college);

	void deleteOne(Long[] ids) throws Exception;

	College findByCollegeNameAndSchoolId(College college);

}
