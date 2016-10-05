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
            currentEdiblePos = new Vector2d(), defaultVector = new Vector2d();

    private Vector2d[] edibles = new Vector2d[4];
    private int currentEdible;
    private boolean isBlinkyEdible, isPinkyEdible, isInkyEdible, isSueEdible;
    static int width = MsPacInterface.width;
    static int height = MsPacInterface.height;
    static int b = -16777216;

    public static int matrix[][] = new int[yPills][xPills]; //positions of the pills, in pixels is i = i*8+3, j = j*8+11
    public static int path[][] = new int[yPills][xPills];

    public GameState() {
        agent = new Agent();
        tmp = new Vector2d();
    }

    private int pathlimit = 324;
    private int count;
    public void setPath(int [] pix){

        if(count > pathlimit) {
            for (int i = 0; i < yPills; i++)
                for (int j = 0; j < xPills; j++)
                    path[i][j] = 0;
        }
        count = 0;
        for(int i = 0; i < yPills; i++) {
            for (int j = 0; j < xPills; j++) {
                if (path[i][j] == 1){
                    count++;
                }
                else if(validatePos(i, j, pix) && validatePos2(i, j, pix)) {
                    path[i][j] = 1;
                    count++;
                }
            }
        }
//        for(int i = 0; i < yPills; i++) {
//            for (int j = 0; j < xPills; j++)
//                System.out.print(path[i][j] + " ");
//            System.out.println();
//        }
//        System.out.println();
    }

    private static int DR[] = {-1, 0, 1, 0};
    private static int DC[] = {0, 1, 0, -1};

    public void search(int tXPos, int tYPos){
        for(int i = 0; i < yPills; i++)
            for (int j = 0; j < xPills; j++)
                matrix[i][j] = 0;
        matrix[tYPos] [tXPos] = 1;
        ArrayDeque<Node> queue = new ArrayDeque<>();
        Node u, child;
        Vector2d vector;
        queue.add(new Node(tYPos, tXPos));
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
                        if(path[tmp1][tmp2] == 1){
                            child = new Node(tmp1, tmp2, u.weight + 1);
                            if (u.dir == 0)
                                child.dir = i+1;
                            else
                                child.dir = u.dir;
                            queue.add(child);
                            vector = new Vector2d(tmp2, tmp1, child.weight, child.dir);
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
                            } else if (MsPacInterface.searchPixels[(tmp1*8+11)*width + (tmp2*8+3)] == MsPacInterface.pill) {
                                updateClosestPill(vector);
                            }
                        }
                    }
                }
            }
        }
    }

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
        else if(v.w <= closestPowerPill.w)
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
        else if(v.w <= closestPill.w)
            closestPill.set(v);
    }

    public void reset(){
        blinkyPos.set(defaultVector);;
        pinkyPos.set(defaultVector);
        inkyPos.set(defaultVector);
        suePos.set(defaultVector);
        currentEdiblePos.set(defaultVector);
        currentPillPos.set(defaultVector);
        currentPowerPillPos.set(defaultVector);
        closestGhost = null;
        closestPowerPill = null;
        closestPill = null;
        closestEdible = null;
    }

    public void update(ConnectedSet cs) {
        if (cs.isPacMan()) {
            agent.updatePosition(cs);
            setPath(MsPacInterface.searchPixels);
            search(agent.x, agent.y);
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
        //else if (cs.isPill()) {
            //updateCurrentPill(cs.x, cs.y);
            //search(agent.x, agent.y);
        //}
        else if (cs.isPowerPill()) {
            updateCurrentPowerPill(cs.x, cs.y);
        }
        else if(cs.isEdible()){
            tmp.set((cs.x-3)/8, (cs.y-11)/8);
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

    public void eatSuperPowerPill(){
        isBlinkyEdible = isPinkyEdible = isSueEdible = isInkyEdible = true;
    }

    public boolean thereAreEdibles(){
        return isBlinkyEdible || isPinkyEdible || isSueEdible || isInkyEdible;
    }

    public boolean allAreEdibles(){ return isBlinkyEdible && isPinkyEdible && isSueEdible && isInkyEdible; }

    public void setCurrentObjective(Vector2d v){
        if(v != null) {
            currentObjective.set(v.x, v.y);
        }
    }

    public void draw(Graphics gg, int w, int h) {
        Graphics2D g = (Graphics2D) gg;
        if (currentObjective != null && agent != null) {
            g.setStroke(stroke);
            g.setColor(Color.cyan);
            g.drawLine((int) currentObjective.x*8+3, (int) currentObjective.y*8+11, (int) agent.cur.x*8+3, (int) agent.cur.y*8+11);
        }
    }

    private static int dw = 6;
    private static boolean validatePos2(int x, int y, int[] pix){
        x = x*8+11;
        y = y*8+3;
        int a = x*width + y;
        if(pix[a] != b && (pix[a] == MsPacInterface.blinky || (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a]))))
            return false;
        x = x + dw + 2;
        if(x < height) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x - dw - 2 - dw;
        if(x >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x + dw;
        y = y + dw + 2;
        if(y < width) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        y = y - dw - 2 -dw;
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
        x = x + dw + 1;
        if(x < height) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x - dw - 1 - dw - 1;
        if(x >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x + dw + 1;
        y = y + dw + 1;
        if(y < width) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        y = y - dw - 1 - dw -1;
        if(y >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        return true;
    }
}
