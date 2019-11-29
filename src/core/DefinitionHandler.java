package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.*;

import java.util.TreeSet;

/**
 * @author all
 */
public class DefinitionHandler extends StmtHandler {
    private TreeSet<Integer> rightVal = new TreeSet<>();

    @Override
        public void handle(Anderson ad, RuntimeEnv in, Unit u, RuntimeEnv out) {
            DefinitionStmt du = (DefinitionStmt) u;
            Value rightOp = du.getRightOp();
            Value leftOp = du.getLeftOp();

        if (rightOp instanceof AnyNewExpr) {
            if (ad.isChecked) {
                rightVal.add(ad.allocId);
                ad.isChecked = false;
            } else rightVal.add(0);
        } else if (rightOp instanceof Local) {
            Local from = (Local) rightOp;
            rightVal.addAll(in.get(from));
        } else if (rightOp instanceof CastExpr) {
            rightVal = handleCast(ad, in, du);
        } else if (rightOp instanceof InvokeExpr) {
            new InvokeExprHandler().run(ad, (InvokeExpr) rightOp, rightVal, in, out);
        } else if (rightOp instanceof InstanceFieldRef) {
            rightVal = handleField(ad, in, du);
        } else {
            System.out.println("\033[33mDefinitionStmt: Not implemented - Right: \033[0m" + rightOp.getClass().getName());
        }

        if (rightVal.size() == 0) return;

        if (leftOp instanceof Local) {
            out.put(leftOp, rightVal);
        } else if (leftOp instanceof InstanceFieldRef) {
            handleLeftField(ad, out, du);
        } else {
            System.out.println(
                    "\033[32mDefinitionStmt: Not implemented - Left: \033[0m" +
                            leftOp.getClass().getName());
        }
        // TODO: more type of left/right OP.
    }

    private TreeSet<Integer> handleCast(Anderson ad, RuntimeEnv in, DefinitionStmt st) {
        return new TreeSet<>(in.get((Local)st.getRightOp()));
    }

    private TreeSet<Integer> handleField(Anderson ad, RuntimeEnv in, DefinitionStmt st) {
        InstanceFieldRef field = (InstanceFieldRef) st.getRightOp();
        // TODO: return in.queryField(tmpField);
        return new TreeSet<>();
    }

    private void handleLeftField(Anderson ad, RuntimeEnv out, DefinitionStmt st) {
        InstanceFieldRef leftField = (InstanceFieldRef) st.getLeftOp();
        out.put(leftField, rightVal);
    }
}
