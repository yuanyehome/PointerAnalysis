package core;

import soot.Value;
import soot.jimple.InstanceFieldRef;

import java.util.*;
import java.util.logging.Logger;

/**
 * Support value with fields. The `HashMap` (object itself) stores common points-to set of variables.
 * `Fields` connect a variable with another `FieldsOfValue` storing points-to sets of its fields.
 *
 * @author guanzhichao
 */
public class FieldsOfValue extends HashMap<Value, TreeSet<Integer>> {
    public static int deepestLayer = 3;
    public Map<Value, FieldsOfValue> fields = new HashMap<>();

    @Override
    public TreeSet<Integer> get(Object obj) {
        if (!(obj instanceof Value)) {
            Logger.getLogger("").warning("get value error: " + obj.getClass().toString());
            return new TreeSet<>();
        } else {
            Value v = (Value) obj;
            if (v instanceof InstanceFieldRef) {
                List<Value> bases = getBases(v);
                return get(v, bases, 0);
            } else {
                return super.get(v);
            }
        }
    }

    @Override
    public TreeSet<Integer> put(Value v, TreeSet<Integer> ts) {
        if (v instanceof InstanceFieldRef) {
            List<Value> bases = getBases(v);
            return put(v, ts, bases, 0);
        } else {
            return super.put(v, ts);
        }
    }

    @Override
    public void clear() {
        super.clear();
        fields.clear();
    }

    @Override
    public void putAll(Map<? extends Value, ? extends TreeSet<Integer>> m) {
        super.putAll(m);
        if (m instanceof FieldsOfValue) {
            FieldsOfValue fv = (FieldsOfValue) m;
            if (!fv.fields.isEmpty()) {
                this.fields.putAll(fv.fields);
            }
        }
    }

    @Override
    public Set<Entry<Value, TreeSet<Integer>>> entrySet() {
        Set<Entry<Value, TreeSet<Integer>>> es = super.entrySet();
        if (!fields.isEmpty()) {
            for (Map.Entry<Value, FieldsOfValue> e : fields.entrySet()) {
                es.addAll(e.getValue().entrySet());
            }
        }
        return es;
    }

    @Override
    public boolean containsKey(Object obj) {
        return this.get(obj) != null;
    }

    void copyFrom(FieldsOfValue fv) {
        this.clear();
        this.mergeFrom(fv);
    }

    void mergeFrom(FieldsOfValue fv) {
        if (!fv.isEmpty()) {
            for (Map.Entry<Value, TreeSet<Integer>> e : fv.entrySet()) {
                if (this.containsKey(e.getKey())) {
                    this.get(e.getKey()).addAll(e.getValue());
                } else {
                    this.put(e.getKey(), new TreeSet<>(e.getValue()));
                }
            }
        }
        if (!fv.fields.isEmpty()) {
            for (Map.Entry<Value, FieldsOfValue> e : fv.fields.entrySet()) {
                this.fields.get(e.getKey()).mergeFrom(e.getValue());
            }
        }
    }

    private TreeSet<Integer> get(Value v, List<Value> bases, int depth) {
        if (bases.size() == 1) {
            return super.get(v);
        } else if (depth == deepestLayer) {
            Value base = bases.remove(0);
            return super.get(base);
        } else {
            Value base = bases.remove(0);
            if (fields.containsKey(base)) {
                return fields.get(base).get(v, bases, depth + 1);
            } else {
                return null;
            }
        }
    }

    private TreeSet<Integer> put(Value v, TreeSet<Integer> ts, List<Value> bases, int depth) {
        if (bases.size() == 1) {
            return super.put(v, ts);
        } else if (depth == deepestLayer) {
            Value base = bases.remove(0);
            return super.put(base, ts);
        } else {
            Value base = bases.remove(0);
            if (!fields.containsKey(base)) {
                fields.put(base, new FieldsOfValue());
            }
            return fields.get(base).put(v, ts, bases, depth + 1);
        }
    }

    private List<Value> getBases(Value v) {
        List<Value> bases = new ArrayList<>();
        int cnt = 0;
        while (v instanceof InstanceFieldRef) {
            bases.add(0, v);
            v = ((InstanceFieldRef) v).getBase();
        }
        bases.add(0, v);
        return bases;
    }
}
