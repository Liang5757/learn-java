package cn.youyitech.anyview.system.dao;

import java.util.List;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.SystemAcl;
import cn.youyitech.anyview.system.entity.SystemUserAcls;

/**
 * DAO - SystemUserAclsDao(用户权限)
 *
 * @version 2.0
 */

public interface SystemUserAclsDao extends GenericDao<SystemUserAcls, Long> {

	List<SystemAcl> getAclsByRoleId(Long roleId);
}
