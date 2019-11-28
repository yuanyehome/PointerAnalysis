package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.*;

import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;

public class DefinitionHandler extends StmtHandler {
    @Override
    public void handle(Anderson ad, Map<Local, TreeSet<Integer>> in, Unit u, Map<Local, TreeSet<Integer>> out) {
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
            rightVal.addAll(in.get(from));
        } else if (RightOp instanceof Ref) {
            // TODO
        } else if (RightOp instanceof CastExpr) {
            rightVal = handleCast(ad, in, du);
        } else if (RightOp instanceof InvokeExpr) {
            // TODO
        } else {
            Logger.getLogger("").warning("DefinitionStmt: Not implemented: " + RightOp.getClass().getName());
        }

        if (LeftOp instanceof Local) {
            out.put((Local) LeftOp, rightVal);
        }

        /*
        TODO Deal with other types of left/right Op.
        TODO Deal with arrays.
        TODO Deal with fields.
         */
    }

    private TreeSet<Integer> handleCast(Anderson ad, Map<Local, TreeSet<Integer>> in, DefinitionStmt st) {
        return new TreeSet<>(in.get((Local)st.getRightOp()));
    }
}
