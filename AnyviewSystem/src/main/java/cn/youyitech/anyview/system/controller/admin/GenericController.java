//
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//                佛祖保佑                  永不宕机
//                心外无法                  法外无心
package cn.youyitech.anyview.system.controller.admin;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.loippi.support.Pageable;
import com.framework.loippi.utils.ParameterUtils;
import com.framework.loippi.utils.web.SpringUtils;

import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.support.AdminEnum;
import cn.youyitech.anyview.system.support.DateEditor;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.template.directive.FlashMessageDirective;

/**
 * Controlelr - GENERIC
 * 
 * @version 1.0
 */
@Controller
public class GenericController {
	/** 错误视图 */
	protected static final String ERROR_VIEW = "/admin/common/error";

	/** 错误消息 */
	protected static final Message ERROR_MESSAGE = Message.error("admin.message.error");

	/** 成功消息 */
	protected static final Message SUCCESS_MESSAGE = Message.success("admin.message.success");

	@Autowired
	private RedisService redisService;

	/**
	 * 获取国际化消息
	 * 
	 * @param code
	 *            代码
	 * @param args
	 *            参数
	 * @return 国际化消息
	 */

	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private TeacherService teacherService;

	protected String message(String code, Object... args) {
		return SpringUtils.getMessage(code, args);
	}

	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
	}

	/**
	 * 添加瞬时消息
	 * 
	 * @param redirectAttributes
	 *            RedirectAttributes
	 * @param message
	 *            消息
	 */
	protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}

	protected void processQueryConditions(Pageable pageable, HttpServletRequest request) {
		Map<String, Object> paramter = ParameterUtils.getParametersMapStartingWith(request, "filter_");
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.schoolManager.getValue()) {
			paramter.put("schoolId", systemUserService.getCurrentUser().getSchoolId());
			paramter.put("schoolName", systemUserService.getCurrentUser().getSchool().getSchoolName());
		}
		if (systemUserService.getCurrentUser().getRoleId() == AdminEnum.authorityEnum.teacher.getValue()) {
			paramter.put("schoolId", systemUserService.getCurrentUser().getSchoolId());
			Teacher teacher = teacherService.find("username", systemUserService.getCurrentUserName());
			paramter.put("teacherDuan", teacher.getId());
			paramter.put("teacherId", teacher.getId());
		}
		if (request.getParameter("filter_vid") != null) {
			paramter.put("VID", Integer.valueOf(request.getParameter("filter_vid")));
		}
		pageable.setParameter(paramter);
	}
}
