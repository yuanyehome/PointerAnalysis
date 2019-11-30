package core;

import soot.Unit;

import java.util.logging.Logger;

/**
 * Common handler for different specific type of handler.
 *
 * @author guanzhichao
 */
abstract class StmtHandler {
    void handle(Anderson ad, PointsToMap in, Unit u, PointsToMap out) {
        Logger.getLogger("StmtHandler").severe("Common Handler. Should never be invoked.");
    }
}
