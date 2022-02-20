package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;
import com.framework.loippi.utils.Paramap;

import cn.youyitech.anyview.system.entity.School;

/**
 * @author TT
 *
 *         2017年8月22日
 */
public interface SchoolDao extends GenericDao<School, Long> {

	List<School> findByDeleteId(Long id);

	List<School> findListByName(School school);

	List<School> findNotIdList(Paramap put);

	School findBySchoolName(String schoolName);

}
