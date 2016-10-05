package pacman;

import java.util.ArrayDeque;
import java.awt.*;


public class GameState implements Drawable {

    Agent agent;
    static private int strokeWidth = 5, xPills = 28, yPills = 29;
    static private Stroke stroke =  new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

    Vector2d closestPill, closestGhost, closestPowerPill, closestEdible = new Vector2d(), blinkyPos = new Vector2d(),
            inkyPos = new Vector2d(), suePos = new Vector2d(), pinkyPos = new Vector2d(),
            currentObjective = new Vector2d(), tmp, currentPowerPillPos = new Vector2d(), currentPillPos = new Vector2d(),
            currentEdiblePos = new Vector2d();

    private Vector2d[] edibles = new Vector2d[4];
    private int currentEdible;
    private boolean isBlinkyEdible, isPinkyEdible, isInkyEdible, isSueEdible;
    static int width = MsPacInterface.width;
    static int height = MsPacInterface.height;
    static int b = -16777216;

    public static int matrix[][] = new int[yPills][xPills]; //positions of the pills, in pixels is i = i*8+3, j = j*8+11
    private static int DR[] = {0, 1, 0, -1};
    private static int DC[] = {1, 0, -1, 0};

    public GameState() {
        agent = new Agent();
        tmp = new Vector2d();
    }

    public void search(int tXPos, int tYPos){
        for(int i = 0; i < yPills; i++)
            for (int j = 0; j < xPills; j++)
                matrix[i][j] = 0;
        matrix[tYPos] [tXPos] = 1;
        ArrayDeque<Node> queue = new ArrayDeque<>();
        Node u, child;
        Vector2d vector;
        queue.add(new Node(tYPos, tXPos, 0));
        int tmp1, tmp2;
        while(!queue.isEmpty()){
            u = queue.poll();
            for(int i = 0; i < 4; i++){
                tmp1 = DR[i] + u.x;
                tmp2 = DC[i] + u.y;
                if(tmp1 < yPills && tmp1 >= 0){
                    if(tmp2 >= xPills)
                        tmp2 = 0;
                    if(tmp2 < 0)
                        tmp2 = xPills - 1;
                    if(matrix[tmp1][tmp2] == 0){
                        matrix[tmp1][tmp2] = 1;
                        if(validatePos(tmp1, tmp2, MsPacInterface.pixels) && validatePos2(tmp1, tmp2, MsPacInterface.pixels)) {
                            child = new Node(tmp1, tmp2, u.weight + 1);
                            if (u.dir == -1)
                                child.dir = i+1;
                            else
                                child.dir = u.dir;
                            queue.add(child);
                            vector = new Vector2d(tmp2, tmp1, u.weight + 1, child.dir);
                            if (inkyPos.equals(vector)) {
                                inkyPos.w = vector.w;
                                updateClosestGhost();
                            } else if (suePos.equals(vector)) {
                                suePos.w = vector.w;
                                updateClosestGhost();
                            } else if (blinkyPos.equals(vector)) {
                                blinkyPos.w = vector.w;
                                updateClosestGhost();
                            } else if (pinkyPos.equals(vector)) {
                                pinkyPos.w = vector.w;
                                updateClosestGhost();
                            } else if (currentPowerPillPos.equals(vector)) {
                                updateClosestPowerPill(vector);
                            } else if (currentPillPos.equals(vector)) {
                                updateClosestPill(vector);
                            }
                        }
                    }
                }
            }
        }
//        for(int i = 0; i < yPills; i++) {
//            for (int j = 0; j < xPills; j++)
//                System.out.print(matrix[i][j] + " ");
//            System.out.println();
//        }
//        System.out.println();
    }

    private static boolean validatePos2(int x, int y, int[] pix){
        x = x*8+11;
        y = y*8+3;
        int a = x*width + y;
        if(pix[a] != b && (pix[a] == MsPacInterface.blinky || (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a]))))
            return false;
        x = x+8;
        if(x < height) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x - 14;
        if(x >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x + 6;
        y = y + 8;
        if(y < width) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        y = y - 14;
        if(y >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        return true;
    }

    private static boolean validatePos(int x, int y, int[] pix){
        x = x*8+12;
        y = y*8+4;
        int a = x*width + y;
        if(pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
            return false;
        x = x+7;
        if(x < height) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x - 14;
        if(x >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x + 7;
        y = y + 7;
        if(y < width) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        y = y - 14;
        if(y >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        return true;
    }

    public void eatSuperPowerPill(){
        isBlinkyEdible = isPinkyEdible = isSueEdible = isInkyEdible = true;
    }

    public boolean thereAreEdibles(){
        return isBlinkyEdible || isPinkyEdible || isSueEdible || isInkyEdible;
    }

    public boolean allAreEdibles(){ return isBlinkyEdible && isPinkyEdible && isSueEdible && isInkyEdible; }

    public void updateClosestGhost(){
        if(blinkyPos.w <= pinkyPos.w && blinkyPos.w <= inkyPos.w && blinkyPos.w <= suePos.w)
            closestGhost = blinkyPos;
        else if(blinkyPos.w > pinkyPos.w && pinkyPos.w < inkyPos.w && pinkyPos.w < suePos.w)
            closestGhost = pinkyPos;
        else if(inkyPos.w  < pinkyPos.w && blinkyPos.w > inkyPos.w && inkyPos.w < suePos.w)
            closestGhost = inkyPos;
        else if(suePos.w < pinkyPos.w && suePos.w < inkyPos.w && blinkyPos.w > suePos.w)
            closestGhost = suePos;
    }

    private void updateGhost(Vector2d ghost, int x , int y){
        x = (x-3) / 8;
        y = (y-11) / 8;
        ghost.set(x, y);
    }

    private void updateCurrentPowerPill(int x , int y){
        x = (x-3) / 8;
        y = (y-11) / 8;
        currentPowerPillPos.set(x, y);
    }

    private void updateClosestPowerPill(Vector2d v){
        if(closestPowerPill == null)
            closestPowerPill = new Vector2d(v);
        else if(v.w < closestPowerPill.w)
                closestPowerPill.set(v);
    }

    private void updateCurrentPill(int x , int y){
        x = (x-3) / 8;
        y = (y-11) / 8;
        currentPillPos.set(x, y);
    }

    private void updateClosestPill(Vector2d v){
        if(closestPill == null)
            closestPill = new Vector2d(v);
        else if(v.w < closestPill.w)
            closestPill.set(v);
    }

    
    public void update(ConnectedSet cs) {
        if (cs.isPacMan()) {
            agent.updatePosition(cs);
            search(agent.x, agent.y);
            //agent.update(cs, pix);
        }
        else if (cs.isBlinky()) {
            isBlinkyEdible = false;
            updateGhost(blinkyPos, cs.x, cs.y);
            //blinkyPos.w = agent.cur.dist(blinkyPos);
        }
        else if (cs.isSue()) {
            isSueEdible = false;
            updateGhost(suePos, cs.x, cs.y);
            //suePos.w = agent.cur.dist(suePos);
        }
        else if (cs.isInky()) {
            isInkyEdible = false;
            updateGhost(inkyPos, cs.x, cs.y);
            //inkyPos.w = agent.cur.dist(inkyPos);
        }
        else if (cs.isPinky()) {
            isPinkyEdible = false;
            updateGhost(pinkyPos, cs.x, cs.y);
            //pinkyPos.w = agent.cur.dist(pinkyPos);
        }
        else if (cs.isPill()) {
            updateCurrentPill(cs.x, cs.y);
            //search(agent.x, agent.y);
        }
        else if (cs.isPowerPill()) {
            updateCurrentPowerPill(cs.x, cs.y);
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
    }

    public void updateClosestEdible(){
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
        if(v != null) {
            currentObjective.set(v.x, v.y);
        }
    }

    public void draw(Graphics gg, int w, int h) {
        Graphics2D g = (Graphics2D) gg;
        if (agent != null)
            agent.draw(g, w, h);
        if (currentObjective != null && agent != null) {
            g.setStroke(stroke);
            g.setColor(Color.cyan);
            g.drawLine((int) currentObjective.x*8+3, (int) currentObjective.y*8+11, (int) agent.cur.x*8+3, (int) agent.cur.y*8+11);
        }
    }

    public void reset() {
        closestPill = null;
    }
}
