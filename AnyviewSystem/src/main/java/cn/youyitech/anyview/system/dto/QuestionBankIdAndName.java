package cn.youyitech.anyview.system.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class QuestionBankIdAndName implements Serializable {

	private static final long serialVersionUID = 5398576585119095332L;

	private int questionBankId;

	private String questionBankName;

}
