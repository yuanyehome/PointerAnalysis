package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.*;

import java.util.TreeSet;
import java.util.logging.Logger;

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
            Logger.getLogger("").warning("New here!!!" + rightOp.toString());
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
            InstanceFieldRef field = (InstanceFieldRef) rightOp;
            rightVal.addAll(in.get(field));
        } else if (rightOp instanceof ParameterRef) {
            int i = ((ParameterRef) rightOp).getIndex();
            if (ad.str2arg.containsKey(Integer.toString(i))) {
                rightVal.addAll(ad.args.get(ad.str2arg.get(Integer.toString(i))));
                if (leftOp instanceof Local) {
                    ad.arg2local.put(ad.str2arg.get(Integer.toString(i)), leftOp.toString());
                }
            }
        } else if (rightOp instanceof ThisRef) {
            if (ad.str2arg.containsKey("this")) {
                rightVal.addAll(ad.args.get(ad.str2arg.get("this")));
                if (leftOp instanceof Local) {
                    ad.arg2local.put(ad.str2arg.get("this"), leftOp.toString());
                }
            }
        } else {
            System.out.println("\033[33mDefinitionStmt: Not implemented - Right: \033[0m"
                    + rightOp.getClass().getName());
        }

        if (rightVal.size() == 0) return;

        if (leftOp instanceof Local) {
            out.put(leftOp, rightVal);
        } else if (leftOp instanceof InstanceFieldRef) {
            handleLeftField(ad, out, du);
        } else {
            System.out.println("\033[32mDefinitionStmt: Not implemented - Left: \033[0m"
                    + leftOp.getClass().getName());
        }
        // TODO: more type of left/right OP.
    }

    private TreeSet<Integer> handleCast(Anderson ad, RuntimeEnv in, DefinitionStmt st) {
        return new TreeSet<>(in.get((Local) st.getRightOp()));
    }

    private void handleLeftField(Anderson ad, RuntimeEnv out, DefinitionStmt st) {
        InstanceFieldRef leftField = (InstanceFieldRef) st.getLeftOp();
        out.put(leftField, rightVal);
    }
}
