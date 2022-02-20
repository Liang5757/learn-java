package cn.youyitech.anyview.system.entity;

import java.util.Date;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 考试编排
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EXAMPLANTABLE")
public class ExamPlan implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** 考试编排ID */
	@Column(id = true, name = "EPID", updatable = false)
	private int epId;

	/** 考试名称 */
	@Column(name = "EPName")
	private String epName;

	/** 教师ID */
	@Column(name = "TID")
	private int tId;

	/** 班级ID */
	@Column(name = "CID")
	private int cId;

	/** 课程ID */
	@Column(name = "CourseID")
	private int courseId;

	/** 作业表ID */
	@Column(name = "VID")
	private int vId;

	/** 考试持续时间 */
	@Column(name = "Duration")
	private int duration;

	/** 考试开始时间 */
	@Column(name = "StartTime")
	private Date startTime;

	/** 类型 */
	@Column(name = "Kind")
	private int kind;

	/** 考试状态 */
	@Column(name = "ExamStatus")
	private int examStatus;

	/** 操作 */
	@Column(name = "Operation")
	private int operation;

	/** 已进行时间 */
	@Column(name = "DoneTime")
	private int doneTime;

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

	// 老师
	private Teacher teacher;

	// 班级
	private ClassEntity classSystem;

	// 课程
	private Course course;

	// 作业表
	private WorkingTable workingTable;

}
