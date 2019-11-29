package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.ReturnStmt;

import java.util.Map;
import java.util.TreeSet;

public class ReturnHandler extends StmtHandler {
    @Override
    public void handle(Anderson ad, StoreType in, Unit u, StoreType out) {
        System.out.println(ad.curPrefix + " args: " + ad.args.table.toString());
        System.out.println(ad.curPrefix + " in: " + in.table.toString());
        for (Map.Entry<Value, StoreType> e : ad.args.entrySet()) {
            if(in.get(ad.arg2local.get(e.getKey())) != null) {
                e.setValue(in.get(ad.arg2local.get(e.getKey())));
            }
        }
        System.out.println(ad.curPrefix + " changed args: " + ad.args.table.toString());
        if (u instanceof ReturnStmt) {
            Value returnOp = ((ReturnStmt) u).getOp();
            if (returnOp instanceof Local) {
                ad.result.addAll(in.getPointsToSet(returnOp));
            }
        }
    }
}
