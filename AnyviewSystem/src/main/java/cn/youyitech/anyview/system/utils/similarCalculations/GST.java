package cn.youyitech.anyview.system.utils.similarCalculations;

import java.util.ArrayList;
import java.util.List;

/**
 * GST算法在两篇文档中通过寻找最大的公共子串并进行标志，然后忽略已标识的字符串并不断重复这个过程，直到剩下的字符在一定范围长度内不形成任何公共子串
 * 最好时间复杂度: O(m*n)
 * 最坏时间复杂度: O(m*n*n)
 * 空间复杂度: O(m+n)
 *
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/26 0:02)
 */
public class GST {

    /**
     * 指定GST算法的一个参数:只有子串长度大于或等于这个可以认为是公共子串
     */
    private int MinimumMatchLength = 8;

    /**
     * 指定GST算法的一个参数:只有子串长度大于或等于这个可以认为是公共子串，测试打印使用
     */
    private List<GSTMatch> tilesFound;

    public GST(List<GSTMatch> tilesFound, int minimumMatchLength) {
        this.tilesFound = tilesFound;
        this.MinimumMatchLength = minimumMatchLength;
    }

    public GST() { }

    /**
     * GST比较算法，这种方法的性能并不依赖于字符串的顺序，是线程安全的方法
     * @param one
     * @param two
     * @return 标识为瓷砖的公共子串的字符总数
     */
    public int compare(String one, String two) {

        //出现文档字符串长度为0时，认为没有相似度
        if ((one.isEmpty()) || (two.isEmpty())) {
            return 0;
        }

        //较短的字符串将被称为"模式(pattern)"，长的字符串称为"文档(document)"。
        String pattern = one;
        String document = two;
        if (one.length() > two.length()) {
            pattern = two;
            document = one;
        }

        int lengthOfTokensTiled = 0;
        final char[] patternChars = getCharacterArrayFromString(pattern);
        final char[] documentChars = getCharacterArrayFromString(document);

        int firstUnmarkedPatternToken = 0;
        int firstUnmarkedDocumentToken = 0;
        List<GSTMatch> matches = new ArrayList<>();

        int maxmatch;
        do {
            maxmatch = MinimumMatchLength;
            matches.clear();

            //寻找未标识的最大公共子串
            for (int i = firstUnmarkedPatternToken; i < pattern.length(); i++) {
                for (int j = firstUnmarkedDocumentToken; j < document.length(); j++) {
                    int k = 0;
                    while (true) {
                        if (((i + k) == pattern.length()) || ((j + k) == document.length())) {
                            break;
                        }

                        char patternToken = patternChars[i + k];
                        char documentToken = documentChars[j + k];
                        if (patternToken != documentToken) { break; }
                        if (patternToken == MARKED_CHARACTER) { break; }
                        if (documentToken == MARKED_CHARACTER) { break; }
                        k++;
                    }
                    if (k == maxmatch) {
                        matches.add(new GSTMatch(i, j, k));
                    } else if (k > maxmatch) {
                        matches.clear();
                        matches.add(new GSTMatch(i, j, k));
                        maxmatch = k;
                    }

                }
            }
            //对公共子串进行标识
            for (GSTMatch match : matches) {

                //阻塞测试
                if (patternChars[match.PatternStart] == MARKED_CHARACTER) { continue; }
                if (patternChars[(match.PatternStart + match.Length) - 1] == MARKED_CHARACTER) {
                    continue;
                }
                if (documentChars[match.DocumentStart] == MARKED_CHARACTER) { continue; }
                if (documentChars[(match.DocumentStart + match.Length) - 1] == MARKED_CHARACTER) {
                    continue;
                }

                //把第一个未标识的指针移向右
                if (match.PatternStart == firstUnmarkedPatternToken) {
                    firstUnmarkedPatternToken = match.PatternStart + match.Length;
                }
                if (match.DocumentStart == firstUnmarkedDocumentToken) {
                    firstUnmarkedDocumentToken = match.DocumentStart + match.Length;
                }

                //标识
                for (int j = 0; j < match.Length; j++) {
                    patternChars[match.PatternStart + j] = MARKED_CHARACTER;
                    documentChars[match.DocumentStart + j] = MARKED_CHARACTER;
                }

                lengthOfTokensTiled += match.Length;

                //测试打印使用:
                tilesFound.add(match);
//                System.out.println("Match found (pattern " + match.PatternStart + "-" + (match.PatternStart + match.Length - 1)
//                					+ ", '" + pattern.substring(match.PatternStart, match.PatternStart + match.Length) + "')");
//
//                System.out.println("Match found (Document " + match.DocumentStart + "-" + (match.DocumentStart + match.Length - 1)
//                					+ ", '" + document.substring(match.DocumentStart, match.DocumentStart + match.Length) + "')");
            }
        }
        while (maxmatch > MinimumMatchLength);
        return lengthOfTokensTiled;
    }

    /**
     * 将字符串转换为新分配的字符数组
     * @param text
     * @return
     */
    private char[] getCharacterArrayFromString(String text) {
        return text.toCharArray();
    }

    //通过将标记直接转化为字符数组而不是在堆上的对象数组
    private static final char MARKED_CHARACTER = '\u0001';

}
