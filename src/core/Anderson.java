package core;

import soot.Local;
import soot.Unit;
import soot.jimple.DefinitionStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;

import java.util.*;

public class Anderson extends ForwardFlowAnalysis {
    public int allocId = 0;
    public boolean isChecked = false;
    public static int hyperPara = 0;

    Map<Local, TreeSet<Integer>> pts =
            new HashMap<>(); // points-to set, each local a state
    TreeMap<Integer, TreeSet<Integer>> queries =
            new TreeMap<>(); // record query info
    TreeSet<Integer> result = new TreeSet<>();
    Map<Local, TreeSet<Integer>> args = new HashMap<>();
    String
            curPrefix; // used for function calls, to distinguish different local vals

    Anderson(DirectedGraph graph, String _curPrefix) {
        super(graph);
        curPrefix = _curPrefix + '/';
    }

    void run(Map<Local, TreeSet<Integer>> _pts,
             TreeMap<Integer, TreeSet<Integer>> _queries,
             TreeSet<Integer> _result, Map<Local, TreeSet<Integer>> _args) {
        System.out.println(curPrefix + " Previous arguments:" + _args.toString());
        args.putAll(_args);
        doAnalysis(); // analysis main body (implemented in FlowAnalysis)
        _pts.putAll(pts);
        _queries.putAll(queries);
        _result.addAll(result);
        _args.putAll(args);
        System.out.println(curPrefix + " After arguments:" + _args.toString());
    }

    protected Object newInitialFlow() {
        return new StoreType(hyperPara);
    }

//    @Override
//    protected Object entryInitialFlow() {
//        Map<String, Set<String>> ret = new HashMap<String, Set<String>>();
//        copy(args, ret);
//        return ret;
//    }

    // deep copy for HashMap
    protected void copy(Object _src, Object _dest) {
        StoreType src, dest;
        src = (StoreType) _src;
        dest = (StoreType) _dest;
        dest.clear();
        for (Map.Entry<Local, TreeSet<Integer>> e : src.entrySet()) {
            dest.put(e.getKey(), new TreeSet<Integer>(e.getValue()));
        }
    }

    protected void merge(Object _in1, Object _in2, Object _out) {
        StoreType in1, in2, out;
        in1 = (StoreType) _in1;
        in2 = (StoreType) _in2;
        out = (StoreType) _out;
        out.clear();
        out.putAll(in1);
        for (Map.Entry<Local, TreeSet<Integer>> e : in2.entrySet()) {
            if (!out.containsKey(e.getKey()))
                out.put(e.getKey(), new TreeSet<Integer>(e.getValue()));
            out.get(e.getKey()).addAll(e.getValue());
        }
    }

    private void prefixCheck(Local l) {
        if (l.getName().contains(curPrefix))
            return;
        l.setName(curPrefix + l.getName());
    }

    // transform function

    /**
     * @param _in   Points-To Set before this statement
     * @param _data Common `Unit` type, will be a specific statement
     * @param _out  Points-To Set after this statement
     */
    @Override
    protected void flowThrough(Object _in, Object _data, Object _out) {
        StoreType in, out;
        in = (StoreType) _in;
        out = (StoreType) _out;
        Unit u = (Unit) _data;

        // print jimple stmt and points-to set
        // System.out.println(u.toString());
        // System.out.println(in.toString());

        copy(in, out);

        // begin processing
        if (u instanceof InvokeStmt) {
          new InvokeHandler().handle(this, in, u, out);
        } else if (u instanceof DefinitionStmt) {
          new DefinitionHandler().handle(this, in, u, out);
        } else if (u instanceof ReturnStmt || u instanceof ReturnVoidStmt) {
          new ReturnHandler().handle(this, in, u, out);
        } else {
            System.out.println("\033[32mStmt not implemented: \033[0m" +
                    u.getClass().getName());
        }
    }

    TreeSet<Integer> getPointsToSet(Local local) {
        return pts.get(local);
    }
}
