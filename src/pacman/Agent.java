package pacman;

import java.awt.*;


public class Agent implements PacAgent, Constants {

    Vector2d cur, prev, tmp;

    int move, currentDirection;

    int x, y;
    Color color;

    public Agent() {
        prev = new Vector2d();
        cur = new Vector2d();
        tmp = new Vector2d();
    }

    public void updatePosition(ConnectedSet cs){
        prev.set(x, y);
        x = (cs.x - 3) / 8;
        y = (cs.y - 11) / 8;
        cur.set(x, y);
        setDir(prev, cur);
        this.color = cs.c;
    }

    public void setDir(Vector2d prev, Vector2d cur) {
        tmp.set(cur);
        tmp.subtract(prev);
        if (tmp.equals(NEUTRAL)) currentDirection = NEUTRAL;
        if (tmp.scalarProduct(vUp) > 0) currentDirection = UP;
        if (tmp.scalarProduct(vRight) > 0) currentDirection = RIGHT;
        if (tmp.scalarProduct(vDown) > 0) currentDirection = DOWN;
        if (tmp.scalarProduct(vLeft) > 0) currentDirection = LEFT;
    }

    private int minGhostDistance = 4;
    private int DR[] = {-1, 0, 1, 0};
    private int DC[] = {0, 1, 0, -1};

    public int move(GameState gs){
        move = 0;
        if(gs.closestGhost != null && gs.closestGhost.w <= minGhostDistance){
            for(int i = 0; i < 4; i++) {
                if (i + 1 != gs.closestGhost.dir) {
                    int tmp1 = DR[i] + x;
                    int tmp2 = DC[i] + y;
                    if (tmp1 < 29 && tmp1 >= 0) {
                        if (tmp2 >= 28)
                            tmp2 = 0;
                        if (tmp2 < 0)
                            tmp2 = 28 - 1;
                        if (GameState.path[tmp1][tmp2] == 1) {
                            move = i+1;
                            gs.setCurrentObjective(gs.closestGhost);
                            return move;
                        }
                    }
                }
            }
        }
        else if (gs.closestEdible != null){
            move = gs.closestEdible.dir;
            gs.setCurrentObjective(gs.closestEdible);
        }
        else if(gs.closestPill != null) {
            move = gs.closestPill.dir;
            gs.setCurrentObjective(gs.closestPill);
        }
        return move;
    }
//static int pill = MsPacInterface.pill & 0xFFFFFF;
    //static int pacMan = MsPacInterface.pacMan & 0xFFFFFF;
    //static int lips = MsPacInterface.blinky & 0xFFFFFF;

    // d is the distance in each direction to the nearest wall
    //static int[] dirs = {-width, 1, width, -1};
    //    public void update(ConnectedSet cs, int[] pix) {
//        cs.validate();
//        for (int i = 0; i < dirs.length; i++)
//            d[i] = search(x + y * width, pix, dirs[i]);
//        this.color = cs.c;
//    }
//    public double closestPowerPillDistance(Vector2d pos, GameState gs) {
//        if (gs.closestPowerPill != null)
//            return pos.dist(gs.closestPowerPill);
//        else
//            return 0;
//    }
//
//    public double closestGhostDistance(Vector2d pos, GameState gs) {
//        if(gs.allAreEdibles())
//            gs.closestGhost = null;
//        if (gs.closestGhost != null)
//            return pos.dist(gs.closestGhost);
//        else
//            return 0;
//    }
//
//    public double closestPillDistance(Vector2d pos, GameState gs) {
//        if (gs.closestPill != null)
//            return pos.dist(gs.closestPill);
//        else
//            return 0;
//    }
//
//    public double closestEdibleDistance(Vector2d pos, GameState gs) {
//        if(!gs.thereAreEdibles())
//            return 0;
//        if (gs.closestEdible != null)
//            return pos.dist(gs.closestEdible);
//        else
//            return 0;
//    }
//    public int move(GameState gs) {
//        move = -1;
//        double closestPillDistance = closestPillDistance(cur, gs),
//                closestPowerPillDistance = closestPowerPillDistance(cur, gs),
//                closestGhostDistance = closestGhostDistance(cur, gs),
//                closestEdibleDistance = closestEdibleDistance(cur, gs), aux;
//
//        int moveToPill = -1, moveToPowerPill = -1, moveAway = -1, moveToEdible = -1;
//
//        for (int i = 0; i < dirs.length; i++) {
//            if (d[i] > 12) {
//                tmp.set(cur);
//                tmp.add(vDirs[i]);
//
//                aux = closestPowerPillDistance(tmp, gs);
//                if(aux < closestPowerPillDistance) {
//                    closestPowerPillDistance = aux;
//                    moveToPowerPill = i;
//                }
//
//                aux = closestPillDistance(tmp, gs);
//                if(aux < closestPillDistance) {
//                    closestPillDistance = aux;
//                    moveToPill = i;
//                }
//
//                aux = closestGhostDistance(tmp, gs);
//                if(aux >= closestGhostDistance) {
//                    closestGhostDistance = aux;
//                    moveAway = i;
//                }
//
//                aux = closestEdibleDistance(tmp, gs);
//                if(aux < closestEdibleDistance) {
//                    closestEdibleDistance = aux;
//                    moveToEdible = i;
//                }
//            }
//        }
//        if(moveToEdible != -1) {
//            move = moveToEdible;
//            gs.setCurrentObjective(gs.closestEdible);
//        }
//        else if(moveToPowerPill != -1 && closestGhostDistance < 24) {
//            move = moveToPowerPill;
//            gs.setCurrentObjective(gs.closestPowerPill);
//        }
//        else if(moveAway != -1 && closestGhostDistance < 44) {
//            move = moveAway;
//            gs.setCurrentObjective(gs.closestGhost);
//        }
//        else {
//            move = moveToPill;
//            gs.setCurrentObjective(gs.closestPill);
//        }
//
//        if(moveToEdible != -1) {
//            move = moveToEdible;
//            gs.setCurrentObjective(gs.closestEdible);
//        }
//        else {
//            move = moveToPowerPill;
//            gs.setCurrentObjective(gs.closestPowerPill);
//        }
//
//        move += 1;
//
//        return move;
//    }

//    private int search(int p, int[] pix, int delta) {
//        int len = 0;
//        int pp = pix[p] & 0xFFFFFF;
//        try {
//            while (pp == 0 || pp == pacMan || pp == pill || pp == lips) {
//                len++;
//                if (len > width) return width;
//                p += delta;
//                pp = pix[p] & 0xFFFFFF;
//            }
//        } catch (Exception e) {}
//        return len;
//    }
}
