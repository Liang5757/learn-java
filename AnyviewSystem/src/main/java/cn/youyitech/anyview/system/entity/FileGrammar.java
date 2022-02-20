package cn.youyitech.anyview.system.entity;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 存储一个文件的语法结果
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/25 23:51)
 */
public class FileGrammar {
	
	    /**
	     * 文件中有效的每一行文法及在文件中对应的行号
	     */
	    private List<LineGrammar>           lineGrammars = new ArrayList<>();

	    /**
	     *文件是否文法成功，true为文法成功，false为文法失败
	     */
	    private boolean                     flag = false;

	    /**
	     * 文件有效文法的行数
	     */
	    private int                         lineCount = 0;

	    /**
	     * 文件中有效文法序号的个数，除去“{”、“}”和“;”
	     */
	    private int                         typeCount = 0;

	    /**
	     * 文档路径与内容
	     */
	    private FileText      fileTexts;

	    public FileGrammar() {

	    }

	    public List<LineGrammar> getLineGrammars() {
	        return lineGrammars;
	    }

	    public void addLineGrammar(LineGrammar lineGrammar) {
	        this.lineGrammars.add(lineGrammar);
	        this.lineCount++;
	    }

	    public String getGrammarContent(){
	        if(CollectionUtils.isEmpty(this.lineGrammars)){
	            return "";
	        }
	        StringBuffer str = new StringBuffer();
	        for(LineGrammar lineGrammar : this.lineGrammars){
	            str.append(lineGrammar.getContent());
	        }
	        return str.toString();
	    }

	    public boolean isFlag() {
	        return flag;
	    }

	    public void setFlag(boolean flag) {
	        this.flag = flag;
	    }

	    public int getLineCount() {
	        return lineCount;
	    }

	    public int getTypeCount() {
	        return typeCount;
	    }

	    public void setTypeCount(int typeCount) {
	        this.typeCount = typeCount;
	    }

	    public FileText getFileTexts() {
	        return fileTexts;
	    }

	    public void setFileTexts(FileText fileTexts) {
	        this.fileTexts = fileTexts;
	    }
	
}
