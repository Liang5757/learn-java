package cn.youyitech.anyview.system.entity;

import java.util.Date;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import cn.youyitech.anyview.system.dto.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - SystemUser，登录系统的用户，包含超级管理员、校级管理员、教师、学生
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ManagerTable")
public class SystemUser extends User implements GenericEntity {

	private static final long serialVersionUID = -7404471728192141480L;

	/** 主键 */
	@Column(id = true, name = "MID", updatable = false)
	private Long id;

	/** 用户名 */
	@Column(name = "MNo")
	private String username;

	/** 密码 */
	@Column(name = "MPsw")
	private String password;

	/** 新密码 */
	private String confimPass;

	/** 用户姓名 */
	@Column(name = "NAME")
	private String name;

	/** 用户邮箱 */
	@Column(name = "EMAIL")
	private String email;

	/** 角色ID: 0-超级管理员 1-校级管理员 2-教师 3-学生 */
	@Column(name = "MIden")
	private Long roleId;

	/** 学校ID */
	@Column(name = "UnID")
	private int schoolId;

	/** 是否启用 */
	@Column(name = "Enabled")
	private Boolean isEnabled;

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

	private School school;

}
