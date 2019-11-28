package core;

import soot.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public class StoreType {
    private static int deepestLayer = 3;
    public Map<Value, StoreType> table;
    public TreeSet<Integer> mapList;

    StoreType() {
        table = new HashMap<>();
        mapList = new TreeSet<>();
    }

    StoreType(StoreType st) {
        for (Map.Entry<Value, StoreType> e : st.table.entrySet()) {
            table.put(e.getKey(), new StoreType(e.getValue()));
        }
        mapList = new TreeSet<>(st.mapList);
    }

    TreeSet<Integer> get(Value v) {
        return table.get(v).mapList;
    }

    void put(Value v, TreeSet<Integer> ts) {
        table.get(v).mapList = new TreeSet<>(ts);
    }

    void putAll(StoreType st) {
        table.putAll(st.table);
    }

    void clear() {
        table.clear();
    }

    void mergeFrom(StoreType st) {
        for (Map.Entry<Value, StoreType> e : st.table.entrySet()) {
            if (table.containsKey(e.getKey())) {
                table.get(e.getKey()).putAll(e.getValue());
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
