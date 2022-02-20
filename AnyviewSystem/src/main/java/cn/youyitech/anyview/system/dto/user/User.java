package cn.youyitech.anyview.system.dto.user;

import java.util.Date;

import cn.youyitech.anyview.system.entity.School;

import com.framework.loippi.mybatis.ext.annotation.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - User:SystemUser,Teacher,Student的公共类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 用户姓名 */
	private String name;

	/** 用户邮箱 */
	private String email;

	/** 角色ID: 0-超级管理员 1-校级管理员 2-教师 3-学生 */
	private Long roleId;

	/** 学校ID */
	private int schoolId;

	/** 创建时间 */
	private Date createdDate;

	/** 创建者 */
	private String createdBy;

	/** 最后一次修改时间 */
	private Date lastUpdatedDate;

	/** 最后一次修改者 */
	private String lastUpdatedBy;

	School school;


}
