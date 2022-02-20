package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.Major;

public interface MajorService extends GenericService<Major, Long> {

	List<Major> majorNameExists(String majorName);

	void deleteOne(Long[] ids) throws Exception;

	Major findByCollegeIdAndMajorName(Major major);

}
