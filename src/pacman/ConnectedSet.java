package pacman;

import java.awt.*;

class ConnectedSet implements Drawable {
    int x, y;
    private int width, height;
    private int fg, xMin, xMax, yMin, yMax, pTot;
    Color c;
    private boolean valid = false;

    ConnectedSet(int x, int y, int fg) {
        this.x = x;
        this.y = y;
        xMin = xMax = x;
        yMin = yMax = y;
        this.fg = fg;
        c = new Color((fg & 0xFF0000) >> 16, (fg & 0xFF00) >> 8, (fg & 0xFF));
    }

    void add(int px, int py) {
        xMin = Math.min(px, xMin);
        xMax = Math.max(px, xMax);
        yMin = Math.min(py, yMin);
        yMax = Math.max(py, yMax);
        pTot += (1 + px - x) * (1 + py - y);
        valid = false;
    }

    public void draw(Graphics g, int w, int h) {
        validate();
        g.setColor(c);
        g.fillRect(xMin, yMin, width, height);
    }

    private void validate() {
        if (!valid) {
            width = xMax - xMin;
            height = yMax - yMin;
            x = (xMax + xMin) / 2;
            y = (yMax + yMin) / 2;
            valid = true;
        }
    }

    boolean isPinky(){
        validate();
        return width >= 10 && height >= 10 && fg == MsPacInterface.pinky;
    }

    boolean isBlinky() {
        validate();
        return width >= 10 && height >= 10 && fg == MsPacInterface.blinky;
    }

    boolean isInky() {
        validate();
        return width >= 10 && height >= 10 && fg == MsPacInterface.inky;
    }

    boolean isSue() {
        validate();
        return width >= 10 && height >= 10 && fg == MsPacInterface.sue;
    }

    boolean isEdible() {
        validate();
        return MsPacInterface.edible == fg && width >= 10 && height >= 10;
    }

    boolean isPacMan() {
        validate();
        return fg == MsPacInterface.pacMan && width >= 10 && height >= 10;
    }

    boolean isPill() {
        validate();
        return width <= 3 && height <= 3 && fg == MsPacInterface.pill;
    }

    public boolean isCherry() {
        validate();
        return MsPacInterface.blinky == fg && width == 5 && height == 5;
    }

    boolean isPowerPill() {
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
