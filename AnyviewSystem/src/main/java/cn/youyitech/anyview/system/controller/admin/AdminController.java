package cn.youyitech.anyview.system.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import cn.youyitech.anyview.system.utils.SortUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.loippi.support.Pageable;

import cn.youyitech.anyview.system.dto.SchoolAndId;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.entity.SystemUser;
import cn.youyitech.anyview.system.service.SchoolService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.support.Message;

/**
 * @author TT 管理员管理 Controller 2017年8月22日
 */
@Controller
@SessionAttributes("principal")
@RequestMapping("/admin")
public class AdminController extends GenericController {

	@Autowired
	private SchoolService schoolService;

	@Autowired
	SystemUserService systemUserService;

	@Value("${system.init_password}")
	private String init_password;

	// 获取管理员内容
	@RequestMapping("/managerContent")
	public String managerContent(Model model, Pageable pageable, HttpServletRequest request) {
		processQueryConditions(pageable, request);
		Map map = (Map) pageable.getParameter();
		pageable.setParameter(map);
		model.addAttribute("page", systemUserService.findByPage(pageable));
		model.addAttribute("params", map);
		return "admin/admin/adminList";
	}

	// 添加管理员
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, SystemUser systemUser) {
		return "admin/admin/addAdmin";
	}

	// 确认添加
	@RequestMapping("/saveManager")
	public String insertManager(SystemUser systemUser, RedirectAttributes redirectAttributes) {
		try {
			systemUserService.saveOne(systemUser);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addFlashMessage(redirectAttributes, Message.error("" + e));
		}
		addFlashMessage(redirectAttributes, Message.success("添加管理员成功，用户名和密码已经发送至管理员邮箱，请注意查收"));
		return "redirect:/admin/managerContent.jhtml";
	}

	// 选中删除
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	public @ResponseBody Message delete(Long[] ids) {
		if (ids.length > 0) {
			for (Long id : ids) {
				systemUserService.delete(id);
			}
			return SUCCESS_MESSAGE;
		} else {
			return ERROR_MESSAGE;
		}
	}

	// 修改管理员
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		SystemUser systemUser = systemUserService.find(id);
		// 查找不是该管理员所在的所有学校
		List<School> schoolList = schoolService.findNotIdList("id", systemUser.getSchool().getId());
		model.addAttribute("schoolLists", schoolList);
		model.addAttribute("systemUser", systemUser);
		return "admin/admin/updateAdmin";
	}

	// 保存修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(SystemUser systemUser, RedirectAttributes redirectAttributes) {
		systemUserService.update(systemUser);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/managerContent.jhtml";
	}

	// 验证
	@RequestMapping(value = "/check_adminName", method = RequestMethod.GET)
	public @ResponseBody boolean check_adminName(String username, int schoolId, Long id) {
		List<SystemUser> systemUserList = systemUserService.usernameExists(username);
		SystemUser systemUser = systemUserService.find(id);
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (systemUserList != null) {
			for (int i = 0; i < systemUserList.size(); i++) {
				if (systemUser != null) {
					if (systemUser.getId().equals(systemUserList.get(i).getId())
							&& systemUserList.get(i).getSchool().getId() == schoolId) {
						return true;
					}
					if (systemUserList.get(i).getSchool().getId() == schoolId) {
						return false;
					}
				}
				if (systemUser == null && systemUserList.get(i).getSchool().getId() == schoolId) {
					return false;
				}
			}
			return true;
		}

		return false;

	}

	// 初始化密码
	@RequestMapping("/resetPassword")
	public String resetPassword(Long[] ids, RedirectAttributes redirectAttributes) {
		SystemUser systemUser;
		if (ids.length != 0) {
			for (Long id : ids) {
				systemUser = systemUserService.find(id);
				try {
					systemUserService.updateResetPassword(systemUser);
				} catch (MessagingException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					addFlashMessage(redirectAttributes, Message.error("" + e));
				}
			}
			addFlashMessage(redirectAttributes, Message.success("初始化密码成功，新密码为123456，并已经发送至管理员邮箱，请注意查收"));
		} else {
			addFlashMessage(redirectAttributes, ERROR_MESSAGE);
		}
		return "redirect:/admin/managerContent.jhtml";
	}

	// 初始化数据
	@ModelAttribute
	public void init(Model model) {
		List<School> schoolList = schoolService.findAll();
		SortUtil.sort(schoolList,true,"schoolName");
		model.addAttribute("schoolList", schoolList);
		model.addAttribute("BysystemUser", systemUserService.getCurrentUser());
	}

	// 联想输入学校名
	@ResponseBody
	@RequestMapping(value = { "/AdminSchool" }, method = { RequestMethod.GET })
	public List<SchoolAndId> recordAdmin(String term) {
		School school = new School();
		school.setSchoolName(term);
		List<School> schoolList = schoolService.findListByName(school);
		SortUtil.sort(schoolList,true,"schoolName");

		List<SchoolAndId> IdAndNameList = new ArrayList<>();
		for (int i = 0; i < schoolList.size(); i++) {
			SchoolAndId schoolAndId = new SchoolAndId();
			schoolAndId.setId(schoolList.get(i).getId());
			schoolAndId.setSchoolName(schoolList.get(i).getSchoolName());
			IdAndNameList.add(schoolAndId);
		}
		return IdAndNameList;
	}

}
