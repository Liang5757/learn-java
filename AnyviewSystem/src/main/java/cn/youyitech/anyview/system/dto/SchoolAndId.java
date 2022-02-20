package cn.youyitech.anyview.system.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SchoolAndId implements Serializable {

	private static final long serialVersionUID = 5398576585119095332L;

	// 学校ID
	private int id;

	// 学校名称
	private String schoolName;

}
