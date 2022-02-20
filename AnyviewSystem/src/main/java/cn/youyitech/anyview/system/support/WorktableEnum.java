package cn.youyitech.anyview.system.support;

public class WorktableEnum {

	// 作业表状态
	public enum statusEnum {

		stop("停用", 0), start("启用", 1);

		private String text;

		private int value;

		private statusEnum(String status, int desc) {
			text = status;
			value = desc;
		}

		/**
		 * @return 当前枚举对象的值。
		 */
		public int getValue() {
			return value;
		}

		/**
		 * @return 当前状态的中文描述。
		 */
		public String getText() {
			return text;
		}
	}

	// 作业表题型
	public enum typeEnum {

		working("作业题", 0), exam("考试题", 1);

		private int value;

		private String text;

		private typeEnum(String status, int desc) {
			text = status;
			value = desc;
		}

		/**
		 * @return 当前枚举对象的值。
		 */
		public int getValue() {
			return value;
		}

		/**
		 * @return 当前状态的中文描述。
		 */
		public String getText() {
			return text;
		}
	}

	// 作业表级别
	public enum levelEnum {

		secrecy("完全保密", 0), schoolOpen("本校公开", 1), openAll("完全公开", 2);

		private int value;

		private String text;

		private levelEnum(String status, int desc) {
			text = status;
			value = desc;
		}

		/**
		 * @return 当前枚举对象的值。
		 */
		public int getValue() {
			return value;
		}

		/**
		 * @return 当前状态的中文描述。
		 */
		public String getText() {
			return text;
		}
	}

}