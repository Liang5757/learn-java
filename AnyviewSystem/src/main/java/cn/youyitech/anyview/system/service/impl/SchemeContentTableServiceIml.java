package cn.youyitech.anyview.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.loippi.utils.Paramap;

import cn.youyitech.anyview.system.dao.SchemeContentTableDao;
import cn.youyitech.anyview.system.entity.Question;
import cn.youyitech.anyview.system.entity.SchemeContentTable;
import cn.youyitech.anyview.system.entity.WorkingTable;
import cn.youyitech.anyview.system.service.QuestionService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchemeContentTableService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.WorkingTableService;

/**
 * @author TT
 *
 *         2017年8月22日
 */
@Service
public class SchemeContentTableServiceIml extends GenericServiceImpl<SchemeContentTable, Long>
		implements SchemeContentTableService {
	@Autowired
	private SchemeContentTableDao schemeContentTableDao;

	@Autowired
	private WorkingTableService workingTableService;

	@Autowired
	private SystemUserService systemUserService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private QuestionService questionService;

	/*
	 * @Autowired private SchemeContentCacheTableService
	 * schemeContentCacheTableService;
	 */

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(schemeContentTableDao);
	}

	@Override
	public List<SchemeContentTable> findContent(SchemeContentTable schemeContentTable) {
		// TODO Auto-generated method stub
		return schemeContentTableDao.findContent(schemeContentTable);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(Long[] ids) {
		// TODO Auto-generated method stub
		// 更改作业表的总题数
		for (Long id : ids) {
			// SchemeContentTable schemeContentTable =
			// schemeContentTableDao.find(id);
			schemeContentTableDao.delete(id);
		}
		List<SchemeContentTable> listSize = schemeContentTableDao.findByParams(Paramap.create().put("VID",
				Long.valueOf((String) redisService.get(systemUserService.getCurrentUserName() + "VID"))));
		WorkingTable workingTable = new WorkingTable();
		workingTable.setId(Long.valueOf((String) redisService.get(systemUserService.getCurrentUserName() + "VID")));
		if (listSize.size() > 0) {
			workingTable.setTotalNum(listSize.size());
		} else {
			workingTable.setTotalNum(0);
		}
		workingTableService.updateTotalNum(workingTable);
	}

	@Override
	public void insert(SchemeContentTable schemeContentTable, String ids) {
		// TODO Auto-generated method stub
		// 添加题目
		List<Question> questionList = new ArrayList<>();
		String[] idSum = ids.split(",");
		for (String idString : idSum) {
			String[] idOne = idString.split(":");
			Question question = questionService.find(Long.valueOf(idOne[0]));
			if (question != null) {
				questionList.add(question);
			}

		}
		if (questionList.size() > 0) {
			for (int i = 0; i < questionList.size(); i++) {
				schemeContentTable.setVChapName(questionList.get(i).getChapter());
				schemeContentTable.setDifficulty(questionList.get(i).getDifficulty());
				schemeContentTable.setPID((long) questionList.get(i).getId());
				schemeContentTable.setVPName(questionList.get(i).getQuestion_name());
				if (questionList.get(i).getState().equals("启用")) {
					schemeContentTable.setStatus(1);
				} else {
					schemeContentTable.setStatus(0);
				}
				schemeContentTable.setUpdater(systemUserService.getCurrentUser().getName());
				schemeContentTableDao.insert(schemeContentTable);

			}
		}

		// 更改作业表的总题数
		List<SchemeContentTable> listSize = schemeContentTableDao
				.findByParams(Paramap.create().put("VID", schemeContentTable.getVID()));
		WorkingTable workingTable = new WorkingTable();
		workingTable.setId((long) schemeContentTable.getVID());
		workingTable.setTotalNum(listSize.size());
		workingTableService.updateTotalNum(workingTable);
	}

}
