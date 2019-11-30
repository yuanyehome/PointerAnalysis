package core;

import soot.Value;
import soot.jimple.InstanceFieldRef;

import java.util.*;
import java.util.logging.Logger;

/**
 * Support value with fields. The `HashMap` (object itself) stores common points-to set of variables.
 * `Fields` connect a variable with another `FieldsOfValue` storing points-to sets of its fields.
 *
 * For example: {a:{1,2}, a.x:{2,3}, a.y:{3}} will be stored as following:
 *    HashMap: [a: {1, 2}, ...]
 *    Fields:  [a->[x: {2, 3}, y:{3}], ...]
 *
 * @author guanzhichao
 */
public class FieldsOfValueByStr extends HashMap<String, TreeSet<Integer>> {
    public static int deepestLayer = 3;
    public Map<String, FieldsOfValueByStr> fields = new HashMap<>();

    @Override
    public TreeSet<Integer> get(Object obj) {
        System.out.println("\033[32mGet " + obj.toString() + " from:\033[m\n" + this.toString());
        if (obj instanceof String) {
            return super.get(obj);
        } else if (!(obj instanceof Value)) {
            Logger.getLogger("").warning("get value error: " + obj.getClass().toString());
            return new TreeSet<>();
        } else {
            Value v = (Value) obj;
            if (v instanceof InstanceFieldRef) {
                List<String> bases = BasesGetter.getBasesListStr(v);
                return get(v, bases, 0);
            } else {
                return super.get(v.toString());
            }
        }
    }

    public TreeSet<Integer> put(Value v, TreeSet<Integer> ts) {
        System.out.println("\033[32mPut " + v.toString() + " to:\033[m\n" + this.toString());

        TreeSet<Integer> res = new TreeSet<>();
        if (v instanceof InstanceFieldRef) {
            List<String> bases = BasesGetter.getBasesListStr(v);
            res = put(v, ts, bases, 0);
        } else {
            res = super.put(v.toString(), ts);
        }
        System.out.println("\033[After put: \033[m\n" + this.toString());
        return res;
    }

    @Override
    public void clear() {
        super.clear();
        fields.clear();
    }

    @Override
    public void putAll(Map<? extends String, ? extends TreeSet<Integer>> m) {
        super.putAll(m);
        if (m instanceof FieldsOfValueByStr) {
            FieldsOfValueByStr fv = (FieldsOfValueByStr) m;
            if (!fv.fields.isEmpty()) {
                this.fields.putAll(fv.fields);
            }
        }
    }

    Set<Entry<String, TreeSet<Integer>>> entrySetAll() {
        Set<Entry<String, TreeSet<Integer>>> es = super.entrySet();
        if (!fields.isEmpty()) {
            for (Map.Entry<String, FieldsOfValueByStr> e : fields.entrySet()) {
                es.addAll(e.getValue().entrySet());
            }
        }
        return es;
    }

    @Override
    public boolean containsKey(Object obj) {
        return this.get(obj) != null;
    }

    void copyFrom(FieldsOfValueByStr fv) {
        this.clear();
        this.mergeFrom(fv);
    }

    void mergeFrom(FieldsOfValueByStr fv) {
        if (!fv.isEmpty()) {
            for (Map.Entry<String, TreeSet<Integer>> e : fv.entrySet()) {
                if (this.containsKey(e.getKey())) {
                    this.get(e.getKey()).addAll(e.getValue());
                } else {
                    this.put(e.getKey(), new TreeSet<>(e.getValue()));
                }
            }
        }
        if (!fv.fields.isEmpty()) {
            for (Map.Entry<String, FieldsOfValueByStr> e : fv.fields.entrySet()) {
                if (!this.fields.containsKey(e.getKey())) {
                    this.fields.put(e.getKey(), new FieldsOfValueByStr());
                }
                this.fields.get(e.getKey()).mergeFrom(e.getValue());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(super.toString());
        s.append(" \nFields: {\n");
        for (Map.Entry<String, FieldsOfValueByStr> e : fields.entrySet()) {
            s.append(e.getKey()).append(": ").append(e.getValue().toString());
        }
        s.append("}\n");
        return s.toString();
    }

    private TreeSet<Integer> get(Value v, List<String> bases, int depth) {
        if (bases.size() == 1) {
            return super.get(v.toString());
        } else if (depth == deepestLayer) {
            String base = bases.remove(0);
            return super.get(base);
        } else {
            String base = bases.remove(0);
            if (fields.containsKey(base)) {
                return fields.get(base).get(v, bases, depth + 1);
            } else {
                return null;
            }
        }
    }

    private TreeSet<Integer> put(Value v, TreeSet<Integer> ts, List<String> bases, int depth) {
        if (bases.size() == 1) {
            return super.put(v.toString(), ts);
        } else if (depth == deepestLayer) {
            String base = bases.remove(0);
            return super.put(base, ts);
        } else {
            String base = bases.remove(0);
            if (!fields.containsKey(base)) {
                fields.put(base, new FieldsOfValueByStr());
            }
            return fields.get(base).put(v, ts, bases, depth + 1);
        }
    }
}
