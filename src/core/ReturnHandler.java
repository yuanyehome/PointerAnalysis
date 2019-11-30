package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.ReturnStmt;

/**
 * @author yangchenyang
 */
public class ReturnHandler extends StmtHandler {

    @Override
    public void handle(Anderson ad, PointsToMap in, Unit u, PointsToMap out) {
        System.out.println(ad.curPrefix + " args: " + ad.args.toString());
        System.out.println(ad.curPrefix + " in: " + in.toString());

        System.out.println(ad.curPrefix + " changed args: " + ad.args.toString());
        if (u instanceof ReturnStmt) {
            Value returnOp = ((ReturnStmt) u).getOp();
            if (returnOp instanceof Local) {
                ad.result.addAll(in.get(returnOp));
            }
        }
    }
}
