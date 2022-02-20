package cn.youyitech.anyview.system.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.youyitech.anyview.system.utils.SortUtil;
import org.apache.commons.lang.StringUtils;
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

import com.framework.loippi.support.Pageable;

import cn.youyitech.anyview.system.entity.College;
import cn.youyitech.anyview.system.entity.Course;
import cn.youyitech.anyview.system.entity.CourseArrange;
import cn.youyitech.anyview.system.entity.CourseArrangeAndWorkingTable;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.entity.WorkingTable;
import cn.youyitech.anyview.system.service.CollegeService;
import cn.youyitech.anyview.system.service.CourseArrangeService;
import cn.youyitech.anyview.system.service.CourseService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.service.WorkingTableService;
import cn.youyitech.anyview.system.support.AdminEnum;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.support.WorktableEnum;
import cn.youyitech.anyview.system.utils.DuplicateRemovalUtil;

/**
 * @author TT 作业表 Controller 2017年10月19日
 */
@Controller
@RequestMapping("/admin/workingtable")
public class WorkingTableController extends GenericController {

	@Autowired
	private WorkingTableService workingTableService;

	@Autowired
	private CollegeService collegeService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private CourseService courseService;

	@Autowired
	SystemUserService systemUserService;

	@Autowired
	private RedisService redisService;

	@Resource
	private CourseArrangeService courseArrangeService;

	@Autowired
	DuplicateRemovalUtil duplicateRemovalUtil;

	@Autowired
	private TeacherService teacherService;

	// 作业表列表内容
	@RequestMapping("/WorkingContent")
	public String workingContent(Model model, Pageable pageable, HttpServletRequest request) {
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("params", map);
		WorkingTable workingTable = new WorkingTable();
		List<WorkingTable> workList = new ArrayList<>();
		// 查询条件
		workingTable.setTableName(request.getParameter("filter_tableName"));
		workingTable.setCourseName(request.getParameter("filter_courseName"));
		workingTable.setCollegeName(request.getParameter("filter_collegeName"));
		workingTable.setSchoolName(request.getParameter("filter_schoolName"));
		// 按更新者排序
		String updater_flag = request.getParameter("updater_flag");
		if ("asc".equals(updater_flag) || "desc".equals(updater_flag)) {
			workingTable.setUpdaterSort(updater_flag);
		}
		// 校级管理员
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.schoolManager.getValue()) {
			List<College> collegeList = schoolService.find((long) systemUserService.getCurrentUser().getSchoolId())
					.getSchoolCollegeList();
			List<Course> courseListNew = new ArrayList<>();
			for (int i = 0; i < collegeList.size(); i++) {
				List<Course> courseList = collegeService.find(collegeList.get(i).getId()).getCollegeCourseList();
				for (int j = 0; j < courseList.size(); j++) {
					courseListNew.add(courseList.get(j));
				}
			}
			List<WorkingTable> workListNew = workingTableService.findContentList(workingTable);
			for (int k = 0; k < workListNew.size(); k++) {
				// 对于完全公开级别，去除某学校不存在的课程的作业表
				boolean reg = false;
				for (int i = 0; i < courseListNew.size(); i++) {
					if (courseListNew.get(i).getCourseName().equals(workListNew.get(k).getCourse().getCourseName())) {
						reg = true;
					}
				}
				if (!reg) {
					continue;
				}
				// 停用
				if (workListNew.get(k).getTableStatus() == WorktableEnum.statusEnum.stop.getValue()
						&& !workListNew.get(k).getTableCreater().equals(systemUserService.getCurrentUser().getName())) {
					continue;
				}
				// 完全保密
				if (workListNew.get(k).getTableLevel() == WorktableEnum.levelEnum.secrecy.getValue()
						&& !workListNew.get(k).getTableCreater().equals(systemUserService.getCurrentUser().getName())) {
					continue;
				}
				// 本校公开
				if (workListNew.get(k).getTableLevel() == WorktableEnum.levelEnum.schoolOpen.getValue()) {
					if (workListNew.get(k).getCourse().getCollege().getSchoolId() != systemUserService.getCurrentUser()
							.getSchoolId())
						continue;
				}
				workList.add(workListNew.get(k));
			}
		} else if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.teacher.getValue()) {
			// 教师端
			Teacher temp = new Teacher();
			temp.setUsername(systemUserService.getCurrentUserName());
			temp.setSchoolId(systemUserService.getCurrentUser().getSchoolId());
			Teacher teacher = teacherService.findByAttribute(temp).get(0);
			List<CourseArrange> courseArrangeList = courseArrangeService.findList("teacher_id", teacher.getId());
			List<Long> tableIdList = new ArrayList<>();
			for (int i = 0; i < courseArrangeList.size(); i++) {
				List<CourseArrangeAndWorkingTable> courseArrangeAndWorkingTableList = courseArrangeList.get(i)
						.getCourseArrangeAndWorkingTable();
				for (int j = 0; j < courseArrangeAndWorkingTableList.size(); j++) {
					tableIdList.add((long) courseArrangeAndWorkingTableList.get(j).getWorkingTableId());
				}
			}
			List<WorkingTable> workingTableByCreaterList = workingTableService.findList("tableCreater",
					systemUserService.getCurrentUser().getName());
			for (int i = 0; i < workingTableByCreaterList.size(); i++) {
				tableIdList.add(workingTableByCreaterList.get(i).getId());
			}
			// 去掉重复的id
			tableIdList = duplicateRemovalUtil.notContains(tableIdList);
			if (tableIdList.size() > 1) {
				tableIdList = duplicateRemovalUtil.notContains(tableIdList);
			}
			if (tableIdList.size() > 0) {
				workingTable.setTableIdList(tableIdList);
				workList = workingTableService.findContentList(workingTable);
			}

		} else {
			// 超级管理员
			List<WorkingTable> workListNew = workingTableService.findContentList(workingTable);
			for (int k = 0; k < workListNew.size(); k++) {
				// 完全保密
				if (workListNew.get(k).getTableLevel() == WorktableEnum.levelEnum.secrecy.getValue()
						&& !workListNew.get(k).getTableCreater().equals(systemUserService.getCurrentUser().getName())) {
					continue;
				}
				// 停用
				if (workListNew.get(k).getTableStatus() == WorktableEnum.statusEnum.stop.getValue()
						&& !workListNew.get(k).getTableCreater().equals(systemUserService.getCurrentUser().getName())) {
					continue;
				}
				workList.add(workListNew.get(k));
			}

		}
		model.addAttribute("updater_flag", updater_flag);
		model.addAttribute("page", workingTableService.pageMethod(pageable, workList));
		return "admin/workingtable/workingTableList";
	}

	// 添加作业表
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addWorkTable(Model model) {
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.teacher.getValue()) {
			Teacher teacher = teacherService.find("username", systemUserService.getCurrentUserName());
			College college = collegeService.find("id", teacher.getCollege().getId());
			model.addAttribute("tteacher", teacher);
			model.addAttribute("tcollege", college);
		}
		return "admin/workingtable/addWorkingTable";

	}

	// 保存添加
	@RequestMapping("/saveAddTable")
	public String inserWorkTable(WorkingTable workingTable, RedirectAttributes redirectAttributes) {
		workingTableService.save(workingTable);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/workingtable/WorkingContent.jhtml";
	}

	// 修改作业表
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		List<String> statusList = new ArrayList<>();
		statusList.add(WorktableEnum.statusEnum.stop.getText());
		statusList.add(WorktableEnum.statusEnum.start.getText());
		List<String> typeList = new ArrayList<>();
		typeList.add(WorktableEnum.typeEnum.working.getText());
		typeList.add(WorktableEnum.typeEnum.exam.getText());
		List<String> levelList = new ArrayList<>();
		levelList.add(WorktableEnum.levelEnum.secrecy.getText());
		levelList.add(WorktableEnum.levelEnum.schoolOpen.getText());
		levelList.add(WorktableEnum.levelEnum.openAll.getText());
		WorkingTable workingTable = workingTableService.find(id);
		College college = new College();
		college.setSchoolId(workingTable.getCourse().getCollege().getSchool().getId());
		college.setId(workingTable.getCourse().getCollege().getId());
		// 找出该作业所在的学校的所有学院（去除本身所在的学院）
		List<College> collegeList = collegeService.findNotIdList(college);
		model.addAttribute("colleges", collegeList);
		Course course = new Course();
		course.setCollegeId(workingTable.getCourse().getCollege().getId());
		course.setId(workingTable.getCourse().getId());
		// 找出该作业所在的学院的所有课程（去除本身所在的课程）
		List<Course> courseList = courseService.findNotIdList(course);
		model.addAttribute("courses", courseList);
		switch (workingTable.getTableStatus()) {
		case 0:
			statusList.remove(0);
			break;
		case 1:
			statusList.remove(1);
			break;
		default:
			break;
		}
		switch (workingTable.getTableType()) {
		case 0:
			typeList.remove(0);
			break;
		case 1:
			typeList.remove(1);
			break;
		default:
			break;
		}
		switch (workingTable.getTableLevel()) {
		case 0:
			levelList.remove(0);
			break;
		case 1:
			levelList.remove(1);
			break;
		case 2:
			levelList.remove(2);
			break;
		default:
			break;
		}
		List<School> schoolList = schoolService.findNotIdList("id",
				workingTable.getCourse().getCollege().getSchool().getId());
		model.addAttribute("schoolList", schoolList);
		model.addAttribute("workingTable", workingTable);
		model.addAttribute("typeList", typeList);
		model.addAttribute("statusList", statusList);
		model.addAttribute("levelList", levelList);
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.teacher.getValue()) {
			Teacher teacher = teacherService.find("username", systemUserService.getCurrentUserName());
			model.addAttribute("tteacher", teacher);
		}
		return "admin/workingtable/updateWorkingTable";
	}

	// 保存修改
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSave(WorkingTable workingTable, RedirectAttributes redirectAttributes) {
		// 作业题的时长为0
		if (workingTable.getTableType() == WorktableEnum.typeEnum.working.getValue()) {
			workingTable.setTotalTime(WorktableEnum.typeEnum.working.getValue());
		}
		workingTable.setTableUpdateTime(new Date());
		workingTableService.update(workingTable);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/workingtable/WorkingContent.jhtml";
	}

	// 选中删除
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {
		if (ids.length != 0) {
			try {
				workingTableService.deleteOne(ids);
			} catch (Exception e) {
				// TODO: handle exception
				return Message.error("异常:" + e);
			}
			redisService.delete(systemUserService.getCurrentUserName() + "ttrecordWork");
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}
	}

	// 获取学院下的课程
	@ResponseBody
	@RequestMapping("/CourseAjax")
	public List<Course> testRequestBody(@RequestBody College college, HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		List<Course> courseList = collegeService.find((long) college.getId()).getCollegeCourseList();
		SortUtil.sort(courseList,true,"courseName");
		return courseList;
	}

	// 校验
	@RequestMapping(value = "/check_tableName", method = RequestMethod.GET)
	public @ResponseBody boolean checkTablename(String tableName, int courseId, Long id) {
		List<WorkingTable> tableList = workingTableService.tableNameExists(tableName);
		WorkingTable workingTable = workingTableService.find(id);
		if (StringUtils.isEmpty(tableName)) {
			return false;
		}
		if (tableList != null) {
			for (int i = 0; i < tableList.size(); i++) {
				if (workingTable != null && tableList.get(i).getId().equals(workingTable.getId())) {
					return true;
				}
				if (tableList.get(i).getCourse().getId() == courseId) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}

	// 初始化数据
	@ModelAttribute
	public void init(Model model) {
		// 获取学校表
		List<School> schoolList = schoolService.findAll();
		model.addAttribute("schoolList", schoolList);
		// 获取学院
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.schoolManager.getValue()) {
			List<College>collegeList=schoolService.find((long) systemUserService.getCurrentUser().getSchoolId()).getSchoolCollegeList();
			SortUtil.sort(collegeList,true,"collegeName");
			model.addAttribute("collegeList", collegeList);
		}
		model.addAttribute("systemUser", systemUserService.getCurrentUser());
	}

}
