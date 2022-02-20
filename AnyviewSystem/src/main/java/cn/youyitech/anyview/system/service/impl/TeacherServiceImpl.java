package cn.youyitech.anyview.system.service.impl;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import cn.youyitech.anyview.system.utils.SortUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.youyitech.anyview.system.dao.TeacherDao;
import cn.youyitech.anyview.system.entity.CourseArrange;
import cn.youyitech.anyview.system.entity.Student;
import cn.youyitech.anyview.system.entity.SystemUser;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.service.CourseArrangeService;
import cn.youyitech.anyview.system.service.MailService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.support.Message;

@Service("teacherServiceImpl")
public class TeacherServiceImpl extends GenericServiceImpl<Teacher, Long> implements TeacherService {

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private CourseArrangeService courseArrangeService;

	@Value("${system.reset_password_startcontent}")
	private String reset_startcontent;

	@Value("${system.login_address_content}")
	private String login_address;

	@Value("${system.reset_title}")
	private String reset_title;

	@Value("${system.password_len}")
	private int password_len;

	@Value("${system.new_user_startcontent}")
	private String user_startcontent;

	@Value("${system.new_user_mincontent}")
	private String user_mincontent;

	@Value("${system.new_user_title}")
	private String user_title;

	@Value("${system.init_password_title}")
	private String init_password_title;

	@Value("${system.init_title_password_content}")
	private String init_title_password_content;

	@Value("${system.init_password}")
	private String init_password;

	@Autowired
	private MailService mailService;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(teacherDao);
	}

	// 查询全部的教师
	@Override
	public List<Teacher> findTotal() {
		List<Teacher>teacherList=teacherDao.findTotal();
		SortUtil.sort(teacherList,true,"name");

		return teacherDao.findTotal();
	}

	// 根据教师参数查询相应的数据
	@Override
	public List<Teacher> findByAttribute(Teacher teacher) {
		return teacherDao.findByAttribute(teacher);
	}

	@Override
	public void updatePassByUserName(Teacher teacher) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		String pass = "";
		for (int j = 0; j < 6; j++) {
			int d = (int) (Math.floor(Math.random() * 10));
			pass = pass + d;
		}
		teacher.setPassword(DigestUtils.md5Hex(teacher.getId() + pass));
		String content = teacher.getUsername() + reset_startcontent + pass + login_address;
		mailService.send(teacher.getEmail(), user_title, content);
		teacherDao.update(teacher);
	}

	@Override
	public Teacher findByEntity(Teacher teacherNew) {
		return teacherDao.findByEntity(teacherNew);
	}

	public void saveOne(Teacher teacher) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		String pass = "";
		for (int j = 0; j < 6; j++) {
			int d = (int) (Math.floor(Math.random() * 10));
			pass = pass + d;
		}
		teacher.setPassword(pass);
		save(teacher);
		Teacher teacherNew = teacherDao.findByEntity(teacher);
		teacher.setId(teacherNew.getId());
		// 为用户修改密码:用户id+6位随机数
		teacher.setPassword(DigestUtils.md5Hex((teacherNew.getId()) + pass));
		update(teacher);
		String content = teacher.getUsername() + user_startcontent + teacher.getUsername() + user_mincontent + pass
				+ login_address;
		mailService.send(teacher.getEmail(), user_title, content);
	}

	@Override
	public void updateInitPass(Teacher teacher) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		teacher.setPassword(DigestUtils.md5Hex(teacher.getId() + init_password));
		String content = teacher.getUsername() + init_title_password_content + init_password + login_address;
		update(teacher);
		mailService.send(teacher.getEmail(), user_title, content);

	}

	@Override
	public Teacher findByUserName(Teacher teacher) {
		return teacherDao.findByUserName(teacher);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(List<String> recordTeacher) throws Exception {
		// TODO Auto-generated method stub
		for (String id_string : recordTeacher) {
			long id = Long.parseLong(id_string);
			// 删除老师要先判断该老师下还有没有课程编排
			List<CourseArrange> courseArrangeList = courseArrangeService.findList("teacher_id", id);
			if (courseArrangeList.size() > 0) {
				throw new RuntimeException("请先删除该教师的所有课程编排！");

			} else {
				// 删除用户表中的教师信息
				teacherDao.delete(id);

			}
		}
	}
}
