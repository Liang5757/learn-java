package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.SystemAcl;
import cn.youyitech.anyview.system.entity.SystemUserAcls;

/**
 * SERVICE - SystemUserAclsService(用户权限)
 * 
 * @version 2.0
 */

public interface SystemUserAclsService extends GenericService<SystemUserAcls, Long> {

	List<SystemAcl> getAclsByRoleId(Long username);
}
