package core;

import fj.data.Tree;
import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.ReturnStmt;

import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * @author yangchenyang
 */
public class ReturnHandler extends StmtHandler {
    @Override
    public void handle(Anderson ad, RuntimeEnv in, Unit u, RuntimeEnv out) {
        System.out.println(ad.curPrefix + " args: " + ad.args.toString());
        System.out.println(ad.curPrefix + " in: " + in.toString());

        // TODO: Is this right? I've tried my best ...
        for (Map.Entry<String, TreeSet<Integer>> e : ad.args.entrySet()) {
            if(in.get(ad.arg2local.get(e.getKey())) != null) {
                e.setValue(in.get(ad.arg2local.get(e.getKey())));
            }
        }
        /*
        for (Map.Entry<Value, StoreType> e : ad.args.entrySet()) {
            if(in.get(ad.arg2local.get(e.getKey())) != null) {
                e.setValue(in.get(ad.arg2local.get(e.getKey())));
            }
        }
        */
        System.out.println(ad.curPrefix + " changed args: " + ad.args.toString());
        if (u instanceof ReturnStmt) {
            Value returnOp = ((ReturnStmt) u).getOp();
            if (returnOp instanceof Local) {
                ad.result.addAll(in.get(returnOp));
            }
        }
    }
}
