package core;

import com.sun.xml.internal.ws.wsdl.writer.document.Definitions;
import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.*;
import sun.security.jca.GetInstance;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author all
 */
public class DefinitionHandler extends StmtHandler {
    @Override
    public void handle(Anderson ad, StoreType in, Unit u, StoreType out) {
        DefinitionStmt du = (DefinitionStmt) u;
        Value rightOp = du.getRightOp();
        Value leftOp = du.getLeftOp();

        TreeSet<Integer> rightVal = new TreeSet<>();

        if (rightOp instanceof AnyNewExpr) {
            if (ad.isChecked) {
                rightVal.add(ad.allocId);
                ad.isChecked = false;
            } else rightVal.add(0);
        } else if (rightOp instanceof Local) {
            // a = *b in Anderson
            Local from = (Local) rightOp;
            rightVal.addAll(in.getPointsToSet(from));
        } else if (rightOp instanceof CastExpr) {
            rightVal = handleCast(ad, in, du);
        } else if (rightOp instanceof InvokeExpr) {
            new InvokeExprHandler().run(ad, (InvokeExpr) rightOp, rightVal, in, out);
        } else if (rightOp instanceof InstanceFieldRef) {
            rightVal = handleField(ad, in, du);
        } else {
            System.out.println("\033[33mDefinitionStmt: Not implemented - Right: \033[0m" + rightOp.getClass().getName());
        }

        if (leftOp instanceof Local) {
            out.put(leftOp, rightVal);
        } else if (leftOp instanceof InstanceFieldRef) {
            System.out.println("\033[32mDefinitionStmt: Not implemented-Left: \033[0m" +
                    leftOp.getClass().getName());
            // out.put((Local) leftOp, RightVal);
        } else {
            System.out.println(
                    "\033[32mDefinitionStmt: Not implemented - Left: \033[0m" +
                            leftOp.getClass().getName());
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
