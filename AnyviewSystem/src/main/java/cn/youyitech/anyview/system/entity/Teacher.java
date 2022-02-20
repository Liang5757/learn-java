package cn.youyitech.anyview.system.entity;

import java.util.Date;

import cn.youyitech.anyview.system.dto.user.User;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 老师信息
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEACHERTABLE")
public class Teacher extends User implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Column(id = true, name = "TID", updatable = false)
	private int id;

	/** 老师姓名 */
	@Column(name = "TName")
	private String name;

	/** 老师性别 */
	@Column(name = "TSex")
	private String sex;

	/** 老师所属学校 */
	@Column(name = "UnID")
	private int schoolId;

	/** 老师所属学院 */
	@Column(name = "CID")
	private int college_id;

	/** 老师登录用户名 */
	@Column(name = "TNo")
	private String username;

	/** 老师登陆密码 */
	@Column(name = "TPsw")
	private String password;

	/** 老师删除标志位 */
	@Column(name = "Enabled")
	private int enabled;

	/** 老师邮箱 */
	@Column(name = "EMAIL")
	private String email;

	/** 是否锁定 */
	@Column(name = "IS_LOCKED")
	private Boolean isLocked;

	/** 锁定日期 */
	@Column(name = "LOCKED_DATE")
	private Date lockedDate;

	/** 连续登录失败次数 */
	@Column(name = "LOGIN_FAILURE_COUNT")
	private Integer loginFailureCount;

	/** 最后登录日期 */
	@Column(name = "LAST_LOGIN_DATE")
	private Date lastLoginDate;

	/** 最后登录IP */
	@Column(name = "LAST_LOGIN_IP")
	private String lastLoginIp;

	/** 创建时间 */
	@Column(name = "CreateTime")
	private Date createdDate;

	/** 创建者 */
	@Column(name = "Creater")
	private String createdBy;

	/** 最后一次修改时间 */
	@Column(name = "UpdateTime")
	private Date lastUpdatedDate;

	/** 最后一次修改者 */
	@Column(name = "Updater")
	private String lastUpdatedBy;

	/** 角色ID: 0-超级管理员 1-校级管理员 2-教师 3-学生 */
	@Column(name = "RoleId")
	private Long roleId;

	private School school;
	// 老师所属学院
	private College college;

}
