package cn.youyitech.anyview.system.dto;

import lombok.Data;

@Data
public class AttitudeCondition {

	// 最慢做完题目的态度分
	private float lowScore;

	// 最快做完题目的态度分
	private float highScore;

	private Long[] ids;

	public AttitudeCondition(float lowScore, float highScore, Long[] ids) {
		this.lowScore = lowScore;
		this.highScore = highScore;
		this.ids = ids;
	}

	public AttitudeCondition() {
	}
	
	

	
}
