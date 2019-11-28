package core;

import soot.Local;
import soot.Value;
import soot.jimple.InstanceFieldRef;

import java.util.*;


public class StoreType {
    public static int deepestLayer = 3;
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
        table.get(v).pointsToSet = new TreeSet<Integer>(ts);
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

    void mergeFrom(Value key, TreeSet<Integer> ts) {
        if (table.containsKey(key)) {
            table.get(key).pointsToSet.addAll(ts);
        } else {
            StoreType tmp = new StoreType();
            tmp.pointsToSet = new TreeSet<>(ts);
            table.put(key, tmp);
        }
    }

    TreeSet<Integer> queryField(InstanceFieldRef query) {
        List<Value> values = new ArrayList<Value>();
        int cnt = 0;
        while (!(query.getBase() instanceof Local)) {
            ++cnt;
            values.add(0, query);
            query = (InstanceFieldRef) query.getBase();
            // a.b.c.d.e => cnt = 4
        }
        values.add(0, query);
        values.add(0, query.getBase());
        int present = 0;
        int layer = Math.min(cnt + 1, StoreType.deepestLayer);
        StoreType presentElement = this;
        while (layer != 0) {
            presentElement = presentElement.get(values.get(present));
            present++;
            layer--;
        }
        return presentElement.getPointsToSet(values.get(present));
    }

    void copyFrom(StoreType src) {
        for (Map.Entry<Value, StoreType> e : src.table.entrySet()) {
            table.put(e.getKey(), new StoreType(e.getValue()));
        }
    }
}
