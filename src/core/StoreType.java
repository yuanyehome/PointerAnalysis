package core;

import soot.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public class StoreType {
    public int deepestLayer = -1;
    public Map<Value, StoreType> table = new HashMap<>();
    TreeSet<Integer> mapList = new TreeSet<>();

    StoreType(int x) {
        deepestLayer = x;
    }
}
