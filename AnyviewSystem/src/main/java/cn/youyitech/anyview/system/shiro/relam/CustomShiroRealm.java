package cn.youyitech.anyview.system.shiro.relam;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.Student;
import cn.youyitech.anyview.system.entity.SystemAcl;
import cn.youyitech.anyview.system.entity.SystemUser;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.service.StudentService;
import cn.youyitech.anyview.system.service.SystemUserAclsService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.shiro.ApplicationPrincipal;
import cn.youyitech.anyview.system.shiro.LoginToken;

/**
 * Realm - 权限认证
 * 
 * @version 1.0
 */

public class CustomShiroRealm extends AuthorizingRealm {

	@Resource
	private SystemUserService systemUserService;

	@Resource
	private TeacherService teacherService;

	@Resource
	private StudentService studentService;

	@Resource
	private SystemUserAclsService userAclsService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ApplicationPrincipal principal = (ApplicationPrincipal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			User user;
			if (principal.getRoleId() == 0) {
				user = teacherService.find("username", principal.getUsername());
			} else if (principal.getRoleId() == 3) {
				user = studentService.find("username", principal.getUsername());
			} else {
				user = systemUserService.find("username", principal.getUsername());
			}
			List<SystemAcl> acls = userAclsService.getAclsByRoleId(user.getRoleId());
			for (SystemAcl acl : acls) {
				info.addStringPermission(acl.getPermission());
			}

			return info;
		}

		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo principal = null;
		LoginToken loginToken = (LoginToken) token;
		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		String roleId = loginToken.getRoleId();
		String schoolId = loginToken.getSchoolId();
		if (username != null && password != null) {

			if (roleId.equals("0")) {
				// 老师
				Teacher user = teacherRole(loginToken, roleId, username, password, schoolId);
				principal = new SimpleAuthenticationInfo(new ApplicationPrincipal((long) user.getId(), username,
						user.getName(), user.getRoleId(), user.getSchoolId()), password, getName());
			} else if (roleId.equals("3")) {
				// 学生
				Student user = studentRole(loginToken, roleId, username, password, schoolId);
				principal = new SimpleAuthenticationInfo(new ApplicationPrincipal((long) user.getId(), username,
						user.getName(), user.getRoleId(), user.getSchoolId()), password, getName());
			} else if (roleId.equals("1")) {
				// 管理员
				SystemUser user = adminRole(loginToken, roleId, username, password, schoolId);
				principal = new SimpleAuthenticationInfo(new ApplicationPrincipal(user.getId(), username,
						user.getName(), user.getRoleId(), user.getSchoolId()), password, getName());
			} else if (roleId.equals("-1")) {
				// 超级管理员
				SystemUser user = superAdminRole(loginToken, roleId, username, password);
				principal = new SimpleAuthenticationInfo(new ApplicationPrincipal(user.getId(), username,
						user.getName(), user.getRoleId(), user.getSchoolId()), password, getName());
			}

			return principal;
		}

		return null;
	}

	public SystemUser superAdminRole(LoginToken loginToken, String roleId, String username, String password) {
		SystemUser user = null;
		SystemUser temp = null;
		try {
			temp = new SystemUser();
			temp.setRoleId(Long.parseLong(roleId));
			temp.setUsername(username);
			user = systemUserService.findByEntity(temp);

		} catch (Exception e) {
			// FIXME: 这样做是不对的
			e.printStackTrace();
		}

		if (user == null) {
			throw new UnknownAccountException();
		}

		if (!user.getIsEnabled()) {
			throw new DisabledAccountException();
		}

		if (user.getIsLocked() == true) {
			throw new LockedAccountException();
		}

		if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
			int loginFailureCount = user.getLoginFailureCount() + 1;
			if (loginFailureCount >= 5) {
				user.setIsLocked(true);
				user.setLockedDate(new Date());
			}
			user.setLoginFailureCount(loginFailureCount);
			systemUserService.update(user);

			throw new IncorrectCredentialsException();
		}

		user.setLastLoginIp(loginToken.getHost());
		user.setLastLoginDate(new Date());
		user.setLoginFailureCount(0);
		systemUserService.update(user);

		return user;

	}

	public SystemUser adminRole(LoginToken loginToken, String roleId, String username, String password,
			String schoolId) {
		List<SystemUser> temp = null;
		SystemUser user = null;
		try {
			temp = systemUserService.findList("username", username);
			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i).getSchool().getId() == Integer.parseInt(schoolId)) {
					user = temp.get(i);
					break;
				}
			}

		} catch (Exception e) {
			// FIXME: 这样做是不对的
			e.printStackTrace();
		}

		if (user == null) {
			throw new UnknownAccountException();
		}

		if (!user.getIsEnabled()) {
			throw new DisabledAccountException();
		}

		if (user.getIsLocked() == true) {
			throw new LockedAccountException();
		}

		if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
			int loginFailureCount = user.getLoginFailureCount() + 1;
			if (loginFailureCount >= 5) {
				user.setIsLocked(true);
				user.setLockedDate(new Date());
			}
			user.setLoginFailureCount(loginFailureCount);
			systemUserService.update(user);

			throw new IncorrectCredentialsException();
		}

		user.setLastLoginIp(loginToken.getHost());
		user.setLastLoginDate(new Date());
		user.setLoginFailureCount(0);
		systemUserService.update(user);

		return user;

	}

	public Teacher teacherRole(LoginToken loginToken, String roleId, String username, String password,
			String schoolId) {
		List<Teacher> temp = null;
		Teacher user = null;
		try {
			temp = teacherService.findList("username", username);
			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i).getSchoolId() == Integer.parseInt(schoolId)) {
					user = temp.get(i);
					break;
				}
			}

		} catch (Exception e) {
			// FIXME: 这样做是不对的
			e.printStackTrace();
		}

		if (user == null) {
			throw new UnknownAccountException();
		}

		if (user.getEnabled() == 0) {
			throw new DisabledAccountException();
		}

		if (user.getIsLocked() == true) {
			throw new LockedAccountException();
		}
		if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
			int loginFailureCount = user.getLoginFailureCount() + 1;
			if (loginFailureCount >= 5) {
				user.setIsLocked(true);
				user.setLockedDate(new Date());
			}
			user.setLoginFailureCount(loginFailureCount);
			teacherService.update(user);

			throw new IncorrectCredentialsException();
		}

		user.setLastLoginIp(loginToken.getHost());
		user.setLastLoginDate(new Date());
		user.setLoginFailureCount(0);
		teacherService.update(user);

		return user;

	}

	public Student studentRole(LoginToken loginToken, String roleId, String username, String password,
			String schoolId) {
		List<Student> temp = null;
		Student user = null;
		try {
			temp = studentService.findList("username", username);
			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i).getSchoolId() == Integer.parseInt(schoolId)) {
					user = temp.get(i);
					break;
				}
			}

		} catch (Exception e) {
			// FIXME: 这样做是不对的
			e.printStackTrace();
		}

		if (user == null) {
			throw new UnknownAccountException();
		}

		if (user.getEnabled() == 0) {
			throw new DisabledAccountException();
		}

		if (user.getIsLocked() == true) {
			throw new LockedAccountException();
		}

		if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
			int loginFailureCount = user.getLoginFailureCount() + 1;
			if (loginFailureCount >= 5) {
				user.setIsLocked(true);
				user.setLockedDate(new Date());
			}
			user.setLoginFailureCount(loginFailureCount);
			studentService.update(user);

			throw new IncorrectCredentialsException();
		}

		user.setLastLoginIp(loginToken.getHost());
		user.setLastLoginDate(new Date());
		user.setLoginFailureCount(0);
		studentService.update(user);

		return user;

	}

}