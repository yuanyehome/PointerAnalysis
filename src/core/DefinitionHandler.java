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
    private boolean realEmpty = false;

    @Override
    public void handle(Anderson ad, PointsToMap in, Unit u, PointsToMap out) {
        DefinitionStmt st = (DefinitionStmt) u;
        Value rightOp = st.getRightOp();
        Value leftOp = st.getLeftOp();

        if (rightOp instanceof AnyNewExpr) { // without new array expr
            if (MemoryTable.repeatedNewExpr.get(u) != null) {
                rightVal.add(MemoryTable.repeatedNewExpr.get(u));
            } else {
                int id = getAndersonId(ad, u);
                id = MemoryTable.allocMemory(id, rightOp);
                rightVal.add(id);
                if (rightOp instanceof NewArrayExpr) {
                    MemoryTable.initialArrayIndex(id);  // add [id@<index>=null] to memory table
                } else if (rightOp instanceof NewMultiArrayExpr) {
                    NewMultiArrayExpr mae = (NewMultiArrayExpr) rightOp;
                    int dim = mae.getSizes().size();
                    MemoryTable.initialArrayIndex(id, dim, rightOp);
                }
                MemoryTable.repeatedNewExpr.put(u, id);
            }
        } else if (rightOp instanceof Local) {
            rightVal.addAll(in.get(rightOp.toString()));
        } else if (rightOp instanceof CastExpr) {
            rightVal.addAll(in.get(((CastExpr) rightOp).getOp().toString()));
        } else if (rightOp instanceof InstanceFieldRef) {
            realEmpty = true;   // * = a.f, which a -> null
            InstanceFieldRef rf = (InstanceFieldRef) rightOp;
            rightVal.addAll(handleRightField(in, rf));
        } else if (rightOp instanceof ArrayRef) {
            ArrayRef ar = (ArrayRef) rightOp;
            rightVal.addAll(handleRightArray(in, ar));
        } else if (rightOp instanceof InvokeExpr) {
            new InvokeExprHandler().run(ad, (InvokeExpr) rightOp, rightVal, in, out);
        }  else if (rightOp instanceof ParameterRef) {
            int i = ((ParameterRef) rightOp).getIndex();
            if (ad.str2arg.containsKey(Integer.toString(i))) {
                rightVal.addAll(ad.args.get(ad.str2arg.get(Integer.toString(i))));
            }
        } else if (rightOp instanceof ThisRef) {
            if (ad.str2arg.containsKey("this")) {
                rightVal.addAll(ad.args.get(ad.str2arg.get("this")));
            }
        } else if (rightOp instanceof NullConstant) {
            rightVal = new TreeSet<>();
            realEmpty = true;
        } else if (rightOp instanceof StaticFieldRef) {
            if (Anderson.staticVal.containsKey(rightOp.toString()))
                rightVal.addAll(Anderson.staticVal.get(rightOp.toString()));
        } else if (rightOp instanceof BinopExpr || rightOp instanceof UnopExpr ||rightOp instanceof IntConstant) {
            realEmpty = true;
        } else {
            System.out.println("\033[33mDefinitionStmt: Not implemented - Right: \033[0m"
                    + rightOp.getClass().getName());
        }

        if (rightVal.size() == 0 && !realEmpty) {
            Logger.getLogger("").warning("Empty right val");
            //rightVal.add(ad.magicNum);
            rightVal = MemoryTable.getGlobalAllId();
        }

        if (leftOp instanceof Local) {
            out.put(leftOp.toString(), rightVal);
        } else if (leftOp instanceof InstanceFieldRef) {
            InstanceFieldRef rf = (InstanceFieldRef) leftOp;
            handleLeftField(out, rf);
        } else if (leftOp instanceof ArrayRef) {
            ArrayRef ar = (ArrayRef) leftOp;
            handleLeftArray(out, ar);
        } else if (leftOp instanceof StaticFieldRef) {
            if (!Anderson.staticVal.containsKey(leftOp.toString()))
                Anderson.staticVal.put(leftOp.toString(), new TreeSet<>());
            Anderson.staticVal.get(leftOp.toString()).addAll(rightVal);
        } else {
            System.out.println("\033[32mDefinitionStmt: Not implemented - Left: \033[0m"
                    + leftOp.getClass().getName());
        }
    }

    private int getAndersonId(Anderson ad, Unit u) {
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

    private TreeSet<Integer> handleRightArray(PointsToMap in, ArrayRef ar) {
        realEmpty = true;
        TreeSet<Integer> ts = in.get(ar.getBase().toString());
        return MemoryTable.getPointsToSet(ts, ArrayHelper.indexStr);
    }

    private void handleLeftField(PointsToMap out, InstanceFieldRef rf) {
        String baseStr = FieldHelper.getBaseStr(rf);
        String fieldName = FieldHelper.getFieldName(rf);
        TreeSet<Integer> ts = out.get(baseStr);
        MemoryTable.update(ts, fieldName, rightVal);    // TODO: Or `set` ?
    }

    private void handleLeftArray(PointsToMap out, ArrayRef ar) {
        TreeSet<Integer> ts = out.get(ar.getBase().toString());
        MemoryTable.update(ts, ArrayHelper.indexStr, rightVal);
    }
}
