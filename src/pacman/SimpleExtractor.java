package pacman;

import java.util.*;

public class SimpleExtractor {

    static int BG = 0;

    int w, h;
    IntStack stack;
    HashSet uniques;
    GameState gs;

    public SimpleExtractor(int w, int h) {
        this.w = w;
        this.h = h;
        int size = 4 * w * h;
        stack = new IntStack(size);
        uniques = new HashSet();
        gs = new GameState();
    }

    public ArrayList<Drawable> consume(int[] pix, Set<Integer> colors) {
        ArrayList<Drawable> objects = new ArrayList<>();
        ConnectedSet cs;
        for (int p = 0; p < pix.length; p++) {
            if ((pix[p] & 0xFFFFFF) != BG && colors.contains(pix[p])) {
                cs = consume(pix, p, pix[p]);
                objects.add(cs);
                gs.update(cs);
            }
        }
        objects.add(gs);
        return objects;
    }


    public ConnectedSet consume(int[] pix, int p, int fg) {
        ConnectedSet cs = new ConnectedSet(p % w, p / w, fg);

        stack.reset();
        stack.push(p);

        while (!stack.isEmpty()) {
            p = stack.pop();
            if (pix[p] == fg) {
                cs.add(p % w, p / w);
                pix[p] = 0;
                int cx = p % w;
                int cy = p / w;
                if (cx > 0)
                    stack.push(p - 1);

                if (cy > 0)
                    stack.push(p - w);

                if (cx < (w - 1))
                    stack.push(p + 1);

                if (cy < (h - 1))
                    stack.push(p + w);
            }
        }
        return cs;
    }
}
