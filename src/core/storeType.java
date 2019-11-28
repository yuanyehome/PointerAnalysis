package core;

import soot.Local;
import soot.SootMethod;
import soot.Value;
import soot.jimple.InvokeExpr;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


public class StoreType {
    public int deepestLayer = -1;
    TreeSet<Integer> map_list = new TreeSet<>();
    public Map<Value, StoreType> table = new HashMap<>();
    StoreType(int x) {deepestLayer = x;}
}
