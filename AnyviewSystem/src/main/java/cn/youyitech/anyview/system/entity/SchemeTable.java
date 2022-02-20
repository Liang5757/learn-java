package cn.youyitech.anyview.system.entity;

import com.framework.loippi.mybatis.ext.annotation.Column;
import com.framework.loippi.mybatis.ext.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schemetable")
public class SchemeTable {
    private static final long serialVersionUID = 1L;
    @Column(name = "VID")
    private int VID;
    @Column(name = "VName")
    private String VName;
    @Column(name = "CourseID")
    private int CourseID;
    @Column(name = "kind")
    private int kind;
    @Column(name ="visit")
    private int visit;
    @Column(name = "FullScore")
    private int FullScore;
    @Column(name = "TotalNum")
    private  int TotalNum;
    @Column(name = "table_creater")
    private String Creater;
    @Column(name = "CreateTime")
    private Date CreateTime;
    /** 更新者 */
    @Column(name = "table_updater")
    private String Updater;
    /** 更新时间 */
    @Column(name = "UpdateTime")
    private Date UpdateTime;
    @Column(name = "Status")
    private int Status;
    /** 是否删除 */
    @Column(name = "Enabled")
    private boolean isDelete;

}
