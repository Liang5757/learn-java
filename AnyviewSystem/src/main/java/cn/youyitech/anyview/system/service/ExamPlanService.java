package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.ExamPlan;

/**
 * SERVICE -考试编排service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface ExamPlanService extends GenericService<ExamPlan, Long> {

	// 查询全部的考试编排
	List<ExamPlan> findTotal();

	List<ExamPlan> findByAttribute(ExamPlan examPlan);

}
