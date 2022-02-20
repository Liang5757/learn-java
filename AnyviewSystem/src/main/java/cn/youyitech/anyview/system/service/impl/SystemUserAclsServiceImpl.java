package cn.youyitech.anyview.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.youyitech.anyview.system.dao.SystemAclDao;
import cn.youyitech.anyview.system.dao.SystemUserAclsDao;
import cn.youyitech.anyview.system.entity.SystemAcl;
import cn.youyitech.anyview.system.entity.SystemUserAcls;
import cn.youyitech.anyview.system.service.SystemUserAclsService;

/**
 * ServiceImpl - SystemUserAclsServiceImpl(用户权限)
 * 
 * @version 2.0
 */

@Service
public class SystemUserAclsServiceImpl extends GenericServiceImpl<SystemUserAcls, Long>
		implements SystemUserAclsService {

	@Autowired
	private SystemUserAclsDao userAclsDao;

	@Autowired
	private SystemAclDao AclDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(userAclsDao);
	}

	@Override
	public List<SystemAcl> getAclsByRoleId(Long roleId) {
		return AclDao.findByRoleId(roleId);
	}

}
