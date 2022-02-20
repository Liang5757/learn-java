package cn.youyitech.anyview.system.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDetectionInfo {
    /**
     * 文档输入路径
     */
    private File inputPath;

    /**
     * 结果输出路径
     */
    private File                outputPath;

    /**
     * 每个文档路径与内容
     */
    private List<FileText> fileTexts;
    public BaseDetectionInfo(File inputPath, File outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.fileTexts = new ArrayList<>();
    }
    public void initFileTexts() {
        this.fileTexts = new ArrayList<>();
    }
    public void addFileText(FileText fileText) {
        this.fileTexts.add(fileText);
    }
}
