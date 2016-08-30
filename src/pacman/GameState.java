package pacman;

import games.math.Vector2d;

import java.util.HashMap;
import java.awt.*;


public class GameState implements Drawable {

    static int strokeWidth = 5;
    static Stroke stroke =  new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

    Agent agent;
    Vector2d closestPill;
    Vector2d closestGhost;
    Vector2d closestPowerPill;
    Vector2d closestEdible;
    Vector2d tmp;

    static int nFeatures = 13;
    double[] vec;

    static HashMap<Integer,Integer> ghostLut = new HashMap <>();

    public GameState() {
        agent = new Agent();
        tmp = new Vector2d();
        vec = new double[nFeatures];
    }

    static {
        ghostLut.put(MsPacInterface.blinky, 0);
        ghostLut.put(MsPacInterface.inky, 1);
        ghostLut.put(MsPacInterface.pinky, 2);
        ghostLut.put(MsPacInterface.sue, 3);
    }

    static boolean isBlinkyEdible = false;
    static boolean isPinkyEdible = false;
    static boolean isInkyEdible = false;
    static boolean isSueEdible = false;


    public void eatSuperPoewrPill(){
        isBlinkyEdible = isPinkyEdible = isSueEdible = isInkyEdible = true;
    }

    public boolean thereAreEdibles(){
        return isBlinkyEdible || isPinkyEdible || isSueEdible || isInkyEdible;
    }

    public void update(ConnectedSet cs, int[] pix) {
        if (cs.isPacMan())
            agent.update(cs, pix);
        else if (cs.ghostLike()) {
            if(!thereAreEdibles())
                closestEdible = null;
            tmp.set(cs.x, cs.y);
            if (closestGhost == null)
                closestGhost = new Vector2d(tmp);
            else if(tmp.dist(agent.cur) < closestGhost.dist(agent.cur))
                closestGhost.set(tmp);
        }
        else if (cs.isPill()) {
            if(closestPill != null)
                if (closestPill.dist(agent.cur) < 8)
                    closestPill = null;
            tmp.set(cs.x, cs.y);
            tmp.set(cs.x, cs.y);
            if (closestPill == null)
                closestPill = new Vector2d(tmp);
            else if (tmp.dist(agent.cur) < closestPill.dist(agent.cur))
                closestPill.set(tmp);
        }
        else if (cs.isPowerPill()) {
            if(closestPowerPill != null)
                if (closestPowerPill.dist(agent.cur) < 8)
                    closestPowerPill = null;
            tmp.set(cs.x, cs.y);
            if (closestPowerPill == null)
                closestPowerPill = new Vector2d(tmp);
            else if (tmp.dist(agent.cur) < closestPowerPill.dist(agent.cur))
                closestPowerPill.set(tmp);
        }
        else if(cs.isEdible()){
            tmp.set(cs.x, cs.y);
            if (closestEdible == null)
                closestEdible = new Vector2d(tmp);
            else if (tmp.dist(agent.cur) < closestEdible.dist(agent.cur))
                closestEdible.set(tmp);
        }
    }

    public void draw(Graphics gg, int w, int h) {
        Graphics2D g = (Graphics2D) gg;

        if (agent != null)
            agent.draw(g, w, h);
        if (closestGhost != null && agent != null) {
            g.setStroke(stroke);
            g.setColor(Color.cyan);
            g.drawLine((int) closestGhost.x, (int) closestGhost.y, (int) agent.cur.x, (int) agent.cur.y);
        }
    }

    public void reset() {
        closestPill = null;
    }
}
