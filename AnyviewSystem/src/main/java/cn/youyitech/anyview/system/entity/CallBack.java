package cn.youyitech.anyview.system.entity;

import javafx.collections.ObservableList;

import java.util.List;


/**
 * 回调接口，生成完报告时调用
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/3/4 18:34)
 */
public interface CallBack {

	void endCalculater(List<BatchDetectionResult> batchDetectionResults,
                       List<DetectionResult> drs);

    void endReport();

}

