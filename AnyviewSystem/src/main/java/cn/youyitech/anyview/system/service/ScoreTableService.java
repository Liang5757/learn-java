package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.Exercise;
import cn.youyitech.anyview.system.entity.ScoreTable;

public interface ScoreTableService extends GenericService<ScoreTable, Long> {

	List<ScoreTable> findRankByScore(ScoreTable scoreTable);

	void updateRankByScore(ScoreTable scoreTable);
	
	void insertDataToScoreTable(String flag, List<Exercise> exerciseList, String currentUserName);

	ScoreTable findBySIDAndVIDAndCID(ScoreTable scoreTable);
	
	void updateBySIDAndVIDAndCID(ScoreTable scoreTable);
	
	void insert(ScoreTable scortTable);
	
}
