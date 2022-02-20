package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.SchemeContentTable;
import org.apache.ibatis.annotations.Param;

/**
 * @author TT 2017年9月15日
 */
public interface SchemeContentTableDao extends GenericDao<SchemeContentTable, Long> {

	List<SchemeContentTable> findContent(SchemeContentTable schemeContentTable);

	void deleteContent(Long VID);
	//
	List<SchemeContentTable> getSchemeContentList(@Param("Vid") int Vid,@Param("Pid") int Pid);
}
