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
 * @author TT 综合评分 Entity 2017年8月22日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ScoreTable")
public class ScoreTable implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** 综合评分表ID */
	@Column(id = true, name = "ID", updatable = false)
	private Long id;

	/** 学生ID */
	@Column(name = "SID")
	private int sID;

	/** 作业计划表ID */
	@Column(name = "VID")
	private int vID;

	/** 班级ID */
	@Column(name = "CID")
	private int cID;

	/** 综合分 */
	@Column(name = "Score")
	private float score;

	/** 卷面分 */
	@Column(name = "PaperScore")
	private float paperScore;

	/** 态度分 */
	@Column(name = "AttitudeScore")
	private float attitudeScore;

	/** 排名 */
	@Column(name = "Rank")
	private int rank;

	/** 通过题数 */
	@Column(name = "PassNum")
	private int passNum;

	/** 总用时（分钟） */
	@Column(name = "TotalTime")
	private int totalTime;

	/** 批改标志 0未批改1已批改 */
	@Column(name = "CorrectFlag")
	private int correctFlag;

	/** 更新者 */
	@Column(name = "Updater")
	private String updater;

	/** 记录更新时间 */
	@Column(name = "UpdateTime")
	private Date updateTime;

	/** 创建者 */
	@Column(name = "Creater")
	private String creater;

	/** 创建时间 */
	@Column(name = "CreateTime")
	private Date createTime;

	/** 是否删除 */
	@Column(name = "Enabled")
	private boolean enabled;

	private Student student;

	private WorkingTable workingTable;

	private ClassEntity classSystem;

	private List<Long> scoreTableIds;

}
