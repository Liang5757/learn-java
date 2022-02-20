package cn.youyitech.anyview.system.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.framework.loippi.support.Pageable;

import cn.youyitech.anyview.system.dto.ImportQuestionContent;
import cn.youyitech.anyview.system.dto.QuestionTemp;
import cn.youyitech.anyview.system.dto.user.User;
import cn.youyitech.anyview.system.entity.Question;
import cn.youyitech.anyview.system.entity.QuestionContent;
import cn.youyitech.anyview.system.entity.QuestionHeaderFile;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.service.ExerciseService;
import cn.youyitech.anyview.system.service.FileService;
import cn.youyitech.anyview.system.service.QuestionService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchemeContentTableService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.support.EnumConstants;
import cn.youyitech.anyview.system.support.FileInfo;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.IdsUtils;
import cn.youyitech.anyview.system.utils.PoiReadWord;
import cn.youyitech.anyview.system.utils.PullParserXmlUtils;
import cn.youyitech.anyview.system.utils.ZipFileRead;

/**
 * Controller - QuestionController
 * 
 * @author zzq
 * @version 1.0
 */

@Controller("adminQuestionController")
@RequestMapping("/admin/question")
public class QuestionController extends GenericController {

	@Resource
	private SchoolService schoolService;

	@Resource
	private QuestionService questionService;

	@Autowired
	SchemeContentTableService schemeContentTableService;

	@Resource
	private SystemUserService systemUserService;
	
	@Resource
	private ExerciseService exerciseService;

	@Resource
	private RedisService redisService;

	@Autowired
	private IdsUtils idsUtils;

	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	@Resource
	private PoiReadWord poiReadWord;

	@Resource
	private ZipFileRead zipFileRead;

	@Resource
	private PullParserXmlUtils parserUtils;

	/**
	 * 检查章节名称和题目名称是否同时存在
	 */
	@RequestMapping(value = "/check_question", method = RequestMethod.GET)
	public @ResponseBody boolean checkQuestion(String id, String chapter, String questionName) {

		// 编辑状态的题目
		Question editQuestion = null;
		if (id != null && !id.equals("")) {
			editQuestion = questionService.find(Long.parseLong(id));
		}
		if (editQuestion != null) {
			// 编辑状态的题目不需要验证自己，可以直接确定
			if (editQuestion.getChapter().equals(chapter) && editQuestion.getQuestion_name().equals(questionName)) {
				return true;
			}
		}
		Question question = new Question();
		question.setQuestion_bank_id(
				Integer.parseInt(redisService.get(systemUserService.getCurrentUserName() + "questionbank_id")));
		question.setChapter(chapter);
		question.setQuestion_name(questionName);
		List<Question> questionList = questionService.findByAttribute(question);
		if (questionList.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {

		// 点击编辑题目内容时记录临时变量question的值
		if (redisService.get(systemUserService.getCurrentUserName() + "question") == null) {
			Question question = new Question();
			question.setPublic_level(-1);
			redisService.save(systemUserService.getCurrentUserName() + "question", JSON.toJSONString(question));
		}
		// 把临时变量question传到前端进行显示
		model.addAttribute("question", JSON
				.parseObject(redisService.get(systemUserService.getCurrentUserName() + "question"), Question.class));
		// 总共有多少个选择的章节
		setChapter(model);

		return "/admin/question/add";
	}

	/**
	 * 添加保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Question question, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// 获取当前登录用户
		User user = systemUserService.getCurrentUser();
		// 保存题目
		// 把该添加的题目添加到进入的题库id
		question.setQuestion_bank_id(
				Integer.parseInt(redisService.get(systemUserService.getCurrentUserName() + "questionbank_id")));
		question.setCreated_person(user.getName());
		question.setCreated_date(new Date());
		question.setUpdate_person(user.getName());
		question.setUpdate_date(new Date());
		question.setEnabled(1);
		// 若登录的用户权限是管理员与老师，选择了本校公开，则公开给特别学校那里显示登录用户所属学校

		if ((systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()
				|| systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.teacher.getValue())
				&& question.getPublic_level() == EnumConstants.publiclevelEnum.localSchoolOpen.getValue()) {
			question.setSpecific_school(String.valueOf(systemUserService.getCurrentUser().getSchoolId()));
		}

		QuestionContent questionContent = JSON.parseObject(
				redisService.get(systemUserService.getCurrentUserName() + "questionContent"), QuestionContent.class);
		List<QuestionHeaderFile> lists_headerfile = idsUtils.qhfToList(
				redisService.get(systemUserService.getCurrentUserName() + "lists_headerfile"),
				QuestionHeaderFile.class);
		// 若题目内容对象为空则初始化
		if (questionContent == null) {
			questionContent = new QuestionContent();
		}
		// 若题目内容头文件集合为空则初始化
		if (lists_headerfile == null) {
			lists_headerfile = new ArrayList<>();
		}
		// 把题目内容与题目内容头文件集合打包成xml文件存入题目
		question.setContent(parserUtils.createQuestionXml(questionContent, lists_headerfile));
		redisService.delete(systemUserService.getCurrentUserName() + "questionContent");
		redisService.delete(systemUserService.getCurrentUserName() + "lists_headerfile");

		questionService.save(question);
		question = null;

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		return "redirect:list.jhtml";
	}

	/**
	 * 列表显示
	 */
	@RequiresPermissions("admin:system:question")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long id, HttpServletRequest request, Pageable pageable, ModelMap model) {
		redisService.delete(systemUserService.getCurrentUserName() + "question");
		redisService.delete(systemUserService.getCurrentUserName() + "questionContent");
		redisService.delete(systemUserService.getCurrentUserName() + "lists_headerfile");
		redisService.delete(systemUserService.getCurrentUserName() + "QuestionFlag");
		// 在题库点击管理查看题目时，传的id
		if (id != null) {
			redisService.save(systemUserService.getCurrentUserName() + "questionbank_id", String.valueOf(id));
		}
		// 查询条件
		String filter_question_name = request.getParameter("filter_question_name");

		// 有查询条件则通过该条件获取到相应的数据
		if (filter_question_name != null && !filter_question_name.equals("")) {
			Question question = new Question();
			question.setQuestion_bank_id(
					Integer.parseInt(redisService.get(systemUserService.getCurrentUserName() + "questionbank_id")));
			question.setQuestion_name(filter_question_name);
			List<Question> questionList = questionService.findByFilterQuestionName(question);
			model.addAttribute("page", questionService.pageMethod(pageable, questionList));
			getQuestionContentList(model, questionList);

		} else {
			List<Question> questionList = questionService.findList("question_bank_id",
					redisService.get(systemUserService.getCurrentUserName() + "questionbank_id"));
			initList(model, pageable, questionList);
		}

		// 批量导入题目数量，提供数量在前端显示提示框
		if (redisService.get(systemUserService.getCurrentUserName() + "importQuestionNumber") != null) {
			model.addAttribute("importQuestionNumber",
					redisService.get(systemUserService.getCurrentUserName() + "importQuestionNumber"));
			redisService.delete(systemUserService.getCurrentUserName() + "importQuestionNumber");
		}

		return "/admin/question/list";
	}

	/**
	 * 初始化题目列表
	 */
	private void initList(ModelMap model, Pageable pageable, List<Question> questionList) {
		// 超级管理员
		if (systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.admin.getValue()) {
			model.addAttribute("page", questionService.pageMethod(pageable, questionList));
			getSpecificSchool(model, questionList);
			getQuestionContentList(model, questionList);
		} else {
			// 管理员或老师
			List<Question> tempList = new ArrayList<>();
			for (int i = 0; i < questionList.size(); i++) {
				// 公开级别为公开给特定学校或本校公开
				if (questionList.get(i).getPublic_level() == EnumConstants.publiclevelEnum.schoolOpen.getValue()
						|| questionList.get(i).getPublic_level() == EnumConstants.publiclevelEnum.localSchoolOpen
								.getValue()) {
					String[] specific_schoolId = questionList.get(i).getSpecific_school().split(",");
					for (int j = 0; j < specific_schoolId.length; j++) {
						if (specific_schoolId[j].equals(systemUserService.getCurrentUser().getSchoolId())) {
							tempList.add(questionList.get(i));
						}
					}
				} else if (questionList.get(i).getPublic_level() != EnumConstants.publiclevelEnum.localSchoolOpen
						.getValue()
						&& questionList.get(i).getPublic_level() != EnumConstants.publiclevelEnum.schoolOpen
								.getValue()) {
					Question question = questionList.get(i);
					// 若公开级别为完全保密，则只有创建者能观看
					if (question.getPublic_level() == EnumConstants.publiclevelEnum.secrecy.getValue()
							&& question.getCreated_person().equals(systemUserService.getCurrentUser().getName())) {
						tempList.add(questionList.get(i));
					}
					// 若公开级别为完全公开直接添加
					if (question.getPublic_level() == EnumConstants.publiclevelEnum.openAll.getValue()) {
						tempList.add(questionList.get(i));
					}
				}
			}
			model.addAttribute("page", questionService.pageMethod(pageable, tempList));
			getSpecificSchool(model, tempList);
			getQuestionContentList(model, tempList);
		}

	}

	/**
	 * 获取公开给特定学校的学校
	 */
	public void getSpecificSchool(ModelMap model, List<Question> list) {
		List<Question> questionList = list;
		List<School> schoolList = new ArrayList<>();
		List<Integer> numberList = new ArrayList<>();
		for (int i = 0; i < questionList.size(); i++) {
			// 通过题目获取全部的公开给特定学校
			String specificSchool = questionList.get(i).getSpecific_school();
			int publicLevel = questionList.get(i).getPublic_level();
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
	 * 通过题目获取相应的题目内容
	 */
	private void getQuestionContentList(ModelMap model, List<Question> list) {
		List<QuestionContent> questionContentList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			questionContentList
					.add(parserUtils.getPullParserQuestionList(list.get(i).getContent()).getQuestionContent());
		}
		model.addAttribute("questionContentList", questionContentList);
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {
		if (ids != null) {
			for (long id : ids) {
				if (schemeContentTableService.findList("PID", id).size() > 0) {
					return Message.error("该题目还被作业表引用，所以不能删除!");
				} else {
				    if(exerciseService.findList("pId", id).size() > 0){
				        return Message.error("该题目还被批改作业引用，所以不能删除!");
				    }else{
				        questionService.delete(id);
				    }
				}
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
		// 在redis中记录编辑的题目信息
		if (id != null) {
			redisService.save(systemUserService.getCurrentUserName() + "question",
					JSON.toJSONString(questionService.find(id)));
		}
		Question question = JSON.parseObject(redisService.get(systemUserService.getCurrentUserName() + "question"),
				Question.class);
		model.addAttribute("question", question);
		// 通过公开给特定学校的id获取相应学校的名字
		if (question.getSpecific_school() != null && !question.getSpecific_school().equals("")) {
			List<String> schoolNameList = new ArrayList<>();
			String specificschoolId = question.getSpecific_school();
			// 该题目拥有多个公开给特定学校
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
		// 总共有多少个选择的章节
		setChapter(model);
		// 把编辑的题目内容记载到redis中
		if (redisService.get(systemUserService.getCurrentUserName() + "questionContent") == null) {
			redisService.save(systemUserService.getCurrentUserName() + "questionContent", JSON
					.toJSONString(parserUtils.getPullParserQuestionList(question.getContent()).getQuestionContent()));
		}
		// 把编辑的题目内容头文件记载到redis中
		if (redisService.get(systemUserService.getCurrentUserName() + "lists_headerfile") == null) {
			redisService.save(systemUserService.getCurrentUserName() + "lists_headerfile", JSON
					.toJSONString(parserUtils.getPullParserQuestionList(question.getContent()).getHeaderFileList()));
		}

		redisService.save(systemUserService.getCurrentUserName() + "QuestionFlag", "1");

		return "/admin/question/edit";
	}

	/**
	 * 编辑保存
	 */
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSave(Question question, RedirectAttributes redirectAttributes) {
		// 获取当前登录用户
		User user = systemUserService.getCurrentUser();
		// 更新题目
		// 若公开级别不为公开给特定学校，则学校设置为空
		if (question.getSpecific_school() == null) {
			question.setSpecific_school("");
		}
		// 若登录的用户权限是管理员与老师，选择了本校公开，则公开给特别学校那里显示登录用户所属学校

		if ((systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.manager.getValue()
				|| systemUserService.getCurrentUser().getRoleId() == EnumConstants.authorityEnum.teacher.getValue())
				&& question.getPublic_level() == EnumConstants.publiclevelEnum.localSchoolOpen.getValue()) {
			question.setSpecific_school(String.valueOf(systemUserService.getCurrentUser().getSchoolId()));
		}
		question.setUpdate_person(user.getName());
		question.setUpdate_date(new Date());

		if (redisService.get(systemUserService.getCurrentUserName() + "questionContent") != null
				&& redisService.get(systemUserService.getCurrentUserName() + "lists_headerfile") != null) {
			// 从redis中获取相应的题目内容与题目内容头文件
			QuestionContent questionContent = JSON.parseObject(
					redisService.get(systemUserService.getCurrentUserName() + "questionContent"),
					QuestionContent.class);
			List<QuestionHeaderFile> lists_headerfile = idsUtils.qhfToList(
					redisService.get(systemUserService.getCurrentUserName() + "lists_headerfile"),
					QuestionHeaderFile.class);
			// 把题目内容与题目内容头文件打包成xml文件存储到题目中
			question.setContent(parserUtils.createQuestionXml(questionContent, lists_headerfile));
			redisService.delete(systemUserService.getCurrentUserName() + "questionContent");
			redisService.delete(systemUserService.getCurrentUserName() + "lists_headerfile");
		}

		questionService.update(question);

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		return "redirect:list.jhtml";
	}

	/**
	 * 添加时的编辑题目内容
	 */
	@RequestMapping(value = "/editContent", method = RequestMethod.GET)
	public String editContent(Long id, String sumbArr, ModelMap model) {

		if (sumbArr != null) {
			analysisArray(sumbArr);
		}

		// 第一次点击编辑题目内容为空，第二次点击编辑后有内容
		if (redisService.get(systemUserService.getCurrentUserName() + "questionContent") == null) {
			redisService.save(systemUserService.getCurrentUserName() + "questionContent",
					JSON.toJSONString(new QuestionContent()));
		}
		// 第一次点击编辑题目内容头文件为空，第二次点击编辑后有内容
		if (redisService.get(systemUserService.getCurrentUserName() + "lists_headerfile") == null) {
			redisService.save(systemUserService.getCurrentUserName() + "lists_headerfile",
					JSON.toJSONString(new ArrayList<>()));
		}

		if (redisService.get(systemUserService.getCurrentUserName() + "fileContent") != null
				&& redisService.get(systemUserService.getCurrentUserName() + "fileFlag") != null) {
			// 导入单个文件内容对应的标志位与内容
			model.addAttribute("fileFlag", redisService.get(systemUserService.getCurrentUserName() + "fileFlag"));
			model.addAttribute("fileName", redisService.get(systemUserService.getCurrentUserName() + "fileName"));
			model.addAttribute("fileContent", redisService.get(systemUserService.getCurrentUserName() + "fileContent"));
			// 导入单个文件内容头文件对应的标志位与内容
			model.addAttribute("headerCount", redisService.get(systemUserService.getCurrentUserName() + "headerCount"));
			model.addAttribute("countArr", redisService.get(systemUserService.getCurrentUserName() + "countArr"));
			// model.addAttribute("textareaLen", redisService.get(systemUserService.getCurrentUserName() + "textareaLen"));

			// 使用完后，把相应的内容从redis中删除
			redisService.delete(systemUserService.getCurrentUserName() + "fileFlag");
			redisService.delete(systemUserService.getCurrentUserName() + "fileName");
			redisService.delete(systemUserService.getCurrentUserName() + "fileContent");

			redisService.delete(systemUserService.getCurrentUserName() + "headerCount");
			redisService.delete(systemUserService.getCurrentUserName() + "countArr");
			// redisService.delete(systemUserService.getCurrentUserName() + "textareaLen");
		}
		// 把redis中相应的题目内容与题目内容头文件传到前端
		model.addAttribute("questionContent", JSON.parseObject(
				redisService.get(systemUserService.getCurrentUserName() + "questionContent"), QuestionContent.class));
//		if(id != null){
//			if(id != -1){
//				model.addAttribute("lists_headerfile",
//						idsUtils.qhfToList(redisService.get(systemUserService.getCurrentUserName() + "lists_headerfile"),
//								QuestionHeaderFile.class));
//			}
//		}
		model.addAttribute("lists_headerfile",
				idsUtils.qhfToList(redisService.get(systemUserService.getCurrentUserName() + "lists_headerfile"),
						QuestionHeaderFile.class));
		// 编辑题目id
		model.addAttribute("question_id", id);

		return "/admin/question/editContent";
	}

	/**
	 * 编辑时记录临时的question值
	 */
	public void analysisArray(String sumbArr) {

		Question question = null;
		if (redisService.get(systemUserService.getCurrentUserName() + "question") != null) {
			question = JSON.parseObject(redisService.get(systemUserService.getCurrentUserName() + "question"),
					Question.class);
		} else {
			question = new Question();
		}

		String[] array = sumbArr.split(",");
		if (!array[0].equals("")) {
			question.setQuestion_name(array[0]);
		}
		if (!array[2].equals("")) {
			question.setChapter(array[2]);
		}
		if (!array[4].equals("")) {
			question.setPublic_level(Integer.parseInt(array[4]));
		}
		if (!array[6].equals("")) {
			question.setSpecific_school(array[6]);
		}
		if (!array[8].equals("")) {
			question.setDifficulty(Float.parseFloat(array[8]));
		}
		if (!array[10].equals("")) {
			question.setState(array[10]);
		}
		if (!array[12].equals("")) {
			question.setRemark(array[12]);
		}
		redisService.save(systemUserService.getCurrentUserName() + "question", JSON.toJSONString(question));
	}

	/**
	 * 编辑时的编辑题目内容
	 */
	@RequestMapping(value = "/editQuestionContent", method = RequestMethod.GET)
	public String editQuestionContent(Long id, ModelMap model) {
		// 通过id获取相应的编辑题目
		Question question = questionService.find(id);
		// 通过编辑题目解析获取题目内容与题目内容头文件
		QuestionTemp questionTemp = parserUtils.getPullParserQuestionList(question.getContent());
		QuestionContent qContent = questionTemp.getQuestionContent();
		List<QuestionHeaderFile> headerFile_List = questionTemp.getHeaderFileList();
		// 判断redis中记载的题目内容是否有值
		if (redisService.get(systemUserService.getCurrentUserName() + "questionContent") != null) {
			model.addAttribute("questionContent",
					JSON.parseObject(redisService.get(systemUserService.getCurrentUserName() + "questionContent"),
							QuestionContent.class));
		} else {
			// 第一次进入的原始数据
			if (qContent != null) {
				model.addAttribute("questionContent", qContent);
			} else {
				model.addAttribute("questionContent", new QuestionContent());
			}
		}
		// 判断redis中记载的题目内容头文件是否有值
		if (redisService.get(systemUserService.getCurrentUserName() + "lists_headerfile") != null) {
			model.addAttribute("lists_headerfile",
					idsUtils.qhfToList(redisService.get(systemUserService.getCurrentUserName() + "lists_headerfile"),
							QuestionHeaderFile.class));
		} else {
			// 第一次进入的原始数据
			if (headerFile_List != null) {
				model.addAttribute("lists_headerfile", headerFile_List);
			} else {
				model.addAttribute("lists_headerfile", new ArrayList<>());
			}
		}

		redisService.save(systemUserService.getCurrentUserName() + "QuestionFlag", "1");

		return "/admin/question/editContent";
	}

	/**
	 * 编辑题目内容保存(flag=0：添加状态 | flag=1:编辑状态)
	 */
	@RequestMapping(value = "/saveQuestionContent", method = RequestMethod.POST)
	public String saveQuestionContent(QuestionContent questionContent, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		redisService.save(systemUserService.getCurrentUserName() + "questionContent",
				JSON.toJSONString(questionContent));
		redisService.delete(systemUserService.getCurrentUserName() + "lists_headerfile");
		// 把前端回传的题目内容头文件数据解析出来保存到redis
		List<QuestionHeaderFile> lists_headerfile = new ArrayList<>();
//		String[] header_array = request.getParameter("headerFiles").split(",,,");
//		for (int i = 0; i < header_array.length; i = i + 2) {
//			QuestionHeaderFile headerFile = new QuestionHeaderFile();
//			headerFile.setHeader_file(header_array[i]);
//			headerFile.setHeader_file_content(header_array[i + 1]);
//			lists_headerfile.add(headerFile);
//		}
        String[] header_array = request.getParameterValues("header_file_content");
        String[] header_files_array = request.getParameterValues("header_file");
        for (int i = 0; i < header_array.length; i++) {
            QuestionHeaderFile headerFile = new QuestionHeaderFile();
            headerFile.setHeader_file(header_files_array[i]);
            headerFile.setHeader_file_content(header_array[i]);
            lists_headerfile.add(headerFile);
        }
		redisService.save(systemUserService.getCurrentUserName() + "lists_headerfile",
				JSON.toJSONString(lists_headerfile));

		if (redisService.get(systemUserService.getCurrentUserName() + "QuestionFlag") != null
				&& redisService.get(systemUserService.getCurrentUserName() + "QuestionFlag").equals("1")) {
			// 编辑时
			QuestionContent qContent = JSON.parseObject(
					redisService.get(systemUserService.getCurrentUserName() + "questionContent"),
					QuestionContent.class);

			redisService.save(systemUserService.getCurrentUserName() + "questionContent", JSON.toJSONString(qContent));

			redisService.save(systemUserService.getCurrentUserName() + "lists_headerfile",
					JSON.toJSONString(lists_headerfile));
		}
		// 若是编辑状态则跳转到编辑界面
		if (redisService.get(systemUserService.getCurrentUserName() + "QuestionFlag") != null
				&& redisService.get(systemUserService.getCurrentUserName() + "QuestionFlag").equals("1")) {
			return "redirect:edit.jhtml";
		}
		return "redirect:add.jhtml";
	}

	/**
	 * 查看题目内容
	 */
	@RequestMapping(value = "/checkContent", method = RequestMethod.GET)
	public String checkContent(Long id, ModelMap model) {
		// 存储查看题目的id到redis
		if (id != null) {
			redisService.save(systemUserService.getCurrentUserName() + "checkContentId", String.valueOf(id));
		} else {
			id = Long.parseLong(redisService.get(systemUserService.getCurrentUserName() + "checkContentId"));
		}
		// 通过id获取相应的题目
		Question question = questionService.find(id);
		// 通过题目xml文件解析出题目内容与题目内容头文件
		QuestionTemp questionTemp = parserUtils.getPullParserQuestionList(question.getContent());
		QuestionContent questionContent = questionTemp.getQuestionContent();
		List<QuestionHeaderFile> lists_headerfile = questionTemp.getHeaderFileList();

		model.addAttribute("id", id);

		if (questionContent == null && lists_headerfile == null) {
			model.addAttribute("questionContent", new QuestionContent());
			model.addAttribute("lists_headerfile", new ArrayList<>());
		} else {
			model.addAttribute("questionContent", questionContent);
			model.addAttribute("lists_headerfile", lists_headerfile);

		}

		if (redisService.get(systemUserService.getCurrentUserName() + "fileContent") != null
				&& redisService.get(systemUserService.getCurrentUserName() + "fileFlag") != null) {
			// 导入单个文件内容对应的标志位与内容
			model.addAttribute("fileFlag", redisService.get(systemUserService.getCurrentUserName() + "fileFlag"));
			model.addAttribute("fileName", redisService.get(systemUserService.getCurrentUserName() + "fileName"));
			model.addAttribute("fileContent", redisService.get(systemUserService.getCurrentUserName() + "fileContent"));
			// 导入单个文件内容头文件对应的标志位与内容
			model.addAttribute("headerCount", redisService.get(systemUserService.getCurrentUserName() + "headerCount"));
			model.addAttribute("countArr", redisService.get(systemUserService.getCurrentUserName() + "countArr"));
			// model.addAttribute("textareaLen", redisService.get(systemUserService.getCurrentUserName() + "textareaLen"));

			// 使用完后，把相应的内容从redis中删除
			redisService.delete(systemUserService.getCurrentUserName() + "fileFlag");
			redisService.delete(systemUserService.getCurrentUserName() + "fileName");
			redisService.delete(systemUserService.getCurrentUserName() + "fileContent");

			redisService.delete(systemUserService.getCurrentUserName() + "headerCount");
			redisService.delete(systemUserService.getCurrentUserName() + "countArr");
			// redisService.delete(systemUserService.getCurrentUserName() + "textareaLen");

		}

		return "/admin/question/checkContent";
	}

	/**
	 * 修改查看的题目内容
	 */
	@RequestMapping(value = "/saveEditQuestionContent", method = RequestMethod.POST)
	public String saveEditQuestionContent(HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes) {

		redisService.delete(systemUserService.getCurrentUserName() + "checkContentId");

		// 当前登录用户信息
		User user = systemUserService.getCurrentUser();
		// 修改题目内容
		String question_id = request.getParameter("question_id");
		// 题目描述
		String question_description = request.getParameter("question_description");
		// 题目标准答案
		String standard_answer = request.getParameter("standard_answer");
		// 题目学生答案
		String student_answer = request.getParameter("student_answer");
		// 题目主文件名称
		String headerfile_name = request.getParameter("headerfile_name");
		// 题目主文件内容
		String headerfile_content = request.getParameter("headerfile_content");

		QuestionContent questionContent = new QuestionContent();
		questionContent.setQuestion_description(question_description);
		questionContent.setStandard_answer(standard_answer);
		questionContent.setStudent_answer(student_answer);
		questionContent.setHeaderfile_name(headerfile_name);
		questionContent.setHeaderfile_content(headerfile_content);

		// 解析题目内容头文件
//		String headerFiles = request.getParameter("headerFiles");
//		String[] hf = headerFiles.split(",,,");
        String[] hf = request.getParameterValues("header_file_content");
        String[] header_files_array = request.getParameterValues("header_file");
		List<QuestionHeaderFile> tempList = new ArrayList<>();

//		for (int i = 0; i < hf.length; i = i + 2) {
//			QuestionHeaderFile qhf = new QuestionHeaderFile();
//			qhf.setHeader_file(hf[i]);
//			qhf.setHeader_file_content(hf[i + 1]);
//			tempList.add(qhf);
//		}
        for (int i = 0; i < hf.length; i++) {
            QuestionHeaderFile qhf = new QuestionHeaderFile();
            qhf.setHeader_file(header_files_array[i]);
            qhf.setHeader_file_content(hf[i]);
            tempList.add(qhf);
        }

		Question question = questionService.find(Long.parseLong(question_id));
		// 把获取到的题目内容与题目头文件内容打包成xml文件存到题目中
		question.setContent(parserUtils.createQuestionXml(questionContent, tempList));
		question.setUpdate_person(user.getName());
		question.setUpdate_date(new Date());
		questionService.update(question);

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);

		return "redirect:list.jhtml";
	}

	/**
	 * 批量导入
	 */
	@RequestMapping(value = "/importQuestion", method = RequestMethod.GET)
	public String importQuestion(ModelMap model) {

		return "/admin/question/importQuestion";
	}

	/**
	 * 题目批量导入保存
	 */
	@Transactional
	@RequestMapping(value = "/saveimportQuestion", method = RequestMethod.POST)
	public String saveimportQuestion(MultipartFile qfile, ModelMap model, RedirectAttributes redirectAttributes) {
		// 上传文件路径
		String url = fileService.uploadLocal(FileInfo.FileType.file, qfile);
		ImportQuestionContent importqc = null;
		if (url.contains(".zip")) {
			importqc = zipFileRead.readQuestionZip(url, 0);
		} else if (url.contains(".rar")) {
			importqc = zipFileRead.readQuestionRar(url, 0);
		}
		if (importqc != null) {
			// 当前登录用户信息
			User user = systemUserService.getCurrentUser();
			// 题目名称集合
			List<String> questionNameList = importqc.getQuestionNameList();
			// 题目内容集合
			List<QuestionContent> questionContentList = importqc.getQuestionContentList();
			// 题目头文件集合
			List<QuestionHeaderFile> headerFileList = importqc.getHeaderFileList();
			//一个题目包含多少个头文件
			List<Integer> headerNameList = importqc.getHeaderNameList();
			//记录头文件数量
			int allHeaderCount = 0;
			
			// 记录成功导入的题目数量
			int number = 0;
			List<QuestionHeaderFile> tempList = new ArrayList<>();
			if (questionNameList != null) {
				for (int i = 0; i < questionNameList.size(); i++) {
					// 导入题目

					String chapterName = getChapterName(
							qfile.getOriginalFilename().substring(0, qfile.getOriginalFilename().indexOf(".")));
					// 检验该题目名称与题目章节是否存在
					//不存在则添加，存在则修改
					if (chapterName != null && !chapterName.equals("")
							&& checkQuestion("", chapterName, questionNameList.get(i))) {
						number++;
						Question question = new Question();
						question.setQuestion_name(questionNameList.get(i));
						// 题目章节为导入文件的文件名
						question.setChapter(chapterName);
						question.setPublic_level(EnumConstants.publiclevelEnum.secrecy.getValue());
						question.setDifficulty(EnumConstants.difficultyEnum.one.getValue());
						question.setState(EnumConstants.statusEnum.start.getText());
						// 设置题目的题库id为进入该题目界面的题目id
						question.setQuestion_bank_id(Integer.parseInt(
								redisService.get(systemUserService.getCurrentUserName() + "questionbank_id")));
						question.setCreated_person(user.getName());
						question.setCreated_date(new Date());
						question.setUpdate_person(user.getName());
						question.setUpdate_date(new Date());
						question.setEnabled(1);

						QuestionContent qc = questionContentList.get(i);
						int headerCount = headerNameList.get(i);
						for(int j = allHeaderCount; j<allHeaderCount + headerCount; j++){
			                  QuestionHeaderFile qhf = headerFileList.get(j);
			                  
		                      tempList.add(qhf);
						}
						// 把获取的题目内容与题目内容头文件打包成xml文件放进题目
						question.setContent(parserUtils.createQuestionXml(qc, tempList));
						tempList.clear();
						allHeaderCount = allHeaderCount + headerCount;
						questionService.save(question);
					}else{
					    //获取已存在的题目
					    Question temp = new Question();
					    temp.setQuestion_bank_id(
				                Integer.parseInt(redisService.get(systemUserService.getCurrentUserName() + "questionbank_id")));
					    temp.setChapter(chapterName);
					    temp.setQuestion_name(questionNameList.get(i));
				        List<Question> questionList = questionService.findByAttribute(temp);
                        Question question = new Question();
                        question.setQuestion_name(questionNameList.get(i));
                        // 题目章节为导入文件的文件名
                        question.setChapter(chapterName);
                        question.setPublic_level(EnumConstants.publiclevelEnum.secrecy.getValue());
                        question.setDifficulty(EnumConstants.difficultyEnum.one.getValue());
                        question.setState(EnumConstants.statusEnum.start.getText());
                        // 设置题目的题库id为进入该题目界面的题目id
                        question.setQuestion_bank_id(Integer.parseInt(
                                redisService.get(systemUserService.getCurrentUserName() + "questionbank_id")));
                        question.setCreated_person(user.getName());
                        question.setCreated_date(new Date());
                        question.setUpdate_person(user.getName());
                        question.setUpdate_date(new Date());
                        question.setEnabled(1);

                        QuestionContent qc = questionContentList.get(i);
                        int headerCount = headerNameList.get(i);
                        for(int j = allHeaderCount; j<allHeaderCount + headerCount; j++){
                              QuestionHeaderFile qhf = headerFileList.get(j);
                              
                              tempList.add(qhf);
                        }
                        // 把获取的题目内容与题目内容头文件打包成xml文件放进题目
                        question.setContent(parserUtils.createQuestionXml(qc, tempList));
                        tempList.clear();
                        allHeaderCount = allHeaderCount + headerCount;
                        question.setId(questionList.get(0).getId());
                        questionService.update(question);
					}
				}
			}

			// 导入的题目数量
			if (number != 0) {
				redisService.save(systemUserService.getCurrentUserName() + "importQuestionNumber",
						String.valueOf(number));
			} else {
				redisService.save(systemUserService.getCurrentUserName() + "importQuestionNumber", String.valueOf(-1));
			}
		}

		return "redirect:list.jhtml";
	}

	/**
	 * 题目内容导入
	 */
	@RequestMapping(value = "/importQuestionContent", method = RequestMethod.GET)
	public String importQuestionContent(String sumbArr, ModelMap model) {
		if (sumbArr != null) {
			analysisArray(sumbArr);
		}
		return "/admin/question/importQuestionContent";
	}

	/**
	 * 题目内容导入保存
	 */
	@RequestMapping(value = "/saveimportQuestionContent", method = RequestMethod.POST)
	public String saveimportQuestionContent(MultipartFile qcfile, ModelMap model,
			RedirectAttributes redirectAttributes) {
		String url = fileService.uploadLocal(FileInfo.FileType.file, qcfile);
		ImportQuestionContent importqc = null;
		if (url.contains(".zip")) {
			importqc = zipFileRead.readQuestionZip(url, 1);
		} else if (url.contains(".rar")) {
			importqc = zipFileRead.readQuestionRar(url, 1);
		}
		if (importqc != null) {
			redisService.save(systemUserService.getCurrentUserName() + "questionContent",
					JSON.toJSONString(importqc.getQuestionContentList().get(0)));
			redisService.save(systemUserService.getCurrentUserName() + "lists_headerfile",
					JSON.toJSONString(importqc.getHeaderFileList()));
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		}
		return "redirect:add.jhtml";
	}

	/**
	 * 题目内容单个导入
	 */
	@RequestMapping(value = "/importContent", method = RequestMethod.GET)
	public String importContent(String flag, String headerCount, String countArr, ModelMap model) {
		redisService.save(systemUserService.getCurrentUserName() + "fileFlag", flag);
		redisService.save(systemUserService.getCurrentUserName() + "headerCount", headerCount);
		redisService.save(systemUserService.getCurrentUserName() + "countArr", countArr);
		// redisService.save(systemUserService.getCurrentUserName() + "textareaLen", textareaLen);
		return "/admin/question/importContent";
	}

	/**
	 * 题目内容单个导入保存
	 */
	@RequestMapping(value = "/saveimportContent", method = RequestMethod.POST)
	public String saveimportContent(@RequestParam("cfile") MultipartFile[] cfile, HttpServletRequest request,
			ModelMap model) {
		String content = "";
        String fileName = "";
		for (int i = 0; i < cfile.length; i++) {
			// 上传文件路径
			String url = fileService.uploadLocal(FileInfo.FileType.file, cfile[i]);
            String fileFlag = redisService.get(systemUserService.getCurrentUserName() + "fileFlag");
			if (!fileFlag.equals("1") && !fileFlag.equals("2") && !fileFlag.equals("3")) {
                fileName = fileName + cfile[i].getOriginalFilename() + "(youyitech)";
                redisService.save(systemUserService.getCurrentUserName() + "fileName", fileName);
			}
			// 单个文件内容
			content = content + poiReadWord.readText(url);
			if(fileFlag.equals("0")){
				content = content + "(youyitech)";
			}
		}
		redisService.save(systemUserService.getCurrentUserName() + "fileContent", content);
		// 跳转标志位
		String contentFlag = request.getParameter("ContentFlag");

		if (contentFlag.equals("1")) {
			return "redirect:checkContent.jhtml";
		} else {
			return "redirect:editContent.jhtml";
		}

	}

	/**
	 * 初始化数据
	 */
	@ModelAttribute
	public void init(Model model) {
		// 当前登录用户信息
		model.addAttribute("systemUser", systemUserService.getCurrentUser());
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
	 * 设置章节数
	 */
	public void setChapter(ModelMap model) {
		List<Integer> chapterList = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			chapterList.add(i);
		}
		model.addAttribute("chapterList", chapterList);
	}

}
