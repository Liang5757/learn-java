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
 * @author TT 学校 Entity 2017年8月22日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UniversityTable")
public class School implements GenericEntity {
	private static final long serialVersionUID = 7557954457625055067L;

	/** 学校ID */
	@Column(id = true, name = "UnID", updatable = false)
	private int id;

	/** 学校名称 */
	@Column(name = "UnName")
	private String schoolName;

	/** 创建者 */
	@Column(name = "Creater")
	private String schoolCreater;

	/** 创建时间 */
	@Column(name = "CreateTime")
	private Date schoolCreateTime;

	/** 更新者 */
	@Column(name = "Updater")
	private String schoolUpdater;

	/** 更新时间 */
	@Column(name = "UpdateTime")
	private Date schoolUpdateTime;

	/** 是否删除 */
	@Column(name = "Enabled")
	private boolean isDelete;

	private String schoolNameSort;

	private List<College> schoolCollegeList;

}
