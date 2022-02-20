package cn.youyitech.anyview.system.shiro;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 身份信息
 * 
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPrincipal implements Serializable {

	private static final long serialVersionUID = 5798882004228239559L;

	/** ID */
	private Long id;

	/** 登录用户名 */
	private String username;

	/** 用户姓名 */
	private String name;

	/** 角色ID */
	private Long roleId;

	/** 学校ID */
	private int schoolId;

}