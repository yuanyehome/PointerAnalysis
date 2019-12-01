package core;

import soot.Unit;

/**
 * Common handler for different specific type of handler.
 *
 * @author guanzhichao
 */
abstract class StmtHandler {
    abstract void handle(Anderson ad, PointsToMap in, Unit u, PointsToMap out);
}
