package cn.youyitech.anyview.system.service.impl;

import cn.youyitech.anyview.system.dao.*;
import cn.youyitech.anyview.system.entity.*;
import cn.youyitech.anyview.system.service.SchemeContentTableService;
import cn.youyitech.anyview.system.service.SimilarityDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
@Service("similartyDectectionServiceImpl")
public class SimilarityDetectionServiceImpl implements SimilarityDetectionService,Runnable {
    @Autowired
    CourseArrangeDao courseArrangeDao;
    @Autowired
    ExerciseDao exerciseDao;
    @Autowired
    SchemeContentTableDao schemeContentTableDao;
    @Autowired
    SchemeContentTableService schemeContentTableService;
    @Autowired
    WorkingTableDao workingTableDao;
    @Autowired
    StudentDao studentDao;
    private int cid;
    private List<Long> ids;
    private String path;
    private HttpServletResponse response;



    @Override
    public List<ClassEntity> getClassByTId(int tid) {
        return null;
    }

    @Override
    public List<CourseArrangeAndWorkingTable> getSchemeByClaIdAndCouId(int claid, int couid) {
        return null;
    }

    @Override
    public boolean downloadAnswer(int cid, List<Long> ids, String path,HttpServletResponse response) {
        this.cid = cid;
        this.ids = ids;
        this.path = path;
        this.response=response;
        run();
        return true;
    }
    private void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory())
        {
            File[] fl = f.listFiles();
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++)
            {
                zip(out, fl[i], base + fl[i].getName());
            }
        }
        else
        {
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            while ( (b = in.read()) != -1)
            {
                out.write(b);
            }
            in.close();
        }
    }
    //递归删除
    private void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }

    @Override
    public void run() {
        for(int i = 0; i < ids.size(); i++) {

            Long intids = new Long(ids.get(i));
            SchemeContentTable sct = schemeContentTableService.find(intids);
            List<Exercise> et = exerciseDao.getExerciseAnswer(cid, sct.getPID().intValue());

            if (et.isEmpty() != true) {
                //查找题目
                List<SchemeContentTable> sc = schemeContentTableDao.getSchemeContentList(et.get(0).getVId(), et.get(0).getPId());
                //压缩包下的第一个文件夹(查询语句是？)
                String FolderPath = path + "/" + workingTableDao.
                	find(new Long((long) et.get(0).getVId())).getTableName() + "_" + sc.get(0).getVChapName() + "/" + sc.get(0).getVPName();
                File folder = new File(FolderPath);
                folder.mkdirs();
                for (int j = 0; j < et.size(); j++) {
                    if (et.get(j).getRunResult()!=1) continue;

                    File file = new File(FolderPath + "/" + studentDao.find(new Long((long) et.get(j).getSId())).getUsername() +
                    	"_" + studentDao.find(new Long((long) et.get(j).getSId())).getName() + ".c");
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                        BufferedWriter bw = new BufferedWriter(osw);
                        if (et.get(j).getEContent() != null) {
                            String content = null;
                            try {
                                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                                XmlPullParser parser = factory.newPullParser();
                                parser.setInput(getStringToStream(et.get(j).getEContent()), "utf-8");
                                parser.next();
                                content = parser.nextText();
                            } catch (Exception e) {
                                e.getCause();
                            }
                            //将数据库获取的学生答案写入文件夹的文件中
                            bw.write(content);
                        }
                        bw.close();
                        osw.close();
                        fos.close();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }
        try {
        	//设置响应头
        	response.setHeader("content-disposition", "attachment;filename="+"下载答案");
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
            File inputFileName = new File(path);
            zip(out, inputFileName, "");
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //删除文件夹
        File deleteFile = new File(path);
        //deleteAllFilesOfDir(deleteFile);
    }
    
    
    // 把字符串变成流
    public InputStream getStringToStream(String string) {
        if (string != null && !string.trim().equals("")) {

            ByteArrayInputStream stream;
            try {
                stream = new ByteArrayInputStream(string.getBytes("utf-8"));
                return stream;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

