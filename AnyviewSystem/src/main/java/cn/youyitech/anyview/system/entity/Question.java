package cn.youyitech.anyview.system.entity;

import java.util.Date;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 题目管理
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROBLEMTABLE")
public class Question implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Column(id = true, name = "PID", updatable = false)
	private int id;

	/** 题目名称 */
	@Column(name = "PName")
	private String question_name;

	/** 章节 */
	@Column(name = "chapter")
	private String chapter;

	/** 公开级别 */
	@Column(name = "public_level")
	private int public_level;

	/** 特定学校 */
	@Column(name = "specific_school")
	private String specific_school;

	/** 难度 */
	@Column(name = "Degree")
	private float difficulty;

	/** 状态 */
	@Column(name = "state")
	private String state;

	/** 备注 */
	@Column(name = "PMemo")
	private String remark;

	/** 题库ID */
	@Column(name = "LID")
	private int question_bank_id;

	/** 题目内容 */
	@Column(name = "PContent")
	private String content;

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

	// 题库
	private QuestionBank questionBank;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion_name() {
		return question_name;
	}

	public void setQuestion_name(String question_name) {
		this.question_name = question_name;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public int getPublic_level() {
		return public_level;
	}

	public void setPublic_level(int public_level) {
		this.public_level = public_level;
	}

	public String getSpecific_school() {
		return specific_school;
	}

	public void setSpecific_school(String specific_school) {
		this.specific_school = specific_school;
	}

	public float getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getQuestion_bank_id() {
		return question_bank_id;
	}

	public void setQuestion_bank_id(int question_bank_id) {
		this.question_bank_id = question_bank_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated_person() {
		return created_person;
	}

	public void setCreated_person(String created_person) {
		this.created_person = created_person;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getUpdate_person() {
		return update_person;
	}

	public void setUpdate_person(String update_person) {
		this.update_person = update_person;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public QuestionBank getQuestionBank() {
		return questionBank;
	}

	public void setQuestionBank(QuestionBank questionBank) {
		this.questionBank = questionBank;
	}
}
