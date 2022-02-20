package cn.youyitech.anyview.system.utils.similarCalculations;

import cn.youyitech.anyview.system.entity.FileGrammar;
import cn.youyitech.anyview.system.entity.LineGrammar;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Iterator;


public class Java8 {

    public FileGrammar java(String str) {
        ANTLRInputStream input = new ANTLRInputStream(str);
        Java8Lexer lexer = new Java8Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        Java8Parser parser = new Java8Parser(tokens);
        ParseTree tree = parser.preDecrementExpression();

        ParseTreeWalker walker = new ParseTreeWalker();
        Java8GetNode node = new Java8GetNode();
        walker.walk(node, tree);

        FileGrammar fg = new FileGrammar();
        if (node.getNodemap().size() <= 0) {
            fg.setFlag(false);
            fg.setTypeCount(0);
        } else {
            Iterator<Integer> it = node.getNodemap().keySet().iterator();
            while (it.hasNext()) {
                LineGrammar lg = new LineGrammar();
                lg.setLine(it.next());
                lg.setContent(node.getNodemap().get(lg.getLine()));
                fg.addLineGrammar(lg);
            }
            fg.setFlag(true);
            fg.setTypeCount(node.getTypeCount());
        }
        return fg;
    }

}

