package cn.youyitech.anyview.system.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.youyitech.anyview.system.utils.SortUtil;
import com.framework.loippi.support.Page;
import com.github.abel533.echarts.code.Sort;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.ClassEntity;
import cn.youyitech.anyview.system.entity.College;
import cn.youyitech.anyview.system.entity.Major;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.service.ClassAndStudentService;
import cn.youyitech.anyview.system.service.ClassService;
import cn.youyitech.anyview.system.service.CollegeService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.support.EnumConstants;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.IdsUtils;

import com.framework.loippi.support.Pageable;

/**
 * Controller - 应用版本
 * 
 * @author zzq
 * @version 1.0
 */
@Controller("adminClassController")
@RequestMapping("/admin/student_class")
public class ClassController extends GenericController {

	@Resource
	private ClassService classService;

	@Resource
	private ClassAndStudentService classAndStudentService;

	@Resource
	private SystemUserService systemUserService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private CollegeService collegeService;

	@Resource
	private RedisService redisService;

	@Autowired
	private IdsUtils idsUtils;

	/**
	 * 检查班级是否存在
	 */
	@RequestMapping(value = "/check_classname", method = RequestMethod.GET)
	public @ResponseBody boolean checkClassName(String classId, String className, int majorId, int year) {
		// 判断班级名称是否为空
		if (StringUtils.isEmpty(className)) {
			return false;
		}

		// 若为班级编辑模式，编辑的班级不需要与自己的名称进行比较
		if (classId != null && !classId.equals("")) {
			ClassEntity classSystem = classService.find(Long.parseLong(classId));

			if (classSystem != null) {
				if (className.equals(classSystem.getClassName()) && majorId == classSystem.getMajor().getId()
						&& year == classSystem.getYear()) {
					return true;
				}
			}
		}

		ClassEntity temp = new ClassEntity();
		temp.setClassName(className);
		temp.setMajor_id(majorId);
		temp.setYear(year);
		ClassEntity classEntity = classService.findByMajorIdAndClassName(temp);
		if (classEntity != null) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {

		return "/admin/student_class/add";
	}

	/**
	 * 添加保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, RedirectAttributes redirectAttributes) {

		// 获取班级名称
		String className = request.getParameter("className");
		// 获取专业id
		String majorId = request.getParameter("majorName");
		// 获取年届
		String year = request.getParameter("year");

		// 获取当前登录的用户
		User user = systemUserService.getCurrentUser();

		// 添加班级
		ClassEntity classEntity = new ClassEntity();
		classEntity.setClassName(className);
		classEntity.setMajor_id(Integer.parseInt(majorId));
		classEntity.setYear(Integer.parseInt(year));
		classEntity.setCreated_person(user.getName());
		classEntity.setCreated_date(new Date());
		classEntity.setUpdate_person(user.getName());
		classEntity.setUpdate_date(new Date());
		classEntity.setEnabled(1);
		classService.save(classEntity);

		// 操作成功的提示
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		return "redirect:list.jhtml";
	}

	/**
	 * 列表显示
	 */
	@RequiresPermissions("admin:system:class")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Pageable pageable, ModelMap model) {
		String classNameflag=request.getParameter("classNameflag");
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("params", map);
		Page<ClassEntity> page= this.classService.findByPage(pageable);
		if (classNameflag!=null){
			if (classNameflag.equals("asc"))SortUtil.sort(page.getContent(),true,"className");
			else SortUtil.sort(page.getContent(),false,"className");
			model.addAttribute("classNameflag",classNameflag);
		}
		// 若当前登录的用户权限是管理员，则列表显示中，只显示该管理员所属学校的班级
		model.addAttribute("page", page);

		return "/admin/student_class/list";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {

		// 通过班级id获取相应的班级
		ClassEntity className = classService.find(id);

		long schoolId = className.getMajor().getCollege().getSchool().getId();
		long collegeId = className.getMajor().getCollege().getId();

		List<College> collegeList = schoolService.find(schoolId).getSchoolCollegeList();
		SortUtil.sort(collegeList,true,"collegeName");
		List<Major> majorList = collegeService.find(collegeId).getCollegeMajorList();
		SortUtil.sort(majorList,true,"majorName");
		model.addAttribute("student_class", className);
		model.addAttribute("collegeList", collegeList);
		model.addAttribute("majorList", majorList);

		return "/admin/student_class/edit";
	}

	/**
	 * 编辑保存
	 */
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSave(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// 班级名称
		String className = request.getParameter("className");
		// 专业id
		String majorId = request.getParameter("majorName");
		// 年届
		String year = request.getParameter("year");

		// 获取当前登录的用户
		User user = systemUserService.getCurrentUser();

		// 添加班级
		ClassEntity classEntity = classService.find(Long.parseLong(request.getParameter("id")));
		classEntity.setClassName(className);
		classEntity.setMajor_id(Integer.parseInt(majorId));
		classEntity.setYear(Integer.parseInt(year));
		classEntity.setUpdate_person(user.getName());
		classEntity.setUpdate_date(new Date());
		classService.update(classEntity);

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		return "redirect:list.jhtml";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {

		List<String> recordClass = new ArrayList<>();
		for (long id : ids) {
			recordClass.add(String.valueOf(id));
		}
		if (recordClass.size() != 0) {
			try {
				classService.deleteOne(recordClass);
			} catch (Exception e) {
				return Message.error(e.getMessage());
			}
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}
	}

	/**
	 * 初始化数据
	 */
	@ModelAttribute
	public void init(Model model) {
		List<School> schoolList = schoolService.findAll();
		// 数据库中全部的学校
		model.addAttribute("schoolList", schoolList);
		// 当前登录的用户
		model.addAttribute("systemUser", systemUserService.getCurrentUser());

		// 若登录的用户权限是管理员，则通过该管理员所属的学校获取全部的学院
		if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()) {
			List<College>collegeList=schoolService.find((long) systemUserService.getCurrentUser().getSchoolId()).getSchoolCollegeList();
			SortUtil.sort(collegeList,true,"collegeName");
			model.addAttribute("collegeList",collegeList);
		}
	}

	/**
	 * 通过获取到的学院id得到相应的专业
	 */
	@ResponseBody
	@RequestMapping("/MajorAjax")
	public List<Major> majorAjax(@RequestBody College college, HttpServletResponse response) {
		List<Major> majorList = collegeService.find((long) college.getId()).getCollegeMajorList();
		SortUtil.sort(majorList,true,"majorName");
		return majorList;
	}

}
