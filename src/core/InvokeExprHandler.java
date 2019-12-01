package core;

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
class InvokeExprHandler {

    void run(Anderson ad, InvokeExpr ie, TreeSet<Integer> res,
             PointsToMap in, PointsToMap out) {
        SootMethod m = ie.getMethod();

        System.out.println(ad.funcStack);
        System.out.println("\033[33mTest: \033[0m"+m.toString());
        // deal with recursion: merge all passing values
        if (ad.funcStack.containsKey(m.toString())) {
            System.out.println("Recursion: merging all possible heaps.");
            TreeSet<Integer> t = ad.funcStack.get(m.toString());
            for (Map.Entry<String, TreeSet<Integer>> e: out.entrySet()) {
                e.getValue().addAll(t);
            }
            return;
        }

        DirectedGraph graph = new ExceptionalUnitGraph(m.retrieveActiveBody());
        Anderson anderson = new Anderson(graph, ad.curPrefix + m.getName());
        anderson.curMethod = m.toString();
        anderson.funcStack.put(anderson.curMethod, new TreeSet<>());

        PointsToMap sonArgs = new PointsToMap();
        Map<String, String> str2arg = new HashMap<>();

        if (ie instanceof InstanceInvokeExpr) {
            Value base = ((InstanceInvokeExpr) ie).getBase();
            sonArgs.put(base.toString(), in.get(base));
            str2arg.put("this", base.toString());
        }
        List<Value> args = ie.getArgs();
        for (int i = 0; i < args.size(); i++) {
            Value arg = args.get(i);
            if (arg instanceof Local) {
                sonArgs.put(arg.toString(), in.get(arg));
                str2arg.put(Integer.toString(i), arg.toString());
            }
        }
        anderson.run(res, sonArgs, str2arg);
        out.putAll(sonArgs);
    }

}
