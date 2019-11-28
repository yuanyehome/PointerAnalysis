package core;

import soot.Local;
import soot.SootMethod;
import soot.Value;
import soot.jimple.InvokeExpr;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author yangchenyang
 */
public class InvokeExprHandler {

    public void run(Anderson ad, InvokeExpr ie, TreeSet<Integer> res,
                    StoreType in, StoreType out) {
        SootMethod m = ie.getMethod();
        DirectedGraph graph = new ExceptionalUnitGraph(m.retrieveActiveBody());
        Anderson anderson = new Anderson(graph, ad.curPrefix + m.getName());

        StoreType sonArgs = new StoreType();
        List<Value> args = ie.getArgs();
        for (Value arg : args) {
            if (arg instanceof Local) {
                sonArgs.put((Local) arg, in.getPointsToSet(arg));
            }
        }
        anderson.run(ad.pts, ad.queries, res, sonArgs);
        out.putAll(sonArgs);
    }

}
