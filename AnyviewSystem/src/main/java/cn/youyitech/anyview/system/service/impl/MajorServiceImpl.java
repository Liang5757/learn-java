package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.youyitech.anyview.system.dao.MajorDao;
import cn.youyitech.anyview.system.entity.Major;
import cn.youyitech.anyview.system.service.MajorService;
import cn.youyitech.anyview.system.support.Message;

import com.framework.loippi.utils.Paramap;

/**
 * @author TT
 *
 *         2017年8月22日
 */
@Service
public class MajorServiceImpl extends GenericServiceImpl<Major, Long> implements MajorService {
	@Autowired
	private MajorDao majorDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(majorDao);
	}

	@Override
	public List<Major> majorNameExists(String majorName) {
		List<Major> majorList = majorDao.findByParams(Paramap.create().put("majorName", majorName));
		return majorList;
	}

	@Override

	public Major findByCollegeIdAndMajorName(Major major) {
		return majorDao.findByCollegeIdAndMajorName(major);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(Long[] ids) throws Exception {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			Major major = majorDao.find(id);
			if (major.getMajorClassList().size() > 0) {
				throw new RuntimeException("请先删除该专业的所有班级！");
			} else {
				majorDao.delete(id);
			}
		}

	}

}
