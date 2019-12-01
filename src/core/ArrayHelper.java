package core;

import soot.Value;
import soot.jimple.ArrayRef;

class ArrayHelper {
    static String indexStr = "<index>";

    static String getNameWithBase(ArrayRef ar) {
        return ar.getBase().toString() + "." + indexStr;
    }

    static String initNameFromBase(Value v) {
        return v.toString() + "." + indexStr;
    }

    static String getIndexStr(ArrayRef ar) {
        return indexStr;
    }
}
