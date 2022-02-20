package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.WorkingTable;

/**
 * @author TT
 *
 *         2017年8月22日
 */
public interface WorkingTableDao extends GenericDao<WorkingTable, Long> {

	List<WorkingTable> findWorkList(long id);

	List<WorkingTable> findContentList(WorkingTable workingTable);

	List<WorkingTable> findListByName(WorkingTable workingTable);

	void updateTotalNum(WorkingTable workingTable);

}
