package cn.youyitech.anyview.system.utils.similarCalculations;

import cn.youyitech.anyview.system.entity.*;

import java.util.*;

/**
 * 行选算法类，单例
 *
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/26 0:03)
 */
public class RowSelected {

    /**
     * 相同类型个数
     */
    private int Count = 0;

    private static RowSelected rowSelected = new RowSelected();

    private RowSelected() {

    }

    /**
     * 双重锁定实现单例
     *
     * @return
     */
    public static RowSelected getRowSelected() {
        if (rowSelected == null) {
            synchronized (RowSelected.class) {
                if (rowSelected == null) {
                    rowSelected = new RowSelected();
                }
            }
        }
        return rowSelected;
    }

    /**
     * 生成两篇文章的对比结果
     *
     * @param fileTextA
     * @param fileTextB
     * @param fa
     * @param fb
     * @param a
     * @return
     */
    public DetectionResult rowSelected(String fileNameA,String fileNameB,String fileTextA, String fileTextB, FileGrammar fa,
                                       FileGrammar fb,
                                       double a) {

        Map<Integer, int[][]> commontLine = selected(fa, fb, a);
        Map<Integer, int[]> commontAnalysis = new TreeMap<Integer, int[]>();
        MatchedTileSet matches = generateMatchedTileSet(commontLine, commontAnalysis);
        double[] cluster = clusterAnalysis(commontAnalysis);
        double similarity = (fa.getTypeCount() > fb.getTypeCount()) ?
            ((double)Count / (double)fa.getTypeCount()) :
            ((double)Count / (double)fb.getTypeCount());
        DetectionResult dr = new DetectionResult(fileNameA,fileNameB,fileTextA, fileTextB, matches, similarity);
        dr.setAvg(cluster[0]);
        dr.setCluster(cluster[1]);
        dr.setCount(Count);
        return dr;
    }
    
    /**
     * 生成两篇文章的对比结果
     *
     * @param fileTextA
     * @param fileTextB
     * @param fa
     * @param fb
     * @param a
     * @return
     */
    public DetectionResult rowSelected(FileText fileTextA, FileText fileTextB, FileGrammar fa,
                                       FileGrammar fb,
                                       double a) {

        Map<Integer, int[][]> commontLine = selected(fa, fb, a);
        Map<Integer, int[]> commontAnalysis = new TreeMap<Integer, int[]>();
        MatchedTileSet matches = generateMatchedTileSet(commontLine, commontAnalysis);
        double[] cluster = clusterAnalysis(commontAnalysis);
        double similarity = (fa.getTypeCount() > fb.getTypeCount()) ?
            ((double)Count / (double)fa.getTypeCount()) :
            ((double)Count / (double)fb.getTypeCount());
        DetectionResult dr = new DetectionResult(fileTextA, fileTextB, matches, similarity);
        dr.setAvg(cluster[0]);
        dr.setCluster(cluster[1]);
        dr.setCount(Count);
        return dr;
    }

    /**
     * @param commontLine     MAP<i, [[j,相似度,k],[相同的个数，相同的类型值编号]]>相同的行号、相似度（相似度为扩大了100倍的整数）、相同的类型值编号
     * @param commontAnalysis MAP<i, [相同的个数，相同的类型值编号]>
     * @return
     */
    private MatchedTileSet generateMatchedTileSet(Map<Integer, int[][]> commontLine,
                                                  Map<Integer, int[]> commontAnalysis) {
        //j去重处理
        Map<Integer, int[]> commont = new TreeMap<>();
        int[] remove = new int[commontLine.size()];
        int removeidex = 0;
        Iterator<Integer> it = commontLine.keySet().iterator();
        while (it.hasNext()) {
            Integer p = it.next();
            int[][] b = commontLine.get(p);
            if (commont.containsKey(b[0][0])) {
                //j已存在，则对比相似度
                int[] l = commont.get(b[0][0]);
                if (l[1] < b[0][1]) {
                    remove[removeidex++] = l[0];
                    l[0] = p;
                    l[1] = b[0][1];
                    commont.remove(b[0][0]);
                    commont.put(b[0][0], l);
                } else {
                    remove[removeidex++] = p;
                }
            } else {
                int[] l = new int[2];
                l[0] = p;
                l[1] = b[0][1];
                commont.put(b[0][0], l);
            }
        }
        for (int i = 0; i < removeidex; i++) {
            commontLine.remove(remove[i]);
        }

        //获取<i, j>，并计算相似的个数和
        int[][] towCommont = new int[commontLine.size()][2];
        int i = 0;
        Iterator<Integer> its = commontLine.keySet().iterator();
        Count = 0;
        while (its.hasNext()) {
            Integer p = its.next();
            int[][] b = commontLine.get(p);
            Count += b[0][2];
            int[] a = new int[2];
            a[0] = p;
            a[1] = b[0][0];
            towCommont[i++] = a;
            commontAnalysis.put(p, b[1]);
        }

        MatchedTileSet matches = new MatchedTileSet();    //存放两篇文档所有匹配字符
        int sum = 1;
        for (int j = 0; j < towCommont.length; j++) {
            //顺序连块
            int end = j;
            while (end < towCommont.length - 1) {
                if ((towCommont[end][0] + 1) == towCommont[end + 1][0]
                    && (towCommont[end][1] + 1) == towCommont[end + 1][1]) {
                    end++;
                } else {
                    break;
                }
            }

            Tile tileA = new Tile(towCommont[j][0], towCommont[end][0]);
            Tile tileB = new Tile(towCommont[j][1], towCommont[end][1]);
            MatchedTile mt = new MatchedTile(tileA, tileB, sum);
            matches.addMatchedTile(mt);
            sum++;
            j = end;
        }
        return matches;

    }

    /**
     * 空间向量特征值计算
     *
     * @param commontAnalysis MAP<i, [相同的个数，相同的类型值编号]>
     * @return
     */
    private double[] clusterAnalysis(Map<Integer, int[]> commontAnalysis) {
        double[] s = new double[2];
        int sum = 0;
        double avgsum = 0;
        if (Count <= 0) {
            s[0] = 0.0;
            s[1] = 0.0;
        } else {
            Iterator<Integer> it1 = commontAnalysis.keySet().iterator();
            while (it1.hasNext()) {
                Integer p = it1.next();
                int[] comment = commontAnalysis.get(p);
                for (int i = 1; i < comment.length; i++) {
                    sum = sum + comment[i];
                }
            }
            s[0] = (double)sum / (double)Count;

            Iterator<Integer> it2 = commontAnalysis.keySet().iterator();
            while (it2.hasNext()) {
                Integer p = it2.next();
                int[] comment = commontAnalysis.get(p);
                for (int i = 1; i < comment.length; i++) {
                    avgsum = avgsum + ((double)comment[i] - s[0]) * ((double)comment[i] - s[0]);
                }
            }
            s[1] = Math.sqrt(avgsum) / (double)Count;
        }
        return s;
    }

    /**
     * 行选算法，输入两个FileGrammar和一个相似因子，计算其相似的字符个数，可在一定范围内忽略一定数量的字符
     *
     * @param fa
     * @param fb
     * @param a
     * @return MAP<i, [[j,相似度,k],[相同的个数，相同的类型值编号]]>相同的行号、相似度（相似度为扩大了100倍的整数）、相同的类型值编号
     */
    private Map<Integer, int[][]> selected(FileGrammar fa, FileGrammar fb, double a) {
        Map<Integer, int[][]> commontLine = new TreeMap<>();    //<第一篇行号，[第二篇行号，相似度，相似字符个数]>

        LineGrammar[] la = lineGrammarClone(fa.getLineGrammars());
        LineGrammar[] lb = lineGrammarClone(fb.getLineGrammars());

        for (int i = 0; i < fa.getLineCount(); i++) {
            int[] patternchar = getCharacterArrayFromString(la[i].getContent());
            for (int j = 0, max = 0; j < fb.getLineCount(); j++) {
                if (lb[j].getLine() != -1) {
                    int[] documentchar = getCharacterArrayFromString(lb[j].getContent());
                    int[] s = (patternchar.length > documentchar.length) ?
                        getCommonCount(documentchar, patternchar, a) :
                        getCommonCount(patternchar, documentchar, a);
                    if (s[0] > max) {
                        int[][] l = new int[2][];
                        l[0] = new int[3];
                        l[0][0] = lb[j].getLine();
                        l[0][1] = (patternchar.length > documentchar.length) ?
                            (int)((double)s[0] / (double)patternchar.length * 100) :
                            (int)((double)s[0] / (double)documentchar.length * 100);
                        l[0][2] = s[0];
                        l[1] = s;
                        if (commontLine.containsKey(la[i].getLine())) { commontLine.remove(la[i].getLine()); }
                        commontLine.put(la[i].getLine(), l);
                        max = s[0];
                        if (s[0] == documentchar.length) {
                            //从lb[]中移除，即将行号设为-1
                            lb[j].setLine(-1);
                            break;
                        }
                    }
                }
            }
        }
        la = null;
        lb = null;
        return commontLine;
    }

    /**
     * 相同字符计算算法
     *
     * @param pattern  短字符串pattern
     * @param document 长字符串document
     * @param a        相似度因子a
     * @return [相同个数，相同的序列]
     */
    private int[] getCommonCount(int[] pattern, int[] document, double a) {
        int Count = 0;    //计算相同的个数
        int cursor = 0;    //记录j的游标
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < pattern.length; i++) {
            for (int j = cursor; j < document.length; j++) {
                if (pattern[i] == document[j]) {
                    Count++;
                    list.add(pattern[i]);
                    cursor = j;
                    break;
                }
            }
        }
        int[] s = new int[Count + 1];
        if ((double)Count / (double)document.length < a) {
            s[0] = 0;
        } else {
            s[0] = Count;
            for (int i = 1; i < s.length; i++) {
                s[i] = list.get(i - 1);
            }
        }
        return s;
    }

    /**
     * 将字符串转换为新分配的整型数组
     *
     * @param text
     * @return
     */
    private int[] getCharacterArrayFromString(String text) {
        String[] strArray = null;
        strArray = text.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        int array[] = new int[strArray.length];
        try {
            for (int i = 0; i < strArray.length; i++) {
                array[i] = Integer.parseInt(strArray[i]);
            }
        } catch (Exception e) {
            System.err.println("字符串转换失败！");
        }
        return array;
    }

    /**
     * lineGrammarClone对象的克隆
     *
     * @param lineGrammars
     * @return
     */
    private LineGrammar[] lineGrammarClone(List<LineGrammar> lineGrammars) {
        LineGrammar[] clone = new LineGrammar[lineGrammars.size()];
        for (int i = 0; i < lineGrammars.size(); i++) {
            clone[i] = (LineGrammar)lineGrammars.get(i).clone();
        }
        return clone;
    }

}
