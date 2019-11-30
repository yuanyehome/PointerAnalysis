package core;

import fj.Hash;
import soot.Local;
import soot.SootMethod;
import soot.Value;
import soot.jimple.InstanceInvokeExpr;
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
                    RuntimeEnv in, RuntimeEnv out) {
        SootMethod m = ie.getMethod();
        DirectedGraph graph = new ExceptionalUnitGraph(m.retrieveActiveBody());
        Anderson anderson = new Anderson(graph, ad.curPrefix + m.getName());

        RuntimeEnv sonArgs = new RuntimeEnv();
        Map<String, String> str2arg = new HashMap<>();

        if (ie instanceof InstanceInvokeExpr) {
            Value base = ((InstanceInvokeExpr) ie).getBase();
            sonArgs.put(base, in.get(base));
            str2arg.put("this", base.toString());
        }
        List<Value> args = ie.getArgs();
        for (int i = 0; i < args.size(); i++) {
            Value arg = args.get(i);
            if (arg instanceof Local) {
                sonArgs.put(arg, in.get(arg));
                str2arg.put(Integer.toString(i), arg.toString());
            }
        }
        anderson.run(ad.pts, ad.queries, res, sonArgs, str2arg);
        out.putAll(sonArgs);
    }

}
