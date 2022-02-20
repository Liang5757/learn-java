package cn.youyitech.anyview.system.entity;

import java.util.Date;
import java.util.List;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 课程编排信息
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLASS_TEACHER_COURSETABLE")
public class CourseArrange implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Column(id = true, name = "ID", updatable = false)
	private Long id;

	/** 班级ID */
	@Column(name = "CID")
	private int class_id;

	/** 课程ID */
	@Column(name = "CourseID")
	private int course_id;

	/** 老师ID */
	@Column(name = "TID")
	private int teacher_id;

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

	/** 删除标志 */
	@Column(name = "Enabled")
	private int enabled;

	// 班级
	private ClassEntity classSystem;
	// 学生与班级关联表
	private List<ClassAndStudent> classAndStudentList;
	// 课程
	private Course course;
	// 老师
	private Teacher teacher;
	// 课程编排与作业表关联表
	private List<CourseArrangeAndWorkingTable> courseArrangeAndWorkingTable;

}
