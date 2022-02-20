package cn.youyitech.anyview.system.dto;

import java.util.List;

import cn.youyitech.anyview.system.entity.QuestionContent;
import cn.youyitech.anyview.system.entity.QuestionHeaderFile;

import com.framework.loippi.mybatis.eitity.GenericEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 题目临时类
 * 
 * @author zzq
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTemp implements GenericEntity {

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public QuestionContent getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(QuestionContent questionContent) {
		this.questionContent = questionContent;
	}

	public List<QuestionHeaderFile> getHeaderFileList() {
		return headerFileList;
	}

	public void setHeaderFileList(List<QuestionHeaderFile> headerFileList) {
		this.headerFileList = headerFileList;
	}

	private static final long serialVersionUID = 1L;

	// 题目内容
	private QuestionContent questionContent;

	// 题目内容头文件
	private List<QuestionHeaderFile> headerFileList;

}
