package cn.youyitech.anyview.system.utils;

import java.util.List;

import javax.annotation.Resource;

import cn.youyitech.anyview.system.entity.ExamPlan;
import cn.youyitech.anyview.system.service.ExamPlanService;

/**
 * spring定时器
 * 
 * @author zzq
 */

public class SchedulTask {

	@Resource
	private ExamPlanService examPlanService;

	/**
	 * 考试编排定时器
	 */
	public void task() {
		List<ExamPlan> examPlanList1 = examPlanService.findAll();
		for (int i = 0; i < examPlanList1.size(); i++) {
			if (examPlanList1.get(i).getKind() == 1 && examPlanList1.get(i).getExamStatus() != 3) {
				long startTime = examPlanList1.get(i).getStartTime().getTime();
				int duration = examPlanList1.get(i).getDuration();
				long systemTime = System.currentTimeMillis();
				if (systemTime > startTime) {
					ExamPlan examPlan = examPlanList1.get(i);
					if (systemTime > (startTime + (duration * 60 * 1000))) {
						examPlan.setExamStatus(3);
					} else {
						examPlan.setExamStatus(1);
					}
					examPlanService.update(examPlan);
				}
			}
			if (examPlanList1.get(i).getKind() == 0 && examPlanList1.get(i).getExamStatus() == 1) {
				ExamPlan examPlan = examPlanList1.get(i);
				examPlan.setDoneTime(examPlan.getDoneTime() + 1);
				if (examPlan.getDoneTime() == examPlan.getDuration()) {
					examPlan.setExamStatus(3);
					examPlan.setOperation(3);
				}
				examPlanService.update(examPlan);
			}
		}
	}

}
