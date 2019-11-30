package core;

import soot.jimple.InstanceFieldRef;

class FieldHelper {
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
