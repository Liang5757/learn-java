package cn.youyitech.anyview.system.utils.similarCalculations;

import cn.youyitech.anyview.system.entity.DetectionResult;
import cn.youyitech.anyview.system.entity.LineText;
import cn.youyitech.anyview.system.entity.MatchedTile;
import cn.youyitech.anyview.system.utils.StringUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Iterator;


/**
 * 生成检测报告，单例模式，IO繁忙型
 *
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/27 23:03)
 */
public class HtmlReport {

    /**
     * 红色，火砖色，猎人绿，新深藏青色，深紫色，橙色 ，霓虹粉红 ，绿色，中木色，黄色
     */
    private final String color[] = {"\"#FF0000\" ", "\"#8E2323\" ", "\"#215E21\" ", "\"#00009C\" ",
                                    "\"#871F78\" ", "\"#FF7F00\" ", "\"#FF6EC7\" ", "\"#00FF00\" ",
                                    "\"#A68064\" ", "\"#CDCD00\" "};

    private static HtmlReport htmlReport = new HtmlReport();

    /**
     * 双重锁定实现单例
     *
     * @return
     */
    public static HtmlReport getHtmlReport() {
        if (htmlReport == null) {
            synchronized (HtmlReport.class) {
                if (htmlReport == null) {
                    htmlReport = new HtmlReport();
                }
            }
        }
        return htmlReport;
    }

    private HtmlReport() {

    }

    /**
     * 生成对比报告
     *
     * @param baseDir
     * @param dR
     * @param counter
     * @throws Exception
     */
    public void generateReport(File baseDir, DetectionResult dR, Integer counter) throws Exception {

        File dir = new File(baseDir.getPath() + File.separator + "detection" + counter);

        if (!dir.mkdir()) {
            throw new IOException("Unable to create directory: " + dir.getPath());
        }

        this.generateIndex(new File(dir.getPath() + File.separator + "index.html"),
            "filea-" + counter + ".html",
            "fileb-" + counter + ".html");
        this.generateResult(dir, dR);
        this.generateFiles(dir, dR, counter);
    }

    /**
     * 生成bootstrap的css样式
     *
     * @param baseDir
     * @throws Exception
     */
    public void generateBootStrap(File baseDir) {

        File csspath = new File(baseDir.getPath() + File.separator + "css");

        //如果文件夹不存在则创建
        if (!csspath.exists()) {
            csspath.mkdir();
        }

        try (InputStream is = this.getClass().getResourceAsStream("bootstrap_min");
             FileOutputStream fos = new FileOutputStream(
                 csspath.getPath() + File.separator + "bootstrap.min.css")) {

            byte[] readData = new byte[1024];
//            int i = is.read(readData);
//            while (i != -1) {
//                fos.write(readData, 0, i);
//                i = is.read(readData);
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成index.html主页面
     *
     * @param file
     * @param leftFile
     * @param rightFile
     * @throws Exception
     */
    private void generateIndex(File file, String leftFile, String rightFile) {

        try (PrintStream f = new PrintStream(new FileOutputStream(file))) {
            f.println("<html>");
            f.println("<head>");
            //f.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            f.println("<link rel=\"stylesheet\" href=\"//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
            //f.println("<link href=\"..\\css\\bootstrap.min.css\" rel=\"stylesheet\">");
            f.println("<title>");
            f.println("检测结果");
            f.println("</title>");
            f.println("</head>");

            f.println("<frameset ROWS=\"60,*\">");
            f.println("  <frame SRC=\"result.html\" name=\"top\">");
            f.println("  <frameset COLS=\"50%,50%\">");
            f.println("     <frame SRC=\"" + leftFile + "\" name=\"left\">");
            f.println("     <frame SRC=\"" + rightFile + "\" name=\"right\">");
            f.println("  </frameset>");
            f.println("</frameset>");

            f.println("</html>");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成计算结果
     *
     * @param dir
     * @param dR
     */
    private void generateResult(File dir, DetectionResult dR) {
    	if(dR.getBatchFileTextA() != null){
	        try (
	        	PrintStream ps = new PrintStream(
	            new FileOutputStream(dir.getPath() + File.separator + "result.html"))) {
	            //检测结果
	            DecimalFormat df = new DecimalFormat("0.00");
	            ps.println("<html>");
	            ps.println("<head>");
	            //ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
	            ps.println("<link rel=\"stylesheet\" href=\"//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
	            //ps.println("<link href=\"..\\css\\bootstrap.min.css\" rel=\"stylesheet\">");
	            ps.println("<div align=center>");
	            ps.println("<H1>");
	            String he = " 和 ";
	            String xiangSi = "  的相似度为:";
	            ps.println(dR.getBatchFileTextA().getFile().getName() +he+ dR.getBatchFileTextB().getFile()
	                .getName() +xiangSi+ df
	                           .format(dR.getSimilarity() * 100) + "%.");
	            ps.println("</H1>");
	            ps.println("</div>");
	            ps.println("</head>");
	            ps.println("</html>");
	            ps.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
    	}else{
    		 try (PrintStream ps = new PrintStream(
    		            new FileOutputStream(dir.getPath() + File.separator + "result.html"))) {
    		            //检测结果
    		            DecimalFormat df = new DecimalFormat("0.00");
    		            ps.println("<html>");
    		            ps.println("<head>");
    		            //ps.println( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    		            ps.println("<link rel=\"stylesheet\" href=\"//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
    		            //ps.println("<link href=\"..\\css\\bootstrap.min.css\" rel=\"stylesheet\">");
    		            ps.println("<div align=center>");
    		            ps.println("<H1>");
    		            //ps.println(dR.getFileTextA() + " 和 " + dR.getFileTextB() + "  的相似度为: " + df
    		            //               .format(dR.getSimilarity() * 100) + "%.");
    		            ps.println(dR.getFileNameA() + " 和 " + dR.getFileNameB() + "  的相似度为: " + df
    		                           .format(dR.getSimilarity() * 100) + "%.");
    		            ps.println("</H1>");
    		            ps.println("</div>");
    		            ps.println("</head>");
    		            ps.println("</html>");
    		            ps.close();
    		        } catch (FileNotFoundException e) {
    		            e.printStackTrace();
    		        }
    	}
    }

    /**
     * 根据行号生成两份对比报告的页面
     *
     * @param out     输出目录
     * @param dR
     * @param counter
     * @throws Exception
     */
    private void generateFiles(File out, DetectionResult dR, int counter) throws Exception {
        System.out.println("1111111111-------------------------------");
        PrintStream psa = new PrintStream(new FileOutputStream(
            out.getPath() + File.separator + "filea-" + counter + ".html"));
        PrintStream psb = new PrintStream(new FileOutputStream(
            out.getPath() + File.separator + "fileb-" + counter + ".html"));
        if(dR.getBatchFileTextA() !=null){		//批量检测
        psa.println("<HTML>");
        psa.println("<HEAD>");
        //psa.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        psa.println("<link rel=\"stylesheet\" href=\"//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
        //psa.println("<link href=\"..\\css\\bootstrap.min.css\" rel=\"stylesheet\">");
        //psa.println("<TITLE>" + dR.getBatchFileTextA().getFile().getPath() + "</TITLE>");
        psa.println("</HEAD>");
        psa.println("<BODY>");
        //待完善，要传入文件的名字
        //psa.println("<H2>" + dR.getBatchFileTextA().getFile().getPath() + "</H2>");
        psa.println("<CODE><PRE>");

        psb.println("<HTML>");
        psb.println("<HEAD>");
        //psb.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        psb.println("<link rel=\"stylesheet\" href=\"//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
        //psb.println("<link href=\"..\\css\\bootstrap.min.css\" rel=\"stylesheet\">");
        //psb.println("<TITLE>" + dR.getBatchFileTextB().getFile().getPath() + "</TITLE>");
        psb.println("</HEAD>");
        psb.println("<BODY>");
        //待完善，要传入文件的名字
        //psb.println("<H2>" + dR.getBatchFileTextB().getFile().getPath() + "</H2>");
        psb.println("<CODE><PRE>");

        MatchedTileSet mtSet = dR.getMatches();
        mtSet.setOrdering(MatchedTileSet.ORDER_BY_A);

        boolean withinMatch = false;
        Iterator it = mtSet.getMatchedTiles();
        MatchedTile mt = null;
        int mtcolor = 0;

        if (it.hasNext()) {
            mt = (MatchedTile)it.next();
        }

        int lineCount = 0;
        for (LineText lineText : dR.getBatchFileTextA().getLineTexts()) {
            lineCount++;
            if ((!withinMatch) && (mt != null) &&
                (mt.getTileA().getStartTokenIndex() == lineCount)) {
                // We moved into new matched tile
                withinMatch = true;
                psa.print("<A NAME=\"match" + mt.getId() + "\"></A>");
                psa.print("<A HREF=\"fileb-" + counter + ".html#match" + mt.getId()
                          + "\" TARGET=\"right\">");
                psa.print(mt.getId());
                psa.print("</A>");
                mtcolor = mt.getId();
                // Scan through all the matches ending on this same line
                while ((mt != null) && (mt.getTileA().getEndTokenIndex() == lineCount)) {
                    withinMatch = false;
                    if (it.hasNext()) {
                        mt = (MatchedTile)it.next();
                        if (mt.getTileA().getStartTokenIndex() == lineCount) {
                            // And the next match starts on this same line, too...
                            withinMatch = true;
                            psa.print("<A NAME=\"match" + mt.getId() + "\"></A>");
                            psa.print(",");
                            psa.print(
                                "<A HREF=\"fileb-" + counter + ".html#match" + mt.getId() + "\" TARGET=\"right\">");
                            psa.print(mt.getId());
                            psa.print("</A>");
                            mtcolor = mt.getId();
                        }
                    } else {
                        withinMatch = false;
                        mt = null;
                    }
                }
                psa.print(":<font color = " + color[mtcolor % 10]
                          + "size=\"2\" style= \"font-weight:bold\" >");
                psa.print(StringUtils.stringToHtml(lineText.getText()));
                psa.print("</font>");
            } else if ((withinMatch) &&
                       (mt.getTileA().getEndTokenIndex() == lineCount)) {
                // We are on the last line of a match
                withinMatch = false;
                psa.print(mt.getId());
                mtcolor = mt.getId();
                if (it.hasNext()) {
                    mt = (MatchedTile)it.next();
                    if (mt.getTileA().getStartTokenIndex() == lineCount) {
                        withinMatch = true;
                        psa.print("<A NAME=\"match" + mt.getId() + "\"></A>");
                        psa.print(",");
                        psa.print("<A HREF=\"fileb-" + counter + ".html#match" + mt.getId()
                                  + "\" TARGET=\"right\">");
                        psa.print(mt.getId());
                        psa.print("</A>");
                        mtcolor = mt.getId();
                    }
                } else {
                    mt = null;
                }
                // Scan through all the remaining matches, that happen to end on this same line
                while ((mt != null) &&
                       (mt.getTileA().getEndTokenIndex() == lineCount)) {
                    withinMatch = false;
                    if (it.hasNext()) {
                        mt = (MatchedTile)it.next();
                        if (mt.getTileA().getStartTokenIndex() == lineCount) {
                            // And the next one starts here, too
                            withinMatch = true;
                            psa.print("<A NAME=\"match" + mt.getId() + "\"></A>");
                            psa.print(",");
                            psa.print("<A HREF=\"fileb-" + counter + ".html#match" + mt.getId()
                                      + "\" TARGET=\"right\">");
                            psa.print(mt.getId());
                            psa.print("</A>");
                            mtcolor = mt.getId();
                        }
                    } else {
                        withinMatch = false;
                        mt = null;
                    }
                }
                psa.print(":<font color = " + color[mtcolor % 10]
                          + "size=\"2\" style= \"font-weight:bold\" >");
                psa.print(StringUtils.stringToHtml(lineText.getText()));
                psa.print("</font>");
            } else if (withinMatch) {
                // We are in the middle of a match
                mtcolor = mt.getId();
                psa.print(mt.getId() + ":");
                psa.print("<font color = " + color[mtcolor % 10]
                          + "size=\"2\" style= \"font-weight:bold\" >");
                psa.print(StringUtils.stringToHtml(lineText.getText()));
                psa.print("</font>");
            } else {
                // We are outside a match
                psa.print(StringUtils.stringToHtml(lineText.getText()));
            }
        }

        mtSet.setOrdering(MatchedTileSet.ORDER_BY_B);

        withinMatch = false;
        it = mtSet.getMatchedTiles();
        mt = null;
        if (it.hasNext()) {
            mt = (MatchedTile)it.next();
        }
        lineCount = 0;
	        for (LineText lineText : dR.getBatchFileTextB().getLineTexts()) {
	            lineCount++;
	            if ((!withinMatch) && (mt != null) &&
	                (mt.getTileB().getStartTokenIndex() == lineCount)) {
	                // We moved into new matched tile
	                withinMatch = true;
	                psb.print("<A NAME=\"match" + mt.getId() + "\"></A>");
	                psb.print("<A HREF=\"filea-" + counter + ".html#match" + mt.getId()
	                          + "\" TARGET=\"left\">");
	                psb.print(mt.getId());
	                psb.print("</A>");
	                mtcolor = mt.getId();
	                // Scan through all the matches ending on this same line
	                while ((mt != null) && (mt.getTileB().getEndTokenIndex() == lineCount)) {
	                    withinMatch = false;
	                    if (it.hasNext()) {
	                        mt = (MatchedTile)it.next();
	                        if (mt.getTileB().getStartTokenIndex() == lineCount) {
	                            // And the next match starts on this same line, too...
	                            withinMatch = true;
	                            psb.print("<A NAME=\"match" + mt.getId() + "\"></A>");
	                            psb.print(",");
	                            psb.print("<A HREF=\"filea-" + counter + ".html#match" + mt.getId()
	                                      + "\" TARGET=\"left\">");
	                            psb.print(mt.getId());
	                            psb.print("</A>");
	                            mtcolor = mt.getId();
	                        }
	                    } else {
	                        withinMatch = false;
	                        mt = null;
	                    }
	                }
	                psb.print(":<font color = " + color[mtcolor % 10]
	                          + "size=\"2\" style= \"font-weight:bold\" >");
	                psb.print(StringUtils.stringToHtml(lineText.getText()));
	                psb.print("</font>");
	            } else if ((withinMatch) &&
	                       (mt.getTileB().getEndTokenIndex() == lineCount)) {
	                // We are on the last line of a match
	                withinMatch = false;
	                psb.print(mt.getId());
	                mtcolor = mt.getId();
	                if (it.hasNext()) {
	                    mt = (MatchedTile)it.next();
	                    if (mt.getTileB().getStartTokenIndex() == lineCount) {
	                        withinMatch = true;
	                        psb.print("<A NAME=\"match" + mt.getId() + "\"></A>");
	                        psb.print(",");
	                        psb.print("<A HREF=\"filea-" + counter + ".html#match" + mt.getId()
	                                  + "\" TARGET=\"left\">");
	                        psb.print(mt.getId());
	                        psb.print("</A>");
	                        mtcolor = mt.getId();
	                    }
	                } else {
	                    mt = null;
	                }
	                // Scan through all the remaining matches, that happen to end on this same line
	                while ((mt != null) &&
	                       (mt.getTileB().getEndTokenIndex() == lineCount)) {
	                    withinMatch = false;
	                    if (it.hasNext()) {
	                        mt = (MatchedTile)it.next();
	                        if (mt.getTileB().getStartTokenIndex() == lineCount) {
	                            // And the next one starts here, too
	                            withinMatch = true;
	                            psb.print("<A NAME=\"match" + mt.getId() + "\"></A>");
	                            psb.print(",");
	                            psb.print("<A HREF=\"filea-" + counter + ".html#match" + mt.getId()
	                                      + "\" TARGET=\"left\">");
	                            psb.print(mt.getId());
	                            psb.print("</A>");
	                            mtcolor = mt.getId();
	                        }
	                    } else {
	                        withinMatch = false;
	                        mt = null;
	                    }
	                }
	                psb.print(":<font color = " + color[mtcolor % 10]
	                          + "size=\"2\" style= \"font-weight:bold\" >");
	                psb.print(StringUtils.stringToHtml(lineText.getText()));
	                psb.print("</font>");
	            } else if (withinMatch) {
	                // We are in the middle of a match
	                mtcolor = mt.getId();
	                psb.print(mt.getId() + ":");
	                psb.print("<font color = " + color[mtcolor % 10]
	                          + "size=\"2\" style= \"font-weight:bold\" >");
	                psb.print(StringUtils.stringToHtml(lineText.getText()));
	                psb.print("</font>");
	            } else {
	                // We are outside a match
	                psb.print(StringUtils.stringToHtml(lineText.getText()));
	            }
	        }
        }else{		//单个检测
        	psa.println("<HTML>");
            psa.println("<HEAD>");
            //psa.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            psa.println("<link rel=\"stylesheet\" href=\"//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
            //psa.println("<link href=\"..\\css\\bootstrap.min.css\" rel=\"stylesheet\">");
            //psa.println("<TITLE>" + dR.getFileTextA() + "</TITLE>");
            psa.println("<TITLE>" + dR.getFileNameA() + "</TITLE>");
            psa.println("</HEAD>");
            psa.println("<BODY>");
            psa.println("<H2>" + dR.getFileNameA() + "</H2>");
            //psa.println("<H2>" + dR.getFileTextA() + "</H2>");
            psa.println("<CODE><PRE>");

            psb.println("<HTML>");
            psb.println("<HEAD>");
            //psb.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            psb.println("<link rel=\"stylesheet\" href=\"//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
            //psb.println("<link href=\"..\\css\\bootstrap.min.css\" rel=\"stylesheet\">");
            //psb.println("<TITLE>" + dR.getFileNameB() + "</TITLE>");
            //psb.println("<TITLE>" + dR.getFileTextB() + "</TITLE>");
            psb.println("</HEAD>");
            psb.println("<BODY>");
            //psb.println("<H2>" + dR.getFileTextB() + "</H2>");
            psb.println("<H2>" + dR.getFileNameB() + "</H2>");
            psb.println("<CODE><PRE>");

            MatchedTileSet mtSet = dR.getMatches();
            mtSet.setOrdering(MatchedTileSet.ORDER_BY_A);

            boolean withinMatch = false;
            Iterator it = mtSet.getMatchedTiles();
            MatchedTile mt = null;
            int mtcolor = 0;

            if (it.hasNext()) {
                mt = (MatchedTile)it.next();
            }
            int lineCount = 0;
//            for (LineText lineText : dR.getFileTextA1().getLineTexts()) {
          StringReader stringReader=new StringReader(dR.getFileTextA());// 创建一个新字符串 reader
  		  BufferedReader bufferedReader=new BufferedReader(stringReader);
  		  String lineText=null;
  		  while((lineText=bufferedReader.readLine())!=null){
  			  lineText = lineText + "\n";
                lineCount++;
                if ((!withinMatch) && (mt != null) &&
                    (mt.getTileA().getStartTokenIndex() == lineCount)) {
                    // We moved into new matched tile
                    withinMatch = true;
                    psa.print("<A NAME=\"match" + mt.getId() + "\"></A>");
                    psa.print("<A HREF=\"fileb-" + counter + ".html#match" + mt.getId()
                              + "\" TARGET=\"right\">");
                    psa.print(mt.getId());
                    psa.print("</A>");
                    mtcolor = mt.getId();
                    // Scan through all the matches ending on this same line
                    while ((mt != null) && (mt.getTileA().getEndTokenIndex() == lineCount)) {
                        withinMatch = false;
                        if (it.hasNext()) {
                            mt = (MatchedTile)it.next();
                            if (mt.getTileA().getStartTokenIndex() == lineCount) {
                                // And the next match starts on this same line, too...
                                withinMatch = true;
                                psa.print("<A NAME=\"match" + mt.getId() + "\"></A>");
                                psa.print(",");
                                psa.print(
                                    "<A HREF=\"fileb-" + counter + ".html#match" + mt.getId() + "\" TARGET=\"right\">");
                                psa.print(mt.getId());
                                psa.print("</A>");
                                mtcolor = mt.getId();
                            }
                        } else {
                            withinMatch = false;
                            mt = null;
                        }
                    }
                    psa.print(":<font color = " + color[mtcolor % 10]
                              + "size=\"2\" style= \"font-weight:bold\" >");
                    psa.print(StringUtils.stringToHtml(lineText));
                    psa.print("</font>");
                } else if ((withinMatch) &&
                           (mt.getTileA().getEndTokenIndex() == lineCount)) {
                    // We are on the last line of a match
                    withinMatch = false;
                    psa.print(mt.getId());
                    mtcolor = mt.getId();
                    if (it.hasNext()) {
                        mt = (MatchedTile)it.next();
                        if (mt.getTileA().getStartTokenIndex() == lineCount) {
                            withinMatch = true;
                            psa.print("<A NAME=\"match" + mt.getId() + "\"></A>");
                            psa.print(",");
                            psa.print("<A HREF=\"fileb-" + counter + ".html#match" + mt.getId()
                                      + "\" TARGET=\"right\">");
                            psa.print(mt.getId());
                            psa.print("</A>");
                            mtcolor = mt.getId();
                        }
                    } else {
                        mt = null;
                    }
                    // Scan through all the remaining matches, that happen to end on this same line
                    while ((mt != null) &&
                           (mt.getTileA().getEndTokenIndex() == lineCount)) {
                        withinMatch = false;
                        if (it.hasNext()) {
                            mt = (MatchedTile)it.next();
                            if (mt.getTileA().getStartTokenIndex() == lineCount) {
                                // And the next one starts here, too
                                withinMatch = true;
                                psa.print("<A NAME=\"match" + mt.getId() + "\"></A>");
                                psa.print(",");
                                psa.print("<A HREF=\"fileb-" + counter + ".html#match" + mt.getId()
                                          + "\" TARGET=\"right\">");
                                psa.print(mt.getId());
                                psa.print("</A>");
                                mtcolor = mt.getId();
                            }
                        } else {
                            withinMatch = false;
                            mt = null;
                        }
                    }
                    psa.print(":<font color = " + color[mtcolor % 10]
                              + "size=\"2\" style= \"font-weight:bold\" >");
                    psa.print(StringUtils.stringToHtml(lineText));
                    psa.print("</font>");
                } else if (withinMatch) {
                    // We are in the middle of a match
                    mtcolor = mt.getId();
                    psa.print(mt.getId() + ":");
                    psa.print("<font color = " + color[mtcolor % 10]
                              + "size=\"2\" style= \"font-weight:bold\" >");
                    psa.print(StringUtils.stringToHtml(lineText));
                    psa.print("</font>");
                } else {
                    // We are outside a match
                    psa.print(StringUtils.stringToHtml(lineText));
                }
            }

            mtSet.setOrdering(MatchedTileSet.ORDER_BY_B);

            withinMatch = false;
            it = mtSet.getMatchedTiles();
            mt = null;
            if (it.hasNext()) {
                mt = (MatchedTile)it.next();
            }
            lineCount = 0;
//    	        for (LineText lineText : dR.getFileTextB1().getLineTexts()) {
            StringReader stringReaderB=new StringReader(dR.getFileTextB());// 创建一个新字符串 reader
  		  	BufferedReader bufferedReaderB=new BufferedReader(stringReaderB);
//  		  String lineText=null;
  		  while((lineText=bufferedReaderB.readLine())!=null){
  			  lineText = lineText + "\n";
    	            lineCount++;
    	            if ((!withinMatch) && (mt != null) &&
    	                (mt.getTileB().getStartTokenIndex() == lineCount)) {
    	                // We moved into new matched tile
    	                withinMatch = true;
    	                psb.print("<A NAME=\"match" + mt.getId() + "\"></A>");
    	                psb.print("<A HREF=\"filea-" + counter + ".html#match" + mt.getId()
    	                          + "\" TARGET=\"left\">");
    	                psb.print(mt.getId());
    	                psb.print("</A>");
    	                mtcolor = mt.getId();
    	                // Scan through all the matches ending on this same line
    	                while ((mt != null) && (mt.getTileB().getEndTokenIndex() == lineCount)) {
    	                    withinMatch = false;
    	                    if (it.hasNext()) {
    	                        mt = (MatchedTile)it.next();
    	                        if (mt.getTileB().getStartTokenIndex() == lineCount) {
    	                            // And the next match starts on this same line, too...
    	                            withinMatch = true;
    	                            psb.print("<A NAME=\"match" + mt.getId() + "\"></A>");
    	                            psb.print(",");
    	                            psb.print("<A HREF=\"filea-" + counter + ".html#match" + mt.getId()
    	                                      + "\" TARGET=\"left\">");
    	                            psb.print(mt.getId());
    	                            psb.print("</A>");
    	                            mtcolor = mt.getId();
    	                        }
    	                    } else {
    	                        withinMatch = false;
    	                        mt = null;
    	                    }
    	                }
    	                psb.print(":<font color = " + color[mtcolor % 10]
    	                          + "size=\"2\" style= \"font-weight:bold\" >");
    	                psb.print(StringUtils.stringToHtml(lineText));
    	                psb.print("</font>");
    	            } else if ((withinMatch) &&
    	                       (mt.getTileB().getEndTokenIndex() == lineCount)) {
    	                // We are on the last line of a match
    	                withinMatch = false;
    	                psb.print(mt.getId());
    	                mtcolor = mt.getId();
    	                if (it.hasNext()) {
    	                    mt = (MatchedTile)it.next();
    	                    if (mt.getTileB().getStartTokenIndex() == lineCount) {
    	                        withinMatch = true;
    	                        psb.print("<A NAME=\"match" + mt.getId() + "\"></A>");
    	                        psb.print(",");
    	                        psb.print("<A HREF=\"filea-" + counter + ".html#match" + mt.getId()
    	                                  + "\" TARGET=\"left\">");
    	                        psb.print(mt.getId());
    	                        psb.print("</A>");
    	                        mtcolor = mt.getId();
    	                    }
    	                } else {
    	                    mt = null;
    	                }
    	                // Scan through all the remaining matches, that happen to end on this same line
    	                while ((mt != null) &&
    	                       (mt.getTileB().getEndTokenIndex() == lineCount)) {
    	                    withinMatch = false;
    	                    if (it.hasNext()) {
    	                        mt = (MatchedTile)it.next();
    	                        if (mt.getTileB().getStartTokenIndex() == lineCount) {
    	                            // And the next one starts here, too
    	                            withinMatch = true;
    	                            psb.print("<A NAME=\"match" + mt.getId() + "\"></A>");
    	                            psb.print(",");
    	                            psb.print("<A HREF=\"filea-" + counter + ".html#match" + mt.getId()
    	                                      + "\" TARGET=\"left\">");
    	                            psb.print(mt.getId());
    	                            psb.print("</A>");
    	                            mtcolor = mt.getId();
    	                        }
    	                    } else {
    	                        withinMatch = false;
    	                        mt = null;
    	                    }
    	                }
    	                psb.print(":<font color = " + color[mtcolor % 10]
    	                          + "size=\"2\" style= \"font-weight:bold\" >");
    	                psb.print(StringUtils.stringToHtml(lineText));
    	                psb.print("</font>");
    	            } else if (withinMatch) {
    	                // We are in the middle of a match
    	                mtcolor = mt.getId();
    	                psb.print(mt.getId() + ":");
    	                psb.print("<font color = " + color[mtcolor % 10]
    	                          + "size=\"2\" style= \"font-weight:bold\" >");
    	                psb.print(StringUtils.stringToHtml(lineText));
    	                psb.print("</font>");
    	            } else {
    	                // We are outside a match
    	                psb.print(StringUtils.stringToHtml(lineText));
    	            }
    	        }
        }

        psa.println("</PRE></CODE>");
        psa.println("</BODY>");
        psa.println("</HTML>");
        psa.close();

        psb.println("</PRE></CODE>");
        psb.println("</BODY>");
        psb.println("</HTML>");
        psb.close();

    }

}
