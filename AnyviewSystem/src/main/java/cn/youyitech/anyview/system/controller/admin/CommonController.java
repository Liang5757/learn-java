package cn.youyitech.anyview.system.controller.admin;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.framework.loippi.utils.doc.JsonUtils;

import cn.youyitech.anyview.system.dto.TempUser;
import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.Student;
import cn.youyitech.anyview.system.entity.SystemAcl;
import cn.youyitech.anyview.system.entity.SystemUser;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.service.MailService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.StudentService;
import cn.youyitech.anyview.system.service.SystemUserAclsService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.shiro.ApplicationPrincipal;
import cn.youyitech.anyview.system.support.AdminEnum;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.IdsUtils;

/**
 * Controller - CommonController
 *
 * @version 1.0
 */
@Controller("adminCommonController")
@RequestMapping("/admin/common")
public class CommonController extends GenericController implements ServletContextAware {

	@Resource
	private MailService mailService;
	@Resource
	private SystemUserAclsService systemUserAclsService;

	@Resource
	private SystemUserService systemUserService;

	@Resource
	private TeacherService teacherService;

	@Resource
	private StudentService studentService;

	@Resource
	private IdsUtils idsUtils;

	/**
	 * servletContext
	 */
	private ServletContext servletContext;

	@Resource
	private RedisService redisService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 主页
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(HttpServletRequest request, ModelMap model) {
		Subject subject = SecurityUtils.getSubject();
		ApplicationPrincipal principal = (ApplicationPrincipal) subject.getPrincipal();
		if (subject != null) {
			if (principal != null && principal.getId() != null) {
				model.addAttribute("principal", principal);
			}
		}
		User user;
		if (principal.getRoleId() == 0) {
			user = teacherService.find("username", principal.getUsername());
		} else if (principal.getRoleId() == 3) {
			user = studentService.find("username", principal.getUsername());
		} else {
			user = systemUserService.find("username", principal.getUsername());
		}

		String aclStr =redisService.get("anyview_system_acl_" + user.getRoleId());

		if (aclStr == null || "[]".equals(aclStr) || "".equals(aclStr)) {
			List<SystemAcl> acls = systemUserAclsService.getAclsByRoleId(user.getRoleId());
			redisService.save("anyview_system_acl_" + user.getRoleId(), JsonUtils.toJson(acls));
			model.addAttribute("acls", acls);
		} else {
			model.addAttribute("acls", JsonUtils.toObject(aclStr, new TypeReference<List<SystemAcl>>() {
				@Override
				public Type getType() {
					return super.getType();
				}
			}));
		}
		return "/admin/common/main";
	}

	/**
	 * 首页
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model) {

		return "/admin/common/index";

	}

	/**
	 * 注销
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody String execute(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		SecurityUtils.getSubject().logout();
		return "0";

	}

	/**
	 * 错误提示
	 */
	@RequestMapping("/error")
	public String error() {
		return ERROR_VIEW;
	}

	/**
	 * 权限错误
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized(HttpServletRequest request, HttpServletResponse response) {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "unauthorized");
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return "/admin/common/unauthorized";
	}

	/**
	 * 资源不存在
	 */
	@RequestMapping("/resource_not_found")
	public String resourceNotFound() {
		return "/admin/common/resource_not_found";
	}

	// 修改密码
	@RequestMapping(value = "/edit_pass", method = RequestMethod.GET)
	public String editPass(ModelMap model) {
		model.addAttribute("user", systemUserService.getCurrentUser());
		return "/admin/passWord/edit_pass";
	}

	// 保存修改
	@RequestMapping(value = "/updatePass", method = RequestMethod.POST)
	public String updatePass(String password, String confimPass, RedirectAttributes redirectAttributes) {
		User systemUser = systemUserService.getCurrentUser();
		if (password.equals(confimPass)) {
			if (systemUser.getRoleId() == AdminEnum.authorityEnum.teacher.getValue()) {
				Teacher user = new Teacher();
				user.setUsername(systemUser.getUsername());
				user.setSchoolId(systemUser.getSchoolId());
				Teacher teacher = teacherService.findByEntity(user);
				teacher.setPassword(DigestUtils.md5Hex(teacher.getId() + password));
				teacherService.update(teacher);
			} else if (systemUser.getRoleId() == AdminEnum.authorityEnum.student.getValue()) {
				Student user = new Student();
				user.setUsername(systemUser.getUsername());
				user.setSchoolId(systemUser.getSchoolId());
				Student student = studentService.findByEntity(user);
				student.setPassword(DigestUtils.md5Hex(student.getId() + password));
				studentService.update(student);
			} else {
				SystemUser user = new SystemUser();
				user.setUsername(systemUser.getUsername());
				user.setSchoolId(systemUser.getSchoolId());
				SystemUser admin = systemUserService.findByEntity(user);
				admin.setPassword(DigestUtils.md5Hex(admin.getId() + password));
				systemUserService.update(admin);
			}
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/common/edit_pass.jhtml";
	}

	// 邮箱重置密码
	@RequestMapping("/forgetPassword")
	public String forgetPassword(Model model) {
		List<TempUser> systemUserNewList = idsUtils.tempUserJSONToList(redisService.get("tempUser"), TempUser.class);
		if (systemUserNewList != null) {
			model.addAttribute("systemUserNew", systemUserNewList.get(0));
		}
		return "/admin/passWord/updatePass";
	}

	@RequestMapping("/doforgetPass")
	public String findUser(TempUser tempUser, RedirectAttributes redirectAttributes) {
		List<TempUser> tempUserList = new ArrayList<>();
		tempUserList.add(tempUser);
		redisService.save("tempUser", JSON.toJSONString(tempUserList));
		if (tempUser.getRoleId() != null && AdminEnum.authorityEnum.teacher.getValue() == tempUser.getRoleId()) {
			Teacher teacherNew = new Teacher();
			teacherNew.setRoleId(tempUser.getRoleId());
			teacherNew.setUsername(tempUser.getUsername());
			teacherNew.setSchoolId(tempUser.getSchoolId());
			teacherNew.setEmail(tempUser.getEmail());
			Teacher teacher = teacherService.findByEntity(teacherNew);
			if (teacher == null) {
				addFlashMessage(redirectAttributes, Message.error("用户名或邮箱错误"));
				return "redirect:/admin/common/forgetPassword.jhtml";
			}
			try {
				teacherService.updatePassByUserName(teacher);
			} catch (MessagingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				addFlashMessage(redirectAttributes, Message.error("" + e));
			}
			addFlashMessage(redirectAttributes, Message.success("重置密码成功，新密码已发送至您的邮箱，请尽快登录系统并修改密码"));
			return "redirect:/admin/common/forgetPassword.jhtml";

		} else if (tempUser.getRoleId() != null && AdminEnum.authorityEnum.student.getValue() == tempUser.getRoleId()) {
			Student studentNew = new Student();
			studentNew.setRoleId(tempUser.getRoleId());
			studentNew.setSchoolId(tempUser.getSchoolId());
			studentNew.setUsername(tempUser.getUsername());
			studentNew.setEmail(tempUser.getEmail());
			Student student = studentService.findByEntity(studentNew);
			if (student == null) {
				addFlashMessage(redirectAttributes, Message.error("用户名或邮箱错误"));
				return "redirect:/admin/common/forgetPassword.jhtml";
			}
			try {
				studentService.updatePassByUserName(student);
			} catch (MessagingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				addFlashMessage(redirectAttributes, Message.error("" + e));
			}
			addFlashMessage(redirectAttributes, Message.success("重置密码成功，新密码已发送至您的邮箱，请尽快登录系统并修改密码"));
			return "redirect:/admin/common/forgetPassword.jhtml";

		} else {
			SystemUser systemUserNew = new SystemUser();
			systemUserNew.setRoleId(tempUser.getRoleId());
			if (tempUser.getRoleId() != null
					&& AdminEnum.authorityEnum.schoolManager.getValue() == tempUser.getRoleId()) {
				systemUserNew.setSchoolId(tempUser.getSchoolId());
			}
			systemUserNew.setUsername(tempUser.getUsername());
			systemUserNew.setEmail(tempUser.getEmail());
			SystemUser systemUser = systemUserService.findByEntity(systemUserNew);
			if (systemUser == null) {
				addFlashMessage(redirectAttributes, Message.error("用户名或邮箱错误"));
				return "redirect:/admin/common/forgetPassword.jhtml";
			}
			try {
				systemUserService.updatePassByUserName(systemUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				addFlashMessage(redirectAttributes, Message.error("重置密码失败：" + e));
			}
			addFlashMessage(redirectAttributes, Message.success("重置密码成功，新密码已发送至您的邮箱，请尽快登录系统并修改密码"));
			return "redirect:/admin/common/forgetPassword.jhtml";
		}
	}

}