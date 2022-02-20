package cn.youyitech.anyview.system.dto;

import java.util.List;

import lombok.Data;

@Data
public class WorkIdAndQuestionBankId {

	/** 作业表id */
	private List<Long> workingIds;

	/** 题库id */
	private List<Long> questionBankIds;

}
