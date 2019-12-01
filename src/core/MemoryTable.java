package core;

import soot.Unit;
import soot.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;

class MemoryTable {
    private static Integer nextAutoAllocId = -1; // 0 should never be alloc
    private static Map<Integer, MemoryItem> table = new HashMap<>();
    static Map<Unit, Integer> repeatedNewExpr = new HashMap<>();

    static Integer allocMemory(Integer id, Value v) {
        if (table.containsKey(id)) {
            Logger.getLogger("").warning("Alloc a repeated id: " + id.toString());
            return id;
        }
        if (id == 0) {
            id = (nextAutoAllocId--);
        }
        System.out.println("\033[33mAlloc " + id.toString() + " to " + v.toString() + "\033[m");
        MemoryItem mi = new MemoryItem(id, v.getType());
        table.put(id, mi);
        return id;
    }

    static void initialArrayIndex(Integer baseId) {
        table.get(baseId).set(ArrayHelper.indexStr, new TreeSet<>());
    }

    static void update(TreeSet<Integer> setToUpdate, String fieldName, TreeSet<Integer> newPointsToSet) {
        System.out.println("\033[32mUpdate: \033[m" + setToUpdate.toString() + " field -" + fieldName
                + "\n" + newPointsToSet.toString() + getString());
        for (Integer id : setToUpdate) {
            MemoryItem mi = table.get(id);
            if (mi.get(fieldName) == null) mi.put(fieldName, newPointsToSet);
            else mi.get(fieldName).addAll(newPointsToSet);
        }
        System.out.println("\033[32mAfter Update: \033[m\n" + getString());
    }

    static void updateFieldsToAll(TreeSet<Integer> setToUpdate) {
        for (Integer id : setToUpdate) {
            table.get(id).setFieldToAll();
        }
    }

    static void set(TreeSet<Integer> setToUpdate, String fieldName, TreeSet<Integer> newPointsToSet) {
        System.out.println("\033[Set: \033[m" + setToUpdate.toString() + " field-" + fieldName + "\n"
                + getString());
        if (setToUpdate.size() == 1) {
            Integer id = setToUpdate.first();
            table.get(id).set(fieldName, newPointsToSet);
        } else {
            for (Integer id : setToUpdate) {
                MemoryItem mi = table.get(id);
                if (mi.get(fieldName) == null) mi.put(fieldName, newPointsToSet);
                else mi.get(fieldName).addAll(newPointsToSet);
            }
        }
        System.out.println("\033[32mAfter Update: \033[m\n" + getString());
    }

    static TreeSet<Integer> getPointsToSet(TreeSet<Integer> setToGet, String fieldName) {
        System.out.println("\033[32mGet: \033[m" + setToGet.toString() + " field - " + fieldName + " from\n"
                + getString());
        TreeSet<Integer> ts = new TreeSet<>();
        for (Integer i : setToGet) {
            MemoryItem mi = table.get(i);
            if (mi.get(fieldName) != null)
                ts.addAll(table.get(i).get(fieldName));
        }
        return ts;
    }

    static String getString() {
        StringBuilder s = new StringBuilder("next auto id: " + nextAutoAllocId.toString() + "\n");
        for (Map.Entry<Integer, MemoryItem> e : table.entrySet()) {
            s.append(e.getValue().toString()).append("\n");
        }
        return s.toString();
    }

    static TreeSet<Integer> getGlobalMaxId() {
        TreeSet<Integer> ts = new TreeSet<>();
        for (Map.Entry<Integer, MemoryItem> e : table.entrySet()) {
            ts.add(e.getKey());
        }
        return ts;
    }
}
