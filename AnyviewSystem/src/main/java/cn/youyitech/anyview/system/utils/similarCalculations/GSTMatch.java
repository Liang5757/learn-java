package cn.youyitech.anyview.system.utils.similarCalculations;

/**
 * 存放GST算法相识的数据，代表两个字符串中一个公共子串
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/25 23:51)
 */
public class GSTMatch {

    /**
     * 在短的字符串里公共子串的开始位置
     */
    public final int    PatternStart;

    /**
     * 在长的字符串里公共子串的开始位置
     */
    public final int    DocumentStart;

    /**
     * 公共子串的长度
     */
    public final int    Length;

    /**
     * 初始化两个字符串的公共子串类
     * @param startPattern
     * @param startDocument
     * @param length
     */
    public GSTMatch(int startPattern, int startDocument, int length) {
        PatternStart = startPattern;
        DocumentStart = startDocument;
        Length = length;
    }

}
