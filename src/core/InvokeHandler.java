package core;

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
        String allocStr2 = "<benchmark.internal.Benchmark: void alloc(int)>";
        String testStr2 = "<benchmark.internal.Benchmark: void test(int,java.lang.Object)>";

        if (methodStr.equals(allocStr) || methodStr.equals(allocStr2)) {
            handleAlloc(ad, ie);
        } else if (methodStr.equals(testStr) || methodStr.equals(testStr2)) {
            handleTest(ad, ie, in);
        } else {
            // current implementation for function calls, context-insensitive, don't
            // consider arguments
            new InvokeExprHandler().run(ad, ie, new TreeSet<Integer>(), in, out);
        }
    }

    private void handleAlloc(Anderson ad, InvokeExpr ie) {
        ad.allocId = ((IntConstant) ie.getArgs().get(0)).value;
        ad.isChecked = true;
        ad.allocIDSet.add(ad.allocId);
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
        Anderson.queries.put(testIndex, tsWithZero);
    }
}
