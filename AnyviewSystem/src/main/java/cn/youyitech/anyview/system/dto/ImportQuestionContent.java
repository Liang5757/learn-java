package cn.youyitech.anyview.system.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import cn.youyitech.anyview.system.entity.QuestionContent;
import cn.youyitech.anyview.system.entity.QuestionHeaderFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportQuestionContent {

	// 题库名称
	List<String> chapterNameList;
	// 一个题库中包含的题目数
	List<Integer> questionNumberList;
	// 题目名称
	List<String> questionNameList;
	// 题目内容
	List<QuestionContent> questionContentList;
	// 题目内容头文件
	List<QuestionHeaderFile> headerFileList;
	//一个题目中包含的头文件数目
	List<Integer> headerNameList;

}
