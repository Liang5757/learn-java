package cn.youyitech.anyview.system.entity;

import cn.youyitech.anyview.system.utils.TestLog4j;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * 存储一个文件中一行的语法结果
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/25 23:52)
 */
public class LineGrammar implements Cloneable{

	    /**
	     * 在文件中的行号
	     */
	    private int         line;

	    /**
	     * 该行的文法结果
	     */
	    private String      content;

	    public LineGrammar() {

	    }

	    public LineGrammar(int line, String content) {
	        this.line = line;
	        this.content = content;
	    }

	    @Override
	    public Object clone() {
	        LineGrammar o = null;
	        try {
	            //Object 中的clone()识别出要复制的是哪一个对象
	            o = (LineGrammar) super.clone();
	        } catch (CloneNotSupportedException e) {
	            Logger log = Logger.getLogger(TestLog4j.class);
	            PropertyConfigurator.configure("resource/log/log4j.properties");
	            log.error(e.toString());
	        }
	        return o;
	    }

	    public int getLine() {
	        return line;
	    }

	    public void setLine(int line) {
	        this.line = line;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

}
