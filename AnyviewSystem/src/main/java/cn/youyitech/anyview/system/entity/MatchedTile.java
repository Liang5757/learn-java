package cn.youyitech.anyview.system.entity;

/**
 * 代表一对匹配的字符
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/26 19:03)
 */
public class MatchedTile {

    private Tile        tileA;

    private Tile        tileB;

    /**
     * 编号
     */
    private int         id;

    public MatchedTile(Tile tileA, Tile tileB, int id) {
        this.tileA = tileA;
        this.tileB = tileB;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getLength() {
        return this.size();
    }

    public int size() {
        return tileA.getLength();
    }

    public Tile getTileA() {
        return tileA;
    }

    public Tile getTileB() {
        return tileB;
    }

}

