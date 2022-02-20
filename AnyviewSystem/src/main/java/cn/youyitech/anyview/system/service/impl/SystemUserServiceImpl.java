package cn.youyitech.anyview.system.service.impl;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.loippi.utils.Paramap;

import cn.youyitech.anyview.system.dao.StudentDao;
import cn.youyitech.anyview.system.dao.SystemUserDao;
import cn.youyitech.anyview.system.dao.TeacherDao;
import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.SystemUser;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.service.MailService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.shiro.ApplicationPrincipal;

/**
 * ServiceImpl - SystemUserServiceImpl
 * 
 * @version 1.0
 */

@Service("systemUserServiceImpl")
public class SystemUserServiceImpl extends GenericServiceImpl<SystemUser, Long> implements SystemUserService {

	@Autowired
	private SystemUserDao systemUserDao;

	@Value("${system.reset_password_startcontent}")
	private String reset_startcontent;

	@Value("${system.login_address_content}")
	private String login_address;

	@Value("${system.reset_title}")
	private String reset_title;

	@Value("${system.password_len}")
	private int password_len;

	@Value("${system.new_user_startcontent}")
	private String user_startcontent;

	@Value("${system.new_user_mincontent}")
	private String user_mincontent;

	@Value("${system.new_user_title}")
	private String user_title;

	@Value("${system.init_password_title}")
	private String init_password_title;

	@Value("${system.init_title_password_content}")
	private String init_title_password_content;

	@Value("${system.init_password}")
	private String init_password;

	@Autowired
	private MailService mailService;

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(systemUserDao);
	}

	@Transactional(readOnly = true)
	public User getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			ApplicationPrincipal principal = (ApplicationPrincipal) subject.getPrincipal();
			if (principal != null && principal.getId() != null) {
				if (principal.getRoleId() == 0) {
					// 老师
					return teacherDao.find(principal.getId());
				} else if (principal.getRoleId() == 3) {
					// 学生
					return studentDao.find(principal.getId());
				} else if (principal.getRoleId() == -1 || principal.getRoleId() == 1) {
					// 超级管理员与管理员
					return systemUserDao.find(principal.getId());
				}
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUserName() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			ApplicationPrincipal principal = (ApplicationPrincipal) subject.getPrincipal();
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	@Override
	public List<SystemUser> usernameExists(String usernames) {
		// TODO Auto-generated method stub
		Paramap p = Paramap.create();
		p.put("username", usernames);
		return systemUserDao.findByParams(p);

	}

	public void deteleByUserName(String username) {
		systemUserDao.deleteByUserName(username);
	}

	@Override
	public SystemUser findByUserName(String username) {
		return systemUserDao.findByUserName(username);
	}

	@Override
	public List<SystemUser> findById(Long roleId) {
		// TODO Auto-generated method stub
		return systemUserDao.findById(roleId);
	}

	// 忘记密码
	@Override
	public void updatePassByUserName(SystemUser systemUser) throws Exception {
		// TODO Auto-generated method stub
		// 6位随机数
		String pass = "";
		for (int j = 0; j < 6; j++) {
			int d = (int) (Math.floor(Math.random() * 10));
			pass = pass + d;
		}
		// 为用户添加密码:用户id+6位随机数
		systemUser.setPassword(DigestUtils.md5Hex(systemUser.getId() + pass));
		String content = systemUser.getUsername() + reset_startcontent + pass + login_address;
		mailService.send(systemUser.getEmail(), reset_title, content);
		systemUserDao.update(systemUser);
	}

	@Override
	public List<SystemUser> findTotal() {
		return systemUserDao.findTotal();
	}

	@Override
	public List<SystemUser> findByAttribute(SystemUser user) {
		return systemUserDao.findByAttribute(user);
	}

	public void saveOne(SystemUser systemUser) throws MessagingException, IOException {
		String pass = "";
		for (int j = 0; j < 6; j++) {
			int d = (int) (Math.floor(Math.random() * 10));
			pass = pass + d;
		}
		String content = systemUser.getUsername() + user_startcontent + systemUser.getUsername() + user_mincontent
				+ pass + login_address;
		systemUser.setPassword(pass);
		save(systemUser);
		SystemUser systemUserNew = systemUserDao.findByEntity(systemUser);
		// 为用户修改密码:用户id+6位随机数
		systemUser.setPassword(DigestUtils.md5Hex((systemUserNew.getId()) + pass));
		update(systemUser);
		mailService.send(systemUser.getEmail(), user_title, content);
	}

	@Override
	public SystemUser findByEntity(SystemUser systemUserNew) {
		return systemUserDao.findByEntity(systemUserNew);
	}

	// 初始化密码
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateResetPassword(SystemUser systemUser) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		String content = systemUser.getUsername() + init_title_password_content + init_password + login_address;
		mailService.send(systemUser.getEmail(), user_title, content);
		// 为用户添加密码:用户id+init_password
		systemUser.setPassword(DigestUtils.md5Hex(systemUser.getId() + init_password));
		update(systemUser);
	}

}
