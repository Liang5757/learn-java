package cn.youyitech.anyview.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.SystemAcl;

/**
 * DAO - SystemAclDao
 *
 * @version 1.0
 */

public interface SystemAclDao extends GenericDao<SystemAcl, Long> {

	/**
	 * 查找资源列表
	 *
	 * @return
	 */
	List<SystemAcl> findRoots();

	/**
	 * 查找资源列表
	 *
	 * @return
	 */
	List<SystemAcl> findByRoleId(@Param("roleId") Long roleId);

	/**
	 * 查找子类资源列表
	 *
	 * @param id
	 * @return
	 */
	List<SystemAcl> findChildrens(@Param("id") Long id);
}
