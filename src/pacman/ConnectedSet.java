package pacman;

import java.awt.*;

public class ConnectedSet implements Drawable {
    int x, y;
    int width, height;
    int fg, xMin, xMax, yMin, yMax, pTot, tot;
    Color c;
    public int px, py;
    private boolean valid = false;

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
        if(isPowerPill() || isPill())
            g.drawRect(xMin, yMin, width+1, height+1);
        else if(isEdible())
            g.fillRect(xMin, yMin, width, height);
        else
            g.fillRect(xMin, yMin, width, height);
    }

    public void validate() {
        if (!valid) {
            width = xMax - xMin;
            height = yMax - yMin;
            valid = true;
        }
    }

    public boolean isPinky(){
        validate();
        if(width >= 10 && height >= 10)
            if(fg == MsPacInterface.pinky)
                return true;
            return false;
    }

    public boolean isBlinky() {
        validate();
        if(width >= 10 && height >= 10)
            if(fg == MsPacInterface.blinky)
                return true;
            return false;
    }

    public boolean isInky() {
        validate();
        if(width >= 10 && height >= 10)
            if(fg == MsPacInterface.inky)
                return true;
            return false;
    }

    public boolean isSue() {
        validate();
        if(width >= 10 && height >= 10)
            if(fg == MsPacInterface.sue)
                return true;
        return false;
        // return width == 13 && height == 13; // fg == MsPacInterface.inky;
    }

    public boolean isEdible() {
        validate();
        return MsPacInterface.edible == fg && width >= 10 && height >= 10;
        // return width == 13 && height == 13; // fg == MsPacInterface.inky;
    }

    public boolean isPacMan() {
        validate();
        return fg == MsPacInterface.pacMan && width >= 10 && height >= 10;
        // return width == 13 && height == 13; // fg == MsPacInterface.inky;
    }

    public boolean isPill() {
        validate();
        //return between(width, 2, 3) && between(height, 2, 3);
        return width == 1 && height == 1 && fg == MsPacInterface.pill;
    }

    public boolean isPowerPill() {
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

    public static int sqr(int x) {
        return x * x;
    }
}
