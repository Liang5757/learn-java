package cn.youyitech.anyview.system.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import cn.youyitech.anyview.system.entity.Major;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.service.CollegeService;
import cn.youyitech.anyview.system.service.MajorService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.support.AdminEnum;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.IdsUtils;

/**
 * @author TT 专业管理 Controller 2017年8月22日
 */
@Controller
@RequestMapping("/admin/major")
public class MajorController extends GenericController {
	@Autowired
	private MajorService majorService;
	@Autowired
	SystemUserService systemUserService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private CollegeService collegeService;

	// 获取专业内容
	@RequestMapping("/MajorContent")
	public String majorContent(Model model, Pageable pageable, HttpServletRequest request) {
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("page", majorService.findByPage(pageable));
		model.addAttribute("params", map);
		return "admin/major/majorList";
	}

	// 添加专业
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "admin/major/addMajor";
	}

	// 保存添加
	@RequestMapping("/saveAddMajor")
	public String insertMajor(Major major, RedirectAttributes redirectAttributes) {
		majorService.save(major);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/major/MajorContent.jhtml";
	}

	// 修改专业
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editMajor(Long id, ModelMap model) {
		Major major = majorService.find(id);
		College college = new College();
		college.setSchoolId(major.getCollege().getSchool().getId());
		college.setId(major.getCollege().getId());
		// 找出该专业所在的学校的所有学院（去除本身所在的学院）
		List<College> collegeList = collegeService.findNotIdList(college);
		model.addAttribute("colleges", collegeList);
		model.addAttribute("major", major);
		List<School> schoolList = schoolService.findNotIdList("id", major.getCollege().getSchool().getId());
		model.addAttribute("schoolLists", schoolList);
		return "/admin/major/updateMajor";
	}

	// 保存修改
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSaveMajor(Major major, RedirectAttributes redirectAttributes) {
		majorService.update(major);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/major/MajorContent.jhtml";
	}

	// 选中删除
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {
		if (ids.length != 0) {
			try {
				majorService.deleteOne(ids);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Message.error(e.getMessage());
			}

		}
		return SUCCESS_MESSAGE;
	}

	// 下拉框联动
	@ResponseBody
	@RequestMapping("/MajorAjax")
	public List<College> ajaxRequestBody(@RequestBody School school, HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		school = schoolService.find((long) school.getId());
		List<College> collegeList = school.getSchoolCollegeList();
		SortUtil.sort(collegeList,true,"collegeName");
		return collegeList;
	}

	// 检查学院是否存在
	@RequestMapping(value = "/check_majorName", method = RequestMethod.GET)
	public @ResponseBody boolean checkMajorName(String majorName, int collegeId, Long id) {
		List<Major> majorList = majorService.majorNameExists(majorName);
		if (StringUtils.isEmpty(majorName)) {
			return false;
		}
		Major major = majorService.find(id);
		if (majorList != null) {
			for (int i = 0; i < majorList.size(); i++) {
				if (major != null) {
					if (majorList.get(i).getId().equals(major.getId())
							&& majorList.get(i).getCollege().getId() == collegeId) {
						return true;
					}
					if (majorList.get(i).getCollege().getId() == collegeId) {
						return false;
					}
				}
				if (major == null && majorList.get(i).getCollege().getId() == collegeId) {
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
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.schoolManager.getValue()) {
			List<College>collegeList=schoolService.find((long) systemUserService.getCurrentUser().getSchoolId()).getSchoolCollegeList();
			SortUtil.sort(collegeList,true,"collegeName");
			model.addAttribute("collegeList", collegeList);
		}
	}

}
