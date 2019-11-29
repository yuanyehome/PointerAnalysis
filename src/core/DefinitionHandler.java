package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.*;

import java.util.TreeSet;

public class DefinitionHandler extends StmtHandler {
    @Override
    public void handle(Anderson ad, StoreType in, Unit u, StoreType out) {
        DefinitionStmt du = (DefinitionStmt) u;
        Value RightOp = du.getRightOp();
        Value LeftOp = du.getLeftOp();

        TreeSet<Integer> rightVal = new TreeSet<>();

        if (RightOp instanceof AnyNewExpr) {
            if (ad.isChecked) {
                rightVal.add(ad.allocId);
                ad.isChecked = false;
            } else rightVal.add(0);
        } else if (RightOp instanceof Local) {
            // a = *b in Anderson
            Local from = (Local) RightOp;
            rightVal.addAll(in.getPointsToSet(from));
        } else if (RightOp instanceof CastExpr) {
            rightVal = handleCast(ad, in, du);
        } else if (RightOp instanceof InvokeExpr) {
            new InvokeExprHandler().run(ad, (InvokeExpr) RightOp, rightVal, in, out);
        } else if (RightOp instanceof InstanceFieldRef) {
            rightVal = handleField(ad, in, du);
        } else if (RightOp instanceof ParameterRef) {
            int i = ((ParameterRef) RightOp).getIndex();
            if (ad.str2arg.containsKey(Integer.toString(i))) {
                rightVal.addAll(ad.args.getPointsToSet(ad.str2arg.get(Integer.toString(i))));
                if (LeftOp instanceof Local) {
                    ad.arg2local.put(ad.str2arg.get(Integer.toString(i)), LeftOp);
                }
            }
        } else if (RightOp instanceof ThisRef) {
            if(ad.str2arg.containsKey("this")) {
                rightVal.addAll(ad.args.getPointsToSet(ad.str2arg.get("this")));
                if (LeftOp instanceof Local) {
                    ad.arg2local.put(ad.str2arg.get("this"), LeftOp);
                }
            }
        }
        else{
            System.out.println("\033[33mDefinitionStmt: Not implemented - Right: \033[0m" + RightOp.getClass().getName());
        }

        if (LeftOp instanceof Local) {
            out.put(LeftOp, rightVal);
        } else if (LeftOp instanceof InstanceFieldRef) {
            System.out.println("\033[32mDefinitionStmt: Not implemented-Left: \033[0m" +
                    LeftOp.getClass().getName());
            // out.put((Local) LeftOp, RightVal);
        } else {
            System.out.println(
                    "\033[32mDefinitionStmt: Not implemented - Left: \033[0m" +
                            LeftOp.getClass().getName());
        }

        /*
        TODO Deal with other types of left/right Op.
        TODO Deal with fields.
         */
    }

    private TreeSet<Integer> handleCast(Anderson ad, StoreType in, DefinitionStmt st) {
        return new TreeSet<>(in.getPointsToSet((Local)st.getRightOp()));
    }

    private TreeSet<Integer> handleField(Anderson ad, StoreType in, DefinitionStmt st) {
//        InstanceFieldRef Field = (InstanceFieldRef) st.getRightOp();
//        InstanceFieldRef tmpField = Field;
//        int cnt = 0;
//        while (!(tmpField.getBase() instanceof Local)) {
//            ++cnt;
//            tmpField = (InstanceFieldRef) tmpField.getBase();
//            // a.b.c.d.e => cnt = 4
//        }
//        Local root = (Local) tmpField.getBase();
        return new TreeSet<>();
        // TODO check if -1->find root->find deepest field->give value
    }

}
