package cn.youyitech.anyview.system.entity;

/**
 * 存放批处理TableView每一行的组成数据
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/25 23:50)
 */
public class
BatchDetectionResult {
	
	/*标记符*/
	private Integer id;

    /**
     * 相似度
     */
    private Double          similarity = 0.0;

    /**
     * 相同类型值的个数
     */
    private Integer         count = 0;

    /**
     * 相同类型值的平均值
     */
    private Double          avg = 0.0;

    /**
     * 特征值，即sqrt((每个相同类型值-平均值)^2)
     */
    private Double          cluster = 0.0;

    /**
     * 文件A名字
     */
    private String          fileAname = "";

    /**
     * 文件B名字
     */
    private String          fileBname = "";

    /**
     * 输出路径
     */
    private String          outputLocal = "";

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Double getCluster() {
        return cluster;
    }

    public void setCluster(Double cluster) {
        this.cluster = cluster;
    }

    public String getFileAname() {
        return fileAname;
    }

    public void setFileAname(String fileAname) {
        this.fileAname = fileAname;
    }

    public String getFileBname() {
        return fileBname;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id=id;
	}

	public void setFileBname(String fileBname) {
        this.fileBname = fileBname;
    }

    public String getOutputLocal() {
        return outputLocal;
    }

    public void setOutputLocal(String outputLocal) {
        this.outputLocal = outputLocal;
    }


}

