package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.ScoreTable;

/**
 * @author TT
 *
 *         2017年8月22日
 */
public interface ScoreTableDao extends GenericDao<ScoreTable, Long> {

	List<ScoreTable> findRankByScore(ScoreTable scoreTable);

	void updateRankByScore(ScoreTable scoreTable);

	ScoreTable findBySIDAndVIDAndCID(ScoreTable scoreTable);
	
	void updateBySIDAndVIDAndCID(ScoreTable scoreTable);
}
