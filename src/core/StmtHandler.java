package core;

import soot.Unit;

import java.util.logging.Logger;

/**
 * @author guanzhichao
 */
public abstract class StmtHandler {
    public void handle(Anderson ad, RuntimeEnv in, Unit u, RuntimeEnv out) {
        Logger.getLogger("StmtHandler").severe("Common Handler. Should never be invoked.");
    }
}
