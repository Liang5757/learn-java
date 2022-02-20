package cn.youyitech.anyview.system.service;

import java.io.IOException;

import javax.mail.MessagingException;

import freemarker.template.TemplateException;

/**
 * @author TT 2017年9月1日
 */
public interface MailService {

	/**
	 * 发送邮件(异步)
	 * 
	 * @param toMail
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param templatePath
	 *            n内容
	 */
	void send(String toMail, String subject, String content) throws MessagingException, IOException;

}