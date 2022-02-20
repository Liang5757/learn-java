package cn.youyitech.anyview.system.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import com.framework.loippi.mybatis.eitity.GenericEntity;

import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.SystemUser;

/**
 * Service - SystemUserService
 * 
 * @version 1.0
 */

public interface SystemUserService extends GenericService<SystemUser, Long> {

	/**
	 * 获取当前登录用户
	 * 
	 * @return 当前登录管理员,若不存在则返回null
	 */
	User getCurrentUser();

	/**
	 * 获取当前登录用户名
	 * 
	 * @return 当前登录用户名,若不存在则返回null
	 */
	String getCurrentUserName();

	/**
	 * 检查用户名是否存在
	 */
	List<SystemUser> usernameExists(String usernames);

	void deteleByUserName(String username);

	SystemUser findByUserName(String username);

	List<SystemUser> findById(Long roleId);

	void updatePassByUserName(SystemUser systemUser) throws Exception;

	List<SystemUser> findTotal();

	List<SystemUser> findByAttribute(SystemUser user);

	SystemUser findByEntity(SystemUser systemUserNew);

	void saveOne(SystemUser systemUser) throws MessagingException, IOException;

	void updateResetPassword(SystemUser systemUser) throws MessagingException, IOException;

}
