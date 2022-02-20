package cn.youyitech.anyview.system.shiro.filter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import cn.youyitech.anyview.system.entity.Student;
import cn.youyitech.anyview.system.entity.SystemUser;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.service.RSAService;
import cn.youyitech.anyview.system.service.StudentService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.shiro.ApplicationPrincipal;
import cn.youyitech.anyview.system.shiro.LoginToken;
import cn.youyitech.anyview.system.support.AdminEnum;

/**
 * AuthenticationFilter - 权限认证Filter
 * 
 * @version 1.0
 */

public class AuthenticationFilter extends FormAuthenticationFilter {

	/** 默认"加密密码"参数名称 */
	private static final String DEFAULT_EN_PASSWORD_PARAM = "enPassword";

	/** 默认"用户权限"参数名称 */
	private static final String DEFAULT_ROLEID_PARAM = "roleId";

	/** 默认"学校"参数名称 */
	private static final String DEFAULT_SCHOOLID_PARAM = "schoolId";

	/** "加密密码"参数名称 */
	private String enPasswordParam = DEFAULT_EN_PASSWORD_PARAM;

	/** "加密密码"参数名称 */
	private String roleIdParam = DEFAULT_ROLEID_PARAM;

	/** "加密密码"参数名称 */
	private String schoolIdParam = DEFAULT_SCHOOLID_PARAM;

	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;

	@Resource
	private SystemUserService systemUserService;

	@Resource
	private TeacherService teacherService;

	@Resource
	private StudentService studentService;

	@Override
	protected org.apache.shiro.authc.AuthenticationToken createToken(ServletRequest servletRequest,
			ServletResponse servletResponse) {
		String username = getUsername(servletRequest);
		String password = getPassword(servletRequest);

		String roleId = getRoleId(servletRequest);
		String schoolId = getSchoolId(servletRequest);

		String host = getHost(servletRequest);
		if (username != null && password != null) {
			if (roleId.equals("0")) {
				// 老师
				List<Teacher> teacherList = teacherService.findList("username", username);
				for (int i = 0; i < teacherList.size(); i++) {
					if (teacherList.get(i).getSchoolId() == Integer.parseInt(schoolId)) {
						password = teacherList.get(i).getId() + password;
						break;
					}
				}

			} else if (roleId.equals("3")) {
				// 学生
				List<Student> studentList = studentService.findList("username", username);
				for (int i = 0; i < studentList.size(); i++) {
					if (studentList.get(i).getSchoolId() == Integer.parseInt(schoolId)) {
						password = studentList.get(i).getId() + password;
						break;
					}
				}

			} else if (roleId.equals("1")) {
				// 管理员
				List<SystemUser> systemUserList = systemUserService.findList("username", username);
				for (int i = 0; i < systemUserList.size(); i++) {
					if (systemUserList.get(i).getSchoolId() == Integer.parseInt(schoolId)) {
						password = systemUserList.get(i).getId() + password;
						break;
					}
				}

			} else if (roleId.equals("-1")) {
				// 超级管理员
				List<SystemUser> systemUserList = systemUserService.findList("username", username);
				for (int i = 0; i < systemUserList.size(); i++) {
					if (systemUserList.get(i).getRoleId() == AdminEnum.authorityEnum.superManager.getValue()) {
						password = systemUserList.get(i).getId() + password;
						break;
					}
				}
			}

		}
		LoginToken loginToken = new LoginToken(username, password, host);
		loginToken.setRoleId(roleId);
		loginToken.setSchoolId(schoolId);

		return loginToken;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "accessDenied");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		return super.onAccessDenied(request, response);
	}

	@Override
	protected boolean onLoginSuccess(org.apache.shiro.authc.AuthenticationToken token, Subject subject,
			ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		Session session = subject.getSession();
		Map<Object, Object> attributes = new HashMap<Object, Object>();
		Collection<Object> keys = session.getAttributeKeys();
		for (Object key : keys) {
			attributes.put(key, session.getAttribute(key));
		}
		session.stop();
		session = subject.getSession();
		for (Entry<Object, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}
		return super.onLoginSuccess(token, subject, servletRequest, servletResponse);
	}

	@Override
	protected String getPassword(ServletRequest servletRequest) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String password = rsaService.decryptParameter(enPasswordParam, request);
		rsaService.removePrivateKey(request);
		return password;
	}

	protected String getRoleId(ServletRequest servletRequest) {
		return WebUtils.getCleanParam(servletRequest, roleIdParam);
	}

	protected String getSchoolId(ServletRequest servletRequest) {
		return WebUtils.getCleanParam(servletRequest, schoolIdParam);
	}

	/**
	 * 获取"加密密码"参数名称
	 * 
	 * @return "加密密码"参数名称
	 */
	public String getEnPasswordParam() {
		return enPasswordParam;
	}

	/**
	 * 设置"加密密码"参数名称
	 * 
	 * @param enPasswordParam
	 *            "加密密码"参数名称
	 */
	public void setEnPasswordParam(String enPasswordParam) {
		this.enPasswordParam = enPasswordParam;
	}

	public String getRoleIdParam() {
		return roleIdParam;
	}

	public void setRoleIdParam(String roleIdParam) {
		this.roleIdParam = roleIdParam;
	}

	public String getSchoolIdParam() {
		return schoolIdParam;
	}

	public void setSchoolIdParam(String schoolIdParam) {
		this.schoolIdParam = schoolIdParam;
	}

}