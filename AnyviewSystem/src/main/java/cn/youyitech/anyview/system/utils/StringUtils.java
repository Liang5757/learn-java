package cn.youyitech.anyview.system.utils;

/**
 * 字符处理工具
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/27 14:55)
 */
public class StringUtils {

    /**
     * 将源字符串的"<",">"和"&"改为html的元素
     * @param str
     * @return
     */
    public static String stringToHtml(String str) {
        StringBuffer hb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '<':
                    hb.append("&lt;");
                    break;
                case '>':
                    hb.append("&gt;");
                    break;
                case '&':
                    hb.append("&amp;");
                    break;
                default:
                    hb.append(c);
            }
        }
        return hb.toString();
    }

}

