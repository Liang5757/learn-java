package cn.youyitech.anyview.system.service.impl;

import cn.youyitech.anyview.system.dao.ExerciseDao;
import cn.youyitech.anyview.system.entity.Exercise;
import cn.youyitech.anyview.system.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("exerciseServiceImpl")
public class ExerciseServiceImpl extends GenericServiceImpl<Exercise, Long> implements ExerciseService {

	@Autowired
	private ExerciseDao exerciseDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(exerciseDao);
	}

	@Override
	public List<Exercise> findByEntity(Exercise exercise) {
		return exerciseDao.findByEntity(exercise);
	}
	public List<Exercise> findbySid(Long sId){return exerciseDao.findbySid(sId);}
	
	public List<Exercise> getExerciseAnswer(int cid, int pid){
		return exerciseDao.getExerciseAnswer(cid,pid);
	}
	
	public List<Exercise> findForStuduent(int cId, int courseId, int vid, int sid){
		return exerciseDao.findForStuduent(cId, courseId, vid, sid);
	}
	
	public Exercise findByPSC(Long pid, int sid, Long cid) {
		return exerciseDao.findByPSC(pid, sid, cid);
	}
	
	public int countRightNumber(int cId, int courseId, int vid, int sid) {
		return exerciseDao.countRightNumber( cId,  courseId,  vid,  sid);
	}
	
	@Override
	public List<Exercise> findByPSVs(List<Long> pids, List<Integer> sids, int vid) {
		Map<String,Object> params = new HashMap<>();
		params.put("pids",pids);
		params.put("sids",sids);
		params.put("vid",vid);
		return exerciseDao.findByPSVs(params);
	}
}
