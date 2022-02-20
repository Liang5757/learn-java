package cn.youyitech.anyview.system.entity;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity - 批改作业
 * 
 * @author zzq
 * @version 1.0
 */
/**
 * @author professorxin
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EXERCISETABLE")
public class Exercise implements GenericEntity {
	private static long serialVersionUID = 1L;
	private SchemeTable scheme;
	/** 作业答案ID */
	@Column(id = true, name = "EID", updatable = false)
	private long eId;

	/** 学生ID */
	@Column(name = "SID")
	private int sId;

	/** 作业表ID */
	@Column(name = "VID")
	private int vId;

	/** 题目ID */
	@Column(name = "PID")
	private int pId;

	/** 班级ID */
	@Column(name = "CID")
	private int cId;

	/** 课程ID */
	@Column(name = "CourseID")
	private int courseId;

	/** 是否通过标志位 */
	@Column(name = "Kind")
	private int kind;

	/** 答案内容 */
	@Column(name = "EContent")
	private String eContent;

	/** 教师批注 */
	@Column(name = "EComment")
	private String eComment;

	/** 更新者 */
	@Column(name = "UpdatePerson")
	private String update_person;

	/** 完成题目所花时间 */
	@Column(name = "AccumTime")
	private Integer accumTime;

	/* 首次完成时间 */
	@Column(name = "FirstPastTime")
	private Date firstPastTime;


	/** 更新日期 */
	@Column(name = "UpdateTime")
	private Date update_date;

	/** 删除标志位 */
	@Column(name = "Enabled")
	private int enabled;

	/** 综合分 */
	@Column(name = "Score")
	private float score;

	/** 卷面分 */
	@Column(name = "pageScore")
	private float pageScore;

	/** 态度分 */
	@Column(name = "attitudeScore")
	private float attitudeScore;

	// 更改内容
	/** 运行结果 */
	@Column(name = "RunResult")
	private int runResult;

	/**运行错误次数**/
	@Column(name = "RunErrCount")
	private int runErrCount;
	/**编译次数**/
	@Column(name = "CmpCount")
	private int cmpCount;
	/**编译正确次数**/
	@Column(name = "CmpRightCount")
	private int cmpRightCount;
	/**编译错误次数**/
	@Column(name = "CmpErrorCount")
	private int cmpErrorCount;

	@Column(name = "RunRightCount")  //数据库无该字段
	/**运行正确次数**/
	private int runRightCount;

	@Column(name = "RunErrCount")  //数据库无该字段
	/**运行正确次数**/
	private int runErrorCount;

	/* 最后一次完成时间 */
	@Column(name = "LastTime")
	private Date lastPastTime;

	@Column(name = "CreateTime")
	private Date createTime;
	// 通过率
	private float passPercent;
	// 学生
	private Student student;
	// 作业表
	private WorkingTable workingTable;
	// 题目
	private Question question;
	// 班级
	private ClassEntity classSystem;
	// 课程
	private Course course;

}
