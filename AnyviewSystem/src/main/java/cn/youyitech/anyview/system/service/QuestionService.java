package cn.youyitech.anyview.system.service;

import java.util.List;
import cn.youyitech.anyview.system.entity.Question;

/**
 * SERVICE -题目service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface QuestionService extends GenericService<Question, Long> {

	// 查询全部题目
	List<Question> findTotal();

	// 通过题库id删除题目
	void deleteByBankId(long bankId);

	List<Question> findByAttribute(Question question);

	List<Question> findByFilterQuestionName(Question question);
}
