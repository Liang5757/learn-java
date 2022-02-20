package cn.youyitech.anyview.system.entity;

import java.util.List;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - SystemAcl，资源
 * 
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_SYSTEM_ACL")
public class SystemAcl implements GenericEntity {

	private static final long serialVersionUID = 5660456626612505104L;

	/** ID */
	@Column(id = true, name = "ID", updatable = false)
	private Long id;

	/** 路径 */
	@Column(name = "URL")
	private String url;

	/** 名称 */
	@Column(name = "ACL_NAME")
	private String aclName;

	/** 权限 */
	@Column(name = "PERMISSION")
	private String permission;

	/** 排序 */
	@Column(name = "SORT")
	private Integer sort;
	
	private List<SystemAcl> children;

}
