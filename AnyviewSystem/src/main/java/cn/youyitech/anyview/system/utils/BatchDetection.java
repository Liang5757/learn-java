package cn.youyitech.anyview.system.utils;

import static java.lang.Thread.sleep;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.youyitech.anyview.system.entity.BaseDetectionInfo;
import cn.youyitech.anyview.system.entity.BatchDetectionResult;
import cn.youyitech.anyview.system.entity.CallBack;
import cn.youyitech.anyview.system.entity.DetectionResult;
import cn.youyitech.anyview.system.entity.FileGrammar;
import cn.youyitech.anyview.system.entity.FileText;
import cn.youyitech.anyview.system.entity.LanguageEnum;
import cn.youyitech.anyview.system.utils.similarCalculations.AnyviewC;
import cn.youyitech.anyview.system.utils.similarCalculations.Calculater;
import cn.youyitech.anyview.system.utils.similarCalculations.Reporter;

public class BatchDetection implements CallBack {
    private Integer gst_k=10;
    public List<BatchDetectionResult> batchDetectionResults;
    private File[] files;
    private String contextPath;
    //存放需要检测的文档的信息
    //BaseDetectionInfo baseInfo = new BaseDetectionInfo(new File(".\\"), new File(".\\"));
    BaseDetectionInfo baseInfo;
    
    public BatchDetection(File[] files,String contextPath){
        this.files=files;
        this.contextPath=contextPath;
        baseInfo = new BaseDetectionInfo(new File(".\\"), new File(contextPath));
    }
    public List<BatchDetectionResult> Detection() throws Exception {
        ArrayList children = new ArrayList();
        FileUtils.getFolder(this.files, children, LanguageEnum.C);
        //children包含了各个文件路径的集合
        System.out.println("children.size:"+children.size());
        if (children.size() <= 1) {
            return null;
        } else {
            System.out.println("START");

            baseInfo.initFileTexts();
            System.out.println("正在读取文件......\n");
            for (int i = 0; i < children.size(); i++) {
                System.out.println(children.get(i).toString());
                FileText ft = new FileText(new File(children.get(i).toString()));
                //每个文档的内容
                baseInfo.addFileText(ft);
            }
            List<FileGrammar> fileGrammars = new ArrayList<>();
            System.out.println("文件读取完成，正在调用AnyviewC编译器进行文法编译......\n");
            AnyviewC anyc = new AnyviewC();
            for (FileText fileText : baseInfo.getFileTexts()) {
                FileGrammar fileGrammar = anyc.ac(fileText.getTextContent());
                fileGrammar.setFileTexts(fileText);
                fileGrammars.add(fileGrammar);
            }
            System.out.println("文法编译完成，正在计算代码相似度......\n");
            List<BatchDetectionResult> batchDetectionResults =new ArrayList<>();
            Calculater calculater = new Calculater(fileGrammars, gst_k, batchDetectionResults, BatchDetection.this);
            Thread calculate = new Thread(calculater);
            System.out.println("6666666");
            calculate.start();
            //暂时设置休眠，等待批量检测完毕。不然batchDetectionResults可能返回空

            while (calculate.isAlive()){
                System.out.println("s");
            }

            return batchDetectionResults;
        }


    }
    @Override
    public void endCalculater(List<BatchDetectionResult> batchDetectionResults, List<DetectionResult> drs) {
        this.batchDetectionResults=batchDetectionResults;
        System.out.println("代码相似度计算完成，正在生成检测报告......\n");
        //生成报告
        //System.out.println(baseInfo.getOutputPath().toString());
        Reporter reporter = new Reporter(drs, baseInfo.getOutputPath(), BatchDetection.this);
        //System.out.println(contextPath);
        Thread report = new Thread(reporter);
        report.start();
    }

    @Override
    public void endReport() {

    }
    

}
