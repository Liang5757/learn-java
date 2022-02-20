package cn.youyitech.anyview.system.entity;

import java.util.Date;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TT 作业表内容 Entity 2017年9月15日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schemecontenttable")
public class SchemeContentTable implements GenericEntity {

	private static final long serialVersionUID = -3400408115885096088L;
	/** 作业内容表id */
	@Column(id = true, name = "ID", updatable = false)
	private Long ID;

	/** 作业表id */
	@Column(name = "VID")
	private int VID;

	/** 题目id */
	@Column(name = "PID")
	private Long PID;

	/** 题目名 */
	@Column(name = "VPName")
	private String VPName;

	/** 章节 */
	@Column(name = "VChapName")
	private String VChapName;

	/** 状态 */
	@Column(name = "Status")
	private int Status;

	/** 分值 */
	@Column(name = "Score")
	private float Score;

	/** 更新者 */
	@Column(name = "Updater")
	private String Updater;

	/** 开始时间 */
	@Column(name = "StartTime")
	private Date StartTime;

	/** 结束时间 */
	@Column(name = "FinishTime")
	private Date FinishTime;

	/** 更新时间 */
	@Column(name = "UpdateTime")
	private Date UpdateTime;

	/** 难度 */
	@Column(name = "difficulty")
	private double difficulty;

	/** 是否删除 */
	@Column(name = "Enabled")
	private boolean isDelete;

	private WorkingTable workingTable;

	private Question question;

	private String difficultySort;

	private String VChapNameSort;

}
