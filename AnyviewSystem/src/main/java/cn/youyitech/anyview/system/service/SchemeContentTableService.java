package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.Question;
import cn.youyitech.anyview.system.entity.SchemeContentTable;

public interface SchemeContentTableService extends GenericService<SchemeContentTable, Long> {

	List<SchemeContentTable> findContent(SchemeContentTable schemeContentTable);

	void insert(SchemeContentTable schemeContentTable, String ids);

	void deleteOne(Long[] ids);

}
