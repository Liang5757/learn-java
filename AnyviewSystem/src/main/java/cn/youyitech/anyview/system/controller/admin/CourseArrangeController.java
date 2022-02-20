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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.framework.loippi.support.Pageable;

import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.ClassEntity;
import cn.youyitech.anyview.system.entity.College;
import cn.youyitech.anyview.system.entity.Course;
import cn.youyitech.anyview.system.entity.CourseArrange;
import cn.youyitech.anyview.system.entity.CourseArrangeAndWorkingTable;
import cn.youyitech.anyview.system.entity.Major;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.entity.WorkingTable;
import cn.youyitech.anyview.system.service.ClassService;
import cn.youyitech.anyview.system.service.CollegeService;
import cn.youyitech.anyview.system.service.CourseArrangeAndWorkingTableService;
import cn.youyitech.anyview.system.service.CourseArrangeService;
import cn.youyitech.anyview.system.service.CourseService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.service.WorkingTableService;
import cn.youyitech.anyview.system.support.EnumConstants;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.support.WorktableEnum;
import cn.youyitech.anyview.system.utils.IdsUtils;

/**
 * Controller - 应用版本
 * 
 * @author zzq
 * @version 1.0
 */

@Controller("adminCourseArrangeController")
@RequestMapping("/admin/course_arrange")
public class CourseArrangeController extends GenericController {

	@Resource
	private CourseArrangeService courseArrangeService;

	@Resource
	private SystemUserService systemUserService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private CollegeService collegeService;

	@Autowired
	private ClassService classService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private WorkingTableService workingTableService;

	@Autowired
	private CourseArrangeAndWorkingTableService courseArrangeAndWorkingTableService;

	@Resource
	private RedisService redisService;

	@Autowired
	private IdsUtils idsUtils;

	// 检查班级是否存在
	@RequestMapping(value = "/checkAll", method = RequestMethod.GET)
	public @ResponseBody boolean checkAll(String id, String class_id, int course_id, int teacher_id) {

		// 获取修改验证时的课程编排
		CourseArrange courseArrange = null;
		if (id != null && !id.equals("")) {
			courseArrange = courseArrangeService.find(Long.parseLong(id));
		}
		if (class_id.contains(",")) {
			String[] classId = class_id.split(",");
			boolean[] bArray = new boolean[classId.length];
			for (int j = 0; j < classId.length; j++) {
				// 修改状态下的验证
				if (courseArrange != null) {
					if ((courseArrange.getClass_id() == Long.parseLong(classId[j]))
							&& courseArrange.getTeacher_id() == teacher_id) {
						bArray[j] = true;
					}else{
						CourseArrange temp = new CourseArrange();
						temp.setClass_id(Integer.parseInt(classId[j]));
						temp.setCourse_id(course_id);
						temp.setTeacher_id(teacher_id);
						List<CourseArrange> courseArrangeList = courseArrangeService.findByAttribute(temp);
						if (courseArrangeList.size() > 0) {
							bArray[j] = false;
						}else{
							bArray[j] = true;
						}
					}
				}else{
					//添加
					CourseArrange temp = new CourseArrange();
					temp.setClass_id(Integer.parseInt(classId[j]));
					temp.setCourse_id(course_id);
					temp.setTeacher_id(teacher_id);
					List<CourseArrange> courseArrangeList = courseArrangeService.findByAttribute(temp);
					if (courseArrangeList.size() > 0) {
						bArray[j] = false;
					}else{
						bArray[j] = true;
					}
				}
			}
			for(int k=0;k<bArray.length;k++){
				if(!bArray[k]){
					return false;
				}
			}
			return true;
		} else {
			// 修改状态下的验证
			if (courseArrange != null) {
				if ((courseArrange.getClass_id() == Long.parseLong(class_id))
						&& courseArrange.getTeacher_id() == teacher_id) {
					return true;
				}
			}
			CourseArrange temp = new CourseArrange();
			temp.setClass_id(Integer.parseInt(class_id));
			temp.setCourse_id(course_id);
			temp.setTeacher_id(teacher_id);
			List<CourseArrange> courseArrangeList = courseArrangeService.findByAttribute(temp);
			if (courseArrangeList.size() > 0) {
				return false;
			}
		}

		return true;

	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		// 删除记载在redis中的班级对话框信息
		redisService.delete(systemUserService.getCurrentUserName() + "class");

		return "/admin/course_arrange/add";
	}

	/**
	 * 添加保存
	 */
	@Transactional
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// 班级id
		String className = request.getParameter("class_name");
		// 课程id
		String courseName = request.getParameter("courseName");
		// 教师id
		String teacherName = request.getParameter("teacherName");
		// 作业表id
		String workingTableId = request.getParameter("working_table");
		// 当前登录的用户
		User user = systemUserService.getCurrentUser();
		// 多个班级
		if (className.contains(",")) {
			String[] classArray = className.split(",");
			for (int i = 0; i < classArray.length; i++) {
				// 添加课程编排
				CourseArrange courseArrange = getCourseArrange("", classArray[i], courseName, teacherName);
				courseArrange.setCreated_person(user.getName());
				courseArrange.setCreated_date(new Date());
				courseArrange.setEnabled(1);

				courseArrangeService.save(courseArrange);

				// 为课程编排添加作业表
				long courseArrangeId = courseArrangeService.findTotal().get(courseArrangeService.findTotal().size() - 1)
						.getId();
				//addcAndw(courseArrange.getClass_id(),courseArrangeId, workingTableId);
				addcAndw(courseArrange.getClass_id(),courseArrangeId, workingTableId, courseName, teacherName);

			}
		} else {
			// 一个班级
			CourseArrange courseArrange = getCourseArrange("", className, courseName, teacherName);
			courseArrange.setCreated_person(user.getName());
			courseArrange.setCreated_date(new Date());
			courseArrange.setEnabled(1);
			courseArrangeService.save(courseArrange);

			// 为课程编排添加作业表
			long courseArrangeId = courseArrangeService.findTotal().get(courseArrangeService.findTotal().size() - 1)
					.getId();
			//addcAndw(courseArrange.getClass_id(),courseArrangeId, workingTableId);
			addcAndw(courseArrange.getClass_id(),courseArrangeId, workingTableId, courseName, teacherName);

		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {

		List<String> recordCourseArrange = new ArrayList<>();
		for (long id : ids) {
			recordCourseArrange.add(String.valueOf(id));
		}
		if (recordCourseArrange.size() != 0) {
			try {
				courseArrangeService.deleteOne(recordCourseArrange);
			} catch (Exception e) {
				return ERROR_MESSAGE;
			}
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}
	}

	/**
	 * 列表显示
	 */
	@RequiresPermissions("admin:system:coursearrange")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Pageable pageable, ModelMap model) {
		// 删除课程编排在redis中的所有记录
		redisService.delete(systemUserService.getCurrentUserName() + "edit_course_arrange");
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("params", map);
		// 当前登录用户的权限为管理员，只能看到该管理学所属学校中的课程编排
		model.addAttribute("page", this.courseArrangeService.findByPage(pageable));

		return "/admin/course_arrange/list";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		// 清除redis中课程编排的信息
		redisService.delete(systemUserService.getCurrentUserName() + "class");
		// 通过id获取相应的课程编排
		CourseArrange edit_course_arrange = courseArrangeService.find(id);
		redisService.save(systemUserService.getCurrentUserName() + "edit_course_arrange",
				JSON.toJSONString(edit_course_arrange));

		// 通过学校id获取相应的学院集合
		long schoolId = edit_course_arrange.getClassSystem().getMajor().getCollege().getSchool().getId();
		School school = schoolService.find(schoolId);
		List<College> collegeList = school.getSchoolCollegeList();
		SortUtil.sort(collegeList,true,"collegeName");
		// 通过学院id获取相应的专业集合
		long collegeId = edit_course_arrange.getClassSystem().getMajor().getCollege().getId();
		College college = collegeService.find(collegeId);
		List<Major> majorList = college.getCollegeMajorList();
		SortUtil.sort(majorList,true,"majorName");
		// 通过学院id获取相应的课程集合
		List<Course> courseList = courseService.findByInMany((int) collegeId);

		// 通过学院id获取相应的老师
		List<Teacher> teacherList = teacherService.findList("college_id", collegeId);
		SortUtil.sort(teacherList,true,"name");
		model.addAttribute("course_arrange", edit_course_arrange);
		model.addAttribute("collegeList", collegeList);
		model.addAttribute("majorList", majorList);
		model.addAttribute("courseList", courseList);
		model.addAttribute("teacherList", teacherList);

		return "/admin/course_arrange/edit";
	}

	/**
	 * 编辑保存
	 */
	@Transactional
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSave(HttpServletRequest request, RedirectAttributes redirectAttributes) {

		String id = request.getParameter("id");
		// 班级id
		String className = request.getParameter("class_name");
		// 课程id
		String courseName = request.getParameter("courseName");
		// 教师id
		String teacherName = request.getParameter("teacherName");
		// 作业表id
		String workingTableId = request.getParameter("working_table");
		// 当前登录用户
		User user = systemUserService.getCurrentUser();

		if (className.contains(",")) {
			// 若更新中添加了多个班级
			String[] classArray = className.split(",");
			// 则第一个班级进行修改
			CourseArrange courseArrange = getCourseArrange(id, classArray[0], courseName, teacherName);
			courseArrangeService.update(courseArrange);

			// 为课程编排修改作业表
			// 第一步：把之前的作业表先全部删除
			courseArrangeAndWorkingTableService.deleteByCourseArrangeId(courseArrange.getId());
			// 第二步：添加新勾选的作业表
			//addcAndw(courseArrange.getClass_id(),courseArrange.getId(), workingTableId);
			addcAndw(courseArrange.getClass_id(),courseArrange.getId(), workingTableId, courseName, teacherName);

			for (int i = 1; i < classArray.length; i++) {
				// 第一个后的班级进行添加
				CourseArrange courseA = getCourseArrange("", classArray[i], courseName, teacherName);
				courseA.setCreated_person(user.getName());
				courseA.setCreated_date(new Date());
				courseA.setEnabled(1);
				courseArrangeService.save(courseA);

				// 为课程编排添加作业表
				//addcAndw(courseA.getClass_id(),courseArrangeService.findTotal().get(courseArrangeService.findTotal().size() - 1).getId(),
				//		workingTableId);
				addcAndw(courseA.getClass_id(),courseArrangeService.findTotal().get(courseArrangeService.findTotal().size() - 1).getId(),
						workingTableId,courseName, teacherName);

			}
		} else {
			// 只有一个班级则直接更新
			CourseArrange courseArrange = getCourseArrange(id, className, courseName, teacherName);
			courseArrangeService.update(courseArrange);

			// 为课程编排修改作业表
			// 第一步：把之前的作业表先全部删除
			courseArrangeAndWorkingTableService.deleteByCourseArrangeId(courseArrange.getId());
			// 第二步：添加新勾选的作业表
			//addcAndw(courseArrange.getClass_id(),courseArrange.getId(), workingTableId);
			addcAndw(courseArrange.getClass_id(),courseArrange.getId(), workingTableId, courseName,teacherName);

		}

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		return "redirect:list.jhtml";
	}

	/**
	 * 初始化数据
	 */
	@ModelAttribute
	public void init(Model model) {

		// 获取数据库中的全部学校
		List<School> schoolList = schoolService.findAll();

		model.addAttribute("schoolList", schoolList);
		model.addAttribute("systemUser", systemUserService.getCurrentUser());
		// 当前登录用户权限为管理员，通过该管理员所属的学院获取全部的学院
		if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()) {
			List<College>collegeList=schoolService.find((long) systemUserService.getCurrentUser().getSchoolId()).getSchoolCollegeList();
			SortUtil.sort(collegeList,true,"collegeName");
			model.addAttribute("collegeList",collegeList);
		}
	}

	/**
	 * 通过获取到的专业id得到相应的班级
	 */
	@ResponseBody
	@RequestMapping("/ClassAjax")
	public List<ClassEntity> classAjax(@RequestBody Major major) {

		// 清楚redis中记载的课程编排班级信息
		redisService.delete(systemUserService.getCurrentUserName() + "class");
		// 通过获取到的专业id得到相应的班级
		List<ClassEntity> classList = classService.findList("major_id", major.getId());
		SortUtil.sort(classList,true,"className");
		// 把班级的集合记载到redis中
		redisService.save(systemUserService.getCurrentUserName() + "class", JSON.toJSONString(classList));

		return classList;
	}

	/**
	 * 班级对话框列表
	 */
	@RequestMapping(value = { "/listDialogClass" }, method = { RequestMethod.GET })
	public String listDialogClass(HttpServletRequest request, Pageable pageable, ModelMap model) {
		// 从redis中获取班级信息
		List<ClassEntity> classList = null;
		if (redisService.get(systemUserService.getCurrentUserName() + "class") != null) {
			classList = idsUtils.classjsonToList(redisService.get(systemUserService.getCurrentUserName() + "class"),
					ClassEntity.class);
			SortUtil.sort(classList,true,"className");
		} else {
			classList = new ArrayList<>();
		}
		// 编辑状态下的班级对话框
		if (redisService.get(systemUserService.getCurrentUserName() + "edit_course_arrange") != null
				&& classList.size() == 0) {
			// 获取到编辑的课程编排
			CourseArrange edit_course_arrange = JSON.parseObject(
					redisService.get(systemUserService.getCurrentUserName() + "edit_course_arrange"),
					CourseArrange.class);
			long majorId = edit_course_arrange.getClassSystem().getMajor().getId();
			List<ClassEntity> newclassList = classService.findList("major_id", majorId);
			classList = newclassList;
			redisService.save(systemUserService.getCurrentUserName() + "class", JSON.toJSONString(newclassList));
			model.addAttribute("page", classService.pageMethod(pageable, newclassList));

		} else {
			redisService.save(systemUserService.getCurrentUserName() + "class", JSON.toJSONString(classList));
			model.addAttribute("page", classService.pageMethod(pageable, classList));
		}
		return "/admin/course_arrange/listDialogClass";
	}

	/**
	 * 通过获取到的学院id得到相应的课程
	 */
	@ResponseBody
	@RequestMapping("/CourseAjax")
	public List<Course> courseAjax(String collegeId) {

		List<Course> courseList = courseService.findByInMany(Integer.parseInt(collegeId));

		return courseList;
	}

	/**
	 * 通过获取到的学院id得到相应的教师
	 */
	@ResponseBody
	@RequestMapping("/TeacherAjax")
	public List<Teacher> teacherAjax(String collegeId) {

		List<Teacher> teacherList = teacherService.findList("college_id", Long.parseLong(collegeId));
		SortUtil.sort(teacherList,true,"name");
		return teacherList;
	}

	/**
	 * 作业表对话框列表
	 */
	@RequestMapping(value = { "/listDialogWorkingTable" }, method = { RequestMethod.GET })
	public String listDialogWorkingTable(String courseId, HttpServletRequest request, Pageable pageable,
			ModelMap model) {
		// 通过课程id获取相应的作业表
		List<WorkingTable> workingTableList = new ArrayList<>();
		if (courseId != null && !courseId.equals("")) {
			List<WorkingTable> temp_workingTableList = workingTableService.findList("courseId", courseId);
			for (int i = 0; i < temp_workingTableList.size(); i++) {
				WorkingTable workingTable = temp_workingTableList.get(i);
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

			redisService.save(systemUserService.getCurrentUserName() + "CourseArrange" + "workingTableList",
					JSON.toJSONString(workingTableList));
		}
		model.addAttribute("page", workingTableService.pageMethod(pageable, workingTableList));
		return "/admin/course_arrange/listDialogWorkingTable";
	}

	/**
	 * 添加课程编排与作业表的关系
	 * 增加教师id 班级id 课程id的保存 2019.03.27 cjs
	 */
	private void addcAndw(int classId,long courseArrangeId, String workingTableId, String courseId, String teacherId) {

		if (workingTableId.contains(",")) {
			// 多个作业表id
			String[] wtIdArray = workingTableId.split(",");
			for (int k = 0; k < wtIdArray.length; k++) {
				CourseArrangeAndWorkingTable cAndw = new CourseArrangeAndWorkingTable();
				cAndw.setCourseArrangeId((int) courseArrangeId);
				cAndw.setClassId(classId);
				System.out.println("班级："+cAndw.getClassId());
				cAndw.setWorkingTableId(Integer.parseInt(wtIdArray[k]));
				cAndw.setCourseId(Integer.parseInt(courseId));
				cAndw.setTeacherId(Integer.parseInt(teacherId));
				cAndw.setEnabled(1);

				//courseArrangeAndWorkingTableService.save(cAndw);
				courseArrangeAndWorkingTableService.saveOne(cAndw);
			}
		} else if (workingTableId != null && !workingTableId.equals("")) {
			// 一个作业表id
			CourseArrangeAndWorkingTable cAndw = new CourseArrangeAndWorkingTable();
			cAndw.setCourseArrangeId((int) courseArrangeId);
			cAndw.setWorkingTableId(Integer.parseInt(workingTableId));
			cAndw.setClassId(classId);
			cAndw.setEnabled(1);
			System.out.println("班级："+cAndw.getClassId());
			cAndw.setCourseId(Integer.parseInt(courseId));
			cAndw.setTeacherId(Integer.parseInt(teacherId));
			//courseArrangeAndWorkingTableService.save(cAndw);
			courseArrangeAndWorkingTableService.saveOne(cAndw);
		}

	}

	/**
	 * 添加或修改获取课程编排信息
	 */
	public CourseArrange getCourseArrange(String id, String className, String courseName, String teacherName) {

		CourseArrange courseArrange = new CourseArrange();
		if (id != null && !id.equals("")) {
			courseArrange.setId(Long.parseLong(id));
		}
		courseArrange.setClass_id(Integer.parseInt(className));
		courseArrange.setCourse_id(Integer.parseInt(courseName));
		courseArrange.setTeacher_id(Integer.parseInt(teacherName));
		courseArrange.setUpdate_person(systemUserService.getCurrentUser().getName());
		courseArrange.setUpdate_date(new Date());
		return courseArrange;
	}

}
