package cn.youyitech.anyview.system.controller.admin;

import cn.youyitech.anyview.system.entity.*;
import cn.youyitech.anyview.system.service.*;
import cn.youyitech.anyview.system.support.FileInfo;
import cn.youyitech.anyview.system.support.Message;
import cn.youyitech.anyview.system.utils.*;
import cn.youyitech.anyview.system.utils.similarCalculations.*;
import com.framework.loippi.support.Pageable;
import javafx.collections.FXCollections;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller("adminSimilarDetectionController")
@RequestMapping("/admin/SimilarDetection")
public class SimilarDetectionController extends GenericController{
    @Resource
    private SimilarityDetectionService simliarityDetectionService;
    @Resource
    private SystemUserService systemUserService;
    @Resource(name = "fileServiceImpl")
    private FileService fileService;
    @Resource
    private TeacherService teacherService;

    @Resource
    private ExerciseService exerciseService;

    @Resource
    private CourseArrangeService courseArrangeService;

    @Resource
    private WorkingTableService workingTableService;

    @Resource
    private SchemeContentTableService schemeContentTableService;
    @Resource
    private QuestionService questionService;
    @Resource
    private CourseArrangeAndWorkingTableService courseArrangeAndWorkingTableService;

    /*
     * @Resource private SchemeContentCacheTableService
     * schemeContentCacheTableService;
     */

    @Resource
    private RedisService redisService;

    @Autowired
    private IdsUtils idsUtils;
    //new File(".\\")直接生成在eclipse的根目录了,暂时不需要这样创建文件
    //private BaseDetectionInfo baseInfo = new BaseDetectionInfo(null, new File(".\\"));
    private Integer gst_k = 10;
    private String content1=null;
    private String content2=null;
    private File file1=null;
    private File file2=null;
    private String file1name=null;
    private String file2name=null;
    private List<BatchDetectionResult> batchDetectionResults;
    private List<SchemeContentTable> sctList;

    @RequiresPermissions("admin:system:similardetection")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Pageable pageable, ModelMap model) {
            sctList=new ArrayList<>();
        redisService.delete(systemUserService.getCurrentUserName() + "Exercise" + "workingTableId");
        redisService.delete(systemUserService.getCurrentUserName() + "ExerciseController" + "exerciseList");
        // 查询条件中的班级id
        String classId = request.getParameter("filter_cId");
        // 查询条件中的课程id
        String courseId = request.getParameter("filter_courseId");
        // 查询条件中的作业表id
        String workingTableId = request.getParameter("filter_vId");
        boolean courseflag=false;
        boolean workingtableflag=false;
        long courseArrangeId=-1;
        if(classId!=null&&courseId!=null&&classId!=""&&courseId != "") {
            List<CourseArrange> courseArrangeList = courseArrangeService.findByIdMany(Integer.parseInt(classId));
            for (int i = 0; i < courseArrangeList.size(); i++) {
                Teacher teacher=teacherService.find("id",courseArrangeList.get(i).getTeacher_id());
                if (courseArrangeList.get(i).getCourse_id() == Integer.parseInt(courseId)&&systemUserService.getCurrentUserName().equals(teacher.getUsername())) {
                    courseflag = true;

                    courseArrangeId=courseArrangeList.get(i).getId();
                    break;
                }
            }

            if (workingTableId!=null&&workingTableId!=""&&courseflag == true) {
                List<CourseArrangeAndWorkingTable> courseArrangeAndWorkingTables = courseArrangeAndWorkingTableService.findByIdMany((int)courseArrangeId);
                for (int i = 0; i < courseArrangeAndWorkingTables.size(); i++) {

                    if (courseArrangeAndWorkingTables.get(i).getWorkingTableId() == Integer.parseInt(workingTableId)) {

                        workingtableflag=true;break;
                    }
                }
            }
        }

        if(workingtableflag==true){
            sctList = schemeContentTableService.findList("VID", workingTableId);}
        int chap=0;
            System.out.println("题目数："+sctList.size());
        List<SchemeContentTable> list=new ArrayList<>();
        for(int i=0;i<sctList.size();i++){
            System.out.println(sctList.get(i).getVChapName());
            if (!sctList.get(i).getVChapName().equals("第"+chap+"章")){
                list.add(sctList.get(i));
                Pattern pattern = Pattern.compile("[^0-9]");
                Matcher matcher=pattern.matcher(sctList.get(i).getVChapName());
                String s=matcher.replaceAll("");
                chap=Integer.parseInt(s);
            }
        }
        SortUtil.sort(list,true,"VChapName");
        model.addAttribute("page",schemeContentTableService.pageMethod(pageable,list));
        // 若查询条件不为空，回传显示到界面上
        model.addAttribute("classId", classId);
        model.addAttribute("courseId", courseId);
        model.addAttribute("workingTableId", workingTableId);
        return "/admin/SimilarDetection/list";
    }

    /*
    下载答案
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public void download(Long classId, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException  {

    	/*
        String[] strings=ids.split(",");
        Long[] id=new Long[strings.length];

        for(int i=0;i<strings.length;i++){
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher=pattern.matcher(strings[i]);
            String s=matcher.replaceAll("");
            id[i]=Long.parseLong(s);//章节
        }
    	 */
        String cid=String.valueOf(classId);
        
        List<Long> idlist=new ArrayList<>();

        /*
        //根据选择的章节下载答案
        for (int j=0;j<id.length;j++) {
            for (int i = 0; i < sctList.size(); i++) {
                if (sctList.get(i).getVChapName().equals("第"+id[j]+"章")){
                    idlist.add(sctList.get(i).getID());

                }
            }
        }
        */
      
        for (int i = 0; i < sctList.size(); i++) {
        	idlist.add(sctList.get(i).getID());
        }       
        System.out.println("idList为："+idlist);

        //创建临时目录存放下载答案
        String path=request.getServletContext().getRealPath("/")+"DownLoad";
        File tmpfile = new File(path);
        tmpfile.mkdir();
        
        simliarityDetectionService.downloadAnswer(Integer.parseInt(cid), idlist, path,response);
        addFlashMessage(redirectAttributes, Message.success("下载成功"));
        //response提交之后不能重定向,如何解决？
    }

    @RequestMapping(value = "/onlineTesting", method = RequestMethod.GET)
    public String test(Model model) {
        return "admin/SimilarDetection/onlineTesting";
    }

    @RequestMapping(value = "/importleftProblems",method = RequestMethod.POST)
    public String importleftProblems(MultipartFile c1File, HttpServletRequest request,ModelMap model) throws IOException{
        InputStreamReader isr = null;
        // String c1File=request.getParameter("c1File");
        // System.out.println(c1File);
        // File file=new File(c1File);
        file1name=c1File.getOriginalFilename();
        //String contextpath = request.getContextPath()+"/tempfileDir";
        String contextpath=request.getServletContext().getRealPath("/")+"tempfileDir";
        File file=new File(contextpath);
        FileUtils.copyInputStreamToFile(c1File.getInputStream(),file);
        if(file != null){
            this.file1=file;
            isr = new InputStreamReader(new FileInputStream(file), "utf-8");

        }
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(isr);
            String tempString = null;
            int line = 1;
            content1=null;

            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(content1 == null){
                    content1 = tempString+ "\n";
                }else{
                    content1 = content1 + tempString + "\n";
                }

                line++;
            }
            System.out.println(content1);
            model.addAttribute("text",content1);
            model.addAttribute("text2",content2);
            model.addAttribute("filename1",c1File);
            //  jsonStr = AjaxObject.newOk("成功导入").setNavTabId("").setCallbackType("").toString();

            //   ResponseUtils.outJson(response, jsonStr);
            reader.close();
        }catch (FileNotFoundException e) {
            // log.error("找不到上传的临时文件:" + e.getMessage());
            //  jsonStr = AjaxObject.newError("导入失败，找不到上传的临时文件").toString();
        } catch (Exception e) {
            //    log.error("导入文件错误：" + e.getMessage());
            //    jsonStr = AjaxObject.newError("导入失败，导入文件错误").toString();
        } finally {
            //    ResponseUtils.outJson(response, jsonStr);
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e1) {
            }
        }
        return "/admin/SimilarDetection/onlineTesting";
    }

    @RequestMapping(value = "/importrightProblems",method = RequestMethod.POST)
    public String importrightProblems(MultipartFile c2File,HttpServletRequest request,ModelMap model) throws IOException{
        InputStreamReader isr = null;
        file2name=c2File.getOriginalFilename();
        //String contextpath = request.getContextPath()+"/tempfileDir";
        String contextpath=request.getServletContext().getRealPath("/")+"tempfileDir";
        File file=new File(contextpath);
        FileUtils.copyInputStreamToFile(c2File.getInputStream(),file);
        if(file != null){
            this.file2=file;
            isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        }
        BufferedReader reader = null;

        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(isr);
            String tempString = null;
            int line = 1;
            content2=null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(content2 == null){
                    content2 = tempString+ "\n";
                }else{
                    content2 = content2 + tempString + "\n";
                }

                line++;
            }
            System.out.println(content2);
            model.addAttribute("text",content1);
            model.addAttribute("text2",content2);
            model.addAttribute("filename2",c2File);
            //  jsonStr = AjaxObject.newOk("成功导入").setNavTabId("").setCallbackType("").toString();

            //   ResponseUtils.outJson(response, jsonStr);
            reader.close();
        }catch (FileNotFoundException e) {
            // log.error("找不到上传的临时文件:" + e.getMessage());
            //  jsonStr = AjaxObject.newError("导入失败，找不到上传的临时文件").toString();
        } catch (Exception e) {
            //    log.error("导入文件错误：" + e.getMessage());
            //    jsonStr = AjaxObject.newError("导入失败，导入文件错误").toString();
        } finally {
            //    ResponseUtils.outJson(response, jsonStr);
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e1) {
            }
        }
        return "/admin/SimilarDetection/onlineTesting";
    }

    @RequestMapping(value = "/compareCodeAjax",method = RequestMethod.POST)
    public String compareCodeAjax(HttpServletRequest request,RedirectAttributes redirectAttributes,Model model){
        System.out.println(content2);
        FileGrammar fgA = null;
        FileGrammar fgB = null;
        DetectionResult dr = null;
        DecimalFormat df = null;
        long t1 = System.currentTimeMillis();    //起始时间
        System.out.println("-------------进来了compareCodeAjax");
        String leftTextArea = content1;
        String rightTextArea =content2;
        //   String leftFileName = request.getParameter("c1file");
        //  String rightFileName = request.getParameter("c2file");
        String leftFileName ="左文件";
        String rightFileName="右文件";
        System.out.println("-----------leftFileName : " + leftFileName);
        System.out.println("-----------rightFileName : " + rightFileName);
        System.out.println("正在调用AnyviewC编译器进行文法编译......\n");
        AnyviewC anyc = new AnyviewC();
        fgA = anyc.ac(leftTextArea);
        fgB = anyc.ac(rightTextArea);

        //文法失败情况下使用GST算法
        if (fgA.isFlag() == false || fgB.isFlag() == false) {
            System.out.println("编译失败！正使用GST算法进行相似度计算中......\n");
            GSTGeneral gstGeneral = GSTGeneral.getGstGeneral();
            dr = gstGeneral.gstCompere(file1name,file2name,leftTextArea, rightTextArea, gst_k);		//修改过
            System.out.println("GST计算成功！正在生成报告中......\n");
        } else {
            System.out.println("编译成功！正在计算相似度及生成报告中......\n");
            RowSelected rs = RowSelected.getRowSelected();
            dr = rs.rowSelected(file1name,file2name,leftTextArea, rightTextArea, fgA, fgB, 0.75);
        }

        try {
            //创建检测报告的路径
            File output=new File(request.getServletContext().getRealPath("/")+"DetectionResult");
            //File output = new File(baseInfo.getOutputPath().toString() + "\\DetectionResult");
            if (output.exists()) {
                cn.youyitech.anyview.system.utils.FileUtils.deleteDir(output);
            }
            output.mkdir();
            //显示报告生成
            HtmlReport hr = HtmlReport.getHtmlReport();
            hr.generateReport(output, dr, 1);
            //不加载本地的bootstrap，用网址形式加载
            //hr.generateBootStrap(output);
            df = new DecimalFormat("0.00");
            System.out.println("左文档 与 右文档" +
                    " 的相似度为：" + df.format(dr.getSimilarity() * 100) + "%\n"
                    + "相同字符个数为：" + dr.getCount() + " 个 ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        long t2 = System.currentTimeMillis();//终止时间
        t2 = (t2 - t1);
        System.out.println("生成报告成功！");
        System.out.println("计算时间：" + t2 + "毫秒\n");
        String content = null;
        content = leftFileName + "与" + rightFileName +
                " 的相似度为：" + df.format(dr.getSimilarity() * 100) + "%\n"
                + "相同字符个数为：" + dr.getCount() + " 个 ";

        if (content!=null){
            model.addAttribute("text",leftTextArea);
            model.addAttribute("text2",rightTextArea);
            model.addAttribute("similarity",df.format(dr.getSimilarity() * 100)+"%");
            model.addAttribute("file1name",file1name);
            model.addAttribute("file2name",file2name);
            model.addAttribute("count",dr.getCount());
            model.addAttribute("avg",dr.getAvg());
            model.addAttribute("cluster",dr.getCluster());

            content1=null;
            content2=null;
        }
        else{ addFlashMessage(redirectAttributes,ERROR_MESSAGE);}
        return "/admin/SimilarDetection/onlineTesting";
        //  ResponseUtils.outJson(response, jsonStr);
    }



    @RequestMapping(value = "/batch", method = RequestMethod.GET)
    public String batch(Model model) {
        return "admin/SimilarDetection/batchDetection";
    }

    @RequestMapping(value ="/batchDetection", method= RequestMethod.POST)
    public String batchDetection(@RequestParam("in") MultipartFile[] in, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model)throws IOException {
        //获取文件的根目录
        String contextPath=request.getServletContext().getRealPath("/");
        //上传文件的数组
        File[] files=new File[in.length];
        for (int i=0;i<in.length;i++){
            String contextpath=request.getServletContext().getRealPath("/")+"fileUpload"+"/"+in[i].getOriginalFilename();
            //String contextpath = request.getContextPath()+in[i].getOriginalFilename();
            File file=new File(contextpath);
            FileUtils.copyInputStreamToFile(in[i].getInputStream(),file);
            files[i]=file;
        }

        BatchDetection batchDetection=new BatchDetection(files,contextPath);
        try {
            batchDetectionResults=batchDetection.Detection();
        }catch (Exception e){
            e.getCause();
        }

/*        String result=batchDetection.Detection();
        if (result.equals("error")){
            addFlashMessage(redirectAttributes,Message.error("该目录下没有或仅有一个可检测的文件，请重新选择输入路径！"));
            return "redirect:batchDetection.jhtml";
        }*/
        // else {
        model.addAttribute("result",batchDetectionResults);
        System.out.println("检测结果是："+batchDetectionResults);
        return "admin/SimilarDetection/batchDetection";
        // }
    }
    @RequestMapping(value="/sort",method = RequestMethod.GET)
    public String sort(HttpServletRequest request,Model model){
        String similarflag=request.getParameter("similarflag");
        System.out.println(similarflag);
        if(batchDetectionResults!=null) {
            if(similarflag.equals("asc")){
                SortUtil.sort(batchDetectionResults,true,"similarity");
            }
            else {SortUtil.sort(batchDetectionResults,false,"similarity");

            }
        }
        model.addAttribute("similarflag",similarflag);
        model.addAttribute("result",batchDetectionResults);
        return "admin/SimilarDetection/batchDetection";
    }
    @ModelAttribute
    public void init(Model model) {
        // 获取当前登录的教师信息
        Teacher temp = new Teacher();
        temp.setUsername(systemUserService.getCurrentUserName());
        temp.setSchoolId(systemUserService.getCurrentUser().getSchoolId());
        Teacher currentTeacher = teacherService.findByUserName(temp);

        List<CourseArrange> courseArrangeList = courseArrangeService.findByTeacher(currentTeacher.getId());

        List<ClassEntity> classList = new ArrayList<>();
        List<Course> courseList = new ArrayList<>();
        List<Long> tableIdList = new ArrayList<>();
        WorkingTable workingTable = new WorkingTable();
        List<WorkingTable> workList = new ArrayList<>();

        boolean classFlag = true;
        boolean courseFlag = true;

        for (int i = 0; i < courseArrangeList.size(); i++) {
            CourseArrange courseArrange = courseArrangeList.get(i);
            // 班级去重判断
            for (int j = 0; j < classList.size(); j++) {
                if (classList.get(j).getId() == courseArrange.getClassSystem().getId()) {
                    classFlag = false;
                }
            }
            if (classFlag) {
                classList.add(courseArrangeList.get(i).getClassSystem());
            } else {
                classFlag = true;
            }
            // 课程去重判断
            for (int j = 0; j < courseList.size(); j++) {
                if (courseList.get(j).getId() == courseArrange.getCourse().getId()) {
                    courseFlag = false;
                }
            }
            if (courseFlag) {
                courseList.add(courseArrangeList.get(i).getCourse());
            } else {
                courseFlag = true;
            }
        }
        // 获取相应的作业表
        for (int i = 0; i < courseArrangeList.size(); i++) {
            List<CourseArrangeAndWorkingTable> courseArrangeAndWorkingTableList = courseArrangeList.get(i)
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

        model.addAttribute("classList", classList);
        model.addAttribute("courseList", courseList);
        model.addAttribute("workingTableList", workList);
        System.out.println(workList);
    }

}



