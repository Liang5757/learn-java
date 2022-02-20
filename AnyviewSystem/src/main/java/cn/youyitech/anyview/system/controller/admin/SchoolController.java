package cn.youyitech.anyview.system.controller.admin;

import java.text.Collator;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import cn.youyitech.anyview.system.utils.SortUtil;
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

import com.alibaba.fastjson.JSONObject;
import com.framework.loippi.support.Pageable;

import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.entity.SystemSchool;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchoolAreaService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.IdsUtils;

/**
 * @author TT 学校管理 Controller 2017年8月22日
 */

@Controller
@RequestMapping("/admin/school")
public class SchoolController extends GenericController {

	@Autowired
	private SchoolService schoolService;

	@Autowired
	SystemUserService systemUserService;

	@Autowired
	private SchoolAreaService schoolAreaService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private IdsUtils idsUtils;

	// 获取学校管理内容
	@RequestMapping("/managerSchoolContent")
	public String managerSchoolContent(Model model, Pageable pageable, HttpServletRequest request) {
		redisService.delete(systemUserService.getCurrentUserName() + "ttsystemSchoolList");
		redisService.delete(systemUserService.getCurrentUserName() + "ttsystemSchool");
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("page", schoolService.findByPage(pageable));
		model.addAttribute("params", map);
		// 学校名排序
		if ("asc".equals(request.getParameter("filter_schoolNameSort"))
				|| "desc".equals(request.getParameter("filter_schoolNameSort"))) {
			model.addAttribute("schoolNameSort", request.getParameter("filter_schoolNameSort"));
		}
		return "admin/school/schoolList";
	}

	// 添加:添加学校
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "admin/school/addSchool";
	}

	// 添加:获取学校表内容
	@RequestMapping("/SchoolContent")
	public String SchoolContent(Model model, Pageable pageable, HttpServletRequest request) {
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("page", schoolAreaService.findByPage(pageable));
		model.addAttribute("params", map);
		List<SystemSchool> systemSchoolList = idsUtils.SystemSchooljsonToList(
				redisService.get(systemUserService.getCurrentUserName() + "ttsystemSchoolList"), SystemSchool.class);
		model.addAttribute("Selected", systemSchoolList);
		// 勾选翻页id
		List<String> recordSystemSchool = idsUtils
				.IdsToList(systemUserService.getCurrentUserName() + "ttsystemSchoolIds");
		model.addAttribute("recordSystemSchool", recordSystemSchool);
		List<School> schoolList = schoolService.findAll();
		// 存在的学校
		List<String> systemSchoolNameList = new ArrayList<>();
		for (int i = 0; i < schoolList.size(); i++) {
			systemSchoolNameList.add(schoolList.get(i).getSchoolName());
		}

		model.addAttribute("systemSchoolNameList", systemSchoolNameList);
		return "admin/school/addSchoolList";
	}

	// 添加:确定选择学校
	@RequestMapping("/sureSelectSchool")
	public String sureSelectSchool(Model model, HttpServletRequest request, Long[] schoolIds) {
		List<SystemSchool> systemSchoolList = new ArrayList<>();
		for (Long id : schoolIds) {
			SystemSchool systemSchool = schoolAreaService.find(id);
			systemSchoolList.add(systemSchool);
		}
		redisService.save(systemUserService.getCurrentUserName() + "ttsystemSchoolIds", schoolIds.toString());
		redisService.save(systemUserService.getCurrentUserName() + "ttsystemSchoolList",
				JSONObject.toJSONString(systemSchoolList));
		model.addAttribute("schoolL", systemSchoolList);
		return "admin/school/addSchool";
	}

	// 添加:确定添加学校
	@RequestMapping("/sureInsert")
	public String sureInsert(School school, RedirectAttributes redirectAttributes) {
		List<SystemSchool> systemSchoolList = idsUtils.SystemSchooljsonToList(
				redisService.get(systemUserService.getCurrentUserName() + "ttsystemSchoolList"), SystemSchool.class);
		for (int i = 0; i < systemSchoolList.size(); i++) {
			school.setSchoolName(systemSchoolList.get(i).getSchoolName());
			school.setDelete(true);
			schoolService.save(school);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/school/managerSchoolContent.jhtml";
	}

	// 修改:
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		School school = schoolService.find(id);
		redisService.save(systemUserService.getCurrentUserName() + "ttschoolId", String.valueOf(school.getId()));
		SystemSchool systemSchool = schoolAreaService.find("schoolName", school.getSchoolName());
		List<SystemSchool> systemSchoolList = new ArrayList<>();
		systemSchoolList.add(systemSchool);
		redisService.save(systemUserService.getCurrentUserName() + "ttsystemSchool",
				JSONObject.toJSONString(systemSchoolList));
		model.addAttribute("school", school);
		model.addAttribute("schoolId", school.getId());
		return "admin/school/updateSchool";
	}

	// 修改: 获取学校表内容
	@RequestMapping("/editSchoolContent")
	public String schoolContent(Model model, Pageable pageable, HttpServletRequest request) {
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("page", schoolAreaService.findByPage(pageable));
		model.addAttribute("params", map);
		List<SystemSchool> systemSchoolList = idsUtils.SystemSchooljsonToList(
				redisService.get(systemUserService.getCurrentUserName() + "ttsystemSchool"), SystemSchool.class);
		model.addAttribute("Selected", systemSchoolList.get(0));
		return "admin/school/updateSchoolList";
	}

	// 修改:确定选择修改学校
	@RequestMapping("/sureEditSchool")
	public String editSchool(String schoolId, Model model) {
		SystemSchool systemSchool = schoolAreaService.find(Long.valueOf(schoolId));
		model.addAttribute("systemSchool", systemSchool);
		// 绑定schoolId
		School school = schoolService
				.find(Long.valueOf(redisService.get(systemUserService.getCurrentUserName() + "ttschoolId")));
		model.addAttribute("schoolId", school.getId());
		return "admin/school/updateSchool";
	}

	// 修改:确定修改学校
	@RequestMapping("/sureEdit")
	public String sureEdit(School school, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		schoolService.update(school);
		redisService.delete(systemUserService.getCurrentUserName() + "ttschoolId");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/school/managerSchoolContent.jhtml";
	}

	// 选中删除
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {
		if (ids.length != 0) {
			try {
				schoolService.deleteOne(ids);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Message.error(e.getMessage());
			}
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}

	}

	// 检查学校是否存在
	@RequestMapping(value = "/check_schoolName", method = RequestMethod.GET)
	public @ResponseBody boolean checkUsername(String schoolName, int id) {
		List<School> schoolList = schoolService.schoolNameExists(schoolName);
		School school = schoolService.find((long) id);
		if (StringUtils.isEmpty(schoolName)) {
			return false;
		}
		if (schoolList != null) {
			for (int i = 0; i < schoolList.size(); i++) {
				if (school != null) {
					if (school.getId() == schoolList.get(i).getId()) {
						return true;
					}
					return false;
				}
			}
		}
		return true;
	}

	// 初始化数据
	@ModelAttribute
	public void init(Model model) {
		List<School> schoolList1 = schoolService.findAll();
		model.addAttribute("schoolList", schoolList1);
		model.addAttribute("systemUser", systemUserService.getCurrentUser());
		SystemSchool sc = new SystemSchool();
		List<SystemSchool> schoolList = new ArrayList<>();
		schoolList = schoolAreaService.getSchoolList(sc);
		List<String> temp = new ArrayList<>();
		for (int k = 0; k < schoolList.size(); k++) {
			temp.add(schoolList.get(k).getSchoolAddress());
		}
		for (int i = 0; i < temp.size() - 1; i++) {
			for (int j = temp.size() - 1; j > i; j--) {
				if (temp.get(j).equals(temp.get(i))) {
					temp.remove(j);
				}
			}
		}
		Collections.sort(temp, Collator.getInstance(Locale.CHINA));
		model.addAttribute("schoolAddress", temp);
	}
}
