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
 * Entity - 班级信息
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLASSTABLE")
public class ClassEntity implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** 班级ID */
	@Column(id = true, name = "CID", updatable = false)
	private int id;

	/** 班级名称 */
	@Column(name = "Cname")
	private String className;

	/** 专业ID */
	@Column(name = "MID")
	private int major_id;

	/** 年届 */
	@Column(name = "StartYear")
	private int year;

	/** 更新者 */
	@Column(name = "CreatePerson")
	private String created_person;

	/** 更新日期 */
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

	// 专业
	private Major major;

	// 课程编排
	private List<CourseArrange> classCourseArrangeList;

	// 学生与班级关联表
	private List<ClassAndStudent> classStudentList;

}
