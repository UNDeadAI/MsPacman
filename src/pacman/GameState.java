package pacman;

import games.math.Vector2d;

import java.util.Collections;
import java.util.HashMap;
import java.awt.*;


public class GameState implements Drawable {

    static int strokeWidth = 5;
    static Stroke stroke =  new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

    Agent agent;
    Vector2d closestPill, closestGhost, closestPowerPill, closestEdible, blinkyPos = new Vector2d(),
            inkyPos = new Vector2d(), suePos = new Vector2d(), pinkyPos = new Vector2d(), tmp;

    Double blinkyDistance = 0.0, inkyDistance = 0.0, sueDistance = 0.0, pinkyDistance = 0.0;

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

    public void updateClosestGhost(){
        if(blinkyDistance <= pinkyDistance && blinkyDistance <= inkyDistance && blinkyDistance <= sueDistance)
            closestGhost = blinkyPos;
        if(blinkyDistance > pinkyDistance && pinkyDistance < inkyDistance && pinkyDistance < sueDistance)
            closestGhost = pinkyPos;
        if(inkyDistance  < pinkyDistance && blinkyDistance > inkyDistance && inkyDistance < sueDistance)
            closestGhost = inkyPos;
        if(sueDistance < pinkyDistance && sueDistance < inkyDistance && blinkyDistance > sueDistance)
            closestGhost = suePos;
    }

    public void update(ConnectedSet cs, int[] pix) {
        if (cs.isPacMan())
            agent.update(cs, pix);
        else if (cs.isBlinky()) {
            if(!thereAreEdibles())
                closestEdible = null;
            blinkyPos.set(cs.x, cs.y);
            if (closestGhost == null)
                closestGhost = new Vector2d(blinkyPos);
            blinkyDistance = agent.cur.dist(blinkyPos);
            updateClosestGhost();
        }
        else if (cs.isSue()) {
            if(!thereAreEdibles())
                closestEdible = null;
            suePos.set(cs.x, cs.y);
            if (closestGhost == null)
                closestGhost = new Vector2d(suePos);
            sueDistance = agent.cur.dist(suePos);
            updateClosestGhost();
        }
        else if (cs.isInky()) {
            if(!thereAreEdibles())
                closestEdible = null;
            inkyPos.set(cs.x, cs.y);
            if (closestGhost == null)
                closestGhost = new Vector2d(inkyPos);
            inkyDistance = agent.cur.dist(inkyPos);
            updateClosestGhost();
        }
        else if (cs.isPinky()) {
            if(!thereAreEdibles())
                closestEdible = null;
            pinkyPos.set(cs.x, cs.y);
            if (closestGhost == null)
                closestGhost = new Vector2d(pinkyPos);
            pinkyDistance = agent.cur.dist(pinkyPos);
            updateClosestGhost();
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
