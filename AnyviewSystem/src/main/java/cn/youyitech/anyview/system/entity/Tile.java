package cn.youyitech.anyview.system.entity;

/**
 * 记录一篇文档相对另一篇文档相识字符出现的位置
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/26 19:02)
 */
public class Tile {

    /**
     * 开始位置
     */
    private int startTokenIndex;

    /**
     * 结束位置
     */
    private int endTokenIndex;

    public Tile(int startTokenIndex, int endTokenIndex) {
        this.startTokenIndex = startTokenIndex;
        this.endTokenIndex = endTokenIndex;
    }

    public int getLength() {
        return endTokenIndex - startTokenIndex + 1;
    }

    public int getStartTokenIndex() {
        return startTokenIndex;
    }

    public int getEndTokenIndex() {
        return endTokenIndex;
    }

}

