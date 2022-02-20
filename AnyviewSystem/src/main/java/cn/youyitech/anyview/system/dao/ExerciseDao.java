package cn.youyitech.anyview.system.dao;

import cn.youyitech.anyview.system.entity.Exercise;
import com.framework.loippi.mybatis.dao.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DAO - 批改作业表dao层
 * 
 * @author zzq
 * @version 1.0
 */
public interface ExerciseDao extends GenericDao<Exercise, Long> {

	List<Exercise> findByEntity(Exercise exercise);
	
	//根据班级id和题目的id来查询该班所有学生这道题目的答案

	List<Exercise> getExerciseAnswer(@Param("cid") int cid, @Param("pid") int pid);
	
	
	List<Exercise> findbySid(@Param("sId") Long sID);
	
	//根据班级id、课程id、作业表id、学生id找答案列表
	List<Exercise> findForStuduent(@Param("cId") int cId, @Param("courseId") int courseId, @Param("vId") int vid, @Param("sId") int sid);
	
	//根据问题id、学生id、班级id找答案
	Exercise findByPSC(@Param("pid") Long pid, @Param("sid") int sid, @Param("cid") Long cid);

	//根据班级id、课程id、作业表id、学生id返回回答正确的个数
	int countRightNumber(@Param("cId") int cId, @Param("courseId") int courseId, @Param("vId") int vid, @Param("sId") int sid);
	
	//根据问题id、学生id、作业表id找答案
	List<Exercise> findByPSVs(Map<String,Object> params);
}
