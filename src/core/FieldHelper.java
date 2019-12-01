package core;

import soot.Value;
import soot.jimple.InstanceFieldRef;

import java.util.ArrayList;
import java.util.List;

class FieldHelper {
    static List<String> getBasesListStr(Value v) {
        List<String> bases = new ArrayList<>();
        while (v instanceof InstanceFieldRef) {
            bases.add(0, v.toString());
            v = ((InstanceFieldRef) v).getBase();
        }
        bases.add(0, v.toString());
        return bases;
    }

    static List<Value> getBasesList(Value v) {
        List<Value> bases = new ArrayList<>();
        while (v instanceof InstanceFieldRef) {
            bases.add(0, v);
            v = ((InstanceFieldRef) v).getBase();
        }
        bases.add(0, v);
        return bases;
    }

    static String getFieldName(InstanceFieldRef fr) {
        String s = fr.toString();
        int r2 = s.lastIndexOf(">");
        int r1 = s.lastIndexOf(" ");
        return s.substring(r1, r2);
    }

    static String getBaseStr(InstanceFieldRef fr) {
        return fr.getBase().toString();
    }
}
