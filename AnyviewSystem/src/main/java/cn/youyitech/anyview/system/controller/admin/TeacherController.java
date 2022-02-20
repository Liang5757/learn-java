package cn.youyitech.anyview.system.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.loippi.support.Pageable;

import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.service.CollegeService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.support.EnumConstants;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.IdsUtils;

/**
 * Controller - 应用版本
 * 
 * @author zzq
 * @version 1.0
 */

@Controller("adminTeacherController")
@RequestMapping("/admin/teacher")
public class TeacherController extends GenericController {

	@Resource
	private TeacherService teacherService;

	@Resource
	private SystemUserService systemUserService;

	@Resource
	private SchoolService schoolService;

	@Resource
	private CollegeService collegeService;

	@Value("${system.init_password}")
	private String init_password;

	@Resource
	private RedisService redisService;

	@Autowired
	private IdsUtils idsUtils;

	/**
	 * 检查教师用户名是否存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody boolean checkUsername(String teacherId, String username, int schoolId) {

		// 判断用户名是否为空
		if (StringUtils.isEmpty(username)) {
			return false;
		}

		// 若是编辑教师状态，用户名不需要与自己用户名进行比较，可以直接确定
		if (teacherId != null && !teacherId.equals("")) {
			Teacher currentTeacher = teacherService.find("id", teacherId);
			String username_string = username;
			if (currentTeacher != null) {
				if (currentTeacher.getUsername().equals(username_string)) {
					return true;
				}
			}
		}

		// 用户名本校唯一，与教师表的用户名进行验证
		Teacher teacher = new Teacher();
		teacher.setUsername(username);
		teacher.setSchoolId(schoolId);
		List<Teacher> teacherList = teacherService.findByAttribute(teacher);
		if (teacherList.size() > 0) {
			return false;
		}

		return true;
	}

	/**
	 * 添加教师
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {

		// 把所有学校传到前端进行选择
		model.addAttribute("schoolLists", schoolService.findAll());

		return "/admin/teacher/add";
	}

	/**
	 * 添加教师保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {

		String schoolId = request.getParameter("schoolId");
		String collegeId = request.getParameter("collegeName");
		// 用户名
		String username = request.getParameter("number");
		// 姓名
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");

		// 当前登录用户
		User currentUser = systemUserService.getCurrentUser();

		Teacher teacher = getTeacher("", sex, collegeId, username, name, schoolId, email);
		teacher.setEnabled(1);
		teacher.setRoleId(EnumConstants.authorityEnum.teacher.getValue());
		teacher.setIsLocked(false);
		teacher.setCreatedDate(new Date());
		teacher.setCreatedBy(currentUser.getName());
		teacherService.saveOne(teacher);

		// 操作成功的提示
		addFlashMessage(redirectAttributes, Message.success("添加教师成功，用户名和密码已经发送至教师邮箱，请注意查收"));

		return "redirect:list.jhtml";
	}

	/**
	 * 列表显示
	 */
	@RequiresPermissions("admin:system:teacher")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Pageable pageable, ModelMap model) {

		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("page", this.teacherService.findByPage(pageable));
		model.addAttribute("params", map);

		return "/admin/teacher/list";
	}

	/**
	 * 删除操作
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {

		List<String> recordTeacher = new ArrayList<>();
		for (long id : ids) {
			recordTeacher.add(String.valueOf(id));
		}
		if (recordTeacher.size() != 0) {
			try {
				teacherService.deleteOne(recordTeacher);
			} catch (Exception e) {
				return ERROR_MESSAGE;
			}
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}
	}

	/**
	 * 初始化密码操作
	 */
	@RequestMapping(value = "/initialize", method = RequestMethod.GET)
	public String initialize(Long[] ids, RedirectAttributes redirectAttributes) {

		if (ids != null) {
			for (long id : ids) {
				Teacher teacher = teacherService.find(id);
				try {
					teacherService.updateInitPass(teacher);
				} catch (MessagingException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					addFlashMessage(redirectAttributes, Message.error("" + e));
				}
			}
			addFlashMessage(redirectAttributes, Message.success("初始化密码成功，新密码为123456，并已经发送至教师邮箱，请注意查收"));
		} else {
			addFlashMessage(redirectAttributes, ERROR_MESSAGE);
		}
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {

		// 通过id获取需要编辑的教师
		Teacher teacher = teacherService.find(id);

		// 把该教师所属的学校中的所有学院发送到前端
		model.addAttribute("colleges", collegeService.findByIdMany(teacher.getCollege().getSchool().getId()));

		model.addAttribute("teacher", teacher);

		return "/admin/teacher/edit";
	}

	/**
	 * 编辑保存
	 */
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSave(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String id = request.getParameter("id");
		String schoolId = request.getParameter("schoolId");
		String collegeId = request.getParameter("collegeName");
		String username = request.getParameter("number");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");

		// 往教师表中修改数据
		Teacher teacher = getTeacher(id, sex, collegeId, username, name, schoolId, email);
		teacherService.update(teacher);

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		return "redirect:list.jhtml";
	}

	/**
	 * 初始化数据
	 */
	@ModelAttribute
	public void init(Model model) {

		List<School> schoolList = schoolService.findAll();
		// 数据库中所有的学校
		model.addAttribute("schoolList", schoolList);

		// 若权限角色为管理员，则直接发送该管理员所属学校下的所有学院

		if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()) {
			model.addAttribute("collegeList",
					schoolService.find((long) systemUserService.getCurrentUser().getSchoolId()).getSchoolCollegeList());
		}

		// 当前登录的用户
		model.addAttribute("systemUser", systemUserService.getCurrentUser());

	}

	/**
	 * 添加或修改教师信息
	 */
	public Teacher getTeacher(String id, String sex, String collegeId, String username, String name, String schoolId,
			String email) {

		Teacher teacher = new Teacher();
		if (id != null && !id.equals("")) {
			teacher.setId(Integer.parseInt(id));
		}
		teacher.setSex(sex);
		teacher.setCollege_id(Integer.parseInt(collegeId));
		teacher.setUsername(username);
		teacher.setName(name);
		teacher.setSchoolId(Integer.parseInt(schoolId));
		teacher.setEmail(email);
		teacher.setLastUpdatedDate(new Date());
		teacher.setLastUpdatedBy(systemUserService.getCurrentUser().getName());

		return teacher;

	}

}
