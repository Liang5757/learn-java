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
 * @author TT 专业 Entity 2017年8月22日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "major")
public class Major implements GenericEntity {

	private static final long serialVersionUID = 8655243486365405956L;

	/** 专业ID */
	@Column(id = true, name = "id", updatable = false)
	private Long id;

	/** 专业名称 */
	@Column(name = "major_name")
	private String majorName;

	/** 学院ID */
	@Column(name = "college_id")
	private Long collegeId;

	/** 更新者 */
	@Column(name = "major_updater")
	private String majorUpdater;

	/** 更新时间 */
	@Column(name = "major_update_time")
	private Date majorUpdateTime;

	/** 创建者 */
	@Column(name = "major_creater")
	private String majorCreater;

	/** 创建时间 */
	@Column(name = "major_create_time")
	private Date majorCreateTime;

	/** 是否删除 */
	@Column(name = "Enabled")
	private boolean isDelete;

	private College college;

	private List<ClassEntity> majorClassList;

}
