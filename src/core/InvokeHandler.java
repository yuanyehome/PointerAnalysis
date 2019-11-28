package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;

import java.util.Map;
import java.util.TreeSet;

public class InvokeHandler extends StmtHandler {
    @Override
    public void handle(Anderson ad, StoreType in, Unit u, StoreType out) {
        InvokeExpr ie = ((InvokeStmt) u).getInvokeExpr();
        String methodStr = ie.getMethod().toString();
        String allocStr = "<benchmark.internal.BenchmarkN: void alloc(int)>";
        String testStr = "<benchmark.internal.BenchmarkN: void test(int,java.lang.Object)>";

        if (methodStr.equals(allocStr)) {
            handleAlloc(ad, ie);
        } else if (methodStr.equals(testStr)) {
            handleTest(ad, ie, in);
        } else {
            // current implementation for function calls, context-insensitive, don't
            // consider arguments
            new InvokeExprHandler().run(ad, ie, new TreeSet<Integer>(), in, out);
            // TODO Implement better analysis for function calls
        }
    }

    private void handleAlloc(Anderson ad, InvokeExpr ie) {
        ad.allocId = ((IntConstant) ie.getArgs().get(0)).value;
        ad.isChecked = true;
    }

    private void handleTest(Anderson ad, InvokeExpr ie, Map<Local, TreeSet<Integer>> in) {
        Value v = ie.getArgs().get(1);
        // prefixCheck((Local) v);
        Local lv = (Local) v;
        int id = ((IntConstant) ie.getArgs().get(0)).value;
        if (!ad.pts.containsKey(lv))
            ad.pts.put(lv, new TreeSet<Integer>());
        ad.pts.get(lv).addAll(in.get(lv));
        ad.queries.put(id, new TreeSet<Integer>(ad.pts.get(lv)));
    }
}
