package core;

import soot.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class StoreType {
    private static int deepestLayer = 3;
    public Map<Value, StoreType> table = new HashMap<>();
    public TreeSet<Integer> pointsToSet;

    StoreType() {
        table = new HashMap<>();
        pointsToSet = new TreeSet<>();
    }

    StoreType(StoreType st) {
        if (st.table.isEmpty()) {
            table = new HashMap<>();
        } else {
            for (Map.Entry<Value, StoreType> e : st.table.entrySet()) {
                table.put(e.getKey(), new StoreType(e.getValue()));
            }
        }
        if (st.pointsToSet.size() == 0) {
            pointsToSet = new TreeSet<>();
        } else{
            pointsToSet = new TreeSet<Integer>(st.pointsToSet);
        }
    }

    StoreType get(Value v) {
        return table.get(v);
    }

    TreeSet<Integer> getPointsToSet(Value v) {
        return table.get(v).pointsToSet;
    }

    void put(Value v, TreeSet<Integer> ts) {
        if (!table.containsKey(v)) {
            table.put(v, new StoreType());
        }
        table.get(v).pointsToSet = new TreeSet<>(ts);
    }

    void putAll(StoreType st) {
        table.putAll(st.table);
    }

    void clear() {
        table.clear();
    }

    Set<Map.Entry<Value, StoreType>> entrySet() {
        return table.entrySet();
    }

    void mergeFrom(StoreType st) {
        for (Map.Entry<Value, StoreType> e : st.table.entrySet()) {
            if (table.containsKey(e.getKey())) {
                table.get(e.getKey()).pointsToSet.addAll(e.getValue().pointsToSet);
            } else {
                table.put(e.getKey(), new StoreType(e.getValue()));
            }
        }
    }

    void copyFrom(StoreType src) {
        for (Map.Entry<Value, StoreType> e : src.table.entrySet()) {
            table.put(e.getKey(), new StoreType(e.getValue()));
        }
    }
}
