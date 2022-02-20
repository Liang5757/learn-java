package cn.youyitech.anyview.system.entity;

import com.framework.loippi.mybatis.eitity.GenericEntity;
import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 学生与班级对应信息
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLASS_STUDENTTBALE")
public class ClassAndStudent implements GenericEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Column(id = true, name = "ID", updatable = false)
	private long id;

	/** 学生班级ID */
	@Column(name = "CID")
	private int student_class_id;

	/** 学生ID */
	@Column(name = "SID")
	private int student_id;

	/** 删除标志位 */
	@Column(name = "Status")
	private int enabled;

	// 班级
	private ClassEntity classSystem;
	// 学生
	private Student student;

}
