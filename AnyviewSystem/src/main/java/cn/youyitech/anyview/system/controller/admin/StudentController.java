package cn.youyitech.anyview.system.controller.admin;

import cn.youyitech.anyview.system.dto.StudentAndUser;
import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.*;
import cn.youyitech.anyview.system.service.*;
import cn.youyitech.anyview.system.support.AdminEnum;
import cn.youyitech.anyview.system.support.EnumConstants;
import cn.youyitech.anyview.system.support.FileInfo;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.IdsUtils;
import cn.youyitech.anyview.system.utils.PoiReadExcel;
import cn.youyitech.anyview.system.utils.SortUtil;
import com.alibaba.fastjson.JSON;
import com.framework.loippi.support.Page;
import com.framework.loippi.support.Pageable;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller - StudentController
 * 
 * @author zzq
 * @version 1.0
 */

@Controller("adminStudentController")
@RequestMapping("/admin/student")
public class StudentController extends GenericController {

	@Resource
	private StudentService studentService;

	@Resource
	private SystemUserService systemUserService;

	@Resource
	private TeacherService teacherService;

	@Resource
	private ClassAndStudentService classAndStudentService;
	@Resource
	private ExerciseService exerciseService;

	@Value("${system.init_password}")
	private String init_password;

	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private CollegeService collegeService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private ClassService classService;

	@Resource
	private RedisService redisService;

	@Autowired
	PoiReadExcel read;

	@Autowired
	private IdsUtils idsUtils;
    private String className;
	private String filter_user_name;
	private String filter_name;
	/**
	 * 检查学生用户名是否存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody boolean checkUsername(String studentId, String username, int schoolId) {
		// 判断用户名是否为空
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		// 若是编辑学生状态，用户名不需要与自己用户名进行比较，可以直接确定
		if (studentId != null && !studentId.equals("")) {
			Student currentStudent = studentService.find("id", studentId);
			String username_string = username;
			if (currentStudent != null) {
				if (currentStudent.getUsername().equals(username_string)) {
					return true;
				}
			}
		}

		// 用户名本校唯一，与学生表的用户名进行验证
		Student student = new Student();
		student.setUsername(username);
		student.setSchoolId(schoolId);
		List<Student> studentList = studentService.findByAttribute(student);
		if (studentList.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		// 把之前对话框记载的学院，专业，班级全部删除
		redisService.delete(systemUserService.getCurrentUserName() + "collegeList");
		redisService.delete(systemUserService.getCurrentUserName() + "majorList");
		redisService.delete(systemUserService.getCurrentUserName() + "classList");

		if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.teacher.getValue()) {
			ClassEntity classSystem = classService.find("id",
					redisService.get(systemUserService.getCurrentUserName() + "ttclassName"));
			model.addAttribute("ttclassSystem", classSystem);
		}

		return "/admin/student/add";
	}

	/**
	 * 添加保存
	 */
	@Transactional
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {

		String schoolId = request.getParameter("schoolId");
		// 用户名
		String username = request.getParameter("number");
		// 姓名
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String state = request.getParameter("state");
		String classString = request.getParameter("className");
		String email = request.getParameter("email");

		// 当前登录的用户
		User currentUser = systemUserService.getCurrentUser();

		// 添加学生信息
		Student student = getStudent("", sex, state, username, name, schoolId, email);
		student.setEnabled(1);
		// 若该学生没有邮箱，则密码为123456，有邮箱则随机生成6位数的密码
		if (request.getParameter("email") != null && !request.getParameter("email").equals("")) {
			student.setEmail(email);
		}
		student.setRoleId(EnumConstants.authorityEnum.student.getValue());
		student.setIsLocked(false);
		student.setCreatedDate(new Date());
		student.setCreatedBy(currentUser.getName());
		studentService.saveOne(student);

		Student temp = new Student();
		temp.setRoleId(EnumConstants.authorityEnum.student.getValue());
		temp.setSchoolId(Integer.parseInt(schoolId));
		temp.setUsername(username);
		student = studentService.findByAttribute(temp).get(0);

		// 学生与班级关联表
		// 该学生有多个班级或者一个班级所以需要进行判断
		if (classString.contains(",")) {
			String[] classArray = classString.split(",");
			// 通过获取班级的数组进行循环入库
			for (int i = 0; i < classArray.length; i++) {
				ClassAndStudent classAndStudent = new ClassAndStudent();
				classAndStudent.setStudent_id(student.getId());
				classAndStudent.setStudent_class_id(Integer.parseInt(classArray[i]));
				classAndStudent.setEnabled(1);
				classAndStudentService.save(classAndStudent);
			}
		} else {
			ClassAndStudent classAndStudent = new ClassAndStudent();
			classAndStudent.setStudent_id(student.getId());
			classAndStudent.setStudent_class_id(Integer.parseInt(classString));
			classAndStudent.setEnabled(1);
			classAndStudentService.save(classAndStudent);
		}
		if (request.getParameter("email") != null && !request.getParameter("email").equals("")) {
			addFlashMessage(redirectAttributes, Message.success("添加学生成功，用户名和密码已经发送至学生邮箱，请注意查收"));
		} else {
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		}

		return "redirect:list.jhtml";
	}

	/**
	 * 模板导出
	 */
	@RequestMapping(value = "/createTemplate", method = RequestMethod.GET)
	public String createTemplate(ModelMap model, HttpServletResponse response) {

		return "redirect:list.jhtml";
	}

	/**
	 * 批量导入
	 */
	@RequestMapping(value = "/importStudent", method = RequestMethod.GET)
	public String importStudent(ModelMap model) {

		return "/admin/student/importStudent";
	}

	/**
	 * 批量导入保存
	 */
	@Transactional
	@RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
	public String saveStudent(MultipartFile excelFile, RedirectAttributes redirectAttributes) {
		// 上传文件到本地的路径
		String url = fileService.uploadLocal(FileInfo.FileType.file, excelFile);
		if (url.contains(".xls") || url.contains(".xlsx")) {
			User currentUser = systemUserService.getCurrentUser();
			// 通过工具类获取到所需的数据
			StudentAndUser studentAndUser = this.read.readStudentExcel(url);
			List<Student> lists_student = studentAndUser.getLists_student();
			List<ClassAndStudent> lists_classAndStudent = studentAndUser.getLists_classAndStudent();
			List<Integer> lists_number = studentAndUser.getLists_number();
			// 记录班级间隔数
			int space_number = 0;
			if (lists_student != null) {
				for (int i = 0; i < lists_student.size(); i++) {
					// 添加学生
					Student student = lists_student.get(i);

					Student temp1 = new Student();
					temp1.setUsername(student.getUsername());
					temp1.setSchoolId(student.getSchoolId());
					Student record = studentService.findByUserNameAndSchoolId(temp1);
					if( record != null){
						record.setEnabled(1);
						record.setRoleId(EnumConstants.authorityEnum.student.getValue());
						record.setIsLocked(false);
						record.setLastUpdatedDate(new Date());
						record.setLastUpdatedBy(currentUser.getName());
						studentService.update(record);
					}else {
						student.setEnabled(1);
						student.setRoleId(EnumConstants.authorityEnum.student.getValue());
						student.setIsLocked(false);
						student.setCreatedDate(new Date());
						student.setCreatedBy(currentUser.getName());
						student.setLastUpdatedDate(new Date());
						student.setLastUpdatedBy(currentUser.getName());
						try {
							studentService.saveOne(student);
						} catch (MessagingException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							addFlashMessage(redirectAttributes, Message.error("" + e));
						}

					}

					Student temp = new Student();
					temp.setUsername(student.getUsername());
					temp.setSchoolId(student.getSchoolId());
					student = studentService.findByUserNameAndSchoolId(student);

					// 通过相应的算法把学生与班级的关系存入关联表
					if (lists_number.size() != 0) {
						int number = lists_number.get(i);
						for (int j = 0; j < number; j++) {
							ClassAndStudent classAndStudent = lists_classAndStudent.get(i + j + space_number);
							classAndStudent.setStudent_id(student.getId());
							classAndStudent.setEnabled(1);
							classAndStudentService.save(classAndStudent);
							if (j == number - 1) {
								space_number = space_number + (number - 1);
							}
						}
					}

				}
			}
			// 把成功导入学生的数量和失败的数量记载到redis
			if (lists_student.size() != 0) {
				redisService.save(systemUserService.getCurrentUserName() + "importStudent_number",
						String.valueOf(lists_student.size()));
			} else {
				redisService.save(systemUserService.getCurrentUserName() + "importStudent_number", String.valueOf(-1));
			}
		}
		return "redirect:list.jhtml";
	}

	/**
	 * 列表显示
	 */
	@RequiresPermissions("admin:system:student")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Pageable pageable, ModelMap model) {
		// 班级id
		className = request.getParameter("filter_cName");
		filter_user_name = request.getParameter("filter_user_name");
		filter_name = request.getParameter("filter_name");
		String usernameflag=request.getParameter("usernameflag");
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.teacher.getValue()
				&& className != null) {
			redisService.save(systemUserService.getCurrentUserName() + "ttclassName", className);
		}
		// 把该登录用户学生管理中的redis数据清空
		redisService.delete(systemUserService.getCurrentUserName() + "collegeFlag");
		redisService.delete(systemUserService.getCurrentUserName() + "majorFlag");
		redisService.delete(systemUserService.getCurrentUserName() + "classFlag");
		redisService.delete(systemUserService.getCurrentUserName() + "editstudent");

		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.teacher.getValue()) {
			List<ClassAndStudent> classAndStudentList = classAndStudentService.findList("student_class_id",
					Integer.valueOf(redisService.get(systemUserService.getCurrentUserName() + "ttclassName")));
			List<Integer> studentIdList = new ArrayList<>();
			for (ClassAndStudent classAndStudent : classAndStudentList) {
				studentIdList.add(classAndStudent.getStudent_id());
			}
			Student student = new Student();
			if (!"".equals(filter_user_name) && filter_user_name != null) {
				student.setUsername(filter_user_name);
			}
			if (!"".equals(filter_name) && filter_name != null) {
				student.setName(filter_name);
			}
			if (studentIdList.size() > 0) {
				student.setStudentIdList(studentIdList);
				List<Student> studentList = studentService.findEntityList(student);
				if (usernameflag!=null){
				if (usernameflag.equals("asc"))SortUtil.sort(studentList,true,"username");
				else  SortUtil.sort(studentList,false,"username");
                    model.addAttribute("usernameflag",usernameflag);
				}

				model.addAttribute("page", studentService.pageMethod(pageable, studentList));
			} else {
				List<Student> studentList = new ArrayList<>();
				model.addAttribute("page", studentList);
			}
		} else {
            Page<Student> page=this.studentService.findByPage(pageable);
            if (usernameflag!=null){
                if (usernameflag.equals("asc"))SortUtil.sort(page.getContent(),true,"username");
                else  SortUtil.sort(page.getContent(),false,"username");
                model.addAttribute("usernameflag",usernameflag);
            }
			model.addAttribute("page", page);
		}
		model.addAttribute("params", map);
		// 判断导入成功数量是否为空，不为空则弹出导入成功提示框
		if (redisService.get(systemUserService.getCurrentUserName() + "importStudent_number") != null) {
			model.addAttribute("number",
					redisService.get(systemUserService.getCurrentUserName() + "importStudent_number"));
			redisService.delete(systemUserService.getCurrentUserName() + "importStudent_number");
		}
		return "/admin/student/list";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(HttpServletRequest request, Long[] ids) {

		List<String> recordStudentId = new ArrayList<>();
		for (long id : ids) {
			recordStudentId.add(String.valueOf(id));
		}
		if (recordStudentId.size() != 0) {
			try {
				for (int i=0;i<recordStudentId.size();i++){
					Long temp=Long.parseLong(recordStudentId.get(i));
					List<Exercise> exercises=exerciseService.findbySid(temp);
					for(int j=0;j<exercises.size();j++){
						exerciseService.delete(exercises.get(j).getEId());
					}
				}
				studentService.deleteOne(recordStudentId);
			} catch (Exception e) {
				System.out.println("错误"+e.getMessage());
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
				Student student = studentService.find(id);
				try {
					studentService.updateInitPass(student);
				} catch (MessagingException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					addFlashMessage(redirectAttributes, Message.error("" + e));
				}
			}
			addFlashMessage(redirectAttributes, Message.success("初始化密码成功，新密码为123456，并已经发送至学生邮箱，请注意查收"));
		} else {
			addFlashMessage(redirectAttributes, ERROR_MESSAGE);
		}

		return "redirect:list.jhtml";
	}

	/**
	 * 编辑保存
	 */
	@Transactional
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSave(HttpServletRequest request, RedirectAttributes redirectAttributes) {

		String id = request.getParameter("id");
		String schoolId = request.getParameter("schoolId");
		// 用户名
		String username = request.getParameter("number");
		// 姓名
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String state = request.getParameter("state");
		// 班级名称
		String classString = request.getParameter("className");
		String email = request.getParameter("email");
		// 修改学生信息
		Student student = getStudent(id, sex, state, username, name, schoolId, email);
		studentService.update(student);

		// 学生与班级关联表
		if (!classString.equals("") && classString != null && !classString.equals("className")) {
			if (classString.contains(",")) {
				String[] classArray = classString.split(",");
				// 获取该学生原先有多少个班级
				List<ClassAndStudent> classAndStudentList = classAndStudentService.findList("student_id",
						Long.parseLong(request.getParameter("id")));
				// 把之前的班级进行更新
				for (int i = 0; i < classAndStudentList.size(); i++) {
					ClassAndStudent classAndStudent = classAndStudentList.get(i);
					classAndStudent.setStudent_id(Integer.parseInt(id));
					classAndStudent.setStudent_class_id(Integer.parseInt(classArray[i]));
					classAndStudentService.update(classAndStudent);
				}
				// 若当前班级数量比之前的班级数量多，则多出来的进行添加
				if (classArray.length > classAndStudentList.size()) {
					for (int i = classAndStudentList.size(); i < classArray.length; i++) {
						ClassAndStudent classAndstudent = new ClassAndStudent();
						classAndstudent.setStudent_id(Integer.parseInt(request.getParameter("id")));
						classAndstudent.setStudent_class_id(Integer.parseInt(classArray[i]));
						classAndstudent.setEnabled(1);
						classAndStudentService.save(classAndstudent);
					}
				}
			} else {
				// 若该学生是从多班级变为一个班级则更新一个班级的信息，把剩下的班级全部删除
				List<ClassAndStudent> casList = classAndStudentService.findList("student_id", Long.parseLong(id));
				for (int i = 0; i < casList.size(); i++) {
					if (i == 0) {
						casList.get(i).setStudent_id(Integer.parseInt(id));
						casList.get(i).setStudent_class_id(Integer.parseInt(classString));
						classAndStudentService.update(casList.get(i));
					} else {
						classAndStudentService.delete(casList.get(i).getId());
					}
				}

			}
		}

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		// 把redis中记载的对话框的学院，专业，班级，还有对话框中的勾选全部删除
		redisService.delete(systemUserService.getCurrentUserName() + "collegeList");
		redisService.delete(systemUserService.getCurrentUserName() + "majorList");
		redisService.delete(systemUserService.getCurrentUserName() + "classList");

		redisService.save(systemUserService.getCurrentUserName() + "collegeFlag", "0");
		redisService.save(systemUserService.getCurrentUserName() + "majorFlag", "0");
		redisService.save(systemUserService.getCurrentUserName() + "classFlag", "0");
		// 把编辑的学生记载到redis
		redisService.save(systemUserService.getCurrentUserName() + "editstudent",
				JSON.toJSONString(studentService.find(id)));
		Student temp_student = JSON
				.parseObject(redisService.get(systemUserService.getCurrentUserName() + "editstudent"), Student.class);
		model.addAttribute("student", temp_student);
		if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.teacher.getValue()) {
			ClassEntity classSystem = classService.find("id",
					redisService.get(systemUserService.getCurrentUserName() + "ttclassName"));
			model.addAttribute("ttclassSystem", classSystem);
		}

		return "/admin/student/edit";
	}

	/**
	 * 初始化数据
	 */
	@ModelAttribute
	public void init(Model model) {
		// 获取数据库中全部的学校
		List<School> schoolList = schoolService.findAll();
		Teacher teacher = teacherService.find("username", systemUserService.getCurrentUserName());
		model.addAttribute("teacherInit", teacher);
		model.addAttribute("schoolList", schoolList);
		model.addAttribute("systemUser", systemUserService.getCurrentUser());

	}

	/**
	 * 获取学校id得到相应的学院集合
	 */
	@ResponseBody
	@RequestMapping("/CollegeAjax")
	public List<College> collegeAjax(String schoolId) {

		// 把redis中记载的对话框的学院，专业，班级，还有对话框中的勾选全部删除
		redisService.delete(systemUserService.getCurrentUserName() + "collegeList");
		redisService.delete(systemUserService.getCurrentUserName() + "majorList");
		redisService.delete(systemUserService.getCurrentUserName() + "classList");
		List<College> collegeList = schoolService.find(Long.parseLong(schoolId)).getSchoolCollegeList();
		SortUtil.sort(collegeList,true,"collegeName");
		// 把获取到的学院保存在redis
		redisService.save(systemUserService.getCurrentUserName() + "collegeList", JSON.toJSONString(collegeList));
		if (collegeList.size() == 0) {
			redisService.save(systemUserService.getCurrentUserName() + "collegeFlag", "1");
		}

		return collegeList;
	}

	/**
	 * 学院对话框列表
	 */
	@RequestMapping(value = { "/listDialogCollege" }, method = { RequestMethod.GET })
	public String listDialogCollege(HttpServletRequest request, Pageable pageable, ModelMap model) {
		// 从redis中获取保存的学院
		List<College> collegeList = null;
		if (redisService.get(systemUserService.getCurrentUserName() + "collegeList") != null) {
			collegeList = idsUtils.collegejsonToList(
					redisService.get(systemUserService.getCurrentUserName() + "collegeList"), College.class);
		} else {
			collegeList = new ArrayList<>();
		}
		// 编辑状态下的学院对话框
		if (redisService.get(systemUserService.getCurrentUserName() + "editstudent") != null && collegeList.size() == 0
				&& redisService.get((systemUserService.getCurrentUserName() + "collegeFlag")).equals("0")) {
			Student temp_student = JSON.parseObject(
					redisService.get(systemUserService.getCurrentUserName() + "editstudent"), Student.class);
			long schoolId = temp_student.getSchoolId();
			// 通过编辑学生所属的学校id获取相应的全部学院
			collegeList = schoolService.find(schoolId).getSchoolCollegeList();
			SortUtil.sort(collegeList,true,"collegeName");
			redisService.save(systemUserService.getCurrentUserName() + "collegeList", JSON.toJSONString(collegeList));
			model.addAttribute("page",
					collegeService.pageMethod(pageable, schoolService.find(schoolId).getSchoolCollegeList()));

		} else if (redisService.get(systemUserService.getCurrentUserName() + "editstudent") != null
				&& collegeList.size() == 0
				&& redisService.get((systemUserService.getCurrentUserName() + "collegeFlag")).equals("1")) {
			List<College> blank_college = new ArrayList<>();
			model.addAttribute("page", collegeService.pageMethod(pageable, blank_college));
		} else {
			// 当前登录用户权限为管理员，获取该管理员所属学校的全部学院
			if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()) {
				collegeList = schoolService.find((long) systemUserService.getCurrentUser().getSchoolId())
						.getSchoolCollegeList();
				SortUtil.sort(collegeList,true,"collegeName");
				redisService.save(systemUserService.getCurrentUserName() + "collegeList",
						JSON.toJSONString(collegeList));
			}
			model.addAttribute("page", collegeService.pageMethod(pageable, collegeList));
		}

		return "/admin/student/listDialogCollege";
	}

	/**
	 * 获取学院id得到相应的专业集合
	 */
	@ResponseBody
	@RequestMapping("/MajorAjax")
	public List<Major> majorAjax(String selectIds) {

		// 把redis中记载的对话框的专业，班级，还有对话框中的勾选全部删除
		redisService.delete(systemUserService.getCurrentUserName() + "majorList");
		redisService.delete(systemUserService.getCurrentUserName() + "classList");

		College college = null;
		if (selectIds.contains(",")) {
			// 选择了多个学院
			List<Major> majorList = new ArrayList<>();
			String[] ids = selectIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				college = collegeService.find(Long.parseLong(ids[i]));
				List<Major> list = college.getCollegeMajorList();
				SortUtil.sort(list,true,"majorName");
				majorList.addAll(list);
			}
			redisService.save(systemUserService.getCurrentUserName() + "majorList", JSON.toJSONString(majorList));
		} else {
			// 选择一个学院
			college = collegeService.find(Long.parseLong(selectIds));
			redisService.save(systemUserService.getCurrentUserName() + "majorList",
					JSON.toJSONString(college.getCollegeMajorList()));
		}
		if (idsUtils
				.majorjsonToList(redisService.get(systemUserService.getCurrentUserName() + "majorList"), Major.class)
				.size() == 0) {
			redisService.save(systemUserService.getCurrentUserName() + "majorFlag", "1");
		}

		return idsUtils.majorjsonToList(redisService.get(systemUserService.getCurrentUserName() + "majorList"),
				Major.class);
	}

	/**
	 * 专业对话框列表
	 */
	@RequestMapping(value = { "/listDialogMajor" }, method = { RequestMethod.GET })
	public String listDialogMajor(HttpServletRequest request, Pageable pageable, ModelMap model) {
		// 从redis中获取保存的专业
		List<Major> majorList = null;
		if (redisService.get(systemUserService.getCurrentUserName() + "majorList") != null) {
			majorList = idsUtils.majorjsonToList(redisService.get(systemUserService.getCurrentUserName() + "majorList"),
					Major.class);
			SortUtil.sort(majorList,true,"majorName");
		} else {
			majorList = new ArrayList<>();
		}
		// 编辑状态下的专业对话框
		if (redisService.get(systemUserService.getCurrentUserName() + "editstudent") != null && majorList.size() == 0
				&& redisService.get(systemUserService.getCurrentUserName() + "majorFlag").equals("0")) {
			List<Major> newmajorList = new ArrayList<>();
			Student temp_student = JSON.parseObject(
					redisService.get(systemUserService.getCurrentUserName() + "editstudent"), Student.class);
			List<ClassAndStudent> classAndStudentList = temp_student.getClassList();
			for (int i = 0; i < classAndStudentList.size(); i++) {
				ClassAndStudent classAndStudent = classAndStudentList.get(i);
				// 从对应的班级找到相应的学院，然后通过学院寻找该学院所有的专业
				long collegeId = classAndStudent.getClassSystem().getMajor().getCollege().getId();
				newmajorList.addAll(collegeService.find(collegeId).getCollegeMajorList());
			}
			majorList = newmajorList;
			if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.teacher.getValue()) {
				Teacher teacher = teacherService.find("username", systemUserService.getCurrentUserName());
				newmajorList = collegeService.find((long) teacher.getCollege_id()).getCollegeMajorList();
			}
			redisService.save(systemUserService.getCurrentUserName() + "majorList", JSON.toJSONString(newmajorList));
			// 去掉重复的major
			if (newmajorList.size() > 1) {
				List<Major> majorListNew = new ArrayList<>();
				for (Major major : newmajorList) {
					if (!majorListNew.contains(major)) {
						majorListNew.add(major);
					}
				}
				newmajorList = majorListNew;
			}

			model.addAttribute("page", majorService.pageMethod(pageable, newmajorList));
		} else if (redisService.get(systemUserService.getCurrentUserName() + "editstudent") != null
				&& majorList.size() == 0
				&& redisService.get(systemUserService.getCurrentUserName() + "majorFlag").equals("1")) {
			List<Major> blank_major = new ArrayList<>();
			model.addAttribute("page", majorService.pageMethod(pageable, blank_major));
		} else {
			// 当前登录用户权限为教师，专业数据为该教师所属学院下的全部专业
			if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.teacher.getValue()) {
				Teacher teacher = teacherService.find("username", systemUserService.getCurrentUserName());
				majorList = collegeService.find((long) teacher.getCollege_id()).getCollegeMajorList();
			}
			redisService.save(systemUserService.getCurrentUserName() + "majorList", JSON.toJSONString(majorList));
			// 去掉重复的major
			if (majorList.size() > 1) {
				List<Major> majorListNew = new ArrayList<>();
				for (Major major : majorList) {
					if (!majorListNew.contains(major)) {
						majorListNew.add(major);
					}
				}
				majorList = majorListNew;
			}
			model.addAttribute("page", majorService.pageMethod(pageable, majorList));
		}

		return "/admin/student/listDialogMajor";
	}

	/**
	 * 获取专业id得到相应的班级集合
	 */
	@ResponseBody
	@RequestMapping("/ClassAjax")
	public List<ClassEntity> classAjax(String selectIds) {
		// 把redis中记载的对话框的班级，还有对话框中的勾选全部删除
		redisService.delete(systemUserService.getCurrentUserName() + "classList");

		if (selectIds.contains(",")) {
			// 选择了多个专业
			List<ClassEntity> classList = new ArrayList<>();
			String[] ids = selectIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				List<ClassEntity> tempList = classService.findList("major_id", Long.parseLong(ids[i]));
				for (int j = 0; j < tempList.size(); j++) {
					if (tempList.get(j).getEnabled() == 1) {
						classList.add(tempList.get(j));
					}
				}
			}
			redisService.save(systemUserService.getCurrentUserName() + "classList", JSON.toJSONString(classList));
		} else {
			// 选择一个专业
			List<ClassEntity> classList = new ArrayList<>();
			List<ClassEntity> tempList = classService.findList("major_id", selectIds);
			for (int i = 0; i < tempList.size(); i++) {
				if (tempList.get(i).getEnabled() == 1) {
					classList.add(tempList.get(i));
				}
			}
			redisService.save(systemUserService.getCurrentUserName() + "classList", JSON.toJSONString(classList));
		}

		if (idsUtils.classjsonToList(redisService.get(systemUserService.getCurrentUserName() + "classList"),
				ClassEntity.class).size() == 0) {
			redisService.save(systemUserService.getCurrentUserName() + "classFlag", "1");
		}

		return idsUtils.classjsonToList(redisService.get(systemUserService.getCurrentUserName() + "classList"),
				ClassEntity.class);
	}

	/**
	 * 班级对话框列表
	 */
	@RequestMapping(value = { "/listDialogClass" }, method = { RequestMethod.GET })
	public String listDialogClass(HttpServletRequest request, Pageable pageable, ModelMap model) {
		// 在redis中获取专业id得到的班级信息
		List<ClassEntity> classList = null;
		if (redisService.get(systemUserService.getCurrentUserName() + "classList") != null) {
			classList = idsUtils.classjsonToList(redisService.get(systemUserService.getCurrentUserName() + "classList"),
					ClassEntity.class);
			SortUtil.sort(classList,true,"className");
		} else {
			classList = new ArrayList<>();
		}
		// 查询条件的班级名称
		String className = request.getParameter("className");
		// 编辑状态下的班级信息
		if (redisService.get(systemUserService.getCurrentUserName() + "editstudent") != null && classList.size() == 0
				&& (className == null || className.equals(""))
				&& redisService.get(systemUserService.getCurrentUserName() + "classFlag").equals("0")) {
			List<ClassEntity> classLists = new ArrayList<>();
			Student temp_student = JSON.parseObject(
					redisService.get(systemUserService.getCurrentUserName() + "editstudent"), Student.class);
			List<ClassAndStudent> classAndStudentList = temp_student.getClassList();

			for (int i = 0; i < classAndStudentList.size(); i++) {
				ClassAndStudent classAndStudent = classAndStudentList.get(i);
				// 从对应的班级找到相应的专业，然后通过专业寻找该专业所有的班级
				long majorId = classAndStudent.getClassSystem().getMajor().getId();
				List<ClassEntity> temp = classService.findList("major_id", majorId);
				for (int j = 0; j < temp.size(); j++) {
					if (temp.get(j).getEnabled() == 1) {
						classLists.add(temp.get(j));
					}
				}
			}

			classList = classLists;
			redisService.save(systemUserService.getCurrentUserName() + "classList", JSON.toJSONString(classLists));
			// 去掉重复的class
			if (classLists.size() > 1) {
				List<ClassEntity> classListNew = new ArrayList<>();
				for (ClassEntity classEntity : classLists) {
					if (!classListNew.contains(classEntity)) {
						classListNew.add(classEntity);
					}
				}
				classLists = classListNew;
			}
			model.addAttribute("page", classService.pageMethod(pageable, classLists));

		} else if (redisService.get(systemUserService.getCurrentUserName() + "editstudent") != null
				&& classList.size() == 0 && (className == null || className.equals(""))
				&& redisService.get(systemUserService.getCurrentUserName() + "classFlag").equals("1")) {

			List<ClassEntity> blank_class = new ArrayList<>();
			model.addAttribute("page", classService.pageMethod(pageable, blank_class));

		} else if (className == null || className.equals("")) {
			redisService.save(systemUserService.getCurrentUserName() + "classList", JSON.toJSONString(classList));
			// 去掉重复的class
			if (classList.size() > 1) {
				List<ClassEntity> classListNew = new ArrayList<>();
				for (ClassEntity classEntity : classList) {
					if (!classListNew.contains(classEntity)) {
						classListNew.add(classEntity);
					}
				}
				classList = classListNew;
			}
			model.addAttribute("page", classService.pageMethod(pageable, classList));

		}
		// 在班级对话框列表中进行班级查询
		if (redisService.get(systemUserService.getCurrentUserName() + "editstudent") != null && classList.size() == 0
				&& className != null) {
			List<ClassEntity> classLists = new ArrayList<>();
			Student temp_student = JSON.parseObject(
					redisService.get(systemUserService.getCurrentUserName() + "editstudent"), Student.class);
			List<ClassAndStudent> classAndStudentList = temp_student.getClassList();

			for (int i = 0; i < classAndStudentList.size(); i++) {
				ClassAndStudent classAndStudent = classAndStudentList.get(i);
				// 从对应的班级找到相应的专业，然后通过专业寻找该专业所有的班级
				long majorId = classAndStudent.getClassSystem().getMajor().getId();
				List<ClassEntity> temp = classService.findList("major_id", majorId);
				for (int j = 0; j < temp.size(); j++) {
					if (temp.get(j).getEnabled() == 1 && temp.get(j).getClassName().contains(className)) {
						classLists.add(temp.get(j));
					}
				}
			}

			classList = classLists;
			// 去掉重复的class
			if (classList.size() > 1) {
				List<ClassEntity> classListNew = new ArrayList<>();
				for (ClassEntity classEntity : classList) {
					if (!classListNew.contains(classEntity)) {
						classListNew.add(classEntity);
					}
				}
				classList = classListNew;
			}
			model.addAttribute("page", classService.pageMethod(pageable, classList));

		} else if (className != null) {
			List<ClassEntity> temp_classLists = new ArrayList<>();
			for (int i = 0; i < classList.size(); i++) {
				if (classList.get(i).getClassName().contains(className)) {
					temp_classLists.add(classList.get(i));
				}
			}
			model.addAttribute("page", classService.pageMethod(pageable, temp_classLists));
		}

		return "/admin/student/listDialogClass";
	}

	/**
	 * 添加或修改学生信息
	 */
	public Student getStudent(String id, String sex, String state, String username, String name, String schoolId,
			String email) {

		Student student = new Student();
		if (id != null && !id.equals("")) {
			student.setId(Integer.parseInt(id));
		}
		student.setSex(sex);
		student.setState(state);
		student.setUsername(username);
		student.setName(name);
		student.setSchoolId(Integer.parseInt(schoolId));
		student.setEmail(email);
		student.setLastUpdatedDate(new Date());
		student.setLastUpdatedBy(systemUserService.getCurrentUser().getName());

		return student;

	}

}
