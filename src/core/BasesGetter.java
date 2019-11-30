package core;

import soot.Value;
import soot.jimple.InstanceFieldRef;

import java.util.ArrayList;
import java.util.List;

public class BasesGetter {
    public static List<String> getBasesListStr(Value v) {
        List<String> bases = new ArrayList<>();
        while (v instanceof InstanceFieldRef) {
            bases.add(0, v.toString());
            v = ((InstanceFieldRef) v).getBase();
        }
        bases.add(0, v.toString());
        return bases;
    }

    public static List<Value> getBasesList(Value v) {
        List<Value> bases = new ArrayList<>();
        while (v instanceof InstanceFieldRef) {
            bases.add(0, v);
            v = ((InstanceFieldRef) v).getBase();
        }
        bases.add(0, v);
        return bases;
    }

    public static String getBaseRootStr(Value v) {
        return getBasesListStr(v).get(0);
    }

    public static Value getBaseRoot(Value v) {
        return getBasesList(v).get(0);
    }
}
