package core;

import soot.Local;
import soot.jimple.DefinitionStmt;

import java.util.Map;
import java.util.TreeSet;

public class CastHandler {
    public TreeSet<Integer> run(Anderson ad, Object in, Object data) {
        TreeSet<Integer> ans = new TreeSet<Integer>(
                ((Map<Local, TreeSet<Integer>>) in).get(((DefinitionStmt)data).getRightOp()));
        return ans;
    }
}
