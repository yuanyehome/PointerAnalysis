package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.ReturnStmt;

import java.util.Map;
import java.util.TreeSet;

public class ReturnHandler extends StmtHandler {
    @Override
    public void handle(Anderson ad, Map<Local, TreeSet<Integer>> in, Unit u, Map<Local, TreeSet<Integer>> out) {
        System.out.println(ad.curPrefix + " args: " + ad.args.toString());
        System.out.println(ad.curPrefix + " in: " + in.toString());
        for (Map.Entry<Local, TreeSet<Integer>> e : ad.args.entrySet()) {
            e.setValue(in.get(e.getKey()));
        }
        if (u instanceof ReturnStmt) {
            Value returnOp = ((ReturnStmt) u).getOp();
            if (returnOp instanceof Local) {
                ad.result.addAll(in.get(returnOp)); // TODO: ???
            }
        }
    }
}
