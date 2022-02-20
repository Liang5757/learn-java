package cn.youyitech.anyview.system.controller.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.youyitech.anyview.system.utils.*;
import com.github.abel533.echarts.code.Sort;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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

import cn.youyitech.anyview.system.dto.ImportQuestionContent;
import cn.youyitech.anyview.system.dto.QuestionTemp;
import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.College;
import cn.youyitech.anyview.system.entity.Course;
import cn.youyitech.anyview.system.entity.Question;
import cn.youyitech.anyview.system.entity.QuestionBank;
import cn.youyitech.anyview.system.entity.QuestionContent;
import cn.youyitech.anyview.system.entity.QuestionHeaderFile;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.service.CollegeService;
import cn.youyitech.anyview.system.service.CourseService;
import cn.youyitech.anyview.system.service.FileService;
import cn.youyitech.anyview.system.service.QuestionBankService;
import cn.youyitech.anyview.system.service.QuestionService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.support.AdminEnum;
import cn.youyitech.anyview.system.support.EnumConstants;
import cn.youyitech.anyview.system.support.FileInfo;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.support.Setting;

import com.alibaba.fastjson.JSON;
import com.framework.loippi.support.Pageable;

/**
 * Controller - 应用版本
 * 
 * @author zzq
 * @version 1.0
 */

@Controller("adminQuestionBankController")
@RequestMapping("/admin/question_bank")
public class QuestionBankController extends GenericController {

	@Resource
	private QuestionBankService questionBankService;

	@Resource
	private QuestionService questionService;

	@Resource
	private SystemUserService systemUserService;

	@Resource
	private SchoolService schoolService;

	@Resource
	private CollegeService collegeService;

	@Resource
	private CourseService courseService;

	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	@Resource
	private RedisService redisService;

	@Autowired
	private IdsUtils idsUtils;

	@Resource
	TeacherService teacherService;

	@Autowired
	ZipFileRead zipFileRead;

	@Autowired
	ZipFileWrite zipFileWrite;

	@Autowired
	FileUtils fileUtils;

	@Resource
	private PullParserXmlUtils parserUtils;

	@RequestMapping(value = "/checkBank", method = RequestMethod.GET)
	public @ResponseBody boolean checkBank(String id, String bankName, String courseName) {
		// 题库名称与课程id不能为空
		if (StringUtils.isEmpty(bankName) || StringUtils.isEmpty(courseName)) {
			return false;
		}
		// 若是编辑状态，不需要判断自己的题库名，可以直接确定
		QuestionBank questionBank = null;
		if (id != null && !id.equals("")) {
			questionBank = questionBankService.find(Long.parseLong(id));
		}
		if (questionBank != null) {
			if (questionBank.getQuestion_bank().equals(bankName) && questionBank.getCourse_name().equals(courseName)) {
				return true;
			}
		}
		// 若题库名和课程名都相同则不能添加
		QuestionBank temp = new QuestionBank();
		temp.setQuestion_bank(bankName);
		temp.setCourse_name(courseName);
		List<QuestionBank> questionBankList = questionBankService.findByAttribute(temp);
		if (questionBankList.size() > 0) {
			return false;
		}

		return true;

	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {

		initCourse(model);

		return "/admin/question_bank/add";
	}

	/**
	 * 添加保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(QuestionBank questionBank, RedirectAttributes redirectAttributes) {
		// 获取当前登录用户
		User user = systemUserService.getCurrentUser();
		questionBank.setEnabled(1);
		questionBank.setCreated_person(user.getName());
		questionBank.setCreated_date(new Date());
		questionBank.setUpdate_person(user.getName());
		questionBank.setUpdate_date(new Date());
		// 若登录的用户权限是管理员与老师，选择了本校公开，则公开给特别学校那里显示登录用户所属学校
		if ((systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()
				|| systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.teacher.getValue())
				&& questionBank.getPublic_level() == EnumConstants.publiclevelEnum.localSchoolOpen.getValue()) {
			questionBank.setSpecific_school(String.valueOf(systemUserService.getCurrentUser().getSchoolId()));
		}
		questionBankService.save(questionBank);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表显示
	 */
	@RequiresPermissions("admin:system:questionbank")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Pageable pageable, ModelMap model) {
		redisService.delete(systemUserService.getCurrentUserName() + "recordSchoolId");
		redisService.delete(systemUserService.getCurrentUserName() + "recordSchoolName");

		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		initList(model, pageable);
		model.addAttribute("params", map);
		// 导入题库数量不为空，则显示提示对话框
		if (redisService.get(systemUserService.getCurrentUserName() + "importBank_number") != null) {
			model.addAttribute("number",
					redisService.get(systemUserService.getCurrentUserName() + "importBank_number"));
			redisService.delete(systemUserService.getCurrentUserName() + "importBank_number");
		}
		return "/admin/question_bank/list";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {
		List<String> recordQuestionBank = new ArrayList<>();
		for (long id : ids) {
			recordQuestionBank.add(String.valueOf(id));
		}
		if (recordQuestionBank.size() > 0) {
			try {
				questionBankService.deleteOne(recordQuestionBank);
			} catch (Exception e) {
				return Message.error(e.getMessage());
			}
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}

	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		// 删除redis中学校对话框中的勾选信息
		redisService.delete(systemUserService.getCurrentUserName() + "recordSchoolId");
		redisService.delete(systemUserService.getCurrentUserName() + "recordSchoolName");
		// 通过id获取相应的题库
		QuestionBank questionbank = questionBankService.find(id);
		model.addAttribute("questionbank", questionbank);

		// 通过公开给特定学校的id获取相应学校的名字
		if (questionbank.getSpecific_school() != null && !questionbank.getSpecific_school().equals("")) {
			List<String> schoolNameList = new ArrayList<>();
			String specificschoolId = questionbank.getSpecific_school();
			if (specificschoolId.contains(",")) {
				String[] specificschoolIdArray = specificschoolId.split(",");
				for (int i = 0; i < specificschoolIdArray.length; i++) {
					schoolNameList.add(schoolService.find(Long.parseLong(specificschoolIdArray[i])).getSchoolName());
				}
			} else {
				schoolNameList.add(schoolService.find(Long.parseLong(specificschoolId)).getSchoolName());
			}
			model.addAttribute("schoolNameList", schoolNameList);
		}

		initCourse(model);

		return "/admin/question_bank/edit";
	}

	/**
	 * 编辑保存
	 */
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSave(QuestionBank questionBank, RedirectAttributes redirectAttributes) {
		// 获取当前登录用户信息
		User user = systemUserService.getCurrentUser();
		// 若公开级别不是公开给特定学校，则特定学校设置为空
		if (questionBank.getSpecific_school() == null) {
			questionBank.setSpecific_school("");
		}
		// 若登录的用户权限是管理员与老师，选择了本校公开，则公开给特别学校那里显示登录用户所属学校
		if ((systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()
				|| systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.teacher.getValue())
				&& questionBank.getPublic_level() == EnumConstants.publiclevelEnum.localSchoolOpen.getValue()) {
			questionBank.setSpecific_school(String.valueOf(systemUserService.getCurrentUser().getSchoolId()));
		}
		questionBank.setUpdate_person(user.getUsername());
		questionBank.setUpdate_date(new Date());
		questionBankService.update(questionBank);

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		return "redirect:list.jhtml";
	}

	/**
	 * 学校对话框列表
	 */
	@RequestMapping(value = { "/listDialogSchool" }, method = { RequestMethod.GET })
	public String listDialogSchool(HttpServletRequest request, Pageable pageable, ModelMap model) {
		// 数据库中全部学校的信息并且传redis中已勾选的学校信息
		model.addAttribute("page", schoolService.findByPage(pageable));
		model.addAttribute("recordSchoolId",
				idsUtils.stringjsonToList(systemUserService.getCurrentUserName() + "recordSchoolId"));
		model.addAttribute("recordSchoolName",
				idsUtils.stringjsonToList(systemUserService.getCurrentUserName() + "recordSchoolName"));

		return "/admin/question_bank/listDialogSchool";
	}

	/**
	 * 记录勾选的学校(0:删除单个学校，1：添加单个学校，2：全选学校，3：删除全选)
	 */
	@ResponseBody
	@RequestMapping(value = { "/recordSchool" }, method = { RequestMethod.POST })
	public List<String> recordSchool(String schoolID, String flag) throws IOException {
		// 获取redis中记载的勾选学校信息
		List<String> recordSchoolId = null;
		List<String> recordSchoolName = null;
		if (redisService.get(systemUserService.getCurrentUserName() + "recordSchoolId") != null) {
			recordSchoolId = idsUtils.stringjsonToList(systemUserService.getCurrentUserName() + "recordSchoolId");
		} else {
			recordSchoolId = new ArrayList<>();
		}
		if (redisService.get(systemUserService.getCurrentUserName() + "recordSchoolName") != null) {
			recordSchoolName = idsUtils.stringjsonToList(systemUserService.getCurrentUserName() + "recordSchoolName");
		} else {
			recordSchoolName = new ArrayList<>();
		}

		if (flag.equals("1")) {
			String schoolName = schoolService.find(Long.parseLong(schoolID)).getSchoolName();
			recordSchoolName.add(schoolName);
			recordSchoolId.add(schoolID);
		} else if (flag.equals("0")) {
			String schoolName = schoolService.find(Long.parseLong(schoolID)).getSchoolName();
			recordSchoolName.remove(schoolName);
			recordSchoolId.remove(schoolID);
		} else if (flag.equals("2")) {
			recordSchoolId.clear();
			recordSchoolName.clear();
			List<School> schoolList = schoolService.findAll();
			for (int i = 0; i < schoolList.size(); i++) {
				recordSchoolId.add(String.valueOf(schoolList.get(i).getId()));
				recordSchoolName.add(schoolList.get(i).getSchoolName());
			}
		} else if (flag.equals("3")) {
			recordSchoolId.clear();
			recordSchoolName.clear();
		}
		// 往redis中添加新已勾选学校信息
		redisService.save(systemUserService.getCurrentUserName() + "recordSchoolId", JSON.toJSONString(recordSchoolId));
		redisService.save(systemUserService.getCurrentUserName() + "recordSchoolName",
				JSON.toJSONString(recordSchoolName));
		return recordSchoolId;
	}

	/**
	 * 批量导入
	 */
	@RequestMapping(value = "/importQuestionBank", method = RequestMethod.GET)
	public String importQuestionBank(ModelMap model) {

		return "/admin/question_bank/importQuestionBank";
	}

	/**
	 * 批量导入题库保存
	 */
	@Transactional
	@RequestMapping(value = "/saveimportQuestionBank", method = RequestMethod.POST)
	public String saveimportQuestionBank(MultipartFile qbfile, ModelMap model, RedirectAttributes redirectAttributes) {
		// 获取上传文件路径
		String url = fileService.uploadLocal(FileInfo.FileType.file, qbfile);
		ImportQuestionContent importqc = null;
		if (url.contains(".zip")) {
			importqc = zipFileRead.readQuestionBankZip(url);
		} else if (url.contains(".rar")) {
			importqc = zipFileRead.readQuestionBankRar(url);
		}
		if (importqc != null) {

			User user = systemUserService.getCurrentUser();
			// 章节名称集合
			List<String> chapterNameList = importqc.getChapterNameList();
			// 一个章节名称拥有多少个题目
			List<Integer> questionNumberList = importqc.getQuestionNumberList();
			// 题目名称集合
			List<String> questionNameList = importqc.getQuestionNameList();
			// 题目内容集合
			List<QuestionContent> questionContentList = importqc.getQuestionContentList();
			// 题目内容头文件集合
			List<QuestionHeaderFile> headerFileList = importqc.getHeaderFileList();
			//一个题目包含多少个头文件
            List<Integer> headerNameList = importqc.getHeaderNameList();
          //记录头文件数量
            int allHeaderCount = 0;
			// 记录添加成功的题库
			int number = 0;
			String fileName = qbfile.getOriginalFilename();
			String courseName = fileName.substring(0, fileName.indexOf("_"));
			String questionBankName = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
			List<Course> courseList = courseService.findList("courseName", courseName);

			if (courseList.size() > 0) {
				if (checkBank("", questionBankName, courseName)) {
					// 导入题库
					number++;
					QuestionBank qb = new QuestionBank();
					// 题库名称为导入文件的文件名
					qb.setQuestion_bank(questionBankName);
					qb.setCourse_name(courseName);
					qb.setPublic_level(EnumConstants.publiclevelEnum.secrecy.getValue());
					qb.setCreated_person(user.getName());
					qb.setCreated_date(new Date());
					qb.setUpdate_person(user.getName());
					qb.setUpdate_date(new Date());
					qb.setEnabled(1);
					questionBankService.save(qb);

					// 记录之前的题目数
					int allnumber = 0;
					// 题目数量的list退后一位，则从1开始
					int flag = 0;
					// 记录当前题库的题目数量
					int questionNumber = 0;
					// 初始的题库id
					long questionbank_id = questionBankService.findTotal()
							.get((questionBankService.findTotal().size() - 1)).getId();
					// 章节名称
					String chapterName = "";

					List<QuestionHeaderFile> tempList = new ArrayList<>();
					if (questionNameList != null) {
						for (int i = 0; i < questionNameList.size(); i++) {
							// 导入题目
							if (questionNumberList.size() > flag) {
								questionNumber = questionNumberList.get(flag);
							}
							// 获取相应的章节名称
							if ((questionNumber + allnumber) == i) {
								allnumber = allnumber + questionNumber;
								chapterName = chapterNameList.get(flag);
								flag++;
							}

							if (!chapterName.equals("") && chapterName != null) {
								Question question = new Question();
								question.setQuestion_name(questionNameList.get(i));
								question.setChapter(getChapterName(chapterName));
								question.setPublic_level(EnumConstants.publiclevelEnum.secrecy.getValue());
								question.setDifficulty(EnumConstants.difficultyEnum.one.getValue());
								question.setState(EnumConstants.statusEnum.start.getText());

								QuestionContent qc = questionContentList.get(i);
								int headerCount = headerNameList.get(i);
		                        for(int j = allHeaderCount; j<allHeaderCount + headerCount; j++){
		                              QuestionHeaderFile qhf = headerFileList.get(j);
		                              
		                              tempList.add(qhf);
		                        }
								// 把题目内容与题目内容头文件打包成xml文件存入题目中
								question.setContent(parserUtils.createQuestionXml(qc, tempList));
								tempList.clear();
								allHeaderCount = allHeaderCount + headerCount;
								question.setQuestion_bank_id((int) questionbank_id);
								question.setCreated_person(user.getName());
								question.setCreated_date(new Date());
								question.setUpdate_person(user.getName());
								question.setUpdate_date(new Date());
								question.setEnabled(1);
								questionService.save(question);
							}

						}
					}
				} else {
					number = -2;
				}
			}
			// 成功导入的数量记载到redis中
			if (number >= 0 || number == -2) {
				redisService.save(systemUserService.getCurrentUserName() + "importBank_number", String.valueOf(number));
			} else {
				redisService.save(systemUserService.getCurrentUserName() + "importBank_number", String.valueOf(-1));
			}
		}

		return "redirect:list.jhtml";
	}

	/**
	 * 批量导出
	 */
	@ResponseBody
	@RequestMapping(value = "/exportQuestionBank", method = RequestMethod.GET)
	public List<String> exportQuestionBank(Long[] ids, ModelMap model, RedirectAttributes redirectAttributes) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		// 通过redis中的记载获取勾选的题库
		List<QuestionBank> questionBankList = new ArrayList<>();
		// 存放路径
		List<String> pathList = new ArrayList<>();
		for (long id : ids) {
			questionBankList.add(questionBankService.find(id));
		}
		// 基础存放路径
		Setting setting = SettingUtils.get();
		String newpath = setting.getUploadPath() + "/upload/tempfile";
		// 循环题库集合把题库内容创建成文件
		for (int a = 0; a < questionBankList.size(); a++) {

			QuestionBank questionBank = questionBankList.get(a);
			List<Question> questionList = questionService.findList("question_bank_id", questionBank.getId());
			String questionbankName = fileUtils.createFileDir(newpath, questionBank.getQuestion_bank());
			for (int i = 0; i < questionList.size(); i++) {
				Question question = questionList.get(i);

				String chapterFileName = fileUtils.createFileDir(questionbankName,
						setChapterName(question.getChapter()));
				String questionFileName = fileUtils.createFileDir(chapterFileName, question.getQuestion_name());
				// 把题目内容的xml解析成题目内容对象与题目内容头文件集合
				QuestionTemp questionTemp = parserUtils.getPullParserQuestionList(question.getContent());
				QuestionContent questionContent = questionTemp.getQuestionContent();
				List<QuestionHeaderFile> qhfList = questionTemp.getHeaderFileList();
				// 创建文件方法
				fileUtils.createFile(questionFileName,
						setChapterName(question.getChapter()) + question.getQuestion_name(),
						questionContent.getStudent_answer(), ".c");
				fileUtils.createFile(questionFileName, "document", questionContent.getQuestion_description(), "");
				fileUtils.createFile(questionFileName, "dx", questionContent.getHeaderfile_content(), ".c");
				fileUtils.createFile(questionFileName, "stds", questionContent.getStandard_answer(), ".c");
				for (int j = 0; j < qhfList.size(); j++) {
					fileUtils.createFile(questionFileName, "head" + (j + 1), qhfList.get(j).getHeader_file_content(),
							"");
				}

			}
			// 把创建的文件打包成zip压缩包
			zipFileWrite.createZip(questionbankName, questionbankName + "_" + format.format(new Date()) + ".zip");
			fileUtils.deleteDirectory(questionbankName);
			pathList.add(questionbankName + "_" + format.format(new Date()) + ".zip");
		}
		zipFileWrite.createZip(newpath, newpath + ".zip");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return pathList;
	}

	/**
	 * 删除生成的压缩包
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
	public String deleteFile(HttpServletRequest request, ModelMap model) {

		String filepath = request.getParameter("filepath");
		if (filepath.contains(",")) {

			String[] paths = filepath.split(",");
			// 多个压缩包与文件
			for (int i = 0; i < paths.length; i++) {
				// 删除生成的压缩包
				File zipfile = new File(paths[i]);
				if (zipfile.exists()) {
					zipfile.delete();
				}
			}
		}
		// 一个压缩包与文件
		else {
			// 删除生成的压缩包
			File zipfile = new File(filepath);
			if (zipfile.exists()) {
				zipfile.delete();
			}
		}

		return "success";
	}

	/**
	 * 初始化数据
	 */
	@ModelAttribute
	public void init(Model model) {
		model.addAttribute("systemUser", systemUserService.getCurrentUser());
	}

	/**
	 * 初始化显示数据
	 */
	public void initList(ModelMap model, Pageable pageable) {
		// 超级管理员权限为-1，管理员权限为1，老师权限为0
		if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.admin.getValue()) {
			model.addAttribute("page", this.questionBankService.findByPage(pageable));
			getSpecificSchool(model, this.questionBankService.findByPage(pageable).getContent());
		} else {
			List<QuestionBank> allqbList = this.questionBankService.findAll();
			List<QuestionBank> qbList = new ArrayList<>();
			// 获取该学校的全部课程
			List<College> collegeList = collegeService.findByIdMany(systemUserService.getCurrentUser().getSchoolId());
			List<Course> allcourse = new ArrayList<>();
			// 去重标志位
			boolean flag = false;
			for (int i = 0; i < collegeList.size(); i++) {
				List<Course> tempcourse = courseService.findByInMany(collegeList.get(i).getId().intValue());
				for (int j = 0; j < tempcourse.size(); j++) {
					for (int k = 0; k < allcourse.size(); k++) {
						if (allcourse.get(k).getCourseName().equals(tempcourse.get(j).getCourseName())) {
							flag = true;
						}
					}
					if (!flag) {
						allcourse.add(tempcourse.get(j));
					} else {
						flag = false;
					}
				}
			}

			// 判断题库的公开给特定学校是否有该用户的学校
			for (int i = 0; i < allqbList.size(); i++) {

				for (int j = 0; j < allcourse.size(); j++) {
					if (allcourse.get(j).getCourseName().equals(allqbList.get(i).getCourse_name())) {
						if (allqbList.get(i).getPublic_level() == EnumConstants.publiclevelEnum.schoolOpen.getValue()
								|| allqbList.get(i).getPublic_level() == EnumConstants.publiclevelEnum.localSchoolOpen
										.getValue()) {
							String[] specific_schoolId = allqbList.get(i).getSpecific_school().split(",");
							for (int k = 0; k < specific_schoolId.length; k++) {
								if (specific_schoolId[k]
										.equals(String.valueOf(systemUserService.getCurrentUser().getSchoolId()))) {
									qbList.add(allqbList.get(i));
								}
							}
						} else if (allqbList.get(i).getPublic_level() == EnumConstants.publiclevelEnum.secrecy
								.getValue()) {
							if (allqbList.get(i).getCreated_person()
									.equals(systemUserService.getCurrentUser().getName())) {
								qbList.add(allqbList.get(i));
							}
						} else {
							qbList.add(allqbList.get(i));
						}

					}
				}

			}
			if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()) {
				model.addAttribute("page", this.questionBankService.pageMethod(pageable, qbList));
				getSpecificSchool(model, qbList);
			} else if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.teacher
					.getValue()) {
				Teacher temp = new Teacher();
				temp.setUsername(systemUserService.getCurrentUserName());
				temp.setSchoolId(systemUserService.getCurrentUser().getSchoolId());
				Teacher teacher = teacherService.findByUserName(temp);
				College c = collegeService.find((long) teacher.getCollege_id());
				// 该教师学院下的全部课程
				List<Course> courseList = c.getCollegeCourseList();
				List<QuestionBank> temp_qbList = new ArrayList<>();
				for (int i = 0; i < qbList.size(); i++) {
					for (int j = 0; j < courseList.size(); j++) {
						if (courseList.get(j).getCourseName().equals(qbList.get(i).getCourse_name())) {
							temp_qbList.add(qbList.get(i));
						}
					}
				}
				model.addAttribute("page", this.questionBankService.pageMethod(pageable, temp_qbList));
				getSpecificSchool(model, temp_qbList);
			}

		}

	}

	/**
	 * 获取公开给特定学校的名称
	 */
	public void getSpecificSchool(ModelMap model, List<QuestionBank> list) {
		List<QuestionBank> questionbankList = list;
		// 记录公开给特定学校的信息
		List<School> schoolList = new ArrayList<>();
		// 记录一个公开给特定学校包含了多少个学校
		List<Integer> numberList = new ArrayList<>();
		for (int i = 0; i < questionbankList.size(); i++) {
			String specificSchool = questionbankList.get(i).getSpecific_school();
			int publicLevel = questionbankList.get(i).getPublic_level();
			if (specificSchool != null && !specificSchool.equals("") && publicLevel ==1) {
				if (specificSchool.contains(",")) {
					String[] temp = specificSchool.split(",");
					for (int j = 0; j < temp.length; j++) {
						schoolList.add(schoolService.find(Long.parseLong(temp[j])));
					}
					numberList.add(temp.length);
				} else {
					schoolList.add(schoolService.find(Long.parseLong(specificSchool)));
					numberList.add(1);
				}
			}

		}
		model.addAttribute("schoolList", schoolList);
		model.addAttribute("numberList", numberList);
	}

	/**
	 * 初始化课程数据
	 */
	@ModelAttribute
	public void initCourse(ModelMap model) {
		// 超级管理员
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.superManager.getValue()) {
			List<Course> allCourseList = courseService.findAll();
			SortUtil.sort(allCourseList,true,"courseName");
			List<Course> courseList = new ArrayList<>();
			// 去重标志位
			boolean flag = true;
			for (int i = 0; i < allCourseList.size(); i++) {
				for (int j = 0; j < courseList.size(); j++) {
					if (courseList.get(j).getCourseName().equals(allCourseList.get(i).getCourseName())) {
						flag = false;
					}
				}
				if (flag) {
					courseList.add(allCourseList.get(i));
				}
				flag = true;
			}
			model.addAttribute("courseList", courseList);

		}
		// 校级管理员
		else if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()) {
			List<College> collegeList = collegeService.findByIdMany(systemUserService.getCurrentUser().getSchoolId());
			List<Course> courseList = new ArrayList<>();
			// 去重标志位
			boolean flag = true;
			for (int i = 0; i < collegeList.size(); i++) {
				List<Course> temp_courseList = courseService.findByInMany(collegeList.get(i).getId().intValue());
				for (int j = 0; j < temp_courseList.size(); j++) {
					for (int k = 0; k < courseList.size(); k++) {
						if (courseList.get(k).getCourseName().equals(temp_courseList.get(j).getCourseName())) {
							flag = false;
						}
					}
					if (flag) {
						courseList.add(temp_courseList.get(j));
					}
					flag = true;
				}

			}
			model.addAttribute("courseList", courseList);

		} else {
			// 教师角色
			Teacher temp = new Teacher();
			temp.setUsername(systemUserService.getCurrentUserName());
			temp.setSchoolId(systemUserService.getCurrentUser().getSchoolId());
			Teacher teacher = teacherService.findByUserName(temp);
			College college = teacher.getCollege();
			model.addAttribute("courseList", courseService.findByInMany(college.getId().intValue()));
		}
	}

	/**
	 * 通过文件名获取章节名称
	 */
	public String getChapterName(String chapter) {
		// 默认第一章
		String chapterName = "第1章";
		for (int i = 20; i > 0; i--) {
			if (chapter.contains(String.valueOf(i))) {
				chapterName = "第" + i + "章";
				return chapterName;
			}
		}
		return chapterName;
	}

	/**
	 * 通过章节名称设置文件名
	 */
	public String setChapterName(String chapter) {
		String chapterName = "";
		for (int i = 20; i > 0; i--) {
			if (chapter.contains(String.valueOf(i))) {
				if (i < 10) {
					chapterName = "CP0" + i;
				} else {
					chapterName = "CP" + i;
				}
				return chapterName;
			}
		}
		return chapterName;
	}

}
