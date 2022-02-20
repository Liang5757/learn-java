package cn.youyitech.anyview.system.utils.similarCalculations;

import cn.youyitech.anyview.system.entity.CallBack;
import cn.youyitech.anyview.system.entity.DetectionResult;
import cn.youyitech.anyview.system.utils.FileUtils;

import java.io.File;
import java.util.List;


/**
 * 报告生成者（多线程，消费计算结果，I/O密集型）
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/3/2 16:41)
 */
public class Reporter implements Runnable {

    /**
     * 存储检测结果
     */
    private List<DetectionResult> drs = null;

    private HtmlReport htmlReport = HtmlReport.getHtmlReport();

    private File baseDir = null;

    private CallBack callBack;

    /**
     * 禁用默认构造器
     */
    private Reporter() {

    }

    public Reporter(List<DetectionResult> drs, File baseDir, CallBack callBack) {
        this.drs = drs;
        this.baseDir = baseDir;
        File output = new File(baseDir.toString() + "\\DetectionResult");
        System.out.println("已经进入"+output);
        if (output.exists()) {
            FileUtils.deleteDir(output);
        }
        //注意，mkdirs才能建立二级或以上文件夹
        output.mkdirs();
        System.out.println("创建文件夹"+output.mkdir());
        this.baseDir = output;
        this.callBack = callBack;
        //htmlReport.generateBootStrap(output);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < drs.size(); i++) {
                DetectionResult dr = drs.get(i);
                htmlReport.generateReport(baseDir, dr, i + 1);
                dr = null;
            }
            callBack.endReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

