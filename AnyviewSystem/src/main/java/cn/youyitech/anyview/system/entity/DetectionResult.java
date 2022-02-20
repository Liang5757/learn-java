package cn.youyitech.anyview.system.entity;

import cn.youyitech.anyview.system.utils.similarCalculations.MatchedTileSet;


/**
 * 存储两个源文件之间的相似度检测的结果
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/25 23:51)
 */
public class DetectionResult {

    /**
     * 相似度
     */
    private double similarity = 0.0;

    /**
     * 相同类型值的个数
     */
    private int count = 0;

    /**
     * 相同类型值的平均值
     */
    private double avg = 0.0;

    /**
     * 特征值，即sqrt((每个相同类型值-平均值)^2)
     */
    private double cluster = 0.0;

    /**
     * 存放两篇文档所有匹配字符对
     */
    private MatchedTileSet matches = null;

    /**
     * 文件A信息
     */
    private String fileTextA = null;

    /**
     * 文件B信息
     */
    private String fileTextB = null;
    
    /**
     * 文件A名字
     */
    private String fileNameA = null;

    /**
     * 文件B名字
     */
    private String fileNameB = null;
    
    
    /**
     * 文件A信息
     */
    private FileText batchFileTextA = null;

    /**
     * 文件B信息
     */
    private FileText batchFileTextB = null;

    public DetectionResult(String fileNameA,String fileNameB,String fileTextA, String fileTextB, MatchedTileSet matches, double similarity) {	//修改过fileTextA
        this.fileNameA=fileNameA;
        this.fileNameB=fileNameB;
    	this.fileTextA = fileTextA;
        this.fileTextB = fileTextB;
        this.matches = matches;
        this.similarity = similarity;
    }
    public DetectionResult(FileText batchFileTextA, FileText batchFileTextB, MatchedTileSet matches, double similarity) {	
        this.batchFileTextA = batchFileTextA;
        this.batchFileTextB = batchFileTextB;
        this.matches = matches;
        this.similarity = similarity;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getCluster() {
        return cluster;
    }

    public void setCluster(double cluster) {
        this.cluster = cluster;
    }

    public MatchedTileSet getMatches() {
        return matches;
    }

    public void setMatches(MatchedTileSet matches) {
        this.matches = matches;
    }

    public String getFileTextA() {
        return fileTextA;
    }

    public void setFileTextA(String fileTextA) {
        this.fileTextA = fileTextA;
    }

    public String getFileTextB() {
        return fileTextB;
    }

    public void setFileTextB(String fileTextB) {
        this.fileTextB = fileTextB;
    }
	public FileText getBatchFileTextA() {
		return batchFileTextA;
	}
	public void setBatchFileTextA(FileText batchFileTextA) {
		this.batchFileTextA = batchFileTextA;
	}
	public FileText getBatchFileTextB() {
		return batchFileTextB;
	}
	public void setBatchFileTextB(FileText batchFileTextB) {
		this.batchFileTextB = batchFileTextB;
	}
	public String getFileNameA() {
		return fileNameA;
	}
	public void setFileNameA(String fileNameA) {
		this.fileNameA = fileNameA;
	}
	public String getFileNameB() {
		return fileNameB;
	}
	public void setFileNameB(String fileNameB) {
		this.fileNameB = fileNameB;
	}
	
	
    
}







