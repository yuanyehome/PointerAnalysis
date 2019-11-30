package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.DefinitionStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Anderson extends ForwardFlowAnalysis {
    public int allocId = 0;
    public boolean isChecked = false;

    Map<String, TreeSet<Integer>> pts =
            new HashMap<>(); // points-to set, each local a state
    TreeMap<Integer, TreeSet<Integer>> queries =
            new TreeMap<>(); // record query info
    TreeSet<Integer> result = new TreeSet<>();

    RuntimeEnv args = new RuntimeEnv();
    Map<String, String> str2arg;
    Map<String, String> arg2local = new HashMap<>();
    String curPrefix; // used for function calls, to distinguish different local vals

    Anderson(DirectedGraph graph, String _curPrefix) {
        super(graph);
        curPrefix = _curPrefix + '/';
    }

    void run(Map<String, TreeSet<Integer>> _pts,
             TreeMap<Integer, TreeSet<Integer>> _queries,
             TreeSet<Integer> _result, RuntimeEnv _args, Map<String, String> _str2arg) {
        System.out.println(curPrefix + " Previous arguments:" + _args.toString());

        args.putAll(_args);
        str2arg = _str2arg;
        doAnalysis(); // analysis main body (implemented in FlowAnalysis)
        _pts.putAll(pts);
        _queries.putAll(queries);
        _result.addAll(result);
        _args.putAll(args);
        System.out.println(curPrefix + " After arguments:" + _args.toString());
    }

    protected Object newInitialFlow() {
        return new RuntimeEnv();
    }

    /*
    @Override
    protected Object entryInitialFlow() {
        Logger.getLogger("").warning("Fuck it!");
        Map<String, Set<String>> ret = new HashMap<String, Set<String>>();
        copy(args, ret);
        return ret;
    }
    */

    // deep copy for HashMap
    protected void copy(Object _src, Object _dest) {
        RuntimeEnv src = (RuntimeEnv) _src;
        RuntimeEnv dest = (RuntimeEnv) _dest;
        dest.copyFrom(src);
    }

    protected void merge(Object _in1, Object _in2, Object _out) {
        RuntimeEnv in1, in2, out;
        in1 = (RuntimeEnv) _in1;
        in2 = (RuntimeEnv) _in2;
        out = (RuntimeEnv) _out;
        out.copyFrom(in1);
        out.mergeFrom(in2);
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
        RuntimeEnv in = (RuntimeEnv) _in;
        RuntimeEnv out = (RuntimeEnv) _out;
        Unit u = (Unit) _data;

        copy(in, out);

        // begin processing
        System.out.println("\033[35mHandle: \033[m" + u.toString());
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
