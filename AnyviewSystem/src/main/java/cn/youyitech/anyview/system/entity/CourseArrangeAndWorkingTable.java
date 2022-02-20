package cn.youyitech.anyview.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import java.util.Date;

/**
 * Entity - 课程编排与作业表对应信息
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLASS_COURSE_SCHEMETABLE")
public class CourseArrangeAndWorkingTable implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Column(id = true, name = "ID", updatable = false)
	private Long id;

	/** 课程编排ID */
	@Column(name = "CTCID")
	private int courseArrangeId;
	/** 班级ID */
	@Column(name="CID")
	private int classId;
	/** 作业表ID */
	@Column(name = "VID")
	private int workingTableId;

	/** 课程id 2019.03.27 cjs */
	@Column(name = "CourseID")
    private int courseId;

    /** 教师Id 2019.03.27 cjs */
	@Column(name = "TID")
    private int teacherId;

	/** 删除标志位 */
	@Column(name = "Enabled")
	private int enabled;
	// 作业表
	WorkingTable workingTable;

}
