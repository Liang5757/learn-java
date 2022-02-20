package cn.youyitech.anyview.system.support;

/**
 * 公共参数
 * 
 * @version 1.0
 */

public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd",
			"yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** application.config.xml文件路径 */
	public static final String FRAMEWORK_XML_PATH = "/application.config.xml";

	/** application.config.properties文件路径 */
	public static final String FRAMEWORK_PROPERTIES_PATH = "/application.config.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}
}