package cn.youyitech.anyview.system.utils;

import cn.youyitech.anyview.system.dto.*;
import cn.youyitech.anyview.system.entity.*;
import cn.youyitech.anyview.system.service.RedisService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TT 2017年9月20日
 */
@Service
public class IdsUtils {
	@Autowired
	private RedisService redisService;

	public List<String> IdsToList(String key) {
		List<String> records = null;
		String newId1 = redisService.get(key);
		if (newId1 != null) {
			records = new ArrayList<>();
			String idArray[] = newId1.split(",");
			for (int i = 0; i < idArray.length; i++) {
				if (idArray[i].equals("null") || idArray[i].equals("")) {

				} else {
					records.add(idArray[i]);
				}
			}

		}
		return records;
	}

	public List<String> stringjsonToList(String key) {
		@SuppressWarnings("unchecked")
		String jsonString = redisService.get(key);
		List<String> ts = (List<String>) JSONArray.parseArray(jsonString, String.class);
		return ts;
	}

	public List<College> collegejsonToList(String jsonString, Class<College> clazz) {
		@SuppressWarnings("unchecked")
		List<College> ts = (List<College>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<Major> majorjsonToList(String jsonString, Class<Major> clazz) {
		@SuppressWarnings("unchecked")
		List<Major> ts = (List<Major>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<ClassEntity> classjsonToList(String jsonString, Class<ClassEntity> clazz) {
		@SuppressWarnings("unchecked")
		List<ClassEntity> ts = (List<ClassEntity>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<Question> jsonToList(String jsonString, Class<Question> clazz) {
		@SuppressWarnings("unchecked")
		List<Question> ts = (List<Question>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<QuestionHeaderFile> qhfToList(String jsonString, Class<QuestionHeaderFile> clazz) {
		@SuppressWarnings("unchecked")
		List<QuestionHeaderFile> ts = (List<QuestionHeaderFile>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<WorkingTable> workingTablejsonToList(String jsonString, Class<WorkingTable> clazz) {
		@SuppressWarnings("unchecked")
		List<WorkingTable> ts = (List<WorkingTable>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<SchemeContentTable> SchemeContentTablejsonToList(String jsonString, Class<SchemeContentTable> clazz) {
		@SuppressWarnings("unchecked")
		List<SchemeContentTable> ts = (List<SchemeContentTable>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<SystemSchool> SystemSchooljsonToList(String jsonString, Class<SystemSchool> clazz) {
		@SuppressWarnings("unchecked")
		List<SystemSchool> ts = (List<SystemSchool>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<TotalScore> totalScoreJsonToList(String jsonString, Class<TotalScore> clazz) {
		@SuppressWarnings("unchecked")
		List<TotalScore> ts = (List<TotalScore>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<AllTotalScore> allTotalScoreJsonToList(String jsonString, Class<AllTotalScore> clazz) {
        @SuppressWarnings("unchecked")
        List<AllTotalScore> ts = (List<AllTotalScore>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<AttitudeCondition> attitudeConditionJsonToList(String jsonString, Class<AttitudeCondition> clazz) {
		@SuppressWarnings("unchecked")
		List<AttitudeCondition> ts = (List<AttitudeCondition>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}
	
	public List<PaperScore> pageScoreConditionJsonToList(String jsonString, Class<PaperScore> clazz) {
		@SuppressWarnings("unchecked")
		List<PaperScore> ts = (List<PaperScore>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<Exercise> exerciseJsonToList(String jsonString, Class<Exercise> clazz) {
		@SuppressWarnings("unchecked")
		List<Exercise> ts = (List<Exercise>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<ScoreTable> scoreTableJSONToList(String jsonString, Class<ScoreTable> clazz) {
		@SuppressWarnings("unchecked")
		List<ScoreTable> ts = (List<ScoreTable>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	public List<TempUser> tempUserJSONToList(String jsonString, Class<TempUser> clazz) {
		@SuppressWarnings("unchecked")
		List<TempUser> ts = (List<TempUser>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

}
