package cn.youyitech.anyview.system.entity;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 用户权限
 * 
 * @version 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_SYSTEM_USER_ACLS")
public class SystemUserAcls implements GenericEntity {

	private static final long serialVersionUID = 5081846432919091193L;

	/** 用户id */
	@Column(id = true, name = "ID", updatable = false)
	private Long id;

	/** AclId */
	@Column(name = "ACL_ID")
	private Long aclId;

	/** roleId */
	@Column(name = "ROLE_ID")
	private Long roleId;

}
