package cn.youyitech.anyview.system.dto;

import lombok.Data;

//综合评分条件
@Data
public class TotalScore {

	// 综合分是否选中卷面或态度标志位
	private int selectOne;

	// 卷面分百分数
	private float paperPercent;

	// 态度分百分数
	private float attitudePercent;

	// 及格分数
	private float passScore;

	// 及格分制度题数
	private int passItemCount;

	// 满分制度题数
	private int fullScoreCount;

	// 是否采用及格分制度
	private int examination;

	// 是否采用满分制度
	private int fullMark;

	private Long[] ids;

	public TotalScore(int selectOne, float paperPercent, float attitudePercent, float passScore, int passItemCount,
			int fullScoreCount, int examination, int fullMark, Long[] ids) {
		this.selectOne = selectOne;
		this.paperPercent = paperPercent;
		this.attitudePercent = attitudePercent;
		this.passScore = passScore;
		this.passItemCount = passItemCount;
		this.fullScoreCount = fullScoreCount;
		this.examination = examination;
		this.fullMark = fullMark;
		this.ids = ids;
	}

	public TotalScore() {
	}

	
}
