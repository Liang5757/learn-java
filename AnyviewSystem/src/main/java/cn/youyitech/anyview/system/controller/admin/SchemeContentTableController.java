package cn.youyitech.anyview.system.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.loippi.support.Pageable;

import cn.youyitech.anyview.system.dto.QuestionBankIdAndName;
import cn.youyitech.anyview.system.dto.Questions;
import cn.youyitech.anyview.system.dto.SettingTime;
import cn.youyitech.anyview.system.dto.TableIdAndName;
import cn.youyitech.anyview.system.dto.WorkIdAndQuestionBankId;
import cn.youyitech.anyview.system.entity.Question;
import cn.youyitech.anyview.system.entity.QuestionBank;
import cn.youyitech.anyview.system.entity.QuestionContent;
import cn.youyitech.anyview.system.entity.SchemeContentTable;
import cn.youyitech.anyview.system.entity.WorkingTable;
import cn.youyitech.anyview.system.service.QuestionBankService;
import cn.youyitech.anyview.system.service.QuestionService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchemeContentTableService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.WorkingTableService;
import cn.youyitech.anyview.system.support.EnumConstants;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.support.WorktableEnum;
import cn.youyitech.anyview.system.utils.PullParserXmlUtils;

/**
 * @author TT 作业表内容 Controller 2017年10月19日
 */
@Controller
@RequestMapping("/admin/schemeContentTable")
public class SchemeContentTableController extends GenericController {

	@Autowired
	private SchemeContentTableService schemeContentTableService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private RedisService redisService;

	@Resource
	private PullParserXmlUtils parserUtils;

	@Autowired
	private WorkingTableService workingTableService;

	@Autowired
	private QuestionBankService questionBankService;

	@Autowired
	private SystemUserService systemUserService;

	// 获取题目内容
	@RequestMapping("/schemeContent")
	public String schemeContent(Model model, Pageable pageable, HttpServletRequest request) {
		SchemeContentTable schemeContentTable = new SchemeContentTable();
		if (request.getParameter("filter_vid") != null) {
			String VID = request.getParameter("filter_vid");
			redisService.save(systemUserService.getCurrentUserName() + "VID", VID);
		}
		schemeContentTable.setVID(Integer.valueOf(redisService.get(systemUserService.getCurrentUserName() + "VID")));
		if (request.getParameter("filter_VPName") != null) {
			String filter_VPName = request.getParameter("filter_VPName");
			schemeContentTable.setVPName(filter_VPName);
		}
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		// 难度排序标志位difficulty_flag
		String difficulty_flag = request.getParameter("difficulty_flag");
		// 章节排序标志位VChapName_flag
		String VChapName_flag = request.getParameter("VChapName_flag");
		if ("asc".equals(difficulty_flag) || "desc".equals(difficulty_flag)) {
			schemeContentTable.setDifficultySort(difficulty_flag);
			model.addAttribute("difficulty_flag", difficulty_flag);
		} else {
			if ("asc".equals(VChapName_flag) || "desc".equals(VChapName_flag)) {
				schemeContentTable.setVChapNameSort(VChapName_flag);
				model.addAttribute("VChapName_flag", VChapName_flag);
			} else {
				schemeContentTable.setVChapNameSort("asc");
				model.addAttribute("VChapName_flag", "asc");
			}
		}
		List<SchemeContentTable> schemeContentTableList = schemeContentTableService.findContent(schemeContentTable);
		model.addAttribute("page", schemeContentTableList);

		List<Question> questionList = new ArrayList<>();
		// 绑定题目描述
		for (int i = 0; i < schemeContentTableList.size(); i++) {
			questionList.add(schemeContentTableList.get(i).getQuestion());
		}
		getQuestionContentList(model, questionList);
		model.addAttribute("params", map);
		model.addAttribute("VID", redisService.get(systemUserService.getCurrentUserName() + "VID"));
		return "admin/schemeContentTable/schemeContentList";
	}

	// Question的题目描述
	private void getQuestionContentList(Model model, List<Question> list) {
		List<QuestionContent> questionContentList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			questionContentList
					.add(parserUtils.getPullParserQuestionList(list.get(i).getContent()).getQuestionContent());
		}
		model.addAttribute("questionContentList", questionContentList);
	}

	// dto的Questions的题目描述
	private void getQuestionContent(Model model, List<Questions> list) {
		List<QuestionContent> questionContentList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			questionContentList
					.add(parserUtils.getPullParserQuestionList(list.get(i).getContent()).getQuestionContent());
		}
		model.addAttribute("questionContentList", questionContentList);
	}

	// 添加题目
	@RequestMapping(value = "/add")
	public String add(Model model, Pageable pageable, HttpServletRequest request,
			WorkIdAndQuestionBankId workIdAndQuestionBankId) {
		// 作业表存在的题目 schemeContentTableListOne
		List<SchemeContentTable> schemeContentTableListOne = schemeContentTableService.findList("VID",
				redisService.get(systemUserService.getCurrentUserName() + "VID"));
		List<Questions> questionsList = new ArrayList<>();
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("params", map);
		// 找到作业表对应的课程的所有作业表
		List<WorkingTable> workingTableList = getWorkingTableList();
		// 找到作业表对应的课程的所有作业表题目
		List<SchemeContentTable> schemeContentTableList = new ArrayList<>();
		for (int i = 0; i < workingTableList.size(); i++) {
			List<SchemeContentTable> schemeContentTableListNew = schemeContentTableService.findList("VID",
					workingTableList.get(i).getId());
			for (int j = 0; j < schemeContentTableListNew.size(); j++) {
				schemeContentTableList.add(schemeContentTableListNew.get(j));
			}
			// 选择题库不选作业表时
			if (workIdAndQuestionBankId.getQuestionBankIds() != null
					&& workIdAndQuestionBankId.getWorkingIds() == null) {
				schemeContentTableList = new ArrayList<>();
			}
		}
		// 按作业表筛选绑定作业表名
		if (workIdAndQuestionBankId.getWorkingIds() != null) {
			WorkingTable workingTable = new WorkingTable();
			workingTable.setTableIdList(workIdAndQuestionBankId.getWorkingIds());
			// 满足查询条件的作业表
			List<WorkingTable> workingList = workingTableService.findContentList(workingTable);
			model.addAttribute("tt_filter_VID", workingList);
			List<SchemeContentTable> schemeContentList = new ArrayList<>();
			for (int i = 0; i < workingList.size(); i++) {
				if (!workingList.get(i).getId()
						.equals(Long.valueOf(redisService.get(systemUserService.getCurrentUserName() + "VID")))) {
					List<SchemeContentTable> schemeContentTableListNew = schemeContentTableService.findList("VID",
							workingList.get(i).getId());
					for (int j = 0; j < schemeContentTableListNew.size(); j++) {
						for (int k = 0; k < schemeContentTableList.size(); k++) {
							if (schemeContentTableListNew.get(j).getID()
									.equals(schemeContentTableList.get(k).getID())) {
								schemeContentList.add(schemeContentTableListNew.get(j));
							}
						}
					}
				}
			}
			schemeContentTableList = schemeContentList;
		}
		model.addAttribute("workingTableList", workingTableList);
		// 去除作业表存在的题目
		List<SchemeContentTable> schemeContentTableListNew = new ArrayList<>();
		for (int i = 0; i < schemeContentTableList.size(); i++) {
			boolean reg = true;
			for (int j = 0; j < schemeContentTableListOne.size(); j++) {
				if (schemeContentTableList.get(i).getQuestion().getId() == schemeContentTableListOne.get(j)
						.getQuestion().getId()) {
					reg = false;
					break;
				}
			}
			if (reg) {
				schemeContentTableListNew.add(schemeContentTableList.get(i));
			}
		}
		schemeContentTableList = schemeContentTableListNew;
		for (int i = 0; i < schemeContentTableList.size(); i++) {
			// 过滤掉停用的题目
			if (!EnumConstants.statusEnum.stop.getText()
					.equals(schemeContentTableList.get(i).getQuestion().getState())) {
				Questions questions = new Questions();
				questions.setId(schemeContentTableList.get(i).getQuestion().getId());
				questions.setQuestion_name(schemeContentTableList.get(i).getQuestion().getQuestion_name());
				questions.setChapter(schemeContentTableList.get(i).getQuestion().getChapter());
				questions.setContent(schemeContentTableList.get(i).getQuestion().getContent());
				questions.setDifficulty(schemeContentTableList.get(i).getQuestion().getDifficulty());
				questions.setQuestionBank(schemeContentTableList.get(i).getQuestion().getQuestionBank());
				questions.setWorkingTableName(schemeContentTableList.get(i).getWorkingTable().getTableName());
				questionsList.add(questions);
			}
		}
		// 作业表的题目id questionsIdList
		List<Long> questionsIdList = new ArrayList<>();
		List<SchemeContentTable> schemeContentTablePidList = schemeContentTableService.findList("VID",
				redisService.get(systemUserService.getCurrentUserName() + "VID"));
		for (int i = 0; i < schemeContentTablePidList.size(); i++) {
			questionsIdList.add((long) schemeContentTablePidList.get(i).getPID());
		}
		// 从题库中添加
		QuestionBank questionBank = new QuestionBank();
		List<Question> questionList = new ArrayList<>();
		// 获取作业表对应的课程的所有题库
		List<QuestionBank> questionBankList = getQuestionBankList();
		if (questionBankList != null) {
			for (int i = 0; i < questionBankList.size(); i++) {
				List<Question> questionLists = questionService.findList("question_bank_id",
						questionBankList.get(i).getId());
				for (int j = 0; j < questionLists.size(); j++) {
					questionList.add(questionLists.get(j));
				}
			}
			// 选择作业表不选题库时
			if (workIdAndQuestionBankId.getQuestionBankIds() == null
					&& workIdAndQuestionBankId.getWorkingIds() != null) {
				questionList = new ArrayList<>();
			}
		}
		// 按题库筛选绑定题库名
		if (workIdAndQuestionBankId.getQuestionBankIds() != null) {
			// 满足查询条件的题库
			List<QuestionBank> questionBankListNew = new ArrayList<>();
			for (int i = 0; i < workIdAndQuestionBankId.getQuestionBankIds().size(); i++) {
				questionBank = questionBankService.find(workIdAndQuestionBankId.getQuestionBankIds().get(i));
				questionBankListNew.add(questionBank);
			}
			// 符合条件的题库名称
			model.addAttribute("checkquestionBank", questionBankListNew);
			List<Question> questionListNew = new ArrayList<>();
			for (int i = 0; i < questionList.size(); i++) {
				for (int j = 0; j < questionBankListNew.size(); j++) {
					if (questionList.get(i).getQuestion_bank_id() == questionBankListNew.get(j).getId()) {
						questionListNew.add(questionList.get(i));
					}
				}
			}
			questionList = questionListNew;
		}
		// 取并集

		List<Question> questionListNew = new ArrayList<>();
		for (int i = 0; i < questionList.size(); i++) {
			boolean reg = true;
			for (int j = 0; j < schemeContentTableList.size(); j++) {
				if (questionList.get(i).getId() == schemeContentTableList.get(j).getQuestion().getId()) {
					reg = false;
					break;
				}
			}
			// 去除作业表存在的题目
			for (int j = 0; j < schemeContentTableListOne.size(); j++) {
				if (questionList.get(i).getId() == schemeContentTableListOne.get(j).getQuestion().getId()) {
					reg = false;
					break;
				}
			}
			if (reg) {
				questionListNew.add(questionList.get(i));
			}
		}
		questionList = questionListNew;

		for (int i = 0; i < questionList.size(); i++) {
			if (!EnumConstants.statusEnum.stop.getText().equals(questionList.get(i).getState())) {
				Questions questions = new Questions();
				questions.setId(questionList.get(i).getId());
				questions.setQuestion_name(questionList.get(i).getQuestion_name());
				questions.setChapter(questionList.get(i).getChapter());
				questions.setContent(questionList.get(i).getContent());
				questions.setDifficulty(questionList.get(i).getDifficulty());
				questions.setQuestionBank(questionList.get(i).getQuestionBank());
				questionsList.add(questions);
			}
		}
		// 难度正序
		if ("asc".equals(request.getParameter("difficulty_flag"))) {
			Collections.sort(questionsList, new Comparator<Questions>() {
				public int compare(Questions questionsOld, Questions questionsNew) {
					if (questionsOld.getDifficulty() > questionsNew.getDifficulty()) {
						return 1;
					}
					if (questionsOld.getDifficulty() == questionsNew.getDifficulty()) {
						return 0;
					}
					return -1;
				}
			});
			model.addAttribute("difficulty_flag", "asc");
		}
		// 难度降序
		if ("desc".equals(request.getParameter("difficulty_flag"))) {
			Collections.sort(questionsList, new Comparator<Questions>() {
				public int compare(Questions questionsOld, Questions questionsNew) {
					if (questionsOld.getDifficulty() < questionsNew.getDifficulty()) {
						return 1;
					}
					if (questionsOld.getDifficulty() == questionsNew.getDifficulty()) {
						return 0;
					}
					return -1;
				}
			});
			model.addAttribute("difficulty_flag", "desc");
		}
		// 章节正序
		if ("asc".equals(request.getParameter("VChapName_flag")) || "".equals(request.getParameter("VChapName_flag"))) {
			Collections.sort(questionsList, new Comparator<Questions>() {
				public int compare(Questions questionsOld, Questions questionsNew) {
					String chapterOld = questionsOld.getChapter().substring(1, questionsOld.getChapter().length() - 1);
					String chapterNew = questionsNew.getChapter().substring(1, questionsNew.getChapter().length() - 1);
					if (Integer.valueOf(chapterOld) > Integer.valueOf(chapterNew)) {
						return 1;
					}
					if (Integer.valueOf(chapterOld) == Integer.valueOf(chapterNew)) {
						return 0;
					}
					return -1;
				}
			});
			model.addAttribute("VChapName_flag", "asc");
		}
		// 章节降序
		if ("desc".equals(request.getParameter("VChapName_flag"))) {
			Collections.sort(questionsList, new Comparator<Questions>() {
				public int compare(Questions questionsOld, Questions questionsNew) {
					String chapterOld = questionsOld.getChapter().substring(1, questionsOld.getChapter().length() - 1);
					String chapterNew = questionsNew.getChapter().substring(1, questionsNew.getChapter().length() - 1);
					if (Integer.valueOf(chapterOld) < Integer.valueOf(chapterNew)) {
						return 1;
					}
					if (Integer.valueOf(chapterOld) == Integer.valueOf(chapterNew)) {
						return 0;
					}
					return -1;
				}
			});
			model.addAttribute("VChapName_flag", "desc");
		}
		model.addAttribute("questionsList", questionsList);
		getQuestionContent(model, questionsList);
		model.addAttribute("ttquestionBankList", questionBankList);
		return "admin/schemeContentTable/add";
	}

	// 保存添加
	@RequestMapping(value = { "/saveAdd" }, method = { RequestMethod.GET })
	public String insert(String ids, RedirectAttributes redirectAttributes) {
		SchemeContentTable schemeContentTable = new SchemeContentTable();
		schemeContentTable.setVID(Integer.valueOf(redisService.get(systemUserService.getCurrentUserName() + "VID")));
		// 保存题库中添加的题目
		schemeContentTableService.insert(schemeContentTable, ids);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/schemeContentTable/schemeContent.jhtml?filter_vid="
				+ redisService.get(systemUserService.getCurrentUserName() + "VID");
	}

	// 选中删除
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {
		if (ids != null) {
			try {
				schemeContentTableService.deleteOne(ids);
			} catch (Exception e) {
				return Message.error("异常:" + e);
			}
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}
	}

	// 统一设置题目的开始/结束时间
	@RequestMapping(value = { "/settingTime" })
	public String settingTime(SettingTime setting, RedirectAttributes redirectAttributes) {
		if (setting.getIdsList() != null) {
			for (Long id : setting.getIdsList()) {
				SchemeContentTable schemeContentTable = schemeContentTableService.find(id);
				schemeContentTable.setStartTime(setting.getStartTime());
				schemeContentTable.setFinishTime(setting.getStopTime());
				schemeContentTableService.update(schemeContentTable);
			}
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
			return "redirect:/admin/schemeContentTable/schemeContent.jhtml?filter_vid="
					+ redisService.get(systemUserService.getCurrentUserName() + "VID");
		} else {
			addFlashMessage(redirectAttributes, ERROR_MESSAGE);
			return "redirect:/admin/schemeContentTable/schemeContent.jhtml?filter_vid="
					+ redisService.get(systemUserService.getCurrentUserName() + "VID");
		}
	}

	// 联想输入作业表
	@ResponseBody
	@RequestMapping(value = { "/tableNames" }, method = { RequestMethod.GET })
	public List<TableIdAndName> tableNames(String term) throws IOException {
		WorkingTable workingTable = new WorkingTable();
		workingTable.setTableName(term);
		List<WorkingTable> workingTableList = workingTableService.findListByName(workingTable);
		List<WorkingTable> workingTableOldList = getWorkingTableList();
		List<WorkingTable> workingTableNewList = new ArrayList<>();
		for (int i = 0; i < workingTableList.size(); i++) {
			for (int j = 0; j < workingTableOldList.size(); j++) {
				if (workingTableList.get(i).getId() == workingTableOldList.get(j).getId()) {
					workingTableNewList.add(workingTableList.get(i));
				}
			}
		}
		workingTableList = workingTableNewList;
		List<TableIdAndName> IdAndNameList = new ArrayList<>();
		for (int i = 0; i < workingTableList.size(); i++) {
			TableIdAndName nameAndId = new TableIdAndName();
			nameAndId.setTableId(workingTableList.get(i).getId());
			nameAndId.setTableName(workingTableList.get(i).getTableName());
			IdAndNameList.add(nameAndId);
		}
		return IdAndNameList;
	}

	// 联想输入题库
	@ResponseBody
	@RequestMapping(value = { "/itemNames" }, method = { RequestMethod.GET })
	public List<QuestionBankIdAndName> itemNames(String term) throws IOException {
		QuestionBank questionBank = new QuestionBank();
		questionBank.setQuestion_bank(term);
		List<QuestionBank> questionBankList = questionBankService.findListByName(questionBank);
		List<QuestionBank> questionBankOldList = getQuestionBankList();
		System.out.println("++++"+questionBankOldList);
		List<QuestionBank> questionBankNewList = new ArrayList<>();
		for (int i = 0; i < questionBankList.size(); i++) {
			for (int j = 0; j < questionBankOldList.size(); j++) {
				if (questionBankList.get(i).getId() == questionBankOldList.get(j).getId()) {
					questionBankNewList.add(questionBankList.get(i));
				}
			}
		}
		questionBankList = questionBankNewList;
		List<QuestionBankIdAndName> IdAndNameList = new ArrayList<>();
		for (int i = 0; i < questionBankList.size(); i++) {
			QuestionBankIdAndName nameAndId = new QuestionBankIdAndName();
			nameAndId.setQuestionBankId(questionBankList.get(i).getId());
			nameAndId.setQuestionBankName(questionBankList.get(i).getQuestion_bank());
			IdAndNameList.add(nameAndId);
		}
		return IdAndNameList;
	}

	// 获取与指定作业表相同课程的所有作业表
	public List<WorkingTable> getWorkingTableList() {
		WorkingTable workingTable = workingTableService
				.find(Long.valueOf(redisService.get(systemUserService.getCurrentUserName() + "VID")));
		List<WorkingTable> workingTableList = new ArrayList<>();
		List<WorkingTable> workingTableListNew = workingTableService.findAll();
		for (int i = 0; i < workingTableListNew.size(); i++) {
			// 去除停用的作业表
			if (workingTableListNew.get(i).getTableStatus() == WorktableEnum.statusEnum.start.getValue()) {
				// 只找课程相同的作业表
				if (workingTableListNew.get(i).getCourse().getCourseName()
						.equals(workingTable.getCourse().getCourseName())) {
					if (workingTableListNew.get(i).getId()
							.equals(Long.valueOf(redisService.get(systemUserService.getCurrentUserName() + "VID")))) {
						continue;
					}
					workingTableList.add(workingTableListNew.get(i));
				}
			}
		}
		return workingTableList;
	}

	// 获取与指定作业表相同课程的所有题库
	public List<QuestionBank> getQuestionBankList() {
		WorkingTable workingTable = workingTableService
				.find(Long.valueOf(redisService.get(systemUserService.getCurrentUserName() + "VID")));
		List<QuestionBank> questionBankList = questionBankService.findAll();
		List<QuestionBank> questionBankNewList = new ArrayList<>();
		for (int i = 0; i < questionBankList.size(); i++) {
			if (questionBankList.get(i).getCourse_name().equals(workingTable.getCourse().getCourseName())) {
				questionBankNewList.add(questionBankList.get(i));
			}

		}
		return questionBankNewList;

		// if (questionBankNewList.size() != 0) {
		// 	return questionBankNewList;
		// } else {
		// 	return null;
		// }
	}

	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String back(Model model) {
		return "redirect:/admin/schemeContentTable/schemeContent.jhtml?filter_vid="
				+ redisService.get(systemUserService.getCurrentUserName() + "VID");
	}

}
