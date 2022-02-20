package cn.youyitech.anyview.system.entity;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TT
 *
 *         2017年8月22日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "system_school")
public class SystemSchool implements GenericEntity {

	private static final long serialVersionUID = -6191530124295312491L;
	/** 学校系统表ID */
	@Column(id = true, name = "id", updatable = false)
	private int id;
	/** 学校名称 */
	@Column(name = "school_name")
	private String schoolName;

	/** 省份 */
	@Column(name = "school_address")
	private String schoolAddress;

}
