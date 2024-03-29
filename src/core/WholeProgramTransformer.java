package core;

import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

public class WholeProgramTransformer extends SceneTransformer {

    @Override
    protected void internalTransform(String arg0, Map<String, String> arg1) {

        SootClass mainClass = Scene.v().getSootClass(MyPointerAnalysis.getMainClass());
        SootMethod m = mainClass.getMethodByName("main");

        // build CFG with exception control flow
        // note that this CFG is local, it does not handle function calls
        DirectedGraph graph = new ExceptionalUnitGraph(m.retrieveActiveBody());
        System.out.println("\033[33mGraph: \033[0m\n" + graph.toString());

        Anderson anderson = new Anderson(graph, m.getName());
        // record function call stack
        anderson.curMethod = m.toString();
        anderson.funcStack.add(anderson.curMethod);

        anderson.run(new TreeSet<>(), new PointsToMap(), new HashMap<>()); // run analysis

        StringBuilder answer = new StringBuilder();
        for (Entry<Integer, TreeSet<Integer>> q : anderson.queries.entrySet()) {
            TreeSet<Integer> result = q.getValue();
            answer.append(q.getKey().toString()).append(":");
            if (result == null)
                continue;
            if (result.contains(anderson.magicNum)) {
                for (Integer i: anderson.allocIDSet) {
                    answer.append(" ").append(i);
                }
            }
            else {
                for (Integer i : result) {
                    answer.append(" ").append(i);
                }
            }
            answer.append("\n");
        }
        System.out.println(answer);
        AnswerPrinter.printAnswer(answer.toString());
    }

}
