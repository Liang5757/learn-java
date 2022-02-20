package cn.youyitech.anyview.system.dao;

import java.util.List;

import cn.youyitech.anyview.system.entity.ExamPlan;

import com.framework.loippi.mybatis.dao.GenericDao;

/**
 * DAO - 考试编排表dao层
 * 
 * @author zzq
 * @version 1.0
 */
public interface ExamPlanDao extends GenericDao<ExamPlan, Long> {

	// 获取全部的考试编排数据
	List<ExamPlan> findTotal();

	List<ExamPlan> findByAttribute(ExamPlan examPlan);

}
