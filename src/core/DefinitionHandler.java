package core;

import fj.data.Array;
import polyglot.ast.New;
import soot.ArrayType;
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
    public void handle(Anderson ad, PointsToMap in, Unit u, PointsToMap out) {
        DefinitionStmt st = (DefinitionStmt) u;
        Value rightOp = st.getRightOp();
        Value leftOp = st.getLeftOp();

        if (rightOp instanceof NewArrayExpr) {
            int id = getAndersonId(ad);
            rightVal.add(MemoryTable.allocMemory(id, rightOp));

        } else if (rightOp instanceof AnyNewExpr) { // new array expr
            int id = getAndersonId(ad);
            rightVal.add(MemoryTable.allocMemory(id, rightOp));
        } else if (rightOp instanceof Local) {
            rightVal.addAll(in.get(rightOp.toString()));
        } else if (rightOp instanceof CastExpr) {
            rightVal.addAll(in.get(rightOp.toString()));
        } else if (rightOp instanceof InstanceFieldRef) {
            InstanceFieldRef rf = (InstanceFieldRef) rightOp;
            rightVal.addAll(handleRightField(in, rf));
        } else if (rightOp instanceof InvokeExpr) {
            new InvokeExprHandler().run(ad, (InvokeExpr) rightOp, rightVal, in, out);
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
        } else if (rightOp instanceof ArrayRef) {
            ArrayRef ar = (ArrayRef) rightOp;

        }
        else {
            System.out.println("\033[33mDefinitionStmt: Not implemented - Right: \033[0m"
                    + rightOp.getClass().getName());
        }

        if (rightVal.size() == 0) {
            Logger.getLogger("").warning("Empty right val");
            rightVal = MemoryTable.getGlobalMaxId();
        }

        if (leftOp instanceof Local) {
            out.put(leftOp.toString(), rightVal);
        } else if (leftOp instanceof InstanceFieldRef) {
            InstanceFieldRef rf = (InstanceFieldRef) leftOp;
            handleLeftField(out, rf);
        } else {
            System.out.println("\033[32mDefinitionStmt: Not implemented - Left: \033[0m"
                    + leftOp.getClass().getName());
        }
    }

    private int getAndersonId(Anderson ad) {
        int id = 0;
        if (ad.isChecked) {
            id = ad.allocId;
            ad.isChecked = false;
        }
        return id;
    }

    private TreeSet<Integer> handleRightField(PointsToMap in, InstanceFieldRef rf) {
        String baseStr = FieldHelper.getBaseStr(rf);
        String fieldName = FieldHelper.getFieldName(rf);
        TreeSet<Integer> ts = in.get(baseStr);
        return MemoryTable.getPointsToSet(ts, fieldName);
    }

    private void handleLeftField(PointsToMap out, InstanceFieldRef rf) {
        String baseStr = FieldHelper.getBaseStr(rf);
        String fieldName = FieldHelper.getFieldName(rf);
        TreeSet<Integer> ts = out.get(baseStr);
        MemoryTable.update(ts, fieldName, rightVal);
    }
}
