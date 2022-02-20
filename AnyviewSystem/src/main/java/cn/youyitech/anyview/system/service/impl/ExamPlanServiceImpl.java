package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.youyitech.anyview.system.dao.ExamPlanDao;
import cn.youyitech.anyview.system.entity.ExamPlan;
import cn.youyitech.anyview.system.service.ExamPlanService;

@Service("examPlanServiceImpl")
public class ExamPlanServiceImpl extends GenericServiceImpl<ExamPlan, Long> implements ExamPlanService {

	@Autowired
	private ExamPlanDao examPlanDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(examPlanDao);
	}

	// 查询全部的考试编排
	@Override
	public List<ExamPlan> findTotal() {
		return examPlanDao.findTotal();
	}

	@Override
	public List<ExamPlan> findByAttribute(ExamPlan examPlan) {
		return examPlanDao.findByAttribute(examPlan);
	}

}
