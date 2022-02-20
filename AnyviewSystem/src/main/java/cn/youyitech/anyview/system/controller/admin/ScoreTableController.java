package cn.youyitech.anyview.system.controller.admin;

import cn.youyitech.anyview.system.dto.AllTotalScore;
import cn.youyitech.anyview.system.dto.AttitudeCondition;
import cn.youyitech.anyview.system.dto.PaperScore;
import cn.youyitech.anyview.system.dto.TotalScore;
import cn.youyitech.anyview.system.entity.*;
import cn.youyitech.anyview.system.service.*;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.IdsUtils;
import cn.youyitech.anyview.system.utils.PullParserXmlUtils;
import cn.youyitech.anyview.system.utils.SortUtil;
import com.alibaba.fastjson.JSON;
import com.framework.loippi.support.Pageable;
import javolution.util.Index;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.framework.loippi.support.Pageable;

import javax.annotation.Resource;
import javax.naming.directory.InvalidAttributeIdentifierException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author TT 综合评分 Controller 2017年8月22日
 */
@Controller
@RequestMapping("/admin/ScoreTable")
public class ScoreTableController extends GenericController {


    @Autowired
    ScoreTableService scoreTableService;

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TeacherService teacherService;

    @Resource
    private ExerciseService exerciseService;

    @Autowired
    private CourseArrangeAndWorkingTableService courseArrangeAndWorkingTableService;

    @Resource
    private CourseArrangeService courseArrangeService;

    @Autowired
    private IdsUtils idsUtils;

    @Autowired
    private WorkingTableService workingTableService;

    @Autowired
    private SchemeContentTableService schemeContentTableService;

    @Autowired
    private CourseService courseService;
    @Autowired
    private ClassAndStudentService classAndStudentService;
    @Autowired
    private StudentService studentService;
    @Resource
    private PullParserXmlUtils parserUtils;

    private Long OnecontentId;
    private String cId;//班级ID
    private List<CourseArrange> courseArrangelist;//级联保存的课程列表


    // 获取成绩内容
    @RequestMapping("/scoreContent")
    public String managerContent(Model model, Pageable pageable, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        // 班级查询
        String classId = request.getParameter("filter_cID");
        cId = classId;
        // 课程查询(要转码，否则会出现乱码)
        String courseNameOne = null;
        try {
            String filterCourseId = (String)session.getAttribute("filter_courseId");
            String filter_courseId = request.getParameter("filter_courseId");
            if ( filter_courseId!= null && !filter_courseId.trim().isEmpty()) {
                courseNameOne = URLDecoder.decode(request.getParameter("filter_courseId"), "utf-8");
            }else if (filterCourseId != null){
                courseNameOne = URLDecoder.decode(filterCourseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 作业表查询
        String workingTableId = request.getParameter("filter_vID");

        System.out.println(classId + "------" + courseNameOne + "----------" + workingTableId);
        System.out.println("---------");
        // 按照查询条件筛选数据
        Teacher teacher = teacherService.find("username", systemUserService.getCurrentUserName());
        //通过老师的id获取需要教学的课程集合
        List<CourseArrange> courseArrangeList = courseArrangeService.findList("teacher_id", teacher.getId());
        List<WorkingTable> workingTableList = new ArrayList<>();
        List<String> courseNames = new ArrayList<>();
        List<ClassEntity> classSystemList = new ArrayList<>();
        for (int i = 0; i < courseArrangeList.size(); i++) {
            //获取改课程编排对应的作业表
            List<CourseArrangeAndWorkingTable> courseArrangeAndWorkingTableList = courseArrangeAndWorkingTableService
                    .findList("courseArrangeId", courseArrangeList.get(i).getId());
            //课程名字
            courseNames.add(courseArrangeList.get(i).getCourse().getCourseName());
            //班级名字
            classSystemList.add(courseArrangeList.get(i).getClassSystem());
            for (int j = 0; j < courseArrangeAndWorkingTableList.size(); j++) {
                //作业表名字
                workingTableList.add(courseArrangeAndWorkingTableList.get(j).getWorkingTable());
            }
        }
        // 教师自己添加的作业表
        List<WorkingTable> workingTableByCreaterList = workingTableService.findList("tableCreater",
                systemUserService.getCurrentUser().getName());
        for (int i = 0; i < workingTableByCreaterList.size(); i++) {
            workingTableList.add(workingTableByCreaterList.get(i));
        }
        // 作业表去重
        if (workingTableList.size() > 1) {
            List<WorkingTable> classSystemListNew = new ArrayList<>();
            for (WorkingTable workingTable : workingTableList) {
                if (!classSystemListNew.contains(workingTable)) {
                    classSystemListNew.add(workingTable);
                }
            }
            workingTableList = classSystemListNew;
        }
        // 班级去重
        if (classSystemList.size() > 1) {
            List<ClassEntity> classSystemListNew = new ArrayList<>();
            for (ClassEntity classSystem : classSystemList) {
                if (!classSystemListNew.contains(classSystem)) {
                    classSystemListNew.add(classSystem);
                }
            }
            classSystemList = classSystemListNew;
        }
        // 课程名字去重
        if (courseNames.size() > 1) {
            List<String> courseNamesNew = new ArrayList<>();
            for (String courseName : courseNames) {
                if (!courseNamesNew.contains(courseName)) {
                    courseNamesNew.add(courseName);
                }
            }
            courseNames = courseNamesNew;
        }
        List<ScoreTable> scoreTableList = new ArrayList<>();

        if (classId != null && !classId.equals("") || courseNameOne != null && !courseNameOne.equals("") || workingTableId != null && !workingTableId.equals("")) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("cID", classId);
            map1.put("vID", workingTableId);
            scoreTableList = scoreTableService.findList(map1);
            if (scoreTableList == null) {
                return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" 
                		+ request.getParameter("filter_cID") + "&filter_courseId=" + request.getParameter("filter_courseId") 
                			+ "&filter_vID=" + request.getParameter("filter_vID");

            }


        } else if (classId != "" && classId != null) {

        }



        //选择窗口的所选条件
        model.addAttribute("classId", classId);
        model.addAttribute("courseName", courseNameOne);
        model.addAttribute("workingTableId", workingTableId);
        //用于提供选择窗口的数据
        model.addAttribute("classList", classSystemList);
        model.addAttribute("courseNames", courseNames);
        model.addAttribute("workingTableList", workingTableList);
        processQueryConditions(pageable, request);
        Map map = (Map) pageable.getParameter();
        pageable.setParameter(map);

        List<Long> scoreTableIds = new ArrayList<>();
        List<Integer> workingTanleIds = new ArrayList<>();
        for (int i = 0; i < scoreTableList.size(); i++) {
            scoreTableIds.add(scoreTableList.get(i).getId());
            workingTanleIds.add(scoreTableList.get(i).getVID());
        }
        // 去除重复的作业表id
        if (workingTanleIds.size() > 1) {
            List<Integer> workingTanleIdsNew = new ArrayList<>();
            for (Integer id : workingTanleIds) {
                if (!workingTanleIdsNew.contains(id)) {
                    workingTanleIdsNew.add(id);
                }
            }
            workingTanleIds = workingTanleIdsNew;
        }
        // 按不同的作业表各自排名
        ScoreTable scoreTable = new ScoreTable();
        scoreTable.setScoreTableIds(scoreTableIds);
        List<ScoreTable> scoreTableListOld = new ArrayList<>();
        for (int i = 0; i < workingTanleIds.size(); i++) {
            scoreTable.setVID(workingTanleIds.get(i));
            // 排名
            scoreTableService.updateRankByScore(scoreTable);
            // 查找已经排名的记录
            scoreTableList = scoreTableService.findRankByScore(scoreTable);
            System.out.println("已排名：" + scoreTableList.get(0));
            for (int j = 0; j < scoreTableList.size(); j++) {
                scoreTableListOld.add(scoreTableList.get(j));
            }
        }

        List<ScoreTable> scoreTableListNew = new ArrayList<>();
        for (int i = 0; i < scoreTableListOld.size(); i++) {
            ScoreTable oldScoreTable = scoreTableListOld.get(i);
            for (int j = 0; j < courseArrangeList.size(); j++) {
                if (courseArrangeList.get(j).getClass_id() == oldScoreTable.getCID()) {
                    scoreTableListNew.add(oldScoreTable);
                    break;
                }
            }
        }
        SortUtil.sort(scoreTableListNew, false, "score");
        for (int i = 0; i < scoreTableListNew.size(); i++) {
            scoreTableListNew.get(i).setRank(i + 1);
        }
        model.addAttribute("page", scoreTableService.pageMethod(pageable, scoreTableListNew));
        redisService.save(systemUserService.getCurrentUserName() + "ttpageScoreTableList",
                JSON.toJSONString(scoreTableListNew));
        model.addAttribute("params", map);


        // 计算态度分条件绑定
        List<AttitudeCondition> attitudeConditionList = idsUtils.attitudeConditionJsonToList(
                redisService.get(systemUserService.getCurrentUserName() + "attitudeCondition"),
                AttitudeCondition.class);
        AttitudeCondition attitudeCondition = new AttitudeCondition();
        if (attitudeConditionList != null) {
            attitudeCondition = attitudeConditionList.get(0);
        }
        model.addAttribute("attitudeCondition", attitudeCondition);
        // 计算综合分条件绑定
        List<TotalScore> totalScoreList = idsUtils.totalScoreJsonToList(
                redisService.get(systemUserService.getCurrentUserName() + "totalScoreCondition"), TotalScore.class);
        TotalScore totalScore = new TotalScore();
        if (totalScoreList != null) {
            totalScore = totalScoreList.get(0);
        }
        model.addAttribute("totalScore", totalScore);

        // 计算所有学生综合分条件绑定
        List<AllTotalScore> allTotalScoreList = idsUtils.allTotalScoreJsonToList(
                redisService.get(systemUserService.getCurrentUserName() + "allTotalScoreCondition"), AllTotalScore.class);
        AllTotalScore allTotalScore = new AllTotalScore();
        if (allTotalScoreList != null) {
            allTotalScore = allTotalScoreList.get(0);
        }
        model.addAttribute("allTotalScore", allTotalScore);
        return "admin/scoretable/scoretableList";
    }

    // 确定计算卷面分
    @RequestMapping(value = {"/paperScore"}, method = {RequestMethod.GET})
    public String paperScore(PaperScore paperScore, HttpServletRequest request) {
        // 班级查询
        String filter_cID = request.getParameter("paperScoreFilter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("paperScoreFilter_courseId");
        // 作业表查询
        String filter_vId = request.getParameter("paperScoreFilter_vId");

        // ids查询
        String idsStr = request.getParameter("ids");

        //将StringArray的ids转换为LongArray
        String[] idsStrArr = idsStr.split(",");
        Long[] ids = new Long[idsStrArr.length];
        for (int i = 0; i < idsStrArr.length; i++) {
            ids[i] = Long.parseLong(idsStrArr[i]);
        }



        List<SchemeContentTable> schemeContentlist = schemeContentTableService.findList("VID", filter_vId);
        int num = schemeContentlist.size();
        float count = 100.0f / num;
        if (ids.length != 0) {
            for (Long id : ids) {
                ScoreTable scoreTable = scoreTableService.find(id);
                // 整个作业表的卷面分
                scoreTable.setPaperScore(scoreTable.getPassNum() * count);
                scoreTableService.update(scoreTable);
            }
        }

        try {
            if (filter_courseId != null) {
                filter_courseId = URLEncoder.encode(filter_courseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vId;
    }


    // 确定计算所有学生卷面分
    @RequestMapping(value = {"/allPaperScore"}, method = {RequestMethod.GET})
    public String allPaperScore( HttpServletRequest request) {
        // 班级查询
        String filter_cID = request.getParameter("filter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("filter_courseId");
        // 作业表查询
        String filter_vId = request.getParameter("filter_vId");

                Map<String, Object> map = new HashMap<>();
                map.put("cID", Integer.parseInt(filter_cID));
                map.put("vID", Integer.parseInt(filter_vId));
                List<ScoreTable> scoreTableList = scoreTableService.findList(map);
                //分数保留一位小数
                DecimalFormat format = new DecimalFormat("#.0");
                List<SchemeContentTable> schemeContentlist = schemeContentTableService.findList("VID", scoreTableList.get(0).getVID());
                int num = schemeContentlist.size();
                float count = 100.0f / num;
                for (ScoreTable scoreTable : scoreTableList){
                    // 整个作业表的卷面分
                    scoreTable.setPaperScore(Float.parseFloat(format.format(scoreTable.getPassNum() * count)));
                    scoreTableService.update(scoreTable);
                }
        try {
            if (filter_courseId != null) {
                filter_courseId = URLEncoder.encode(filter_courseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vId;
    }

    // 计算态度分对话框
    @RequestMapping(value = {"/attitudeDialog"}, method = {RequestMethod.GET})
    public String attitudeDialog() {
        return "/admin/scoretable/attitudeDialog";
    }

    // 计算态度分
    @RequestMapping(value = {"/attitude"}, method = {RequestMethod.GET})
    public String attitude(  HttpServletRequest request) {
        // 班级查询
        String filter_cID = request.getParameter("filter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("filter_courseId");
        // 作业表查询

        String filter_vId = request.getParameter("filter_vId");
        // ids查询
        String idsStr = request.getParameter("ids");

        //将StringArray的ids转换为LongArray
        String[] idsStrArr = idsStr.split(",");
        Long[] ids = new Long[idsStrArr.length];
        for (int i = 0; i < idsStrArr.length; i++) {
            ids[i] = Long.parseLong(idsStrArr[i]);
        }

        //获取态度分评分条件
        List<AttitudeCondition> attitudeConditionList = idsUtils.attitudeConditionJsonToList(
                redisService.get(systemUserService.getCurrentUserName() + "attitudeCondition"), AttitudeCondition.class);
        AttitudeCondition attitudeCondition = attitudeConditionList.get(0);

        //若为设置评分条件，则设置默认评分条件
        if (attitudeCondition == null) {
            attitudeCondition = new AttitudeCondition(60, 100, ids);
        }

        float element = attitudeCondition.getHighScore() - attitudeCondition.getLowScore();
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        if (ids.length > 0) {
            for (Long id : ids) {
                ScoreTable scoreTable = scoreTableService.find(id);
                List<Exercise> exerciseList = searchExercise(scoreTable);
                float count = 0.0f;
                int fastTime = 0;
                if (exerciseList.size() > 0) {
                    // 找出最先完成者的时间
                    fastTime = exerciseList.get(0).getAccumTime();
                    for (int j = 0; j < exerciseList.size(); j++) {
                        if (exerciseList.get(j).getAccumTime() < fastTime) {
                            if (exerciseList.get(j).getRunResult() == 1) {
                                fastTime = exerciseList.get(j).getAccumTime();
                            }
                        }
                    }
                }

                // 每道题的规定时间
                int time = scoreTable.getTotalTime() / scoreTable.getWorkingTable().getTotalNum();
                //作为计算每分钟分值的分母
                int denominator = time - fastTime;

                for (int i = 0; i < exerciseList.size(); i++) {
                    // 每道题的实际完成时间
                    int realTime = exerciseList.get(i).getAccumTime();

                    // 每道题的态度分
                    float score = 0;

                    // 保存每道题的态度分，不通过的态度分为0分
                    if (exerciseList.get(i).getRunResult() == 1) {
                        if (realTime < time) {
//                        score = attitudeCondition.getLowScore() + element / denominator * (10 - realTime);
                            //(element / denominator)为分数段/时间间隔，计算得出每分钟对应的分值
                            score = attitudeCondition.getLowScore() + ((element / denominator) * (time - realTime));
                            //保留一位小数
                            score = Float.parseFloat(decimalFormat.format(score));
                        }
//                        exerciseList.get(i).setAttitudeScore(score);
                    }
//                    else {
//                        exerciseList.get(i).setAttitudeScore(0);
//                    }
//                    exerciseService.update(exerciseList.get(i));
                    // 累计每道题的态度分
                    count = count + score;
                }
                // 作业表的态度分
                float attitudeScore = 0;
                if (exerciseList.size() > 0) {
                    attitudeScore = count / exerciseList.size();
                    //保留一位小数
                    attitudeScore = Float.parseFloat(decimalFormat.format(attitudeScore));
                }
                scoreTable.setAttitudeScore(attitudeScore);
                scoreTableService.update(scoreTable);
            }
        }
        try {
            if (filter_courseId != null) {
                filter_courseId = URLEncoder.encode(filter_courseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vId;

    }

    //计算所有学生态度分
    @RequestMapping(value = {"/allAttitude"}, method = {RequestMethod.GET})
    public String allAttitude( HttpServletRequest request) {
        // 班级查询
        String filter_cID = request.getParameter("filter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("filter_courseId");
        // 作业表查询
        String filter_vId = request.getParameter("filter_vId");

        //获取态度分评分条件
        List<AttitudeCondition> attitudeConditionList = idsUtils.attitudeConditionJsonToList(
                redisService.get(systemUserService.getCurrentUserName() + "attitudeCondition"), AttitudeCondition.class);
        AttitudeCondition attitudeCondition = attitudeConditionList.get(0);

        //若为设置评分条件，则设置默认评分条件
        if (attitudeCondition == null) {
            attitudeCondition = new AttitudeCondition(60, 100, null);
        }
        //计算每分钟分值时的分子(分数段)
        float element = attitudeCondition.getHighScore() - attitudeCondition.getLowScore();

        //将查询班级分数表的条件存放于map中
        Map<String, Object> map = new HashMap<>();
        map.put("cID", Integer.parseInt(filter_cID));
        map.put("vID", Integer.parseInt(filter_vId));
        List<ScoreTable> scoreTableList = scoreTableService.findList(map);
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        for (ScoreTable scoreTable : scoreTableList) {
            List<Exercise> exerciseList = searchExercise(scoreTable);
            float count = 0.0f;
            int fastTime = 0;
            if (exerciseList.size() > 0) {
                // 找出最先完成者的时间
                fastTime = exerciseList.get(0).getAccumTime();
                for (int j = 0; j < exerciseList.size(); j++) {
                    if (exerciseList.get(j).getAccumTime() < fastTime) {
                        if (exerciseList.get(j).getRunResult() == 1) {
                            fastTime = exerciseList.get(j).getAccumTime();
                        }
                    }
                }
            }

            for (int i = 0; i < exerciseList.size(); i++) {
                // 每道题的实际完成时间
                int realTime = exerciseList.get(i).getAccumTime();

                // 每道题的规定时间
                int time = scoreTable.getTotalTime() / scoreTable.getWorkingTable().getTotalNum();
                int denominator = time - fastTime;
                // 每道题的态度分
                float score = 0;
                if (exerciseList.get(i).getRunResult() == 1) {
                    if (realTime < time) {
//                        score = attitudeCondition.getLowScore() + element / denominator * (10 - realTime);
                        //(element / denominator)为分数段/时间间隔，计算得出每分钟对应的分值
                        score = attitudeCondition.getLowScore() + ((element / denominator) * (time - realTime));
                        //保留一位小数
                        score = Float.parseFloat(decimalFormat.format(score));
                    }
                }

                // 累计每道题的态度分
                count = count + score;
            }
            // 作业表的态度分
            float attitudeScore = 0;
            if (exerciseList.size() > 0) {
                attitudeScore = count / exerciseList.size();
                //保留一位小数
                attitudeScore = Float.parseFloat(decimalFormat.format(attitudeScore));
            }
            scoreTable.setAttitudeScore(attitudeScore);
            scoreTableService.update(scoreTable);
        }
        try {
            if (filter_courseId != null) {
                filter_courseId = URLEncoder.encode(filter_courseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vId;
    }

    // 计算综合分
    @RequestMapping(value = {"/totalScore"}, method = {RequestMethod.GET})
    public String totalScore( HttpServletRequest request) {
        // 班级查询
        String filter_cID = request.getParameter("totalScoreFilter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("totalScoreFilter_courseId");
        // 作业表查询
        String filter_vId = request.getParameter("totalScoreFilter_vId");
        String idsStr = request.getParameter("ids");
        String[] idsStrArr = idsStr.split(",");
        Long[] ids = new Long[idsStrArr.length];
        for (int i = 0; i < idsStrArr.length; i++) {
            ids[i] = Long.parseLong(idsStrArr[i]);
        }

        List<TotalScore> totalScoreList = idsUtils.totalScoreJsonToList(
                redisService.get(systemUserService.getCurrentUserName() + "totalScoreCondition"), TotalScore.class);
        TotalScore totalScore = totalScoreList.get(0);

        //若未设置评分
        if (totalScore == null) {
            totalScore = new TotalScore(3, 50, 50, 0, 0, 0, 0, 0, ids);
        }

        if (ids.length > 0) {
            for (Long id : ids) {
                ScoreTable scoreTable = scoreTableService.find(id);
                // 满分条件
                if (totalScore.getFullMark() == 1 && scoreTable.getPassNum() >= totalScore.getFullScoreCount()) {
                    scoreTable.setScore(100);
                } // 及格条件
                else if (totalScore.getExamination() == 1 && scoreTable.getPassNum() == totalScore.getPassItemCount()) {
                    // 及格分totalScore.getPassScore()
                    scoreTable.setScore(totalScore.getPassScore());
                } else {
                    // 综合分只取卷面分
                    if (totalScore.getSelectOne() == 1) {
                        scoreTable.setScore(scoreTable.getPaperScore());

                        // 综合分只取态度分
                    } else if (totalScore.getSelectOne() == 2) {
                        scoreTable.setScore(scoreTable.getAttitudeScore());

                        // 综合分卷面分*态度分
                    } else {
                        float paperPercent = totalScore.getPaperPercent() / 100;
                        float attitudePercent = totalScore.getAttitudePercent() / 100;
                        scoreTable.setScore(scoreTable.getAttitudeScore() * attitudePercent
                                + scoreTable.getPaperScore() * paperPercent);
                    }
                }
                scoreTableService.update(scoreTable);
            }
        }
        try {
            if (filter_courseId != null) {
                filter_courseId = URLEncoder.encode(filter_courseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vId;

    }
    // 计算所有学生综合分
    @RequestMapping(value = {"/allTotalScore"}, method = {RequestMethod.GET})
    public String allTotalScore( HttpServletRequest request) {
        // 班级查询
        String filter_cID = request.getParameter("filter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("filter_courseId");
        // 作业表查询
        String filter_vId = request.getParameter("filter_vId");


        List<TotalScore> totalScoreList = idsUtils.totalScoreJsonToList(
                redisService.get(systemUserService.getCurrentUserName() + "totalScoreCondition"), TotalScore.class);
        TotalScore allTotalScore = totalScoreList.get(0);
        if (allTotalScore == null) {
            // 一键计算默认的条件
            allTotalScore = new TotalScore(3, 50, 50, 0, 0, 0, 0, 0,null);
        }

//        if (totalScore.getIds().length > 0) {
//            for (Long id : totalScore.getIds()) {
        Map<String, Object> map = new HashMap<>();
        map.put("cID", Integer.parseInt(filter_cID));
        map.put("vID", Integer.parseInt(filter_vId));
        List<ScoreTable> scoreTableList = scoreTableService.findList(map);
        DecimalFormat format = new DecimalFormat("#.0");
            for (ScoreTable scoreTable : scoreTableList) {
                // 满分条件
                if (allTotalScore.getFullMark() == 1 && scoreTable.getPassNum() >= allTotalScore.getFullScoreCount()) {
                    scoreTable.setScore(100);
                } // 及格条件
                else if (allTotalScore.getExamination() == 1 && scoreTable.getPassNum() == allTotalScore.getPassItemCount()) {
                    // 及格分totalScore.getPassScore()
                    scoreTable.setScore(allTotalScore.getPassScore());
                } else {

                    // 综合分只取卷面分
                    if (allTotalScore.getSelectOne() == 1) {
                        scoreTable.setScore(scoreTable.getPaperScore());

                        // 综合分只取态度分
                    } else if (allTotalScore.getSelectOne() == 2) {
                        scoreTable.setScore(scoreTable.getAttitudeScore());
                        List<Exercise> exerciseList = searchExercise(scoreTable);

                    // 综合分卷面分*态度分
                    } else {
                        float paperPercent = allTotalScore.getPaperPercent() / 100;
                        float attitudePercent = allTotalScore.getAttitudePercent() / 100;
                        scoreTable.setScore(Float.parseFloat(format.format(scoreTable.getAttitudeScore() * attitudePercent
                                + scoreTable.getPaperScore() * paperPercent)));
                    }
                }
                scoreTableService.update(scoreTable);
            }

//            }
//        }
        try {
            if (filter_courseId != null) {
                filter_courseId = URLEncoder.encode(filter_courseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vId;
    }

    // 一键计算
    @RequestMapping(value = {"/oneKey"}, method = {RequestMethod.GET})
    public String oneKey(Long[] ids, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // 班级查询
        String filter_cID = request.getParameter("filter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("filter_courseId");
        // 作业表查询
        String filter_vId = request.getParameter("filter_vId");

        System.out.println(filter_cID + "------" + filter_courseId + "----------" + filter_vId);

        TotalScore totalScore = null;
        AttitudeCondition attitudeCondition = null;
        PaperScore pageScore = null;
        //计算卷面分
        paperScore(pageScore, request);
        //计算态度分
        attitude(  request);
        //计算综合分
        totalScore(  request);

        try {
            if (filter_courseId != null) {
                filter_courseId = URLEncoder.encode(filter_courseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vId;

    }


    // 一键计算所有学生
    @RequestMapping(value = {"/oneKeyAll"}, method = {RequestMethod.GET})
    public String oneKeyAll( HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // 班级查询
        String filter_cID = request.getParameter("filter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("filter_courseId");
        // 作业表查询
        String filter_vId = request.getParameter("filter_vId");

        System.out.println(filter_cID + "------" + filter_courseId + "----------" + filter_vId);

        AllTotalScore allTotalScore = null;
        AttitudeCondition attitudeCondition = null;
        PaperScore pageScore = null;
        //计算所有学生卷面分
        allPaperScore( request);
        //计算所有学生态度分
        allAttitude( request);
        //计算所有学生综合分
        allTotalScore( request);

        try {
            if (filter_courseId != null) {
                filter_courseId = URLEncoder.encode(filter_courseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vId;
    }

    //一键更新学生信息
    @RequestMapping(value = {"/oneUpdate"}, method = {RequestMethod.GET})
    public String oneUpdate(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // 班级查询
        String filter_cID = request.getParameter("filter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("filter_courseId");
        // 作业表查询
        String filter_vID = request.getParameter("filter_vId");

//        // 按照查询条件筛选数据
//        Teacher teacher = teacherService.find("username", systemUserService.getCurrentUserName());
//        //通过老师的id获取需要教学的课程集合
//        List<CourseArrange> courseArrangeList = courseArrangeService.findList("teacher_id", teacher.getId());
//        List<WorkingTable> workingTableList = new ArrayList<>();
//        for (int i = 0; i < courseArrangeList.size(); i++) {
//            //获取该课程编排对应的作业表
//            List<CourseArrangeAndWorkingTable> courseArrangeAndWorkingTableList = courseArrangeAndWorkingTableService
//                    .findList("courseArrangeId", courseArrangeList.get(i).getId());
//            for (int j = 0; j < courseArrangeAndWorkingTableList.size(); j++) {
//                //作业表名字
//                workingTableList.add(courseArrangeAndWorkingTableList.get(j).getWorkingTable());
//            }
//        }
        // 教师自己添加的作业表
//        List<WorkingTable> workingTableByCreaterList = workingTableService.findList("tableCreater",
//                systemUserService.getCurrentUser().getName());
//        for (int i = 0; i < workingTableByCreaterList.size(); i++) {
//            workingTableList.add(workingTableByCreaterList.get(i));
//        }

        //更新scoreTable表格的数据
        String currentUserName = systemUserService.getCurrentUserName();

//		for(int i=0;i<workingTableList.size();i++) {
//			int vid=workingTableList.get(i).getId().intValue();

        //先查询该作业表所有的学生ID
        List<ClassAndStudent> classAndStudentList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        int vId = -1;
        int cId = -1;
        if ((filter_vID != null) && !filter_vID.equals("")) {
            vId = Integer.parseInt(filter_vID);
        }
        if ((filter_cID != null) && !filter_cID.equals("")) {
            cId = Integer.parseInt(filter_cID);
            classAndStudentList = classAndStudentService.findList("student_class_id", Integer.parseInt(filter_cID));
        }
        map.put("vId", vId);
        map.put("cId", cId);
//
//        Map<Integer, Integer> sIdAndCIdMap = new HashMap<>();
//        for (int m = 0; m < exerciseList.size(); m++) {
//            int sid = exerciseList.get(m).getSId();
//            int cid = exerciseList.get(m).getCId();
//            if (!sIdAndCIdMap.containsKey(sid)) {
//                sIdAndCIdMap.put(sid, cid);
//            }
//        }

//        for (int key : sIdAndCIdMap.keySet()) {
        List<ScoreTable> scoreTableList = new ArrayList<>();
        //通过SID、CID、VID多条件查询，获取该学生该课程的所有习题
        for (int j = 0; j < classAndStudentList.size(); j++) {
            int sId = classAndStudentList.get(j).getStudent_id();
            map.put("sId", sId);
            List<Exercise> exerciseList = exerciseService.findList(map);
            int pass = 0;
            int totalTime=0;
            for (int k = 0; k < exerciseList.size(); k++) {
                totalTime += exerciseList.get(k).getAccumTime();
                if (exerciseList.get(k).getRunResult() == 1) {
                    pass++;
                }
            }
            ScoreTable scoreTable = new ScoreTable();
            scoreTable.setSID(sId);
            scoreTable.setVID(vId);
            scoreTable.setCID(cId);
            scoreTable.setTotalTime(totalTime);
            scoreTable.setPassNum(pass);
            scoreTable.setCorrectFlag(0);
            scoreTable.setCreater(currentUserName);
            scoreTable.setUpdater(currentUserName);
            scoreTableList.add(scoreTable);
            if (scoreTableService.findBySIDAndVIDAndCID(scoreTable) != null) {
                scoreTableService.updateBySIDAndVIDAndCID(scoreTable);
            } else {
                scoreTable.setEnabled(true);
                scoreTableService.insert(scoreTable);
            }
        }
//        map.remove("sID");
//        scoreTableList = scoreTableService.findList(map);
//        }
//		}
        try {
            if (filter_courseId != null) {
                filter_courseId = URLEncoder.encode(filter_courseId, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vID;
    }

    private String itemid;

    // 题目完成情况
    @RequestMapping(value = {"/itemDetails"}, method = {RequestMethod.GET})
    public String itemDetails(Pageable pageable, Model model, String filter_cID, String filter_vId, String filter_courseId) {
        Map<String, Object> map = new HashMap<>();
        if (filter_cID != null && !filter_cID.equals("")) {
            int cId = Integer.parseInt(filter_cID);
            map.put("cId", cId);
        }
        if (filter_vId != null && !filter_vId.equals("")) {
            int vId = Integer.parseInt(filter_vId);
            map.put("vId", vId);
        }
        if (filter_courseId != null && !filter_courseId.equals("")) {
            Course courseByCourseName = courseService.findByCourseName(filter_courseId);
            long courseId = courseByCourseName.getId();
            map.put("courseId", courseId);
        }

        //查询该班级所做习题
        List<Exercise> exerciseList = exerciseService.findList(map);
        // 保存给查看
        redisService.save(systemUserService.getCurrentUserName() + "ttexerciseList",
                JSON.toJSONString(exerciseList));

        // 计算每道题的通过率
        Set<Integer> pIdSet = new HashSet<>();
        if (exerciseList.size() > 0) {
            for (int i = 0; i < exerciseList.size(); i++) {
                pIdSet.add(exerciseList.get(i).getPId());
            }
        }

        //将set转换成list
        List<Integer> pIdList = new ArrayList<>(pIdSet);
        List<Exercise> exerciseListNew = new ArrayList<>();
        // 去除重复的题目
        for (int i = 0; i < exerciseList.size(); i++) {
            boolean reg = true;
            for (int j = 0; j < exerciseListNew.size(); j++) {
                if (exerciseList.get(i).getPId() == exerciseListNew.get(j).getPId()) {
                    reg = false;
                }
            }
            if (reg) {
                exerciseListNew.add(exerciseList.get(i));
            }
        }
        // 把通过率设置到集合中
        //总人数
        int student_class_id = Integer.parseInt(filter_cID);
        List<ClassAndStudent> sIdList = classAndStudentService.findByClassID(student_class_id);
        int examPeople = sIdList.size();
        for (int k = 0; k < exerciseListNew.size(); k++) {
            for (int i = 0; i < pIdList.size(); i++) {
                // 每道题的通过数passKind
                int passKind = 0;
                // 参与考试人数
//                int examPeople = 0;
                for (int j = 0; j < exerciseList.size(); j++) {
                    if (pIdList.get(i) == exerciseList.get(j).getPId()) {
                        if (exerciseList.get(j).getRunResult() == 1) {
                            passKind++;
                        }
                    }
                }
                // 每道题的通过率
                float tempPassPercent = (float) passKind / examPeople;
                String passPercentStr = new DecimalFormat("0.000").format(tempPassPercent);
                float passPercent = Float.parseFloat(passPercentStr);
                if (exerciseListNew.get(k).getPId() == pIdList.get(i)) {
                    exerciseListNew.get(k).setPassPercent(passPercent);
                }
            }
        }
        exerciseList = exerciseListNew;
        // 通过相应的题目内容解析成QuestionContent对象
        List<QuestionContent> questionContentList = new ArrayList<>();
        for (int i = 0; i < exerciseList.size(); i++) {
            questionContentList
                    .add(parserUtils.getPullParserQuestionList(exerciseList.get(i).getQuestion().getContent())
                            .getQuestionContent());
        }
        model.addAttribute("questionContentList", questionContentList);
        model.addAttribute("page", exerciseService.pageMethod(pageable, exerciseList));
        model.addAttribute("filter_cID", filter_cID);
        model.addAttribute("filter_vId", filter_vId);
        model.addAttribute("filter_courseId", filter_courseId);
        return "admin/scoretable/itemDetailsList";
    }

    private int conttentId;

    // 题目完成情况中的查看
    @RequestMapping(value = {"/checkContent"}, method = {RequestMethod.GET})
    public String checkContent(Pageable pageable, Model model, HttpServletRequest request) {
        int pId;
        if (request.getParameter("questionId") != null) {
            pId = Integer.valueOf(request.getParameter("questionId"));
            conttentId = pId;
        } else pId = conttentId;
        List<Exercise> exerciseList = idsUtils.exerciseJsonToList(
                redisService.get(systemUserService.getCurrentUserName() + "ttexerciseList"), Exercise.class);
        List<Exercise> exerciseListNew = new ArrayList<>();



        for (int i = 0; i < exerciseList.size(); i++) {
            if (exerciseList.get(i).getPId() == pId && (cId == null || cId.equals(""))) {
                exerciseListNew.add(exerciseList.get(i));
            } else if (exerciseList.get(i).getPId() == pId && exerciseList.get(i).getCId() == Integer.valueOf(cId)) {
                exerciseListNew.add(exerciseList.get(i));
            }
        }
        exerciseList = exerciseListNew;
        String filter_cID = request.getParameter("filter_cID");
        int student_class_id = Integer.parseInt(filter_cID);
        Map map = new HashMap();
        map.put("student_class_id",student_class_id);
        List<ClassAndStudent> sIdList = classAndStudentService.findList(map);
        for (int i = 0; i < sIdList.size(); i++) {
            int sId = sIdList.get(i).getStudent_id();
            boolean flag = true;
            map.put("id", sId);
            List<Student> student = studentService.findList(map);
            for (int j = 0; j <exerciseList.size() ; j++) {
                if (exerciseList.get(j).getSId() == sId) {
                    flag = false;
                }
            }
            if (flag) {
                Exercise exercise = new Exercise();
                exercise.setStudent(student.get(0));
                exerciseList.add(exercise);
            }

        }
        model.addAttribute("filter_cID", filter_cID);
        model.addAttribute("page", exerciseService.pageMethod(pageable, exerciseList));
        return "admin/scoretable/checkContent";
    }

    // 查看当前学生做题情况
    @RequestMapping(value = {"/checkOneContent"}, method = {RequestMethod.GET})
    public String checkOneContent(Pageable pageable, Model model, HttpServletRequest request) {
        Long id;
        if (request.getParameter("id") == null) id = OnecontentId;
        else {
            id = Long.valueOf(request.getParameter("id"));
            OnecontentId = id;
        }
        ScoreTable scoreTable = scoreTableService.find(id);
        List<Exercise> exerciseListNew = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("vId", scoreTable.getVID());
        map.put("sId", scoreTable.getSID());
        map.put("courseId", scoreTable.getWorkingTable().getCourseId());
        List<Exercise> exerciseList = exerciseService.findList(map);
        for (int i = 0; i < exerciseList.size(); i++) {
            if (exerciseList.get(i).getSId() == scoreTable.getSID()) {
                exerciseListNew.add(exerciseList.get(i));
            }
        }
        exerciseList = exerciseListNew;
        // 通过相应的题目内容解析成QuestionContent对象
        List<QuestionContent> questionContentList = new ArrayList<>();
        for (int i = 0; i < exerciseList.size(); i++) {
            questionContentList
                    .add(parserUtils.getPullParserQuestionList(exerciseList.get(i).getQuestion().getContent())
                            .getQuestionContent());
        }
        model.addAttribute("questionContentList", questionContentList);
        model.addAttribute("page", exerciseService.pageMethod(pageable, exerciseList));
        return "admin/scoretable/checkOneContent";
    }

    // 导出
    @RequestMapping(value = {"/exportExcel"})
    public @ResponseBody
    Message exportExcel(HttpServletResponse response) {
        List<ScoreTable> scoreTableList = idsUtils.scoreTableJSONToList(
                redisService.get(systemUserService.getCurrentUserName() + "ttpageScoreTableList"), ScoreTable.class);
        // 第一步，创建一个webbook，对应一个Excel文件
        Workbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        Sheet sheet = wb.createSheet("成绩表");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        Row row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        Font font = wb.createFont();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        // 设置字体大小
        font.setFontHeightInPoints((short) 0);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        Cell cell = row.createCell((short) 0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("学号");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("完成题数");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("累计时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("卷面分");
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);
        cell.setCellValue("态度分");
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);
        cell.setCellValue("综合分");
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);
        cell.setCellValue("排名");
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);
        cell.setCellValue("班级");
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);
        cell.setCellValue("课程");
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);
        cell.setCellValue("作业表");
        cell.setCellStyle(style);
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        for (int i = 0; i < scoreTableList.size(); i++) {
            row = sheet.createRow((int) i + 1);
            ScoreTable scoreTable = (ScoreTable) scoreTableList.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell((short) 0).setCellValue(i + 1);
            row.createCell((short) 1).setCellValue(scoreTable.getStudent().getUsername());
            row.createCell((short) 2).setCellValue(scoreTable.getStudent().getName());
            row.createCell((short) 3).setCellValue(scoreTable.getPassNum());
            row.createCell((short) 4).setCellValue(scoreTable.getTotalTime());
            row.createCell((short) 5).setCellValue(scoreTable.getPaperScore());
            row.createCell((short) 6).setCellValue(scoreTable.getAttitudeScore());
            row.createCell((short) 7).setCellValue(scoreTable.getScore());
            row.createCell((short) 8).setCellValue(scoreTable.getRank());
            row.createCell((short) 9).setCellValue(scoreTable.getClassSystem().getClassName());
            row.createCell((short) 10).setCellValue(scoreTable.getWorkingTable().getCourse().getCourseName());
            row.createCell((short) 11).setCellValue(scoreTable.getWorkingTable().getTableName());
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        String filedisplay = createtFileName();
        response.setHeader("Content-Disposition", "attachment;filename=" + filedisplay);
        // 防止文件名含有中文乱码
        try {
            filedisplay = new String(filedisplay.getBytes("gb2312"), "ISO8859-1");
            OutputStream out = response.getOutputStream();
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SUCCESS_MESSAGE;
    }

    // 查找符合条件的题目
    public List<Exercise> searchExercise(ScoreTable scoreTable) {
        Exercise exercise = new Exercise();
        exercise.setVId(scoreTable.getVID());
        exercise.setCId(scoreTable.getCID());
        exercise.setSId(scoreTable.getSID());
        exercise.setCourseId(scoreTable.getWorkingTable().getCourseId());
        return exerciseService.findByEntity(exercise);
    }

    // 自动生成文件名
    public synchronized String createtFileName() {
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = fmt.format(dt);
        fileName = fileName + ".xls";
        return fileName;
    }

    @ResponseBody
    @RequestMapping("getChangeajax")
    public Object getChangeList(String parentId, String flag) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Teacher temp = new Teacher();
        temp.setUsername(systemUserService.getCurrentUserName());
        temp.setSchoolId(systemUserService.getCurrentUser().getSchoolId());
        Teacher currentTeacher = teacherService.findByUserName(temp);
        List<CourseArrange> courseArrangeList = courseArrangeService.findByTeacher(currentTeacher.getId());
        List<CourseArrange> courseArranges = new ArrayList<>();
        if (flag.equals("C")) {

            List<Course> courseList = new ArrayList<>();
            for (int i = 0; i < courseArrangeList.size(); i++) {
                if (Integer.parseInt(parentId) == courseArrangeList.get(i).getClassSystem().getId()) {
                    courseArranges.add(courseArrangeList.get(i));
                }
            }
            boolean courseflag = true;
            for (int i = 0; i < courseArranges.size(); i++) {
                CourseArrange courseArrange = courseArranges.get(i);
                // 课程去重判断
                for (int j = 0; j < courseList.size(); j++) {
                    if (courseList.get(j).getId() == courseArrange.getClassSystem().getId()) {
                        courseflag = false;
                    }
                }
                if (courseflag) {
                    courseList.add(courseArranges.get(i).getCourse());
                } else courseflag = true;
            }
            courseArrangelist = courseArranges;
            returnMap.put("result", "success");
            returnMap.put("courselist", courseList);
        } else {
            List<WorkingTable> workList = new ArrayList<>();
            List<Long> tableIdList = new ArrayList<>();
            WorkingTable workingTable = new WorkingTable();
            for (int i = 0; i < courseArrangelist.size(); i++) {
                List<CourseArrangeAndWorkingTable> courseArrangeAndWorkingTableList = courseArrangelist.get(i)
                        .getCourseArrangeAndWorkingTable();
                for (int j = 0; j < courseArrangeAndWorkingTableList.size(); j++) {
                    tableIdList.add((long) courseArrangeAndWorkingTableList.get(j).getWorkingTableId());
                }
            }
            List<WorkingTable> workingTableByCreaterList = workingTableService.findList("tableCreater",
                    systemUserService.getCurrentUser().getName());
            for (int i = 0; i < workingTableByCreaterList.size(); i++) {
                tableIdList.add(workingTableByCreaterList.get(i).getId());
            }
            // 去掉重复的id
            if (tableIdList.size() > 1) {
                List<Long> tableIdListNew = new ArrayList<>();
                for (Long id : tableIdList) {
                    if (!tableIdListNew.contains(id)) {
                        tableIdListNew.add(id);
                    }
                }
                tableIdList = tableIdListNew;
            }
            if (tableIdList.size() > 0) {
                workingTable.setTableIdList(tableIdList);
                workList = workingTableService.findContentList(workingTable);
            }
            System.out.println(workList.size() + "------------");
            returnMap.put("result", "success");
            returnMap.put("workingtablelist", workList);

        }
        return returnMap;
    }

    //设置评分条件
    @RequestMapping("setCondition")
    public String setCondition(TotalScore totalScore,AttitudeCondition attitudeCondition,HttpServletRequest request){
        // 班级查询
        String filter_cID = request.getParameter("filter_cID");
        // 课程查询
        String filter_courseId = request.getParameter("filter_courseId");
        // 作业表查询
        String filter_vId = request.getParameter("filter_vId");

        HttpSession session = request.getSession();
        session.setAttribute("filter_courseId",filter_courseId);
        List<TotalScore> totalScoresList = new ArrayList<>();
        List<AttitudeCondition> attitudeConditionList = new ArrayList<>();

        totalScoresList.add(totalScore);
        redisService.save(systemUserService.getCurrentUserName() + "totalScoreCondition", JSON.toJSONString(totalScoresList));
        attitudeConditionList.add(attitudeCondition);
        redisService.save(systemUserService.getCurrentUserName() + "attitudeCondition",
                JSON.toJSONString(attitudeConditionList));
        return "redirect:/admin/ScoreTable/scoreContent.jhtml?pageNumber=1&recordflag=0&filter_cID=" + filter_cID + "&filter_courseId=" + filter_courseId + "&filter_vID=" + filter_vId;
    }

}
