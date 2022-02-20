package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.School;

public interface SchoolService extends GenericService<School, Long> {

	List<School> schoolNameExists(String schoolName);

	List<School> findByDeleteId(Long id);

	List<School> findListByName(School school);

	List<School> findNotIdList(String string, int id);

	School findBySchoolName(String schoolName);

	void deleteOne(Long[] ids) throws Exception;
}
