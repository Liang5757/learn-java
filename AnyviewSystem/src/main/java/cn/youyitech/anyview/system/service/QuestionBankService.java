package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.QuestionBank;

/**
 * SERVICE -题库service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface QuestionBankService extends GenericService<QuestionBank, Long> {
	// 查询全部的题库数据
	List<QuestionBank> findTotal();

	List<QuestionBank> findListByName(QuestionBank questionBank);

	List<QuestionBank> findByAttribute(QuestionBank questionBank);

	void deleteOne(List<String> recordCollege) throws Exception;

}
