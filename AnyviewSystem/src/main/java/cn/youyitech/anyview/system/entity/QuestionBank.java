package cn.youyitech.anyview.system.entity;

import java.util.Date;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 题库管理
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROBLEMLIBTABLE")
public class QuestionBank implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Column(id = true, name = "LID", updatable = false)
	private int id;

	/** 题库名称 */
	@Column(name = "LName")
	private String question_bank;

	/** 课程名 */
	@Column(name = "course_name")
	private String course_name;

	/** 公开级别 */
	@Column(name = "public_level")
	private int public_level;

	/** 特定学校 */
	@Column(name = "specific_school")
	private String specific_school;

	/** 创建者 */
	@Column(name = "CreatePerson")
	private String created_person;

	/** 创建日期 */
	@Column(name = "CreateTime")
	private Date created_date;

	/** 更新者 */
	@Column(name = "UpdatePerson")
	private String update_person;

	/** 更新日期 */
	@Column(name = "UpdateTime")
	private Date update_date;

	/** 删除标志位 */
	@Column(name = "Enabled")
	private int enabled;

}
