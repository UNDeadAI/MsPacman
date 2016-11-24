package pacman;

import java.awt.*;


public class Agent implements PacAgent, Constants {

    Vector2d cur, prev, tmp;

    private int move, currentDirection;

    int x, y;
    Color color;

    Agent() {
        prev = new Vector2d();
        cur = new Vector2d();
        tmp = new Vector2d();
    }

    void updatePosition(ConnectedSet cs){
        prev.set(x, y);
        x = (cs.x - 3) / 8;
        y = (cs.y - 11) / 8;
        cur.set(x, y);
        setDir(prev, cur);
        this.color = cs.c;
    }

    void setDir(Vector2d prev, Vector2d cur) {
        tmp.set(cur);
        tmp.subtract(prev);
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
        else if(gs.closestPowerPill != null){
            move = gs.closestPowerPill.dir;
            gs.setCurrentObjective(gs.closestPowerPill);

        }
        else if(gs.closestPill != null) {
            move = gs.closestPill.dir;
            gs.setCurrentObjective(gs.closestPill);
        }

        return move;
    }
}
