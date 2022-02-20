package cn.youyitech.anyview.system.service;

import cn.youyitech.anyview.system.entity.ClassEntity;
import cn.youyitech.anyview.system.entity.CourseArrange;
import cn.youyitech.anyview.system.entity.CourseArrangeAndWorkingTable;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface SimilarityDetectionService {




    /**
     *
     * @Description: TODO(根据教师id获取班级)
     */
    public List<ClassEntity> getClassByTId(int tid);


    /**
     *
     * @Description: TODO(根据claid和couid获取作业表)
     **/

    public  List<CourseArrangeAndWorkingTable> getSchemeByClaIdAndCouId(int claid, int couid);

    /**
     *
     * @Description: TODO()
    @SuppressWarnings("rawtypes")
    */
    //public Pagination<SchemeContentTable> getSchemeContentPage(Map param);

    /**
     *
     * @Description: TODO(下载学生作业答案)
     */
    public boolean downloadAnswer(int cid, List<Long> ids, String path,HttpServletResponse response);
}
