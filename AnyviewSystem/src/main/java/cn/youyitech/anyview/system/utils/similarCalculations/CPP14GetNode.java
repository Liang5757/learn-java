package cn.youyitech.anyview.system.utils.similarCalculations;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Map;
import java.util.TreeMap;

/**
 * 遍历语法树，忽略"{"、"}"和";"字符
 * @author dianyu.fang
 * @version 1.0
 * @since 1.0 (2017/2/25 23:53)
 */
public class CPP14GetNode extends CPP14BaseListener {

    private Map<Integer, String> nodemap = new TreeMap<>();

    private Integer typeCount = 0;

    public Map<Integer, String> getNodemap() {
        return nodemap;
    }

    public Integer getTypeCount() {
        return typeCount;
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        if (node.getSymbol().getType() != 81 && node.getSymbol().getType() != 82
            && node.getSymbol().getType() != 120) {
            if (nodemap.containsKey(node.getSymbol().getLine())) {
                String str = nodemap.get(node.getSymbol().getLine());
                str = str + "," + node.getSymbol().getType();
                nodemap.put(node.getSymbol().getLine(), str);
            } else {
                String str = "" + node.getSymbol().getType();
                nodemap.put(node.getSymbol().getLine(), str);
            }
            typeCount++;
        }

        if (node.getParent().getParent() != null) {
            nodemap.clear();
        }
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        if (node.getSymbol().getType() != 81 && node.getSymbol().getType() != 82
            && node.getSymbol().getType() != 120) {
            if (nodemap.containsKey(node.getSymbol().getLine())) {
                String str = nodemap.get(node.getSymbol().getLine());
                str = str + "," + node.getSymbol().getType();
                nodemap.put(node.getSymbol().getLine(), str);
            } else {
                String str = "" + node.getSymbol().getType();
                nodemap.put(node.getSymbol().getLine(), str);
            }
            typeCount++;
        }
    }

}

