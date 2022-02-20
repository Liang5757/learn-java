package cn.youyitech.anyview.system.service;

import java.util.List;

import cn.youyitech.anyview.system.entity.CourseArrangeAndWorkingTable;

/**
 * SERVICE -课程编排与作业表关系表service层
 * 
 * @author zzq
 * @version 1.0
 */
public interface CourseArrangeAndWorkingTableService extends GenericService<CourseArrangeAndWorkingTable, Long> {

	// 查询全部的课程编排与作业表关系表数据
	List<CourseArrangeAndWorkingTable> findTotal();
	List<CourseArrangeAndWorkingTable>findByIdMany(int course_id);
	// 通过作业表id删除课程编排与作业表关系表的数据
	void deleteByWorkingTableId(long workingTableId);

	// 通过作业表课程编排id删除课程编排与作业表关系表数据
	void deleteByCourseArrangeId(long courseArrangeId);

	//保存表中的所有字段 2019.03.27(数据库对应字段为空解决) cjs
	void saveOne(CourseArrangeAndWorkingTable courseArrangeAndWorkingTable);

}
