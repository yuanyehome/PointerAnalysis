package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.ReturnStmt;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author yangchenyang
 */
public class ReturnHandler extends StmtHandler {
    @Override
    public void handle(Anderson ad, RuntimeEnv in, Unit u, RuntimeEnv out) {
        System.out.println(ad.curPrefix + " args: " + ad.args.toString());
        System.out.println(ad.curPrefix + " in: " + in.toString());
        for (Map.Entry<String, TreeSet<Integer>> e : ad.args.entrySetAll()) {
            e.setValue(in.get(e.getKey()));
        }
        if (u instanceof ReturnStmt) {
            Value returnOp = ((ReturnStmt) u).getOp();
            if (returnOp instanceof Local) {
                ad.result.addAll(in.get(returnOp));
            }
        }
    }
}
