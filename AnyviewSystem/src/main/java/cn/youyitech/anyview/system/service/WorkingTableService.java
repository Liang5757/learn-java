package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.WorkingTable;

public interface WorkingTableService extends GenericService<WorkingTable, Long> {

	List<WorkingTable> tableNameExists(String tableName);

	List<WorkingTable> findWorkList(long id);

	List<WorkingTable> findContentList(WorkingTable workingTable);

	List<WorkingTable> findListByName(WorkingTable workingTable);

	void deleteOne(Long[] ids);

	void updateTotalNum(WorkingTable workingTable);

}
