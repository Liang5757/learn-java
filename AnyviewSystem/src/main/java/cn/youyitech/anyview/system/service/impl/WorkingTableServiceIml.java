package cn.youyitech.anyview.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.loippi.utils.Paramap;

import cn.youyitech.anyview.system.dao.WorkingTableDao;
import cn.youyitech.anyview.system.entity.CourseArrangeAndWorkingTable;
import cn.youyitech.anyview.system.entity.SchemeContentTable;
import cn.youyitech.anyview.system.entity.WorkingTable;
import cn.youyitech.anyview.system.service.CourseArrangeAndWorkingTableService;
import cn.youyitech.anyview.system.service.SchemeContentTableService;
import cn.youyitech.anyview.system.service.WorkingTableService;
import cn.youyitech.anyview.system.service.ExamPlanService;
import cn.youyitech.anyview.system.entity.ExamPlan;

/**
 * @author TT
 *
 *         2017年8月22日
 */
@Service
public class WorkingTableServiceIml extends GenericServiceImpl<WorkingTable, Long> implements WorkingTableService {
	@Autowired
	private WorkingTableDao workingTableDao;

	@Autowired
	private SchemeContentTableService schemeContentTableService;

	@Autowired
	private CourseArrangeAndWorkingTableService courseArrangeAndWorkingTableService;

	@Autowired
	private ExamPlanService examPlanService;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(workingTableDao);
	}

	@Override
	public List<WorkingTable> tableNameExists(String tableName) {
		// TODO Auto-generated method stub

		return workingTableDao.findByParams(Paramap.create().put("tableName", tableName));
	}

	@Override
	public List<WorkingTable> findWorkList(long id) {
		// TODO Auto-generated method stub
		return workingTableDao.findWorkList(id);
	}

	@Override
	public List<WorkingTable> findContentList(WorkingTable workingTable) {
		// TODO Auto-generated method stub
		return workingTableDao.findContentList(workingTable);
	}

	@Override
	public List<WorkingTable> findListByName(WorkingTable workingTable) {
		// TODO Auto-generated method stub
		return workingTableDao.findListByName(workingTable);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(Long[] workingTableids) {
		// TODO Auto-generated method stub
		List<Long> ids = new ArrayList<>();
		for (Long id : workingTableids) {
			// long id = Long.parseLong(id_string);
			List<ExamPlan> examPlanList = examPlanService.findList("vId", id);
			if (examPlanList.size() > 0) {
				throw new RuntimeException("请先删除该作业表的所有考试编排！");
			}
			workingTableDao.delete(id);
			List<CourseArrangeAndWorkingTable> courseArrangeAndWorkingTableList = courseArrangeAndWorkingTableService
					.findList("workingTableId", id);
			if (courseArrangeAndWorkingTableList.size() > 0) {
				courseArrangeAndWorkingTableService.deleteByWorkingTableId(id);
			}
			List<SchemeContentTable> schemeContentTableList = schemeContentTableService.findList("VID", id);
			for (int i = 0; i < schemeContentTableList.size(); i++) {
				ids.add(schemeContentTableList.get(i).getID());
			}
		}
		if (ids.size() > 0) {
			Long[] array = new Long[ids.size()];
			for (int i = 0; i < ids.size(); i++) {
				array[i] = ids.get(i);
			}
			schemeContentTableService.deleteOne(array);
		}
	}

	@Override
	public void updateTotalNum(WorkingTable workingTable) {
		// TODO Auto-generated method stub
		workingTableDao.updateTotalNum(workingTable);
	}

}
