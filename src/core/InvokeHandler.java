package core;

import fj.data.Tree;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import soot.Unit;
import soot.Value;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;

import java.util.TreeSet;

/**
 * @author all
 */
public class InvokeHandler extends StmtHandler {
    @Override
    public void handle(Anderson ad, PointsToMap in, Unit u, PointsToMap out) {
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
        ad.funcStack.get(ad.curMethod).add(ad.allocId);
    }

    private void handleTest(Anderson ad, InvokeExpr ie, PointsToMap in) {
        Value valueToTest = ie.getArgs().get(1);
        int testIndex = ((IntConstant) ie.getArgs().get(0)).value;

        TreeSet<Integer> ts = new TreeSet<>(in.get(valueToTest));
        TreeSet<Integer> tsWithZero = new TreeSet<>();
        for (Integer i : ts) {
            if (i >= 0) tsWithZero.add(i);
            else tsWithZero.add(0);
        }
        ad.queries.put(testIndex, tsWithZero);
    }
}
