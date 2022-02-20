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
 * @author TT 课程 Entity 2017年8月22日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CourseTable")
public class Course implements GenericEntity {

	private static final long serialVersionUID = 6516577002304983054L;
	/** 课程ID */
	@Column(id = true, name = "CourseID", updatable = false)
	private Long id;

	/** 课程名称 */
	@Column(name = "CourseName")
	private String courseName;

	/** 学院ID */
	@Column(name = "CeID")
	private Long collegeId;

	/** 更新者 */
	@Column(name = "Updater")
	private String courseUpdater;

	/** 更新时间 */
	@Column(name = "UpdateTime")
	private Date courseUpdateTime;

	/** 创建者 */
	@Column(name = "Creater")
	private String courseCreater;

	/** 创建时间 */
	@Column(name = "CreateTime")
	private Date courseCreateTime;

	/** 是否删除 */
	@Column(name = "Enabled")
	private boolean isDelete;

	private College college;

	private List<CourseArrange> courseCourseArrangeList;

	private List<WorkingTable> workingTableList;

}
