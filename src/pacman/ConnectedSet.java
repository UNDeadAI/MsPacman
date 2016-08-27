package pacman;

import java.awt.*;

public class ConnectedSet implements Drawable {
    int x, y;
    int width, height;
    int fg; // the value of the FG pixels
    int xMin, xMax, yMin, yMax;
    int pTot;
    int tot;
    Color c;
    public int px, py;
    boolean valid = false;

    public ConnectedSet(int x, int y, int fg) {
        this.x = x;
        this.y = y;
        xMin = x;
        xMax = x;
        yMin = y;
        yMax = y;
        this.fg = fg;
        c = new Color((fg & 0xFF0000) >> 16, (fg & 0xFF00) >> 8, (fg & 0xFF));
    }

    public void add(int px, int py, int pos, int val) {
        xMin = Math.min(px, xMin);
        xMax = Math.max(px, xMax);
        yMin = Math.min(py, yMin);
        yMax = Math.max(py, yMax);
        pTot += (1 + px - x) * (1 + py - y);
        tot++;
        valid = false;
    }

    public void draw(Graphics g, int w, int h) {
        validate();
        g.setColor(c);
        if (ghostLike())
            g.fillRect(xMin, yMin, width, height);
<<<<<<< HEAD

        else if (powerPill() || pill() && true)
            g.fillRect(xMin, yMin, width+1, height+1);

        else if (isCherricita())
            g.fillRect(xMin, yMin, width, height);

        else if (edible())
=======
        else if(powerPill() || pill())
            g.drawRect(xMin, yMin, width+1, height+1);
        else if(edible())
>>>>>>> Camilo
            g.fillRect(xMin, yMin, width, height);
    }

    public void validate() {
        if (!valid) {
            width = xMax - xMin;
            height = yMax - yMin;
            valid = true;
        }
    }

    public boolean ghostLike() {
        validate();
        return ghostColor(fg) && width >= 10 && height >= 10;                    //198 - 208
        // return width == 13 && height == 13; // fg == MsPacInterface.inky;
    }

<<<<<<< HEAD
    public boolean edible() {
        validate();
        return MsPacInterface.edible == fg && width >= 10 && height >= 10;
        // return width == 13 && height == 13; // fg == MsPacInterface.inky;
    }

    public boolean isCherricita(){
        validate();
        return MsPacInterface.cherricita == fg && height >= 3 && width >= 1;
    }

=======
>>>>>>> Camilo
    public boolean ghostColor(int c) {
        return c == MsPacInterface.blinky ||
                c == MsPacInterface.pinky ||
                c == MsPacInterface.inky ||
                c == MsPacInterface.sue;
    }

    public boolean edible() {
        validate();
        return MsPacInterface.edible == fg && width >= 10 && height >= 10;
        // return width == 13 && height == 13; // fg == MsPacInterface.inky;
    }

    public boolean isPacMan() {
        validate();
        return fg == MsPacInterface.pacMan && width >= 10 && height >= 10;
        // return width == 13 && height == 13; // fg == MsPacInterface.inky;
    }

    public boolean pill() {
        validate();
        //return between(width, 2, 3) && between(height, 2, 3);
        return width == 1 && height == 1 && fg == MsPacInterface.pill;
    }

    public boolean powerPill() {
        validate();
        return width == 7 && height == 7 && fg == MsPacInterface.pill;
        //return between(width, 2, 7) && between(height, 2, 7);
    }
//    public boolean isCherry(){
//        validate();
//        return fg == MsPacInterface.cherry && width <= 3 && height  <= 4;
//    }

    public static boolean between(int x, int low, int high) {
        return x >= low && x <= high;
    }

    public int x() {
        return xMin + (xMax - xMin) / 2;
    }

    public int y() {
        return yMin + (yMax - yMin) / 2;
    }

    public void rescale(double fac) {}

    public int hashCode() {
        return pTot;
    }

    public boolean equals(Object obj) {
        ConnectedSet cs = (ConnectedSet) obj;
        return cs.pTot == pTot;
    }

    public String toString() {
        return x + " : " + y + " : " + pTot;
    }

    public int dist(Position p) {
        return (int) Math.sqrt(sqr(x - p.x) + sqr(y - p.y));
    }

    public static int sqr(int x) {
        return x * x;
    }
}

//height 257 - 260
