package cn.youyitech.anyview.system.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class TableIdAndName implements Serializable {

	private static final long serialVersionUID = 5398576585119095332L;

	// 作业表ID
	private Long tableId;

	// 作业表名称
	private String tableName;

}
