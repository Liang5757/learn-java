package cn.youyitech.anyview.system.dto;

import lombok.Data;

//卷面分评分条件
@Data
public class PaperScore {
	//未完成一道题目得分
	private float lowScore;
	//完成一道题目得分
	private float highScore;
	private Long[] ids;
	
	public PaperScore(float lowScore, float highScore, Long[] ids) {
		this.lowScore = lowScore;
		this.highScore = highScore;
		this.ids = ids;
	}
	
	public PaperScore() {
	}
	
	
	
}
