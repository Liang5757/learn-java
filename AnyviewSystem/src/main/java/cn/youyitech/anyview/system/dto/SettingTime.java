package cn.youyitech.anyview.system.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import lombok.Data;

@Data
public class SettingTime implements Serializable {

	private static final long serialVersionUID = 3320622525800445755L;

	// 开始时间
	private Date startTime;

	private Date stopTime;

	// 结束时间
	private Long[] idsList;

	// 选中的id
	public Long[] getIdsList() {
		return idsList;
	}

}
