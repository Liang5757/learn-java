package cn.youyitech.anyview.system.dto;

import com.framework.loippi.mybatis.ext.annotation.Column;

import cn.youyitech.anyview.system.entity.QuestionBank;
import lombok.Data;

@Data
public class Questions {

	/** ID */
	@Column(id = true, name = "PID", updatable = false)
	private int id;

	/** 题目名称 */
	private String question_name;

	/** 章节 */
	private String chapter;

	/** 难度 */
	private float difficulty;

	/** 题目内容 */
	private String content;

	/** 作业表名称 */
	private String workingTableName;

	/** 题库 */
	private QuestionBank questionBank;

}
