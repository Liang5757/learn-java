package cn.youyitech.anyview.system.utils.similarCalculations;

import cn.youyitech.anyview.system.entity.FileGrammar;
import cn.youyitech.anyview.system.entity.LineGrammar;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Iterator;


public class CPP14 {

    public FileGrammar cpp(String str) {
        ANTLRInputStream input = new ANTLRInputStream(str);
        CPP14Lexer lexer = new CPP14Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CPP14Parser parser = new CPP14Parser(tokens);
        ParseTree tree = parser.primaryexpression();

        ParseTreeWalker walker = new ParseTreeWalker();
        CPP14GetNode node = new CPP14GetNode();
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
