package cn.youyitech.anyview.system.utils.similarCalculations;

import cn.youyitech.anyview.system.entity.MatchedTile;
import cn.youyitech.anyview.system.entity.Tile;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;


/**
 * 存放两篇文档所有匹配字符对，并提供操作方法
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/26 19:03)
 */
public class MatchedTileSet {

    private TreeSet matchedTiles = new TreeSet(new OrderByLength());

    public static final int ORDER_BY_LENGTH = 0;
    public static final int ORDER_BY_A      = 1;
    public static final int ORDER_BY_B      = 2;

    /**
     * 当前排序情况
     */
    private int currentOrder = ORDER_BY_LENGTH;

    public void setOrdering(int newOrder) {
        if (newOrder != this.currentOrder) {
            Comparator comp = null;

            switch (newOrder) {
                case ORDER_BY_LENGTH:
                    comp = new OrderByLength();
                    break;
                case ORDER_BY_A:
                    comp = new OrderByA();
                    break;
                case ORDER_BY_B:
                    comp = new OrderByB();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown ordering " + newOrder);
            }

            TreeSet newMatchedTiles = new TreeSet(comp);
            Iterator i = this.matchedTiles.iterator();

            while (i.hasNext()) {
                newMatchedTiles.add(i.next());
            }

            this.matchedTiles = newMatchedTiles;
            this.currentOrder = newOrder;
        }
    }

    private static class OrderByLength implements Comparator {
        public int compare(Object o1, Object o2) {
            MatchedTile mt1 = (MatchedTile) o1;
            MatchedTile mt2 = (MatchedTile) o2;

            if (mt1.size() > mt2.size()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * 按照文件A的出现顺序排序
     */
    private static class OrderByA implements Comparator {
        public int compare(Object o1, Object o2) {
            MatchedTile mt1 = (MatchedTile) o1;
            MatchedTile mt2 = (MatchedTile) o2;

            Tile t1 = mt1.getTileA();
            Tile t2 = mt2.getTileA();

            int startTokenIndex1 = t1.getStartTokenIndex();
            int startTokenIndex2 = t2.getStartTokenIndex();
            int endTokenIndex1 = t1.getEndTokenIndex();
            int endTokenIndex2 = t2.getEndTokenIndex();

            if (startTokenIndex1 < startTokenIndex2) {
                return -1;
            } else if (startTokenIndex1 > startTokenIndex2) {
                return 1;
            } else {
                // Same start tokens
                if (endTokenIndex1 < endTokenIndex2) {
                    return -1;
                } else if (endTokenIndex1 > endTokenIndex2) {
                    return 1;
                } else {
                    // Same start and end tokens
                    return -1;
                }
            }
        }
    }

    /**
     * 按照文件B的出现顺序排序
     */
    private static class OrderByB implements Comparator {
        public int compare(Object o1, Object o2) {
            MatchedTile mt1 = (MatchedTile) o1;
            MatchedTile mt2 = (MatchedTile) o2;

            Tile t1 = mt1.getTileB();
            Tile t2 = mt2.getTileB();

            int startTokenIndex1 = t1.getStartTokenIndex();
            int startTokenIndex2 = t2.getStartTokenIndex();
            int endTokenIndex1 = t1.getEndTokenIndex();
            int endTokenIndex2 = t2.getEndTokenIndex();

            if (startTokenIndex1 < startTokenIndex2) {
                return -1;
            } else if (startTokenIndex1 > startTokenIndex2) {
                return 1;
            } else {
                // Same start tokens
                if (endTokenIndex1 < endTokenIndex2) {
                    return -1;
                } else if (endTokenIndex1 > endTokenIndex2) {
                    return 1;
                } else {
                    // Same start and end tokens
                    return -1;
                }
            }
        }

    }

    /**
     * 返回所有行号迭代器
     * @return
     */
    public Iterator getMatchedTiles() {
        return matchedTiles.iterator();
    }

    public void addMatchedTile(MatchedTile mt) {
        matchedTiles.add(mt);
    }

}

