
package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.youyitech.anyview.system.dao.ScoreTableDao;
import cn.youyitech.anyview.system.dao.StudentDao;
import cn.youyitech.anyview.system.entity.Exercise;
import cn.youyitech.anyview.system.entity.ScoreTable;
import cn.youyitech.anyview.system.service.ScoreTableService;

/**
 * @author TT
 *
 *         2017年8月22日
 */
@Service
public class ScoreTableServiceIml extends GenericServiceImpl<ScoreTable, Long> implements ScoreTableService {
	@Autowired
	private ScoreTableDao scoreTableDao;

	@Autowired
    private StudentDao studentDao;
	
	@Autowired
	public void setGenericDao() {
		super.setGenericDao(scoreTableDao);
	}

	@Override
	public List<ScoreTable> findRankByScore(ScoreTable scoreTable) {
		// TODO Auto-generated method stub
		return scoreTableDao.findRankByScore(scoreTable);
	}

	@Override
	public void updateRankByScore(ScoreTable scoreTable) {
		// TODO Auto-generated method stub
		scoreTableDao.updateRankByScore(scoreTable);
	}

    @Override
    public void insertDataToScoreTable(String flag, List<Exercise> exerciseList,String currentUserName) {
        //前作业表
        int frontVID = 0;
        //后作业表
        int afterVID = 0;
        //通过题目数量
        int passNum = 0;
        //学生ID
        int sId;
        //班级ID
        int cId;
        //前学生ID
        int frontSID = 0;
        //后学生ID
        int afterSID = 0;
        //作业表ID
        int vId;
        //flag为0，学生批改；flag为1，题目批改
        if(flag.equals("0")){
            if(exerciseList.size() == 1){
                Exercise exercise = exerciseList.get(0);
                setScoreTable(exercise.getSId(),exercise.getCId(),exercise.getVId(),
                        exercise.getKind() == 1 ? 1 : 0,studentDao.find((long)exercise.getSId()).getSaccumTime(),
                                currentUserName);
            }else{
                for(int i=0;i<exerciseList.size();i++){
                    Exercise exercise = exerciseList.get(i);
                    //学生id
                    sId = exercise.getSId();
                    //班级id
                    cId = exercise.getCId();
                    //frontVID和afterVID是用于计算不用作业表的通过数目
                    if(i == 0){
                        frontVID = exercise.getVId();
                        if(exercise.getKind() == 1){
                            passNum++;
                        }
                    }else{
                        afterVID = exercise.getVId();
                    }
                    //前作业表与后作业表相同并且通过，则通过题目数量+1
                    if(frontVID == afterVID && afterVID != 0){
                        if(exercise.getKind() == 1){
                             passNum++;
                        }
                    }
                    //如果前作业表与后作业表不相同，则添加一条记录
                    if(frontVID != afterVID && afterVID != 0){
                        setScoreTable(sId, cId, frontVID, passNum,
                                studentDao.find((long)exercise.getSId()).getSaccumTime(), currentUserName);

                        frontVID = afterVID;
                        if(exercise.getKind() == 1){
                            passNum = 1;
                        }else{
                            passNum = 0;
                        }
                    }
                    
                    if(exerciseList.size()-1 == i){
                        setScoreTable(sId, cId, afterVID, passNum, 
                                studentDao.find((long)exercise.getSId()).getSaccumTime(), currentUserName);
                    }
                }
            }
        }else if(flag.equals("1")){
            if(exerciseList.size() == 1){
                Exercise exercise = exerciseList.get(0);
                setScoreTable(exercise.getSId(), exercise.getCId(), exercise.getVId(), exercise.getKind() == 1 ? 1 : 0, 
                        studentDao.find((long)exercise.getSId()).getSaccumTime(), currentUserName);
            }else{
                for(int i=0;i<exerciseList.size();i++){
                    Exercise exercise = exerciseList.get(i);
                    cId = exercise.getCId();
                    vId = exercise.getVId();
                    if(i == 0){
                        frontSID = exercise.getSId();
                        if(exercise.getKind() == 1){
                            passNum++;
                        }
                    }else{
                        afterSID = exercise.getSId();
                    }
                    //前作业表与后作业表相同并且通过，则通过题目数量+1
                    if(frontSID == afterSID && afterSID != 0){
                        if(exercise.getKind() == 1){
                             passNum++;
                        }
                    }
                    if(frontSID != afterSID && afterSID != 0){
                        setScoreTable(frontSID, cId, vId, passNum, 
                                studentDao.find((long)exercise.getSId()).getSaccumTime(), currentUserName);
                        
                        frontSID = afterSID;
                        if(exercise.getKind() == 1){
                            passNum = 1;
                        }else{
                            passNum = 0;
                        }
                    }
                    if(exerciseList.size()-1 == i){
                        setScoreTable(afterSID, cId, vId, passNum, 
                                studentDao.find((long)exercise.getSId()).getSaccumTime(), currentUserName);
                    }
                }
            }
        }
    }


    @Override
    public ScoreTable findBySIDAndVIDAndCID(ScoreTable scoreTable) {
        return scoreTableDao.findBySIDAndVIDAndCID(scoreTable);
    }

    @Override
    public void updateBySIDAndVIDAndCID(ScoreTable scoreTable) {  
        scoreTableDao.updateBySIDAndVIDAndCID(scoreTable);
    }
    
    private void setScoreTable(int sId,int cId,int vId,int passNum,int totalTime,String currentUserName){
        ScoreTable scoreTable = new ScoreTable();
        scoreTable.setSID(sId);
        scoreTable.setCID(cId);
        scoreTable.setVID(vId);
        scoreTable.setPassNum(passNum);
        scoreTable.setCorrectFlag(0);
        scoreTable.setTotalTime(totalTime);
        scoreTable.setUpdater(currentUserName);
        scoreTable.setCreater(currentUserName);
        if(findBySIDAndVIDAndCID(scoreTable) != null){
            updateBySIDAndVIDAndCID(scoreTable);
        }else{
            scoreTable.setEnabled(true);
            scoreTableDao.insert(scoreTable);
        }
    }
    
    public void insert(ScoreTable scoreTable) {
    	scoreTableDao.insert(scoreTable);
    }

}
