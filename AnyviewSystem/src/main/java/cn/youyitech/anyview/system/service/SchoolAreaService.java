package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.SystemSchool;

public interface SchoolAreaService extends GenericService<SystemSchool, Long> {

	public List<SystemSchool> getSchoolList(SystemSchool school);

}
