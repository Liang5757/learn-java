package cn.youyitech.anyview.system.controller.admin;

import java.security.interfaces.RSAPublicKey;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.youyitech.anyview.system.service.RSAService;
import cn.youyitech.anyview.system.shiro.ApplicationPrincipal;
import cn.youyitech.anyview.system.support.Message;

/**
 * Controlelr - 登录
 *
 * @version 1.0
 */

@Controller("adminLoginController")
public class LoginController extends GenericController {

	@Resource
	private RSAService rsaService;

	/**
	 * 登录
	 *
	 * @return
	 */
	@RequestMapping(name = "/admin/login", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			ApplicationPrincipal principal = (ApplicationPrincipal) subject.getPrincipal();
			if (principal != null && principal.getId() != null) {
				return "redirect:/admin/common/main.jhtml";
			}
		}
		RSAPublicKey publicKey = rsaService.generateKey(request);
		try {
			String modulus = Base64.encodeBase64String(publicKey.getModulus().toByteArray());
			String exponent = Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray());
			model.addAttribute("modulus", modulus);
			model.addAttribute("exponent", exponent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/admin/login/index";
	}

	@RequestMapping(name = "/admin/login", method = RequestMethod.POST)
	public String submit(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String message = null;
		String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (StringUtils.isNotEmpty(loginFailure)) {
			if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException")) {
				message = "admin.captcha.invalid";
			} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
				message = "admin.login.unknownAccount";
			} else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {
				message = "admin.login.disabledAccount";
			} else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {
				message = "admin.login.lockedAccount";
			} else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {
				message = "admin.login.incorrectCredentials";
			} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
				message = "admin.login.authentication";
			}
			addFlashMessage(redirectAttributes, Message.error(message));

			return "redirect:/admin/login.jhtml";
		}
		return "redirect:/admin/common/main.jhtml";
	}

}
