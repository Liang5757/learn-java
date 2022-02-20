package cn.youyitech.anyview.system.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.youyitech.anyview.system.constants.AppConstants;
import cn.youyitech.anyview.system.dto.user.LoginUser;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.utils.ApplicationStringUtil;
import cn.youyitech.anyview.system.utils.ReturnJson;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisService redisService;

	// 不需要登录的接口控制
	private Set<Pattern> freeset = new HashSet<>();

	// session参数名字
	private String sessionName = "sessionId";

	{
		freeset.add(Pattern.compile("/api/app/auth"));
		freeset.add(Pattern.compile("api/article"));
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String ip = request.getRemoteAddr();

		// 先检查是否为不需要签名的界面
		for (Pattern pattern : freeset) {
			if (pattern.matcher(uri).find()) {
				return true;
			}
		}
		String sessionId = request.getHeader(sessionName);
		if (sessionId == null) {
			sessionId = request.getParameter(sessionName);
		}
		if (sessionId == null) {
			responseJsonMessage(response, ReturnJson.jsonString("用户未登录", AppConstants.USER_NOT_LOGIN));
			return false;
		}
		LoginUser user = validateRedisLogiUser(sessionId);
		if (user == null) {
			responseJsonMessage(response, ReturnJson.jsonString("会话信息失效", AppConstants.USER_SESS_EXPIRED));
			return false;
		}
		request.setAttribute("session", user);
		request.setAttribute("ip", ip);
		return true;
	}

	/**
	 * 获取登录的用户
	 * 
	 * @param sessionId
	 * @return
	 */
	public LoginUser validateRedisLogiUser(String sessionId) {
		if (ApplicationStringUtil.isEmpty(sessionId)) {
			return null;
		}
		// 检查是否已经登录
		LoginUser loginUser = redisService.getUser(sessionId);// 将建json对象转换为Person对象
		return loginUser;
	}

	private void responseJsonMessage(HttpServletResponse response, String msg) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
