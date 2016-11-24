package pacman;

import java.awt.*;

public class ConnectedSet implements Drawable {
    public int x, y;
    public int width, height;
    public int fg, xMin, xMax, yMin, yMax, pTot, tot;
    public Color c;
    private boolean valid = false;

    public ConnectedSet(int x, int y, int fg) {
        this.x = x;
        this.y = y;
        xMin = xMax = x;
        yMin = yMax = y;
        this.fg = fg;
        c = new Color((fg & 0xFF0000) >> 16, (fg & 0xFF00) >> 8, (fg & 0xFF));
    }

    public void add(int px, int py) {
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
        else
            g.fillRect(xMin, yMin, width, height);
    }

    public void validate() {
        if (!valid) {
            width = xMax - xMin;
            height = yMax - yMin;
            x = (xMax + xMin) / 2;
            y = (yMax + yMin) / 2;
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
    }

    public boolean isEdible() {
        validate();
        return MsPacInterface.edible == fg && width >= 10 && height >= 10;
    }

    public boolean isPacMan() {
        validate();
        return fg == MsPacInterface.pacMan && width >= 10 && height >= 10;
    }

    public boolean isPill() {
        validate();
        return width == 1 && height == 1 && fg == MsPacInterface.pill;
    }

    public boolean isCherry() {
        validate();
        return MsPacInterface.blinky == fg && width == 5 && height == 5;
    }

    public boolean isPowerPill() {
        validate();
        return width == 7 && height == 7 && fg == MsPacInterface.pill;
    }

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
}
