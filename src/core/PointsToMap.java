package core;

import soot.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class PointsToMap extends HashMap<String, TreeSet<Integer>> {

    @Override
    public TreeSet<Integer> get(Object key) {
        if (key instanceof Value) {
            return super.get(key.toString());
        } else {
            return super.get(key);
        }
    }

    void copyFrom(PointsToMap ptm) {
        super.clear();
        super.putAll(ptm);
    }

    void mergeFrom(PointsToMap ptm) {
        for (Map.Entry<String, TreeSet<Integer>> e : ptm.entrySet()) {
            if (super.containsKey(e.getKey())) {
                super.get(e.getKey()).addAll(e.getValue());
            } else {
                super.put(e.getKey(), e.getValue());
            }
        }
    }
}
