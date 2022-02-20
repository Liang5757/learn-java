package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.QuestionBank;

/**
 * DAO - 题库管理表dao层
 * 
 * @author zzq
 * @version 1.0
 */
public interface QuestionBankDao extends GenericDao<QuestionBank, Long> {

	// 获取全部的题库数据
	List<QuestionBank> findTotal();

	List<QuestionBank> findListByName(QuestionBank questionBank);

	List<QuestionBank> findByAttribute(QuestionBank questionBank);

}
