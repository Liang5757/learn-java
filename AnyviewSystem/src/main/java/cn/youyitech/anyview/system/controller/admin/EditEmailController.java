package cn.youyitech.anyview.system.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.youyitech.anyview.system.entity.Student;
import cn.youyitech.anyview.system.service.StudentService;
import cn.youyitech.anyview.system.service.SystemUserService;

import com.framework.loippi.support.Pageable;

/**
 * Controller - 应用版本
 * 
 * @author zzq
 * @version 1.0
 */

@Controller("adminEditEmailController")
@RequestMapping("/admin/editemail")
public class EditEmailController extends GenericController {

	@Resource
	private SystemUserService systemUserService;

	@Resource
	private StudentService studentService;

	/**
	 * 修改邮箱界面
	 */
	@RequiresPermissions("admin:system:editemail")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Pageable pageable, ModelMap model) {
		// 当前登录的学生信息
		model.addAttribute("student", (Student) systemUserService.getCurrentUser());

		return "/admin/editemail/list";
	}

	/**
	 * 保存修改邮箱
	 */
	@RequestMapping(value = { "/updateEmail" }, method = { RequestMethod.POST })
	public String updateEmail(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// 邮箱信息
		String email = request.getParameter("email");
		// 确认邮箱信息
		String confimEmail = request.getParameter("confimEmail");
		// 邮箱信息与确认邮箱信息相同，则更新邮箱成功
		if (email.equals(confimEmail)) {
			Student student = (Student) systemUserService.getCurrentUser();
			student.setEmail(email);
			studentService.update(student);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else {
			addFlashMessage(redirectAttributes, ERROR_MESSAGE);
		}

		return "redirect:list.jhtml";
	}

}
