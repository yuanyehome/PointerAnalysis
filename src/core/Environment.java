package core;

import soot.Value;

import java.util.Map;
import java.util.TreeSet;

public class Environment {
    public static int deepestLayer = 3;
    public Map<Value, TreeSet<Integer>> pointsToSet;

}
