package cn.youyitech.anyview.system.service.impl;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.youyitech.anyview.system.service.MailService;
import cn.youyitech.anyview.system.service.TemplateService;
import cn.youyitech.anyview.system.support.Setting;
import cn.youyitech.anyview.system.utils.SettingUtils;

/**
 * @author TT 2017年9月1日
 */
@Service("mailServiceImpl")
public class MailServiceImpl implements MailService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "javaMailSender")
	private JavaMailSenderImpl javaMailSender;

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;

	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;

	/**
	 * 添加邮件发送任务
	 *
	 * @param mimeMessage
	 *            MimeMessage
	 */
	private void addSendTask(final MimeMessage mimeMessage) {
		try {
			taskExecutor.execute(new Runnable() {
				public void run() {
					javaMailSender.send(mimeMessage);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword,
			String toMail, String subject, String content, Map<String, Object> model, boolean async)
			throws MessagingException, IOException {
		Assert.hasText(smtpFromMail);
		Assert.hasText(smtpHost);
		Assert.notNull(smtpPort);
		Assert.hasText(smtpUsername);
		Assert.hasText(smtpPassword);
		Assert.hasText(toMail);
		Assert.hasText(subject);
		Setting setting = SettingUtils.get();
		javaMailSender.setHost(smtpHost);
		javaMailSender.setPort(smtpPort);
		javaMailSender.setUsername(smtpUsername);
		javaMailSender.setPassword(smtpPassword);
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
		mimeMessageHelper.setFrom(MimeUtility.encodeWord(setting.getSiteName()) + " <" + smtpFromMail + ">");
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setTo(toMail);
		mimeMessageHelper.setText(content, true);
		if (async) {
			addSendTask(mimeMessage);
		} else {
			javaMailSender.send(mimeMessage);
		}
	}

	public void send(String toMail, String subject, String content) throws MessagingException, IOException {
		Setting setting = SettingUtils.get();
		send(setting.getSmtpFromMail(), setting.getSmtpHost(), setting.getSmtpPort(), setting.getSmtpUsername(),
				setting.getSmtpPassword(), toMail, subject, content, null, true);
	}

}