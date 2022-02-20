package cn.youyitech.anyview.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

/**
 * Entity - 题目内容管理
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_content")
public class QuestionContent implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Column(id = true, name = "id", updatable = false)
	private int id;

	/** 题目描述 */
	@Column(name = "question_description")
	private String question_description;

	/** 标准答案 */
	@Column(name = "standard_answer")
	private String standard_answer;

	/** 学生答案 */
	@Column(name = "student_answer")
	private String student_answer;

	/** 主文件名称 */
	@Column(name = "headerfile_name")
	private String headerfile_name;

	/** 主文件内容 */
	@Column(name = "headerfile_content")
	private String headerfile_content;

}
