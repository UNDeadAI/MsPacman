package pacman;

import games.math.Vector2d;

import java.util.HashMap;
import java.awt.*;


public class GameState implements Drawable {

    static private int strokeWidth = 5;
    static private Stroke stroke =  new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

    Agent agent;
    Vector2d closestPill, closestGhost, closestPowerPill, closestEdible = new Vector2d(), blinkyPos = new Vector2d(),
            inkyPos = new Vector2d(), suePos = new Vector2d(), pinkyPos = new Vector2d(),
            currentObjective = new Vector2d(), tmp, profeObjetivo = new Vector2d(196, 13),
            profeObjetivo2 = new Vector2d(220, 140), profeObjetivo3 = new Vector2d(27, 189), border = new Vector2d(12, 13);
    //240, 140

    private double blinkyDistance, inkyDistance, sueDistance, pinkyDistance;

    static private int nFeatures = 13;
    private double[] vec;
    private Vector2d[] edibles = new Vector2d[4];
    private int currentEdible;
    static private HashMap<Integer,Integer> ghostLut = new HashMap <>();

    public GameState() {
        agent = new Agent();
        tmp = new Vector2d();
        vec = new double[nFeatures];
        currentObjective.set(profeObjetivo);
    }

    static {
        ghostLut.put(MsPacInterface.blinky, 0);
        ghostLut.put(MsPacInterface.inky, 1);
        ghostLut.put(MsPacInterface.pinky, 2);
        ghostLut.put(MsPacInterface.sue, 3);
    }

    private boolean isBlinkyEdible, isPinkyEdible, isInkyEdible, isSueEdible;

    public void eatSuperPowerPill(){
        isBlinkyEdible = isPinkyEdible = isSueEdible = isInkyEdible = true;
    }

    public boolean thereAreEdibles(){
        return isBlinkyEdible || isPinkyEdible || isSueEdible || isInkyEdible;
    }

    public boolean allAreEdibles(){ return isBlinkyEdible && isPinkyEdible && isSueEdible && isInkyEdible; }

    private void updateClosestGhost(){
        if(blinkyDistance <= pinkyDistance && blinkyDistance <= inkyDistance && blinkyDistance <= sueDistance)
            closestGhost = blinkyPos;
        else if(blinkyDistance > pinkyDistance && pinkyDistance < inkyDistance && pinkyDistance < sueDistance)
            closestGhost = pinkyPos;
        else if(inkyDistance  < pinkyDistance && blinkyDistance > inkyDistance && inkyDistance < sueDistance)
            closestGhost = inkyPos;
        else if(sueDistance < pinkyDistance && sueDistance < inkyDistance && blinkyDistance > sueDistance)
            closestGhost = suePos;
    }

    private void updateClosestGhost(Vector2d ghost, int x , int y){
        ghost.set(x, y);
        if (closestGhost == null)
            closestGhost = new Vector2d(ghost);
    }

    public void update(ConnectedSet cs, int[] pix) {
        if (cs.isPacMan())
            agent.update(cs, pix);
        else if (cs.isBlinky()) {
            isBlinkyEdible = false;
            updateClosestGhost(blinkyPos, cs.x, cs.y);
            blinkyDistance = agent.cur.dist(blinkyPos);
            updateClosestGhost();
        }
        else if (cs.isSue()) {
            isSueEdible = false;
            updateClosestGhost(suePos, cs.x, cs.y);
            sueDistance = agent.cur.dist(suePos);
            updateClosestGhost();
        }
        else if (cs.isInky()) {
            isInkyEdible = false;
            updateClosestGhost(inkyPos, cs.x, cs.y);
            inkyDistance = agent.cur.dist(inkyPos);
            updateClosestGhost();
        }
        else if (cs.isPinky()) {
            isPinkyEdible = false;
            updateClosestGhost(pinkyPos, cs.x, cs.y);
            pinkyDistance = agent.cur.dist(pinkyPos);
            updateClosestGhost();
        }
        else if (cs.isPill()) {
            tmp.set(cs.x, cs.y);
            if (closestPill == null)
                closestPill = new Vector2d(tmp);
            else if (tmp.dist(agent.cur) < closestPill.dist(agent.cur))
                closestPill.set(tmp);
            if(closestPill != null)
                if (closestPill.dist(agent.cur) < 8)
                    closestPill = null;
        }
        else if (cs.isPowerPill()) {
            tmp.set(cs.x, cs.y);
            if (closestPowerPill == null)
                closestPowerPill = new Vector2d(tmp);
            else if (tmp.dist(agent.cur) < closestPowerPill.dist(agent.cur))
                closestPowerPill.set(tmp);
            if(closestPowerPill != null)
                if (closestPowerPill.dist(agent.cur) < 8) {
                    eatSuperPowerPill();
                    closestPowerPill = null;
                    eatSuperPowerPill();
                }
        }
        else if(cs.isEdible()){
            tmp.set(cs.x, cs.y);
            nextEdible();
            updateClosestEdible();
        }
    }

    private void nextEdible(){
        if(!isBlinkyEdible)
            edibles[0] = null;
        if(!isInkyEdible)
            edibles[1] = null;
        if(!isPinkyEdible)
            edibles[2] = null;
        if(!isSueEdible)
            edibles[3] = null;

        for(int i = 0; i < 4; i++){
            if(currentEdible == 0){
                if(isBlinkyEdible) {
                    edibles[0] = tmp;
                    currentEdible++;
                    break;
                }
                currentEdible++;
            }
            else if(currentEdible == 1){
                if(isInkyEdible) {
                    edibles[1] = tmp;
                    currentEdible++;
                    break;
                }
                currentEdible++;
            }
            else if(currentEdible == 2){
                if(isPinkyEdible) {
                    edibles[2] = tmp;
                    currentEdible++;
                    break;
                }
                currentEdible++;
            }
            if(currentEdible == 3){
                if(isSueEdible) {
                    edibles[3] = tmp;
                    currentEdible = 0;
                    break;
                }
                currentEdible = 0;
            }
        }
        //System.out.println(edibles[0] + " " + edibles[1] + " " + edibles[2] + " " + edibles[3]);
    }

    private void updateClosestEdible(){
        Vector2d aux = null;
        for(int i = 0; i < 4; i++){
            if(edibles[i] != null)
                if(aux == null)
                    aux = edibles[i];
                else
                    if(edibles[i].dist(agent.cur) < aux.dist(agent.cur))
                        aux.set(edibles[i]);
        }
        if(aux != null)
            closestEdible.set(aux);
    }

    public void setCurrentObjective(Vector2d v){
        if(v != null)
            currentObjective.set(v);
    }

    public void draw(Graphics gg, int w, int h) {
        Graphics2D g = (Graphics2D) gg;
        if (agent != null)
            agent.draw(g, w, h);
        if (currentObjective != null && agent != null) {
            g.setStroke(stroke);
            g.setColor(Color.cyan);
            g.drawLine((int) currentObjective.x, (int) currentObjective.y, (int) agent.cur.x, (int) agent.cur.y);
        }
        //System.out.println(currentObjective);
    }

    public void reset() {
        closestPill = null;
    }
}
