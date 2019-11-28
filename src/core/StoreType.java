package core;

import soot.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public class StoreType {
    private static int deepestLayer = 3;
    public Map<Value, StoreType> table = new HashMap<>();
    public TreeSet<Integer> mapList = new TreeSet<>();

    public TreeSet<Integer> get(Value v) {
        return table.get(v).mapList;
    }

    public void put(Value v, StoreType st) {
        table.put(v, st);
    }

    public void clear() {

    }

}
