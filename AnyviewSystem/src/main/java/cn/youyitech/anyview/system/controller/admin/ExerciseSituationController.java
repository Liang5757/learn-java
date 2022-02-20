package cn.youyitech.anyview.system.controller.admin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import cn.youyitech.anyview.system.controller.admin.ExerciseSituationController.ChapterTable;
import cn.youyitech.anyview.system.controller.admin.ExerciseSituationController.StudentTable;
import cn.youyitech.anyview.system.entity.ClassAndStudent;
import cn.youyitech.anyview.system.entity.ClassEntity;
import cn.youyitech.anyview.system.entity.Course;
import cn.youyitech.anyview.system.entity.CourseArrange;
import cn.youyitech.anyview.system.entity.CourseArrangeAndWorkingTable;
import cn.youyitech.anyview.system.entity.Exercise;
import cn.youyitech.anyview.system.entity.Question;
import cn.youyitech.anyview.system.entity.QuestionContent;
import cn.youyitech.anyview.system.entity.SchemeContentTable;
import cn.youyitech.anyview.system.entity.Student;
import cn.youyitech.anyview.system.entity.Teacher;
import cn.youyitech.anyview.system.entity.WorkingTable;
import cn.youyitech.anyview.system.service.ClassAndStudentService;
import cn.youyitech.anyview.system.service.ClassService;
import cn.youyitech.anyview.system.service.CourseArrangeAndWorkingTableService;
import cn.youyitech.anyview.system.service.CourseArrangeService;
import cn.youyitech.anyview.system.service.ExerciseService;
import cn.youyitech.anyview.system.service.QuestionService;
import cn.youyitech.anyview.system.service.RedisService;
import cn.youyitech.anyview.system.service.SchemeContentTableService;
import cn.youyitech.anyview.system.service.StudentService;
import cn.youyitech.anyview.system.service.SystemUserService;
import cn.youyitech.anyview.system.service.TeacherService;
import cn.youyitech.anyview.system.service.WorkingTableService;
import cn.youyitech.anyview.system.utils.PullParserXmlUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther:Blanche
 * @Date:2019/10/12
 * @Description:cn.youyitech.anyview.system.controller.admin
 * @version:1.0
 */
@Controller("adminExerciseSituationController")
@RequestMapping("/admin/exercise_situation")
public class ExerciseSituationController extends GenericController{
	@Resource
	private SystemUserService systemUserService;
    @Autowired
    private ClassService classService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ClassAndStudentService classAndStudentService;
	@Resource
	private TeacherService teacherService;	
	@Resource
	private CourseArrangeService courseArrangeService;	
	@Autowired
	private WorkingTableService workingTableService;	
	@Resource
	private ExerciseService exerciseService;	
	@Resource
	private SchemeContentTableService schemeContentTableService;	
    @Autowired
    private CourseArrangeAndWorkingTableService courseArrangeAndWorkingTableService;
    @Autowired
    private RedisService redisService;
    
	@RequiresPermissions("admin:system:exercisesituation")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request,ModelMap model){//入口
	
		//通过老师的id获取需要教学的课程集合
		Teacher teacher = teacherService.find("username", systemUserService.getCurrentUserName());
		List<CourseArrange> courseArrangeList = courseArrangeService.findList("teacher_id", teacher.getId());	
		
		List<String> courseNameList = new ArrayList<String>();//这个老师一共教几门课，要对上面的列表去重
		for (CourseArrange courseArrange : courseArrangeList) {
			courseNameList.add(courseArrange.getCourse().getCourseName());
		}
		//用set去重
		Set courNameSet = new HashSet(courseNameList);
		courseNameList = new ArrayList(courNameSet);
		
		//构建map
		Map map = new HashMap();
		List<CourseArrange> historyList = new ArrayList();

		for(int i=0;i<courseArrangeList.size();i++) {
			CourseArrange courseArrange = courseArrangeList.get(i);
			CourseArrangeAndWorkingTable cawt = courseArrange.getCourseArrangeAndWorkingTable().get(0);//这一步目前有问题，先取第一个作业表
			int workingTableId = cawt.getWorkingTableId();
			
			//把历史课程排除
			List<SchemeContentTable> sct = schemeContentTableService.findList("VID", workingTableId);
			//把做最后一道题的结束日期拿出来当作业表时限，所以要求老师至少把这道题时限给出来，否则不会形成历史课程
			Date finishTime = sct.get(sct.size()-1).getFinishTime();
			Date nowTime = new Date();

			if(finishTime!=null && nowTime.after(finishTime)) {
				historyList.add(courseArrange);
				courseArrangeList.remove(i);
				map.put(courseArrange.getCourse().getCourseName()+courseArrange.getClassSystem().getClassName()+"id",cawt.getId());
				i--;
				continue;
			}	
			
			WorkingTable wt = workingTableService.find((long)workingTableId);
			float questionNum = wt.getTotalNum();//作业表的题目数
			
			
			List <ClassAndStudent>classAndStudentList = courseArrange.getClassAndStudentList();
			float stundetNum = classAndStudentList.size();//学生人数
			
			
			float totalNum = stundetNum*questionNum;//分母
			
			
			float overNum = 0;//分子
			for (ClassAndStudent classAndStudent : classAndStudentList) {
				overNum += exerciseService.countRightNumber(courseArrange.getClass_id(),
					courseArrange.getCourse_id(), workingTableId, classAndStudent.getStudent_id()); 
			}
			//保留两位小数
			DecimalFormat df = new DecimalFormat("0.00"); 
			double percent = Double.parseDouble(df.format((double) overNum/totalNum));
			percent*=100;

			map.put(courseArrange.getCourse().getCourseName()+courseArrange.getClassSystem().getClassName()+"percent",percent);
			//map.put(courseArrange.getCourse().getCourseName()+courseArrange.getClassSystem().getClassName()+"id",courseArrange.getId());
			//System.out.println("id是："+cawt.getId());
			map.put(courseArrange.getCourse().getCourseName()+courseArrange.getClassSystem().getClassName()+"id",cawt.getId());

		}
		
		model.addAttribute("map", map);
		model.addAttribute("courseArrangeList", courseArrangeList);
		model.addAttribute("courseNameList", courseNameList);
		model.addAttribute("historyList", historyList);
		
		
        return "/admin/exercise_situation/list";
    }

    @RequestMapping(value = "/scheme_detail",method = RequestMethod.POST)
    public ModelAndView schemeDetail(HttpServletRequest request, Map<String,Object> map){ //按作业表统计	
    	String[] values = request.getParameterValues("ccsIds");
    	if(values!=null) {
    		System.out.println("id数组长度："+values.length);
    		
    		for(int i=0;i<values.length;i++) {
    			String tempStr = values[i];
    			if(tempStr.substring(tempStr.length()-1).equals("/")) {//submit过来之后带斜杠
    				tempStr = tempStr.substring(0, tempStr.length()-1);
    			}
    			
    			else if(tempStr.substring(tempStr.length()-1).equals("]")) {//submit过来之后带中括号
    				tempStr = tempStr.substring(1, tempStr.length()-1);
    				System.out.println("去掉括号后"+tempStr);
    				values = tempStr.split(",");
    				break;
    			}
    			
    			values[i] = tempStr;
    		}
    		
    		System.out.println("最后的ids");
    		for(String str:values){
    			System.out.println(str);
    		}
    	}
    	System.out.println();
    	
    	/*                   下面是他们的代码                   */
    	
    	//String s = request.getParameter("ccsIds");
        //List<Long> ccsIds = JSON.parseArray(request.getParameter("ccsIds"),Long.class);
    	String s = request.getParameter("ccsIds");
        List<Long> ccsIds = new ArrayList<Long>();
        
        
		for(String str:values){
			ccsIds.add(Long.parseLong(str));
		}
		s = JSONArray.toJSONString(ccsIds);
		
        //echarts 实现 所需数据: 横坐标 chapterNames 纵坐标(堆叠) 完成人数、未完成人数
        Map<String, List<Long>> chapterAndQuestions = new HashMap<>();//存放章节题目
        List<String> chapterNames = new ArrayList<>();//章节名称
        List<Chapter> chapters = new ArrayList<>();
        int schemeId = -1;
        List<Integer> studentIds = new ArrayList<>();
        //1.获取学生和题目
        for (Long ccsId : ccsIds) {
            //1.1 ccsid->classId、schemeId
        	//System.out.println("ccsId:"+ccsId);
            CourseArrangeAndWorkingTable courseArrangeAndWorkingTable = courseArrangeAndWorkingTableService.find("id", ccsId);
            
            //只有相同作业表的才能合班统计
            if (schemeId == -1 || schemeId == courseArrangeAndWorkingTable.getWorkingTableId()) {
            	//System.out.println("进入if");
                schemeId = courseArrangeAndWorkingTable.getWorkingTableId();
                //System.out.println("courseArrangeAndWorkingTableid:"+courseArrangeAndWorkingTable.getId());
                //System.out.println("classid:"+courseArrangeAndWorkingTable.getClassId());
                //System.out.println("CourseArrangeId:"+courseArrangeAndWorkingTable.getCourseArrangeId());
                //System.out.println("CourseArrangeId:"+courseArrangeAndWorkingTable.getCourseArrangeId());
                CourseArrange ca = courseArrangeService.find((long)courseArrangeAndWorkingTable.getCourseArrangeId());
                //System.out.println("classid:"+ca.getClass_id());
                
                studentIds.addAll(classAndStudentService.findByClassID(ca.getClass_id()).
                        stream().map(ClassAndStudent::getStudent_id).collect(Collectors.toList()));
                if (chapterAndQuestions.isEmpty()) {//获取题目id
                    List<SchemeContentTable> schemeContentTables = schemeContentTableService.findList("VID", schemeId);
                    chapterNames = schemeContentTables.stream().map(SchemeContentTable::getVChapName).distinct().sorted().collect(Collectors.toList());
                    //1.2按章节划分
                    for (String chapterName : chapterNames) {
                        List<Long> questionsId = schemeContentTables.stream()
                                .filter(SchemeContentTable -> (SchemeContentTable.getVChapName().equals(chapterName))).collect(Collectors.toList())
                                .stream().map(SchemeContentTable::getPID).collect(Collectors.toList());
                        chapterAndQuestions.put(chapterName, questionsId);
                        Chapter chapter = new Chapter(schemeId,chapterName);
                        chapters.add(chapter);
                    }
                }
            }else {
                break;
            }

        }
        //2.获取exercise计算其中firstPastTime不为空的数量(学生端保存结果未使用kind字段)
        List<Integer> finished = new ArrayList<>();
        List<Integer> unFinished = new ArrayList<>();
        for (List<Long> ids : chapterAndQuestions.values()) {//一个章节的题目
            int total =ids.size()*studentIds.size(); //每章总人数
            //每章所有学生的exercise
            //System.out.println("studentIds:"+studentIds);
            //System.out.println("schemeId:"+schemeId);
            //System.out.println("要进psvs了");
            List<Exercise> exercises = exerciseService.findByPSVs(ids,studentIds,schemeId);
            int finish = (int) exercises.stream().filter(Exercise -> (Exercise.getFirstPastTime() != null)).count();
            int unFinish = total-finish;
            finished.add(finish);
            unFinished.add(unFinish);
        }
        map.put("chapterNames",JSON.toJSONString(chapterNames));
        map.put("chapters",chapters);
        map.put("finished",finished);
        map.put("unFinished",unFinished);
        map.put("studentIds",studentIds);
        map.put("ccsIds",s);
        return new ModelAndView("admin/exercise_situation/scheme_detail",map);
    }


    @RequestMapping(value = "/chapter_bar", method = RequestMethod.POST)
    public ModelAndView chapter(@RequestParam String chapterName , @RequestParam Integer schemeId, @RequestParam String studentIds) { //作业表统计->各章完成情况
        List<Integer> ids = JSONObject.parseArray(studentIds,Integer.class);
        List<Integer> finished = new ArrayList<>();
        List<Integer> unFinished = new ArrayList<>();
        List<String> questionName = new ArrayList<>();
        //作业表id 章节名 获取所有题目 完成情况
        List<SchemeContentTable> schemeContentTables = schemeContentTableService.findList("VID", schemeId);
        List<Long> questionsIds = schemeContentTables.stream()
                .filter(SchemeContentTable -> (SchemeContentTable.getVChapName().equals(chapterName))).collect(Collectors.toList())
                .stream().map(SchemeContentTable::getPID).collect(Collectors.toList());
        int total = ids.size();
        for (Long questionsId : questionsIds) {//单个章节的答案
            Question question = questionService.find(questionsId);
            List<Exercise> exercises = exerciseService.findByPSVs(Collections.singletonList(questionsId),ids,schemeId);
            int finish = (int) exercises.stream().filter(Exercise -> (Exercise.getFirstPastTime() != null)).count();
            int unFinish = total-finish;
            finished.add(finish);
            unFinished.add(unFinish);
            questionName.add(question.getQuestion_name());
        }
        questionName.sort(String::compareTo);
        Map<String,Object> map = new HashMap<>();
        map.put("finished",finished);
        map.put("unFinished",unFinished);
        map.put("questionNames",JSON.toJSONString(questionName));
        map.put("chapterName",chapterName);
        map.put("studentIds",ids);
        map.put("schemeId",schemeId);
        //List<Chapter> chapters = JSON.parseArray(s,Chapter.class);
        return new ModelAndView("admin/exercise_situation/chapter_bar",map);
    }

    @RequestMapping(value = "/chapter_table",method = RequestMethod.POST)
    public ModelAndView chapter_table(@RequestParam String chapterName ,@RequestParam Integer schemeId,@RequestParam String studentIds){
        List<Integer> ids = JSONObject.parseArray(studentIds,Integer.class);
        List<ChapterTable> chapterTables = new ArrayList<>();
        //作业表id 章节名 获取所有题目 完成情况
        List<SchemeContentTable> schemeContentTables = schemeContentTableService.findList("VID", schemeId);
        List<Long> questionsIds = schemeContentTables.stream()
                .filter(SchemeContentTable -> (SchemeContentTable.getVChapName().equals(chapterName))).collect(Collectors.toList())
                .stream().map(SchemeContentTable::getPID).collect(Collectors.toList());
        for (Long questionsId : questionsIds) {//单个章节的答案
            Question question = questionService.find(questionsId);
            List<Exercise> exercises = exerciseService.findByPSVs(Collections.singletonList(questionsId),ids,schemeId);

            char chapter = chapterName.charAt(1);
            int beginWorkCount = exercises.size();//有exercise说明开始做题
            int finish = (int) exercises.stream().filter(Exercise -> (Exercise.getFirstPastTime() != null)).count();
            int cmpRightCount = exercises.stream().mapToInt(Exercise::getCmpRightCount).sum();
            int cmpErrorCount = exercises.stream().mapToInt(Exercise::getCmpErrorCount).sum();
            int runRightCount = exercises.stream().mapToInt(Exercise::getRunRightCount).sum();
            int runErrorCount = exercises.stream().mapToInt(Exercise::getRunErrorCount).sum();
            //int averageTime = exercises.stream().mapToInt(Exercise::getAccumTime).sum()/exercises.size();

            ChapterTable table = new ChapterTable(chapter,question.getQuestion_name(),question.getRemark(),finish,beginWorkCount,cmpRightCount,cmpErrorCount,runRightCount,runErrorCount,0);
            chapterTables.add(table);
        }
        chapterTables.sort(Comparator.comparing(ChapterTable::getQuestionName));
        Map<String,Object> map = new HashMap<>();
        map.put("tables",chapterTables);

        map.put("chapterName",chapterName);
        map.put("studentIds",ids);
        map.put("schemeId",schemeId);
        return new ModelAndView("admin/exercise_situation/chapter_table",map);
    }


    @RequestMapping(value = "/students_detail", method = RequestMethod.POST)
    public ModelAndView studentsDetail(@RequestParam String ccsIds) { //按学生统计
        Map<String,Object> map = new HashMap<>();
        List<Integer> ccsids = JSON.parseArray(ccsIds,Integer.class);
        List<StudentTable> results = new ArrayList<>();
        //1. 学生信息
        for (Integer ccsId: ccsids){
        	//System.out.println("ccsid:"+ccsId);
        	
            //1.1 ccsid->classId、schemeId
            CourseArrangeAndWorkingTable courseArrangeAndWorkingTable = courseArrangeAndWorkingTableService.find("id", ccsId);
            CourseArrange tempCa = courseArrangeService.find((long)courseArrangeAndWorkingTable.getCourseArrangeId());
            int classId = tempCa.getClass_id();
            //System.out.println("classid:"+classId);
            
            List<Integer> studentIds =classAndStudentService.findByClassID(classId).
                    stream().map(ClassAndStudent::getStudent_id).collect(Collectors.toList());
            ClassEntity classEntity = classService.find((long) classId);
            String className= classEntity.getClassName();
            for (Integer sid : studentIds) {
                Student student = studentService.find((long)sid);
                List<Exercise> exercises = exerciseService.findForStuduent
                        (classId, courseArrangeAndWorkingTable.getCourseId(), courseArrangeAndWorkingTable.getWorkingTableId(), sid);
                
                //2. 开始做题时间、完成题数、首次通过时间、在线时长、IP端口、上次登录时间
                //Date beginTime = exercises.stream().filter(Exercise -> Exercise.getCreateTime()!=null).map(Exercise::getCreateTime)
                //        .distinct().min(Date::compareTo).get();
                int doneCount = (int) exercises.stream().filter(Exercise->Exercise.getFirstPastTime()!=null).count();

                Date firstPastTime = exercises.stream().filter(Exercise -> Exercise.getFirstPastTime()!=null).map(Exercise::getFirstPastTime)
                        .distinct().min(Date::compareTo).orElse(new Date());
                String ipAndPort = student.getLogIP()+":"+student.getLogPort();
                int accumTime = exercises.stream().filter(Exercise -> Exercise.getAccumTime()!=null).mapToInt(Exercise::getAccumTime).sum();

                //System.out.println("前面的vid"+courseArrangeAndWorkingTable.getWorkingTableId());
                StudentTable table = new StudentTable(className,student.getUsername(),student.getName(),student.getSex(),
                		doneCount,firstPastTime,firstPastTime,accumTime,student.getLogTime(),ipAndPort,classId,sid);
                results.add(table);
                map.put("vid", courseArrangeAndWorkingTable.getWorkingTableId());
            }
        }
        
        map.put("tables",results);
        map.put("ccsIds",ccsIds);
        return new ModelAndView("admin/exercise_situation/students_detail",map);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class Chapter {
        int schemeId;
        String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class StudentTable {
        String className;
        String userName;
        String name;
        String sex;
        int doneCount;
        Date beginTime;
        Date firstPastTime;
        int accumTime;
        Date lastLoginTime;
        String ipAndPort;
        int classId;
        int studentId;
        //int vId;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class ChapterTable{
        char chapter;
        String questionName;
        String description;
        int finish;
        int beginWorkCount;
        int cmpRightCount;
        int cmpErrorCount;
        int runRightCount;
        int runErrorCount;
        int averageTime;
    }
    @Resource
    private PullParserXmlUtils parserUtils;
    @RequestMapping(value = "/data_analysis", method = RequestMethod.GET)
    public String dataAnalysis(Model model, HttpServletRequest request) { //学生的数据分析
        String CID = request.getParameter("CID");
        String SID = request.getParameter("SID");
        String VID = request.getParameter("VID");
        
        //String CID = "54";
        //String SID = "2475";
        //String VID = "83";
        Map<String, Object> map = new HashMap<>();
        map.put("sId", SID);
        map.put("cId", CID);
        map.put("vId", VID);
        //查找班级
        ClassEntity classEntity = classService.find(Long.parseLong(CID));
        //System.out.println("cid:"+classEntity.getId());
        //查找学生
        Student student = studentService.find(Long.parseLong(SID));
        //System.out.println("sid:"+student.getId());
        //查找该学生习题
        System.out.println("VID"+VID);
        WorkingTable wt = workingTableService.find(Long.parseLong(VID));
        List<Exercise> exerciseList = exerciseService.findList(map);
        exerciseList = exerciseService.findForStuduent(Integer.parseInt(CID), wt.getCourseId(), Integer.parseInt(VID), Integer.parseInt(SID));
        System.out.println("exerciselistsize:"+exerciseList.size());
        redisService.save(systemUserService.getCurrentUserName() + "exerciseList", JSON.toJSONString(exerciseList));


        //查询该同学所在班级的所有习题
        map.remove("sId");
        List<Exercise> classExerciseList = exerciseService.findList(map);
        int[] compRightAvgArray = new int[exerciseList.size()];
        int[] compErrorAvgArray = new int[exerciseList.size()];
        int[] runErrorAvgArray = new int[exerciseList.size()];
        int[] runRightAvgArray = new int[exerciseList.size()];
        int[] accuTimeAvgArray = new int[exerciseList.size()];

        //定义计算态度分的条件
        int minCompRight;
        int maxCompRight;
        int minRunRight;
        int maxRunRight;
        int minAccumTime;
        int maxAccumTime;  //最大运行时间
        long earliestFirstPastTime;
        long latestFirstPastTime;  //最晚通过时间


        //定义计算态度分的变量
        int compScore;
        int runScore;
        int accumTimeScore;
        long pastTimeScore;
        long[] attitudeScoreArr = new long[exerciseList.size()];

        //查询班级同学人数
        map.remove("vId");
        map.remove("cId");
        map.put("student_class_id", CID);
        List<ClassAndStudent> classAndStudentList = classAndStudentService.findList(map);
        int stuNum = classAndStudentList.size();


        List<QuestionContent> questionContentList = new ArrayList<>();
        //计算平均值
        for (int i = 0; i < exerciseList.size(); i++) {
            // 通过相应的题目内容解析成QuestionContent对象
            questionContentList
                    .add(parserUtils.getPullParserQuestionList(exerciseList.get(i).getQuestion().getContent())
                            .getQuestionContent());

            int pId = exerciseList.get(i).getPId();
            int cmpRightTemp = 0;
            int cmpErrtTemp = 0;
            int runRightTemp = 0;
            int runErrTemp = 0;
            int accumTimeTemp = 0;
            int cmpTemp = exerciseList.get(i).getCmpRightCount();
            int runTemp = exerciseList.get(i).getRunRightCount();
            //int timeTemp = exerciseList.get(i).getAccumTime();
            int timeTemp = 0;
            //long firstPastTimeTemp = exerciseList.get(i).getFirstPastTime().getTime();
            long firstPastTimeTemp = 0;
            //long lastPastTimeTemp = exerciseList.get(i).getLastPastTime().getTime();
            long lastPastTimeTemp = 0;
            minCompRight = cmpTemp;
            maxCompRight = cmpTemp;
            minRunRight = runTemp;
            maxRunRight = runTemp;
            minAccumTime = timeTemp;
            maxAccumTime = timeTemp;
            earliestFirstPastTime = firstPastTimeTemp;
            latestFirstPastTime = lastPastTimeTemp;

            for (int j = 0; j < classExerciseList.size(); j++) {
                if (classExerciseList.get(j).getPId() == pId) {
                    cmpRightTemp += classExerciseList.get(j).getCmpRightCount()+1;  //不知道哪里爆0的错，所以都加1
                    cmpErrtTemp += classExerciseList.get(j).getCmpErrorCount();
                    runRightTemp += classExerciseList.get(j).getRunRightCount();
                    runErrTemp += classExerciseList.get(j).getRunErrCount();
                    //accumTimeTemp += classExerciseList.get(j).getAccumTime();
                    accumTimeTemp += 0;

                    //态度分计算条件
                    minCompRight = Math.min(minCompRight, classExerciseList.get(j).getCmpRightCount());
                    maxCompRight = Math.max(maxCompRight, classExerciseList.get(j).getCmpRightCount())+1; //不知道哪里爆0的错，所以都加1
                    minRunRight = Math.min(minRunRight, classExerciseList.get(j).getRunRightCount());
                    maxRunRight = Math.max(maxRunRight, classExerciseList.get(j).getRunRightCount())+1;
                    //minAccumTime = Math.min(minAccumTime, classExerciseList.get(j).getAccumTime());
                    minAccumTime = 1;
                    //maxAccumTime = Math.max(maxAccumTime, classExerciseList.get(j).getAccumTime());
                    maxAccumTime = 1;
                    earliestFirstPastTime = Math.min(earliestFirstPastTime, classExerciseList.get(j).getFirstPastTime()!= null ? classExerciseList.get(j).getFirstPastTime().getTime() : 0);
                    latestFirstPastTime = Math.max(latestFirstPastTime, classExerciseList.get(j).getLastPastTime()!= null ? classExerciseList.get(j).getLastPastTime().getTime() : 0);

                }
            }
            //学生编译、运行、做题总时间平均次数
            compRightAvgArray[i] = cmpRightTemp / stuNum;
            compErrorAvgArray[i] = cmpErrtTemp / stuNum;
            runRightAvgArray[i] = runRightTemp / stuNum;
            runErrorAvgArray[i] = runErrTemp / stuNum;
            accuTimeAvgArray[i] = accumTimeTemp / stuNum;

            if (exerciseList.get(i).getRunResult() == 1) {
                compScore = (100 - 50) * (maxCompRight - cmpTemp) / (maxCompRight - minCompRight) + 50;
                runScore = (100 - 50) * (maxRunRight - runTemp) / (maxRunRight - minRunRight) + 50;
                accumTimeScore = (100 - 50) * (maxAccumTime - accumTimeTemp) / (maxAccumTime - minAccumTime + 1) + 50;
                pastTimeScore = (100 - 50) * ((latestFirstPastTime - firstPastTimeTemp) / 1000 / 60 + 1) / ((latestFirstPastTime - earliestFirstPastTime+1) / 1000 / 60 + 1) + 50;
                //正确分暂时20
                attitudeScoreArr[i] = compScore + runScore + accumTimeScore + pastTimeScore;
            }else{
                attitudeScoreArr[i] = (Math.min(maxCompRight, cmpRightTemp) / Math.max(maxCompRight, cmpRightTemp+1)
                        + Math.min(maxRunRight, runRightTemp) / Math.max(maxRunRight, runRightTemp+1)
                        + Math.min(maxAccumTime, accumTimeTemp) / Math.max(maxAccumTime, accumTimeTemp)+1) / 3 * 49;
            }
        }

        //将所有平均值数组存放到list中
        List<int[]> arrayList = new ArrayList<>();
        arrayList.add(compRightAvgArray);
        arrayList.add(compErrorAvgArray);
        arrayList.add(runRightAvgArray);
        arrayList.add(runErrorAvgArray);
        arrayList.add(accuTimeAvgArray);
        //保存到redis
        redisService.save(systemUserService.getCurrentUserName() + "arrayList", JSON.toJSONString(arrayList));
        redisService.save(systemUserService.getCurrentUserName() + "attitudeScoreArr", JSON.toJSONString(attitudeScoreArr));
        model.addAttribute("student", student);
        model.addAttribute("classEntity", classEntity);
        model.addAttribute("exerciseList", exerciseList);
        model.addAttribute("questionContentList", questionContentList);

        return "/admin/exercise_situation/data_analysis";
    }

    @RequestMapping(value = "/getExerciseList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getExerciseList() {

        Map<String, Object> map = new HashMap<>();
        //获取该同学所作习题
        String key = redisService.get(systemUserService.getCurrentUserName() + "exerciseList");
        List<Exercise> exerciseList = JSON.parseObject(key, new TypeReference<List<Exercise>>() {
        });

        //获取arrayList
        String s = redisService.get(systemUserService.getCurrentUserName() + "arrayList");
        List<int[]> arrayList = JSON.parseObject(s, new TypeReference<List<int[]>>() {
        });
        for (int i = 0; i < arrayList.size(); i++) {
            map.put("compRightAvgArray", arrayList.get(0));
            map.put("compErrorAvgArray", arrayList.get(1));
            map.put("runRightAvgArray", arrayList.get(2));
            map.put("runErrorAvgArray", arrayList.get(3));
            map.put("accuTimeAvgArray", arrayList.get(4));
        }

        //生成该存放该学生数据的数组
        int[] compRightArray = new int[exerciseList.size()];
        int[] compErrorArray = new int[exerciseList.size()];
        int[] runErrorArray = new int[exerciseList.size()];
        int[] runRightArray = new int[exerciseList.size()];
        int[] accuTimeArray = new int[exerciseList.size()];
        Date[] firstPastArray = new Date[exerciseList.size()];

        String[] strings = new String[exerciseList.size()];

        for (int i = 0; i < exerciseList.size(); i++) {
            int index = i + 1;
            strings[i] = "题" + index;
            compRightArray[i] = exerciseList.get(i).getCmpRightCount();
            compErrorArray[i] = exerciseList.get(i).getCmpErrorCount();
            runErrorArray[i] = exerciseList.get(i).getRunErrCount();
            runRightArray[i] = exerciseList.get(i).getRunRightCount();
//          accuTimeArray[i] = exerciseList.get(i).getAccumTime();
            accuTimeArray[i] = 2;
//            Date dt = new Date(Long.parseLong(property.getString("transTime")));//获取后台传来的时间并转换为长整形数据
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//            System.out.println(sdf.format(dt));
            firstPastArray[i] = exerciseList.get(i).getFirstPastTime();
        }
        map.put("xAxis", strings);
        map.put("compRightArray", compRightArray);
        map.put("compErrorArray", compErrorArray);
        map.put("runErrorArray", runErrorArray);
        map.put("runRightArray", runRightArray);
        map.put("accuTimeArray", accuTimeArray);
        map.put("firstPastArray", firstPastArray);
        return map;
    }

    @RequestMapping(value = "/attitudeScore", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> attitudeScore() {
        Map<String, Object> map = new HashMap<>();
        long[] attitudeScoreArr = JSON.parseObject(redisService.get(systemUserService.getCurrentUserName() + "attitudeScoreArr"), new TypeReference<long[]>() {
        });
        String key = redisService.get(systemUserService.getCurrentUserName() + "exerciseList");
        List<Exercise> exerciseList = JSON.parseObject(key, new TypeReference<List<Exercise>>() {
        });
        long totalAttitudeScore = 0;
        for (int i = 0; i < attitudeScoreArr.length; i++) {
            long scoreTemp = attitudeScoreArr[i];
            totalAttitudeScore += scoreTemp;
        }
        totalAttitudeScore = totalAttitudeScore / exerciseList.size();
        map.put("totalAttitudeScore", totalAttitudeScore);
        return map;
    }

    @RequestMapping(value = "/confirmScore", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> confirmScore(@RequestParam int attitudeScore) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(attitudeScore);
        map.put("Message", "OK");
        return map;
    }

}
