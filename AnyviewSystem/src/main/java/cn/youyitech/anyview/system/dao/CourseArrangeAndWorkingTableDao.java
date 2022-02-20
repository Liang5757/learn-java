package cn.youyitech.anyview.system.dao;

import java.util.List;

import cn.youyitech.anyview.system.entity.CourseArrangeAndWorkingTable;

import com.framework.loippi.mybatis.dao.GenericDao;

/**
 * DAO - 课程编排与作业表关系表dao层
 *
 * @author zzq
 * @version 1.0
 */
public interface CourseArrangeAndWorkingTableDao extends GenericDao<CourseArrangeAndWorkingTable, Long> {

	// 获取全部的课程编排与作业表关系表数据
	List<CourseArrangeAndWorkingTable> findTotal();

	// 通过作业表id删除课程编排与作业表关系表数据
	void deleteByWorkingTableId(long workingTableId);

	// 通过作业表课程编排id删除课程编排与作业表关系表数据
	void deleteByCourseArrangeId(long courseArrangeId);

	List<CourseArrangeAndWorkingTable>findByIdMany(int id);

	//保存 2019.03.27(保存表中的所有字段) cjs
	void saveOne(CourseArrangeAndWorkingTable c);

}
