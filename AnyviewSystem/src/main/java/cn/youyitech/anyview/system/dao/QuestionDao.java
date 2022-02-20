package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.Question;

/**
 * DAO - 题目管理表dao层
 * 
 * @author zzq
 * @version 1.0
 */
public interface QuestionDao extends GenericDao<Question, Long> {

	// 获取全部的题目数据
	List<Question> findTotal();

	// 通过题库id删除题目
	void deleteByBankId(long bankId);

	List<Question> findByAttribute(Question question);

	List<Question> findByFilterQuestionName(Question question);
}
