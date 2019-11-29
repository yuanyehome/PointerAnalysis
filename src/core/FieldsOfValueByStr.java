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
 * @author guanzhichao
 */
public class FieldsOfValueByStr extends HashMap<String, TreeSet<Integer>> {
    public static int deepestLayer = 3;
    public Map<String, FieldsOfValueByStr> fields = new HashMap<>();

    @Override
    public TreeSet<Integer> get(Object obj) {
        System.out.println("\033[32mGet: " + obj.toString() + "\033[m");
        System.out.println("\033[32mBefore get: \033[m\n" + this.toString());
        if (obj instanceof String) {
            return super.get(obj);
        } else if (!(obj instanceof Value)) {
            Logger.getLogger("").warning("get value error: " + obj.getClass().toString());
            System.out.println("\033[32mAfter get: \033[m\n" + this.toString());
            return new TreeSet<>();
        } else {
            Value v = (Value) obj;
            if (v instanceof InstanceFieldRef) {
                List<String> bases = getBases(v);
                System.out.println("\033[32mAfter get: \033[m\n" + this.toString());
                return get(v, bases, 0);
            } else {
                System.out.println("\033[32mAfter get: \033[m\n" + this.toString());
                return super.get(v.toString());
            }
        }
    }

    public TreeSet<Integer> put(Value v, TreeSet<Integer> ts) {
        System.out.println("\033[32mBefore put: \033[m\n" + this.toString());
        System.out.println("\033[32mPut: " + v.toString() + "\033[m");
        if (v instanceof InstanceFieldRef) {
            List<String> bases = getBases(v);
            System.out.println("\033[32mAfter put: \033[m\n" + this.toString());
            return put(v, ts, bases, 0);
        } else {
            System.out.println("\033[32mAfter put: \033[m\n" + this.toString());
            return super.put(v.toString(), ts);
        }
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

    public Set<Entry<String, TreeSet<Integer>>> entrySetAll() {
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

    private List<String> getBases(Value v) {
        List<String> bases = new ArrayList<>();
        while (v instanceof InstanceFieldRef) {
            bases.add(0, v.toString());
            v = ((InstanceFieldRef) v).getBase();
        }
        bases.add(0, v.toString());
        return bases;
    }
}
