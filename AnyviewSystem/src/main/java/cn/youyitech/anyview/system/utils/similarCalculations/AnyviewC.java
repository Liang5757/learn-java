package cn.youyitech.anyview.system.utils.similarCalculations;

import cn.youyitech.anyview.system.entity.FileGrammar;
import cn.youyitech.anyview.system.entity.LineGrammar;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Iterator;


public class AnyviewC {
	
	 public FileGrammar ac(String str) {
	        ANTLRInputStream input = new ANTLRInputStream(str);
	        AnyviewCLexer lexer = new AnyviewCLexer(input);
	        CommonTokenStream tokens = new CommonTokenStream(lexer);

	        AnyviewCParser parser = new AnyviewCParser(tokens);
	        ParseTree tree = parser.primaryexpression();

	        ParseTreeWalker walker = new ParseTreeWalker();
	        AnyviewCGetNode node = new AnyviewCGetNode();
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
