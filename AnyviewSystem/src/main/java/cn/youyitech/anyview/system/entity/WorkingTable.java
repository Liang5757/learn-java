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
 * @author TT 作业计划表 Entity 2017年8月22日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SchemeTable")
public class WorkingTable implements GenericEntity {

	private static final long serialVersionUID = 8655243486365405956L;

	/** 作业表ID */
	@Column(id = true, name = "VID", updatable = false)
	private Long id;

	/** 作业表名 */
	@Column(name = "VName")
	private String tableName;

	/** 作业表类型 */
	@Column(name = "Kind")
	private int tableType;

	/** 课程id */
	@Column(name = "CourseID")
	private int courseId;

	/** 级别 */
	@Column(name = "Visit")
	private int tableLevel;

	/** 状态 */
	@Column(name = "Status")
	private int tableStatus;

	/** 创建者 */
	@Column(name = "table_creater")
	private String tableCreater;

	/** 创建时间 */
	@Column(name = "CreateTime")
	private Date tableCreateTime;

	/** 更新者 */
	@Column(name = "table_updater")
	private String tableUpdater;

	/** 更新时间 */
	@Column(name = "UpdateTime")
	private Date tableUpdateTime;

	/** 总时间 */
	@Column(name = "totalTime")
	private int totalTime;

	/** 是否删除 */
	@Column(name = "Enabledd")
	private boolean isDeleted;

	/** 满分值 */
	@Column(name = "FullScore")
	private float fullScore;

	/** 总题数 */
	@Column(name = "TotalNum")
	private int totalNum;

	private String courseName;

	private String collegeName;

	private String schoolName;

	private Course course;

	private List<Long> courseIdList;

	private List<Long> tableIdList;

	private String updaterSort;

}
