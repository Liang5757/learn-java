package cn.youyitech.anyview.system.entity;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * 存储一个文件，仅在构造时读取文件文本内容
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/26 16:04)
 */
public class FileText {

    /**
     * 文件的每一行及对应的行号（按插入顺序排序）
     */
    private List<LineText> lineTexts = new ArrayList<>();

    /**
     * 文件总行数
     */
    private int lineCount = 0;

    /**
     * 文本文本内容
     */
    private String textContent = "";

    private File file = null;

    private FileText(){

    }

    public FileText(File file){
        this.file = file;
        this.setLineTextByFile();
        this.setTextContent();
    }

    /**
     * 获取lineTexts文本内容
     * @return
     */
    private void setTextContent() {
        if (CollectionUtils.isEmpty(this.lineTexts)) {
            textContent = "";
        }
        else if (StringUtils.isEmpty(textContent)) {
            StringBuffer str = new StringBuffer();
            for (LineText lineText : this.lineTexts) {
                str.append(lineText.getText());
            }
            textContent = str.toString();
        }
    }

    /**
     * 根据文件名读取文件内容到lineTexts
     */
    private void setLineTextByFile() {
        if (file != null) {
        	try (InputStreamReader in = new InputStreamReader(new FileInputStream(file), "utf-8");
//          try (InputStreamReader in = new InputStreamReader(new FileInputStream(file), "GB2312");
//        	try (InputStreamReader in = new InputStreamReader(new FileInputStream(file), "GBK");
                 BufferedReader bReader = new BufferedReader(in)) {
                int lineCount = 1;
                String line = bReader.readLine();
                while (line != null) {
                    LineText lt = new LineText(lineCount, line + '\n');
                    this.addLineText(lt);
                    lineCount++;
                    line = bReader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addLineText(LineText lineText) {
        this.lineTexts.add(lineText);
        this.lineCount++;
    }

    public List<LineText> getLineTexts() {
        return lineTexts;
    }

    public int getLineCount() {
        return lineCount;
    }

    public String getTextContent() {
        return textContent;
    }

    public File getFile() {
        return file;
    }
}
