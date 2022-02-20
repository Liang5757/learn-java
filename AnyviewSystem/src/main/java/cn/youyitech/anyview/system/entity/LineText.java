package cn.youyitech.anyview.system.entity;

/**
 * 存储一个文件中的一行
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/26 16:02)
 */
public class LineText {

    /**
     * 在文件中的行号
     */
    private int         line;

    /**
     * 该行的文本内容，注意需要存入换行符
     */
    private String      text;

    public LineText() {

    }

    public LineText(int line, String text) {
        this.line = line;
        this.text = text;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

