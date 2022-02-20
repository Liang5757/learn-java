package cn.youyitech.anyview.system.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.youyitech.anyview.system.utils.SortUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.youyitech.anyview.system.entity.ClassEntity;
import cn.youyitech.anyview.system.entity.Course;
import cn.youyitech.anyview.system.entity.CourseArrange;
import cn.youyitech.anyview.system.entity.ExamPlan;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.entity.WorkingTable;
import cn.youyitech.anyview.system.service.CourseArrangeService;
import cn.youyitech.anyview.system.service.ExamPlanService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.service.WorkingTableService;
import cn.youyitech.anyview.system.support.EnumConstants;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.support.WorktableEnum;
import cn.youyitech.anyview.system.utils.IdsUtils;

import com.alibaba.fastjson.JSON;
import com.framework.loippi.support.Pageable;

/**
 * Controller - 应用版本
 * 
 * @author zzq
 * @version 1.0
 */

@Controller("adminExamPlanController")
@RequestMapping("/admin/examplan")
public class ExamPlanController extends GenericController {

	@Resource
	private SystemUserService systemUserService;

	@Resource
	private ExamPlanService examPlanService;

	@Resource
	private TeacherService teacherService;

	@Resource
	private CourseArrangeService courseArrangeService;

	@Resource
	private WorkingTableService workingTableService;

	@Resource
	private RedisService redisService;

	@Autowired
	private IdsUtils idsUtils;

	/**
	 * 检查考试名称是否存在
	 */
	@RequestMapping(value = "/check_ExamName", method = RequestMethod.GET)
	public @ResponseBody boolean checkExamName(int classId, int courseId, int workingTableId) {

		// 课程，班级，作业表三者不能一模一样
		ExamPlan examPlan = new ExamPlan();
		examPlan.setCId(classId);
		examPlan.setCourseId(courseId);
		examPlan.setVId(workingTableId);
		List<ExamPlan> examPlanList = examPlanService.findByAttribute(examPlan);
		if (examPlanList.size() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {

		return "/admin/examplan/add";
	}

	/**
	 * 添加保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ExamPlan examPlan, RedirectAttributes redirectAttributes) throws Exception {
		// 获取当前登录的教师信息
		Teacher temp = new Teacher();
		temp.setUsername(systemUserService.getCurrentUserName());
		temp.setSchoolId(systemUserService.getCurrentUser().getSchoolId());
		Teacher currentTeacher = teacherService.findByUserName(temp);

		examPlan.setTId(currentTeacher.getId());
		// 若考试为自动，则设置操作为开始
		if (examPlan.getKind() == EnumConstants.kindEnum.manual.getValue()) {
			examPlan.setOperation(EnumConstants.operationEnum.start.getValue());
		}
		// 默认考试状态为未开始
		examPlan.setExamStatus(EnumConstants.examStatusEnum.notStarted.getValue());
		examPlan.setDoneTime(0);
		examPlan.setCreated_person(systemUserService.getCurrentUser().getName());
		examPlan.setCreated_date(new Date());
		examPlan.setUpdate_person(systemUserService.getCurrentUser().getName());
		examPlan.setUpdate_date(new Date());
		examPlan.setEnabled(1);
		examPlanService.save(examPlan);

		return "redirect:list.jhtml";
	}

	/**
	 * 列表显示
	 */
	@RequiresPermissions("admin:system:examplan")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Pageable pageable, ModelMap model) {

		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("page", examPlanService.findByPage(pageable));
		model.addAttribute("params", map);

		return "/admin/examplan/list";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {

		for (long id : ids) {

			examPlanService.delete(id);

		}

		return SUCCESS_MESSAGE;
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		// 通过id获取相应的考试编排
		ExamPlan editExamPlan = examPlanService.find(id);
		model.addAttribute("examPlan", editExamPlan);
		// 通过班级id获取相应的课程
		model.addAttribute("courseList", getCourseFromClassId(String.valueOf(editExamPlan.getCId())));
		// 通过课程id获取相应作业表
		model.addAttribute("workingTableList", getWorkingTableFromCourseId(String.valueOf(editExamPlan.getCourseId())));

		return "/admin/examplan/edit";
	}

	/**
	 * 编辑保存
	 */
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSave(ExamPlan examPlan, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// 获取当前登录的教师信息
		Teacher temp = new Teacher();
		temp.setUsername(systemUserService.getCurrentUserName());
		temp.setSchoolId(systemUserService.getCurrentUser().getSchoolId());
		Teacher currentTeacher = teacherService.findByUserName(temp);
		if (examPlan.getKind() == EnumConstants.kindEnum.manual.getValue()) {
			examPlan.setStartTime(null);
		}
		examPlan.setTId(currentTeacher.getId());
		examPlan.setUpdate_person(systemUserService.getCurrentUser().getName());
		examPlan.setUpdate_date(new Date());
		examPlanService.update(examPlan);
		return "redirect:list.jhtml";
	}

	/**
	 * 更新状态
	 */
	@ResponseBody
	@RequestMapping(value = { "/update" }, method = { RequestMethod.POST })
	public String update(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// 考试编排id
		String examPlanId = request.getParameter("examPlanId");
		// 状态
		String examStatus = request.getParameter("examStatus");
		// 操作
		String operation = request.getParameter("operation");
		// 开始时间
		String startTime = request.getParameter("startTime");
		// 通过id获取相应的考试编排
		ExamPlan examPlan = examPlanService.find(Long.parseLong(examPlanId));
		examPlan.setExamStatus(Integer.parseInt(examStatus));
		// 如果之前的操作为重新开始，则持续时长重置为0
		if (examPlan.getOperation() == EnumConstants.operationEnum.restart.getValue()) {
			examPlan.setDoneTime(0);
		}
		examPlan.setOperation(Integer.parseInt(operation));
		if (startTime != null && !startTime.equals("")) {
			examPlan.setStartTime(new Date(Long.parseLong(startTime)));
		}
		examPlan.setUpdate_person(systemUserService.getCurrentUser().getName());
		examPlan.setUpdate_date(new Date());
		examPlanService.update(examPlan);

		return "";
	}

	/**
	 * 获取当前数据库考试编排信息
	 */
	@ResponseBody
	@RequestMapping(value = { "/getStatus" }, method = { RequestMethod.POST })
	public List<ExamPlan> getStatus(ModelMap model) {

		List<ExamPlan> examPlanList = examPlanService.findAll();
		SortUtil.sort(examPlanList,true,"epName");
		return examPlanList;
	}

	/**
	 * 初始化数据
	 */
	@ModelAttribute
	public void init(Model model) {
		// 获取当前登录的教师信息
		Teacher temp = new Teacher();
		temp.setUsername(systemUserService.getCurrentUserName());
		temp.setSchoolId(systemUserService.getCurrentUser().getSchoolId());
		Teacher currentTeacher = teacherService.findByUserName(temp);
		// 通过该教师获取相应的课程编排
		List<CourseArrange> courseArrangeList = courseArrangeService.findByTeacher(currentTeacher.getId());
		// 通过课程编排获取该老师教的全部的班级
		List<ClassEntity> classList = new ArrayList<>();
		// 去重标志位
		boolean flag = true;
		for (int i = 0; i < courseArrangeList.size(); i++) {
			CourseArrange courseArrange = courseArrangeList.get(i);
			for (int j = 0; j < classList.size(); j++) {
				if (classList.get(j).getClassName().equals(courseArrange.getClassSystem().getClassName())) {
					flag = false;
				}
			}
			if (flag) {
				classList.add(courseArrange.getClassSystem());
			} else {
				flag = true;
			}
		}

		model.addAttribute("classList", classList);

	}

	/**
	 * 通过获取到的班级id得到相应的课程
	 */
	@ResponseBody
	@RequestMapping(value = { "/CourseAjax" }, method = { RequestMethod.POST })
	public List<Course> courseAjax(String classId) {
		return getCourseFromClassId(classId);
	}

	/**
	 * 通过获取到的班级id得到相应的课程
	 */
	public List<Course> getCourseFromClassId(String classId) {
		// 通过班级id获取相应的课程编排
		List<CourseArrange> courseArrangeList = courseArrangeService.findList("class_id", classId);
		// 通过课程编排获取相应的课程
		List<Course> courseList = new ArrayList<>();
		// 去重标志位
		boolean flag = true;
		for (int i = 0; i < courseArrangeList.size(); i++) {
			CourseArrange courseArrange = courseArrangeList.get(i);
			for (int j = 0; j < courseList.size(); j++) {
				if (courseList.get(j).getCourseName().equals(courseArrange.getCourse().getCourseName())) {
					flag = false;
				}
			}
			if (flag) {
				courseList.add(courseArrange.getCourse());
			} else {
				flag = true;
			}
		}
		return courseList;
	}

	/**
	 * 通过获取到的课程id得到相应的作业表
	 */
	@ResponseBody
	@RequestMapping("/WorkingTableAjax")
	public List<WorkingTable> workingTableAjax(String courseId) {
		return getWorkingTableFromCourseId(courseId);
	}

	/**
	 * 通过获取到的课程id得到相应的作业表
	 */
	public List<WorkingTable> getWorkingTableFromCourseId(String courseId) {
		// 通过课程id获取相应的作业表
		List<WorkingTable> temp_workingTableList = workingTableService.findList("courseId", courseId);
		List<WorkingTable> workingTableList = new ArrayList<>();
		for (int i = 0; i < temp_workingTableList.size(); i++) {
			WorkingTable workingTable = temp_workingTableList.get(i);
			// 考试编排中所选择的作业表只能是考试类型的
			if (workingTable.getTableType() == WorktableEnum.typeEnum.exam.getValue()) {
				// 当前登录的老师自己创建的作业表
				if (workingTable.getTableCreater().equals(systemUserService.getCurrentUser().getName())) {
					workingTableList.add(workingTable);
				}
				// 某个创建者本校公开的作业表
				else if (workingTable.getTableLevel() == WorktableEnum.levelEnum.schoolOpen.getValue()
						&& workingTable.getCourse().getCollege().getSchool().getId() == systemUserService
								.getCurrentUser().getSchoolId()) {
					workingTableList.add(workingTable);
				}
				// 某个作业表的创建者完全公开
				else if (workingTable.getTableLevel() == WorktableEnum.levelEnum.openAll.getValue()) {
					workingTableList.add(workingTable);
				}
			}
		}
		return workingTableList;
	}

}
