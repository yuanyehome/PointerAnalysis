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
    // used to set index after `BenchmarkN.alloc(x)`
    int allocId = 0;
    boolean isChecked = false;

    static int magicNum = 65536;
    static Map<Integer, TreeSet<Integer>> queries =
            new TreeMap<>(); // record query info
    //static Map<String, TreeSet<Integer>> funcStack = new HashMap<>();
    static TreeSet<Integer> allocIDSet = new TreeSet<>();
    static Map<String, TreeSet<Integer>> staticVal = new HashMap<>();
    static List<String> funcStack = new ArrayList<>();
    String curMethod;

    TreeSet<Integer> result;
    PointsToMap args;
    Map<String, String> str2arg;
    String curPrefix; // used for function calls, to distinguish different local vals

    Anderson(DirectedGraph graph, String _curPrefix) {
        super(graph);
        curPrefix = _curPrefix + '/';
    }

    void run(TreeSet<Integer> _result, PointsToMap _args, Map<String, String> _str2arg) {
        System.out.println(curPrefix + " Memory when entering:\n" + MemoryTable.getString());
        result = _result;
        args = _args;
        str2arg = _str2arg;
        doAnalysis(); // analysis main body (implemented in FlowAnalysis)
        funcStack.remove(curMethod);
        System.out.println(curPrefix + " Memory when leaving:\n" + MemoryTable.getString());
    }

    @Override
    protected Object newInitialFlow() {
        return new PointsToMap();
    }


    @Override
    protected void copy(Object _src, Object _dest) {
        assert (_src instanceof PointsToMap) : "Error type when copy";
        PointsToMap src = (PointsToMap) _src;
        PointsToMap dest = (PointsToMap) _dest;
        dest.copyFrom(src);
    }

    @Override
    protected void merge(Object _in1, Object _in2, Object _out) {
        assert (_in1 instanceof PointsToMap) : "Error type when merge";
        PointsToMap in1 = (PointsToMap) _in1;
        PointsToMap in2 = (PointsToMap) _in2;
        PointsToMap out = (PointsToMap) _out;
        out.copyFrom(in1);
        out.mergeFrom(in2);
    }

    // transform function

    /**
     * @param _in   Points-To Set before this statement
     * @param _data Common `Unit` type, will be a specific statement
     * @param _out  Points-To Set after this statement
     */
    @Override
    protected void flowThrough(Object _in, Object _data, Object _out) {
        assert (_in instanceof PointsToMap) : "Error type when flowThrough";
        PointsToMap in = (PointsToMap) _in;
        PointsToMap out = (PointsToMap) _out;
        Unit u = (Unit) _data;
        out.copyFrom(in);

        // begin processing
        System.out.println("\033[35mHandle: \033[m" + u.toString());
        System.out.println("\033[35mCurrent pointsToMap: \033[m" + in);
        if (u instanceof DefinitionStmt) {
            new DefinitionHandler().handle(this, in, u, out);
        } else if (u instanceof InvokeStmt) {
          new InvokeHandler().handle(this, in, u, out);
        } else if (u instanceof ReturnStmt || u instanceof ReturnVoidStmt) {
          new ReturnHandler().handle(this, in, u, out);
        } else {
            System.out.println("\033[32mStmt not implemented: \033[0m" +
                    u.getClass().getName());
        }
    }
}
