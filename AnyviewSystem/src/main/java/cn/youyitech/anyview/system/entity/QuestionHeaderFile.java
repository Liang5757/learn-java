package cn.youyitech.anyview.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

/**
 * Entity - 题目内容头文件管理
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_headerfile")
public class QuestionHeaderFile implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Column(id = true, name = "id", updatable = false)
	private int id;

	/** 头文件名称 */
	@Column(name = "header_file")
	private String header_file;

	/** 头文件内容 */
	@Column(name = "header_file_content")
	private String header_file_content;

}
