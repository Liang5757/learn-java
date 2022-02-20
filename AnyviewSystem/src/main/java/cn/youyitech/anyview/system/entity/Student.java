package cn.youyitech.anyview.system.entity;

import java.util.Date;
import java.util.List;

import cn.youyitech.anyview.system.dto.user.User;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 学生信息
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STUDENTTABLE")
public class Student extends User implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** id */
	@Column(id = true, name = "SID", updatable = false)
	private int id;

	/** 学生性别 */
	@Column(name = "Ssex")
	private String sex;

	/** 学生状态 */
	@Column(name = "State")
	private String state;

	/** 学生登录用户名 */
	@Column(name = "Sno")
	private String username;

	/** 删除标志位 */
	@Column(name = "Enabled")
	private int enabled;

	/** 学生登陆密码 */
	@Column(name = "SPsw")
	private String password;

	/** 学生姓名 */
	@Column(name = "SName")
	private String name;

	/** 学生所属学校 */
	@Column(name = "UnID")
	private int schoolId;

	/** 学生邮箱 */
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
	
	/** 累积做题时间 */
    @Column(name = "SaccumTime")
    private int saccumTime;
    
	/** IP地址 */
    @Column(name = "LogIP")
    private String logIP;
    
	/** 端口号 */
    @Column(name = "LogPort")
    private int logPort;

	/** 端口号 */
	@Column(name = "LogTime")
	private Date logTime;

	/** 登录状态 */
    @Column(name = "LoginStatus")
    private int loginStatus;

	// 学生与班级关联表
	private List<ClassAndStudent> classList;

	private List<Integer> studentIdList;

	// 学校
	private School school;

}
