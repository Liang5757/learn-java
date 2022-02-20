package cn.youyitech.anyview.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.youyitech.anyview.system.dao.QuestionBankDao;
import cn.youyitech.anyview.system.entity.Exercise;
import cn.youyitech.anyview.system.entity.Question;
import cn.youyitech.anyview.system.entity.QuestionBank;
import cn.youyitech.anyview.system.entity.SchemeContentTable;
import cn.youyitech.anyview.system.service.ExerciseService;
import cn.youyitech.anyview.system.service.QuestionBankService;
import cn.youyitech.anyview.system.service.QuestionService;
import cn.youyitech.anyview.system.service.SchemeContentTableService;

@Service("questionBankServiceImpl")
public class QuestionBankServiceImpl extends GenericServiceImpl<QuestionBank, Long> implements QuestionBankService {

	@Autowired
	private QuestionBankDao questionBankDao;

	@Resource
	private QuestionService questionService;

	@Autowired
	SchemeContentTableService schemeContentTableService;
	
	@Autowired
	ExerciseService exerciseService;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(questionBankDao);
	}

	// 查询全部的题库数据
	@Override
	public List<QuestionBank> findTotal() {
		return questionBankDao.findTotal();
	}

	@Override
	public List<QuestionBank> findListByName(QuestionBank questionBank) {
		return questionBankDao.findListByName(questionBank);
	}

	@Override
	public List<QuestionBank> findByAttribute(QuestionBank questionBank) {
		return questionBankDao.findByAttribute(questionBank);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(List<String> recordQuestionBank) throws Exception {
		// TODO Auto-generated method stub
		for (String id_string : recordQuestionBank) {
			long id = Long.parseLong(id_string);
			List<Question> questionList = questionService.findList("question_bank_id", id);
			List<SchemeContentTable> schemeContentTableList = new ArrayList<>();
			List<Exercise> exerciseList = new ArrayList<>();
			for (int i = 0; i < questionList.size(); i++) {
				SchemeContentTable schemeContentTable = schemeContentTableService.find("PID",
						questionList.get(i).getId());
				if (schemeContentTable != null) {
					schemeContentTableList.add(schemeContentTable);
				}
				List<Exercise> exerciseTempList = exerciseService.findList("pId", questionList.get(i).getId());
                if(exerciseTempList.size()>0){
                    exerciseList.addAll(exerciseTempList);
                }
			}
			if (schemeContentTableList.size() > 0) {
				throw new RuntimeException("该题库还被作业表引用，所以不能删除!");
			}else if(exerciseList.size() > 0){
			    throw new RuntimeException("该题库还被批改作业表引用，所以不能删除!");
			}else {
				// 删除题库
				questionBankDao.delete(id);
				// 删除题目
				questionService.deleteByBankId(id);
			}
		}

	}

}
