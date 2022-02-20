package cn.youyitech.anyview.system.support;

public class AdminEnum {

	public enum authorityEnum {

		schoolManager(1), superManager(-1), teacher(0), student(3);

		private int value;

		private authorityEnum(int desc) {
			value = desc;
		}

		public int getValue() {
			return value;
		}
	}

}
