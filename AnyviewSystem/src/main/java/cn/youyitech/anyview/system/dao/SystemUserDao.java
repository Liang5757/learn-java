package cn.youyitech.anyview.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.loippi.mybatis.dao.GenericDao;

import cn.youyitech.anyview.system.entity.SystemUser;

/**
 * DAO - SystemUserDao
 *
 * @version 1.0
 */

public interface SystemUserDao extends GenericDao<SystemUser, Long> {

	SystemUser findUserAndRole(@Param("id") Long id);

	SystemUser findByUserName(@Param("username") String username);

	void deleteByUserName(@Param("username") String username);

	List<SystemUser> findById(Long roleId);

	void UpdatePassByUserName(SystemUser systemUser);

	List<SystemUser> findTotal();

	List<SystemUser> findByAttribute(SystemUser user);

	SystemUser findByEntity(SystemUser systemUserNew);

}
