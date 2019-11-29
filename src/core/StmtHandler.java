package core;

import soot.Local;
import soot.Unit;

import java.awt.*;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * @author guanzhichao
 */
public abstract class StmtHandler {
    public void handle(Anderson ad, RuntimeEnv in, Unit u, RuntimeEnv out) {
        Logger.getLogger("StmtHandler").severe("Common Handler. Should never be invoked.");
    }
}
