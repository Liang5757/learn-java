package cn.youyitech.anyview.system.utils.similarCalculations;

import cn.youyitech.anyview.system.entity.DetectionResult;
import cn.youyitech.anyview.system.entity.FileText;

import java.util.ArrayList;
import java.util.List;


/**
 * GST算法生成类，单例
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/3/4 16:58)
 */
public class GSTGeneral {

    private static GSTGeneral gstGeneral = new GSTGeneral();

    private GSTGeneral() {

    }

    /**
     * 双重锁定实现单例
     * @return
     */
    public static GSTGeneral getGstGeneral() {
        if (gstGeneral == null) {
            synchronized (GSTGeneral.class) {
                if (gstGeneral == null) {
                    gstGeneral = new GSTGeneral();
                }
            }
        }
        return gstGeneral;
    }

    /**
     * 生成两篇文章的对比结果
     * @param fileTextA
     * @param fileTextB
     * @param minimumMatchLength
     * @return
     */
    public DetectionResult gstCompere(String fileNameA,String fileNameB,String fileTextA, String fileTextB,    //将FileText修改为String
                                      int minimumMatchLength) {
        List<GSTMatch> tilesFound = new ArrayList<>();
        GST gst = new GST(tilesFound, minimumMatchLength);
        int Count = gst.compare(fileTextA, fileTextB);
        double similarity = (fileTextA.length() > fileTextB
                .length()) ?
                ((double) Count / (double) fileTextA.length()) :
                ((double) Count / (double) fileTextB.length());
        DetectionResult dr = new DetectionResult(fileNameA,fileNameB,fileTextA, fileTextB, new MatchedTileSet(),
                similarity);
        dr.setAvg(0.0);
        dr.setCluster(0.0);
        dr.setCount(Count);
        return dr;
    }
    /**
     * 生成两篇文章的对比结果
     * @param fileTextA
     * @param fileTextB
     * @param minimumMatchLength
     * @return
     */
    public DetectionResult gstCompere(FileText fileTextA, FileText fileTextB,    //将FileText修改为String
                                      int minimumMatchLength) {
        List<GSTMatch> tilesFound = new ArrayList<>();
        GST gst = new GST(tilesFound, minimumMatchLength);
        int Count = gst.compare(fileTextA.getTextContent(), fileTextB.getTextContent());
        double similarity = (fileTextA.getTextContent().length() > fileTextB.getTextContent()
                .length()) ?
                ((double) Count / (double) fileTextA.getTextContent().length()) :
                ((double) Count / (double) fileTextB.getTextContent().length());
        DetectionResult dr = new DetectionResult(fileTextA, fileTextB, new MatchedTileSet(),
                similarity);
        dr.setAvg(0.0);
        dr.setCluster(0.0);
        dr.setCount(Count);
        return dr;
    }

}

