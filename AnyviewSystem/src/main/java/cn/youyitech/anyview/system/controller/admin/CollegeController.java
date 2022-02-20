package cn.youyitech.anyview.system.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.service.CollegeService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.support.Message;

/**
 * @author TT 学院管理 Controller 2017年10月19日
 */
@Controller
@RequestMapping("/admin/college")
public class CollegeController extends GenericController {

	@Autowired
	CollegeService collegeService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private RedisService redisService;

	/*
	 * @Autowired private IdsUtils idsUtils;
	 */

	@Autowired
	private SystemUserService systemUserService;

	// 获取学院内容
	@RequestMapping("/CollegeContent")
	public String managerCollegeContent(Model model, Pageable pageable, HttpServletRequest request) {
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("page", collegeService.findByPage(pageable));
		model.addAttribute("params", map);
		return "admin/college/collegeList";
	}

	// 添加学院
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "admin/college/addCollege";
	}

	// 保存添加
	@RequestMapping("/saveCollege")
	public String insetCollege(College college, RedirectAttributes redirectAttributes) {
		collegeService.save(college);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/college/CollegeContent.jhtml";
	}

	// 选中删除
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {
		if (ids.length > 0) {
			try {
				collegeService.deleteOne(ids);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Message.error(e.getMessage());
			}
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}

	}

	// 修改学院
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		College college = collegeService.find(id);
		List<School> schoolList = schoolService.findNotIdList("id", college.getSchool().getId());
		System.out.println(schoolList);
		model.addAttribute("schoolLists", schoolList);
		model.addAttribute("college", college);
		return "admin/college/updateCollege";
	}

	// 保存修改
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSaveCollege(College college, RedirectAttributes redirectAttributes) {
		collegeService.update(college);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/college/CollegeContent.jhtml";
	}

	// 校验学院名称
	@RequestMapping(value = "/check_collegeName", method = RequestMethod.GET)
	public @ResponseBody boolean checkCollegename(String collegeName, int schoolId, Long id) {
		List<College> collegeList = collegeService.collegeNameExists(collegeName);
		College college = collegeService.find(id);
		if (StringUtils.isEmpty(collegeName)) {
			return false;
		}
		if (collegeList != null) {
			for (int i = 0; i < collegeList.size(); i++) {
				if (college != null) {
					if (college.getId() == collegeList.get(i).getId()
							&& collegeList.get(i).getSchool().getId() == schoolId) {
						return true;
					}
					if (collegeList.get(i).getSchool().getId() == schoolId) {
						return false;
					}
				}
				if (college == null && collegeList.get(i).getSchool().getId() == schoolId) {
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
		List<School> schoolList = schoolService.findAll();

		model.addAttribute("schoolList", schoolList);
		model.addAttribute("systemUser", systemUserService.getCurrentUser());
	}
}
