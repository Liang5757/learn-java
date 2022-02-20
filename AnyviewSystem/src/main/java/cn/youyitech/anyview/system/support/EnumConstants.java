package cn.youyitech.anyview.system.support;

public class EnumConstants {

	// 角色权限
	public enum authorityEnum {

		admin(-1), manager(1), teacher(0), student(3);

		private long value;

		private authorityEnum(long value) {
			this.value = value;
		}

		public long getValue() {
			return value;
		}

	}

	// 作业表题型
	public enum difficultyEnum {

		one(1), two(2), three(3), four(4), five(5);

		private int value;

		private difficultyEnum(int desc) {
			value = desc;
		}

		/**
		 * @return 当前枚举对象的值。
		 */
		public int getValue() {
			return value;
		}

	}

	// 公开级别
	public enum publiclevelEnum {

		secrecy("完全保密", 0), schoolOpen("公开给特定学校", 1), openAll("完全公开", 2), localSchoolOpen("本校公开", 3);

		private int value;

		private String text;

		private publiclevelEnum(String status, int desc) {
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

	// 题目状态
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

	// 考试编排种类
	public enum kindEnum {

		manual("手动", 0), automatic("自动", 1);

		private String text;

		private int value;

		private kindEnum(String status, int desc) {
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

	// 考试编排状态
	public enum examStatusEnum {

		notStarted("未开始", 0), starting("进行中", 1), pause("暂停中", 2), end("已结束", 3);

		private String text;

		private int value;

		private examStatusEnum(String status, int desc) {
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

	// 考试编排操作
	public enum operationEnum {

		start("开始", 0), pause("暂停", 1), keepOn("继续", 2), restart("重新开始", 3);

		private String text;

		private int value;

		private operationEnum(String status, int desc) {
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