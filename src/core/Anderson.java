package core;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.*;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;

import java.util.*;


public class Anderson extends ForwardFlowAnalysis {
    public static int allocId = 0;
    public static boolean isChecked = false;
    Map<Local, TreeSet<Integer>> pts = new HashMap<>(); // points-to set, each local a state
    TreeMap<Integer, TreeSet<Integer>> queries = new TreeMap<>(); // record query info
    TreeSet<Integer> result = new TreeSet<>();
    Map<Local, TreeSet<Integer>> args = new HashMap<>();
    String curPrefix; // used for function calls, to distinguish different local vals

    Anderson(DirectedGraph graph, String _curPrefix) {
        super(graph);
        curPrefix = _curPrefix + '/';
    }

    void run(Map<Local, TreeSet<Integer>> _pts, TreeMap<Integer, TreeSet<Integer>> _queries,
             TreeSet<Integer> _result, Map<Local, TreeSet<Integer>> _args) {
//		System.out.println(curPrefix);
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
        return new HashMap<Local, TreeSet<Integer>>();
    }

    @Override
    protected Object entryInitialFlow() {
        Map<String, Set<String>> ret = new HashMap<String, Set<String>>();
        copy(args, ret);
        return ret;
    }

    // deep copy for HashMap
    protected void copy(Object _src, Object _dest) {
        Map<Local, TreeSet<Integer>> src, dest;
        src = (Map<Local, TreeSet<Integer>>) _src;
        dest = (Map<Local, TreeSet<Integer>>) _dest;
        dest.clear();
        for (Map.Entry<Local, TreeSet<Integer>> e : src.entrySet()) {
            dest.put(e.getKey(), new TreeSet<Integer>(e.getValue()));
        }
    }

    protected void merge(Object _in1, Object _in2, Object _out) {
        Map<Local, TreeSet<Integer>> in1, in2, out;
        in1 = (Map<Local, TreeSet<Integer>>) _in1;
        in2 = (Map<Local, TreeSet<Integer>>) _in2;
        out = (Map<Local, TreeSet<Integer>>) _out;
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
    protected void flowThrough(Object _in, Object _data, Object _out) {
        Map<Local, TreeSet<Integer>> in, out;
        in = (Map<Local, TreeSet<Integer>>) _in;
        out = (Map<Local, TreeSet<Integer>>) _out;
        Unit u = (Unit) _data;

        // print jimple stmt and points-to set
        // System.out.println(u.toString());
        // System.out.println(in.toString());

        copy(in, out);

        //begin processing
        if (u instanceof InvokeStmt) {
            InvokeExpr ie = ((InvokeStmt) u).getInvokeExpr();
            if (ie.getMethod().toString().equals("<benchmark.internal.BenchmarkN: void alloc(int)>")) {
                allocId = ((IntConstant) ie.getArgs().get(0)).value;
                isChecked = true;
                return;
            }
            if (ie.getMethod().toString().equals("<benchmark.internal.BenchmarkN: void test(int,java.lang.Object)>")) {
                Value v = ie.getArgs().get(1);
                // prefixCheck((Local) v);
                Local lv = (Local) v;
                int id = ((IntConstant) ie.getArgs().get(0)).value;
                if (!pts.containsKey(lv))
                    pts.put(lv, new TreeSet<Integer>());
                pts.get(lv).addAll(in.get(lv));
                queries.put(id, new TreeSet<Integer>(pts.get(lv)));
                return;
            }

            // current implementation for function calls, context-insensitive, don't consider arguments
            new InvokeExprHandler().run(this, ie, new TreeSet<Integer>(), in, out);

			/*
			System.out.println("------------------------------------");
			System.out.println(curPrefix);
			System.out.println(graph.toString());
			 */

            // TODO Implement better analysis for function calls
        } else if (u instanceof DefinitionStmt) {
            Value RightOp = ((DefinitionStmt) u).getRightOp();
            Value LeftOp = ((DefinitionStmt) u).getLeftOp();
            TreeSet<Integer> RightVal = new TreeSet<Integer>();

            if (RightOp instanceof NewExpr || RightOp instanceof NewArrayExpr
                    || RightOp instanceof NewMultiArrayExpr) {
                Local to = (Local) ((DefinitionStmt) u).getLeftOp();
                if (isChecked) {
                    RightVal.add(allocId);
                    isChecked = false;
                } else RightVal.add(0);
            } else if (RightOp instanceof Local) {
                Local from = (Local) RightOp;
                RightVal.addAll(in.get(from));
            }
//			else if (RightOp instanceof NewArrayExpr) {
//				ArrayHandler handler = new ArrayHandler();
//				handler.run(this, _in, _data);
//			}
            // Don't delete, just for future work
            else if (RightOp instanceof CastExpr) {
                new CastHandler().run(this, _in, _data);
            } else if (RightOp instanceof InvokeExpr) {
                new InvokeExprHandler().run(this, (InvokeExpr) RightOp, RightVal, in, out);
            } else if (RightOp instanceof Ref) {

            } else {
                System.out.println("DefinitionStmt: Not implemented: " + RightOp.getClass().getName());
            }


            if (LeftOp instanceof Local) {
                out.put((Local) LeftOp, RightVal);
            }

			/*
			TODO Deal with other types of left/right Op.
			TODO Deal with arrays.
			TODO Deal with fields.
			 */
        } else if (u instanceof ReturnStmt || u instanceof ReturnVoidStmt) {
            System.out.println(curPrefix + " args: " + args.toString());
            System.out.println(curPrefix + " in: " + in.toString());
            for (Map.Entry<Local, TreeSet<Integer>> e : args.entrySet()) {
                e.setValue(in.get(e.getKey()));
            }
            if (u instanceof ReturnStmt) {
                Value returnOp = ((ReturnStmt) u).getOp();
                if (returnOp instanceof Local) {
                    result.addAll(in.get(returnOp));
                }
            }
        } else if (u instanceof IfStmt) {
            System.out.println("\033[32mIfStmt not implemented\033[0m: " + u.getClass().getName());
        } else {
            System.out.println("Stmt not implemented: " + u.getClass().getName());
        }

    }

    TreeSet<Integer> getPointsToSet(Local local) {
        return pts.get(local);
    }
}
