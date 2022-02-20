package cn.youyitech.anyview.system.utils.similarCalculations;

import cn.youyitech.anyview.system.entity.BatchDetectionResult;
import cn.youyitech.anyview.system.entity.CallBack;
import cn.youyitech.anyview.system.entity.DetectionResult;
import cn.youyitech.anyview.system.entity.FileGrammar;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 相似度计算者，CPU密集型
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/3/2 16:40)
 */
public class Calculater implements Runnable{
	
	/*递增变量，区分批量检测报告*/
	private static Integer id=0;
	
    /**
     * 存储检测结果
     */
    private List<DetectionResult> drs = null;

    private List<FileGrammar> fileGrammars = null;

    private Integer gst_k = 0;

    private List<BatchDetectionResult> batchDetectionResults = null;

    private GSTGeneral gstGeneral = GSTGeneral.getGstGeneral();

    private RowSelected rs = RowSelected.getRowSelected();

    private CallBack callBack;

    /**
     * 禁用默认构造器
     */
    private Calculater() {

    }

    public Calculater(List<FileGrammar> fileGrammars, Integer gst_k,
                      List<BatchDetectionResult> batchDetectionResults, CallBack callBack) {
        this.fileGrammars = fileGrammars;
        this.gst_k = gst_k;
        this.batchDetectionResults = batchDetectionResults;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        DetectionResult dr = null;
        drs = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");
        Integer sum = 1;
        //计算根据文法计算两篇文档的相似度
        for (int i = 0; i < fileGrammars.size(); i++) {
            for (int j = i + 1; j < fileGrammars.size(); j++) {

                //文法失败情况下使用GST算法
                if (fileGrammars.get(i).isFlag() == false
                    || fileGrammars.get(j).isFlag() == false) {
                    dr = gstGeneral.gstCompere(fileGrammars.get(i).getFileTexts(),
                            fileGrammars.get(j).getFileTexts(), gst_k);
                } else {
                    dr = rs.rowSelected(fileGrammars.get(i).getFileTexts(),
                            fileGrammars.get(j).getFileTexts(), fileGrammars.get(i),
                            fileGrammars.get(j), 0.75);
                }
                BatchDetectionResult bdr = new BatchDetectionResult();//构建值对象
                bdr.setId(++id);
                bdr.setAvg(Double.parseDouble(df.format(dr.getAvg())));
                bdr.setCluster(Double.parseDouble(df.format(dr.getCluster())));
                bdr.setSimilarity(Double.parseDouble(df.format(dr.getSimilarity() * 100)));
                bdr.setCount(dr.getCount());
                bdr.setFileAname(dr.getBatchFileTextA().getFile().getName());	//修改过，原为dr.getFileTextA()
                bdr.setFileBname(dr.getBatchFileTextB().getFile().getName());	//修改过，原为dr.getFileTextB()
                //输出地址
                //bdr.setOutputLocal("D:\\ceshi\\DetectionResult\\"+"detection"+sum+"\\index.html");
                //bdr.setOutputLocal("\\DetectionResult\\" + "detection" + sum + "\\index.html");
                batchDetectionResults.add(bdr);
                drs.add(dr);
                sum++;
            }
        }
        //批量报告结果设置完成后重新置为0
        id=0;
        callBack.endCalculater(batchDetectionResults, drs);
    }

    public List<BatchDetectionResult> getBatchDetectionResults() {
        return batchDetectionResults;
    }

    public List<DetectionResult> getDrs() {
        return drs;
    }
}

