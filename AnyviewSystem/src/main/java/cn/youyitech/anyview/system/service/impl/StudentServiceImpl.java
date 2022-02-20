package cn.youyitech.anyview.system.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.youyitech.anyview.system.dao.StudentDao;
import cn.youyitech.anyview.system.entity.Student;
import cn.youyitech.anyview.system.entity.SystemUser;
import cn.youyitech.anyview.system.service.ClassAndStudentService;
import cn.youyitech.anyview.system.service.MailService;
import cn.youyitech.anyview.system.service.StudentService;
import cn.youyitech.anyview.system.service.ExerciseService;
import cn.youyitech.anyview.system.entity.Exercise;

@Service("studentServiceImpl")
public class StudentServiceImpl extends GenericServiceImpl<Student, Long> implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Resource
	private ClassAndStudentService classAndStudentService;

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
	private ExerciseService exerciseService;

	@Autowired
	public void setGenericDao() {
		super.setGenericDao(studentDao);
	}

	// 查询全部学生
	@Override
	public List<Student> findTotal() {
		return studentDao.findTotal();
	}

	// 根据学生参数查询相应的数据
	@Override
	public List<Student> findByAttribute(Student student) {
		return studentDao.findByAttribute(student);
	}

	@Override
	public Student findByEntity(Student studentNew) {
		return studentDao.findByEntity(studentNew);
	}

	@Override
	public void updatePassByUserName(Student student) throws MessagingException, IOException {
		String password = "";
		for (int j = 0; j < 6; j++) {
			int d = (int) (Math.floor(Math.random() * 10));
			password = password + d;
		}
		student.setPassword(DigestUtils.md5Hex(student.getId() + password));
		String content = student.getUsername() + reset_startcontent + password + login_address;
		studentDao.update(student);
		mailService.send(student.getEmail(), user_title, content);

	}

	public void saveOne(Student student) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		// 若有邮箱，则把自动生成的6位数密码发送到邮箱
		String pass = init_password;
		if (student.getEmail() != null && !student.getEmail().equals("")) {
			pass = "";
			for (int j = 0; j < 6; j++) {
				int d = (int) (Math.floor(Math.random() * 10));
				pass = pass + d;
			}
		}
		student.setPassword(pass);
		save(student);
		Student studentNew = studentDao.findByEntity(student);
		student.setId(studentNew.getId());
		// 为用户修改密码:用户id+6位随机数
		student.setPassword(DigestUtils.md5Hex((studentNew.getId()) + pass));
		update(student);
		if (student.getEmail() != null && !student.getEmail().equals("")) {
			String content = student.getUsername() + user_startcontent + student.getUsername() + user_mincontent + pass
					+ login_address;
			mailService.send(student.getEmail(), user_title, content);
		}
	}

	@Override
	public void updateInitPass(Student student) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		String content = student.getUsername() + init_title_password_content + init_password + login_address;
		student.setPassword(DigestUtils.md5Hex(student.getId() + init_password));

		update(student);
		if (student.getEmail() != null && !student.getEmail().equals("")) {
			mailService.send(student.getEmail(), user_title, content);
		}
	}

	@Override
	public Student findByUserNameAndSchoolId(Student student) {
		return studentDao.findByUserNameAndSchoolId(student);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void deleteOne(List<String> recordStudent) throws Exception {
		// TODO Auto-generated method stub
		for (String id_string : recordStudent) {
			long id = Long.parseLong(id_string);
			List<Exercise> exerciseList = exerciseService.findList("sId", id);
			if (exerciseList.size() > 0) {
				throw new RuntimeException("请先删除该学生的所有批改作业！");
			}
			// 删除学生
			studentDao.delete(id);
			// 删除该学生与班级的关联关系
			classAndStudentService.deleteByStudentID(id);
		}
	}

	@Override
	public List<Student> findEntityList(Student student) {
		// TODO Auto-generated method stub
		return studentDao.findEntityList(student);
	}

}
