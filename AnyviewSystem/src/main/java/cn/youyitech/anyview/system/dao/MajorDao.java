package cn.youyitech.anyview.system.dao;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.Major;

/**
 * @author TT
 *
 *         2017年8月22日
 */
public interface MajorDao extends GenericDao<Major, Long> {

	Major findByCollegeIdAndMajorName(Major major);

}
