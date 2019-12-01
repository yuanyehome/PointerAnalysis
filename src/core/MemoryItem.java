package core;

import soot.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Method `put` will merge new set and old set. But method `set` will replace it instead.
 *
 * @author guanzhichao
 */
public class MemoryItem {
    private Integer id;
    private Type type;
    private Map<String, TreeSet<Integer>> fieldIndex;

    MemoryItem(Integer id, Type type) {
        this.id = id;
        this.type = type;
        fieldIndex = new HashMap<>();
    }

    public String toString() {
        return id.toString() + "\t<" + type.toString() + ">: " + fieldIndex.toString();
    }

    TreeSet<Integer> get(String fieldStr) {
        return fieldIndex.get(fieldStr);
    }

    void put(String fieldStr, TreeSet<Integer> ts) {
        TreeSet<Integer> _ts = fieldIndex.get(fieldStr);
        if (_ts == null) fieldIndex.put(fieldStr, new TreeSet<>(ts));
        else _ts.addAll(ts);
    }

    void set(String fieldStr, TreeSet<Integer> ts) {
        fieldIndex.put(fieldStr, ts);
    }

    void setFieldToAll() {
        TreeSet<Integer> all = MemoryTable.getGlobalAllId();
        for (Map.Entry<String, TreeSet<Integer>> e : fieldIndex.entrySet()) {
            e.setValue(new TreeSet<>(all));
        }
    }
}
