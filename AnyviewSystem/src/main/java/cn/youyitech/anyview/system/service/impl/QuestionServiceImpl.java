package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.youyitech.anyview.system.dao.QuestionDao;
import cn.youyitech.anyview.system.entity.Question;
import cn.youyitech.anyview.system.service.QuestionService;

@Service("questionServiceImpl")
public class QuestionServiceImpl extends GenericServiceImpl<Question, Long> implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(questionDao);
	}

	// 查询全部题目
	@Override
	public List<Question> findTotal() {
		return questionDao.findTotal();
	}

	// 通过题库id删除题目
	@Override
	public void deleteByBankId(long bankId) {
		questionDao.deleteByBankId(bankId);
	}

	@Override
	public List<Question> findByAttribute(Question question) {
		return questionDao.findByAttribute(question);
	}

	@Override
	public List<Question> findByFilterQuestionName(Question question) {
		return questionDao.findByFilterQuestionName(question);
	}

}
