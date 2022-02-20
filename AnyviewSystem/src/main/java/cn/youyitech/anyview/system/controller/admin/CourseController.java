package cn.youyitech.anyview.system.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.youyitech.anyview.system.utils.SortUtil;
import com.framework.loippi.support.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.loippi.support.Pageable;

import cn.youyitech.anyview.system.entity.College;
import cn.youyitech.anyview.system.entity.Course;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.service.CollegeService;
import cn.youyitech.anyview.system.service.CourseArrangeService;
import cn.youyitech.anyview.system.service.CourseService;
import cn.youyitech.anyview.system.service.ExamPlanService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.WorkingTableService;
import cn.youyitech.anyview.system.support.AdminEnum;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.IdsUtils;

/**
 * @author TT 课程管理 Controller 2017年8月22日
 */
@Controller
@RequestMapping("/admin/course")
public class CourseController extends GenericController {

	@Autowired
	SystemUserService systemUserService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private CollegeService collegeService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private RedisService redisService;

	// 获取课程内容
	@RequestMapping("/CourseContent")
	public String majorContent(Model model, Pageable pageable, HttpServletRequest request) {
		String courseNameflag=request.getParameter("courseNameflag");
		System.out.println(courseNameflag);
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		Page<Course>page=courseService.findByPage(pageable);
		if (courseNameflag!=null){
			if (courseNameflag.equals("asc")) SortUtil.sort(page.getContent(),true,"courseName");
			else SortUtil.sort(page.getContent(),false,"courseName");
			model.addAttribute("courseNameflag",courseNameflag);
		}
		model.addAttribute("page", page);
		model.addAttribute("params", map);
		return "admin/course/courseList";
	}

	// 添加课程
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addCourse(Model model) {
		return "admin/course/addCourse";
	}

	// 保存添加
	@RequestMapping("/saveAddCourse")
	public String inserCourse(Course course, RedirectAttributes redirectAttributes) {
		courseService.save(course);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/course/CourseContent.jhtml";
	}

	// 修改课程
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		Course course = courseService.find(id);
		College college = new College();
		college.setSchoolId(course.getCollege().getSchool().getId());
		college.setId(course.getCollege().getId());
		// 找出该课程所在的学校的所有学院（去除本身所在的学院）
		List<College> collegeList = collegeService.findNotIdList(college);
		model.addAttribute("colleges", collegeList);
		model.addAttribute("course", course);
		List<School> schoolList = schoolService.findNotIdList("id", course.getCollege().getSchool().getId());
		model.addAttribute("schoolLists", schoolList);
		return "/admin/course/updateCourse";
	}

	// 保存修改
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSaveCourse(Course course, RedirectAttributes redirectAttributes) {
		courseService.update(course);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/course/CourseContent.jhtml";
	}

	// 选中删除
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {
		if (ids.length != 0) {
			try {
				courseService.deleteOne(ids);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Message.error(e.getMessage());
			}
			redisService.delete(systemUserService.getCurrentUserName() + "ttrecordCourse");
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}
	}

	// 检查课程是否存在
	@RequestMapping(value = "/check_courseName", method = RequestMethod.GET)
	public @ResponseBody boolean checkCourseName(String courseName, int collegeId, Long id) {
		List<Course> courseList = courseService.courseNameExists(courseName);
		if (StringUtils.isEmpty(courseName)) {
			return false;
		}
		Course course = courseService.find(id);
		if (courseList != null) {
			for (int i = 0; i < courseList.size(); i++) {
				if (course != null) {
					if (courseList.get(i).getId().equals(course.getId())
							&& courseList.get(i).getCollegeId() == collegeId) {
						return true;
					}
					if (courseList.get(i).getCollege().getId() == collegeId) {
						return false;
					}
				}
				if (course == null && courseList.get(i).getCollege().getId() == collegeId) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	// 初始化数据
	@ModelAttribute
	public void init(Model model) {
		model.addAttribute("systemUser", systemUserService.getCurrentUser());
		List<School> schoolList = schoolService.findAll();
		model.addAttribute("schoolList", schoolList);
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.schoolManager.getValue()) {
			model.addAttribute("collegeList",
					schoolService.find((long) systemUserService.getCurrentUser().getSchoolId()).getSchoolCollegeList());
		}
	}
}
