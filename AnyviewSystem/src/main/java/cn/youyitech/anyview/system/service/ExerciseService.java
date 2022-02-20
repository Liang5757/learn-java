package cn.youyitech.anyview.system.service;

import cn.youyitech.anyview.system.entity.Exercise;

import java.util.List;

import cn.youyitech.anyview.system.entity.Exercise;


/**
 * SERVICE -批改作业service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface ExerciseService extends GenericService<Exercise, Long> {

	List<Exercise> findByEntity(Exercise exercise);
	List<Exercise> findbySid(Long sId);
	List<Exercise> getExerciseAnswer(int cid, int pid);
	List<Exercise> findForStuduent(int cId, int courseId, int vid, int sid);
	Exercise findByPSC(Long pid, int sid, Long cid);
	int countRightNumber(int cId, int courseId, int vid, int sid);
	List<Exercise> findByPSVs(List<Long> pids,List<Integer> sids,int vid);
}
