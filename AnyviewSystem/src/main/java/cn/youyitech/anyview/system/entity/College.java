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
 * @author TT 学院信息 Entity 2017年8月22日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CollegeTable")
public class College implements GenericEntity {

	private static final long serialVersionUID = 3517840362415843360L;

	/** 学院ID */
	@Column(id = true, name = "CeID", updatable = false)
	private Long id;

	/** 学院名称 */
	@Column(name = "CeName")
	private String collegeName;

	/** 学校ID */
	@Column(name = "UnID")
	private int schoolId;

	/** 更新者 */
	@Column(name = "Updater")
	private String collegeUpdater;

	/** 更新时间 */
	@Column(name = "UpdateTime")
	private Date collegeUpdateTime;

	/** 创建者 */
	@Column(name = "Creater")
	private String collegeCreater;

	/** 创建时间 */
	@Column(name = "CreateTime")
	private Date collegeCreateTime;

	/** 是否删除 */
	@Column(name = "Enabled")
	private boolean isDelete;

	private List<Major> collegeMajorList;

	private List<Course> collegeCourseList;

	private List<Teacher> collegeTeacherList;

	private School school;

}
