package cn.youyitech.anyview.system.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登录令牌
 * 
 * @version 1.0
 */

public class LoginToken extends UsernamePasswordToken {

	private String schoolId;

	private String roleId;

	private static final long serialVersionUID = 5898441540965086534L;

	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param host
	 *            IP
	 */
	public LoginToken(String username, String password, String host) {
		super(username, password, host);
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}