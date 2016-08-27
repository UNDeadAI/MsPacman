package pacman;

import javafx.stage.Screen;
import utilities.JEasyFrame;
import utilities.ElapsedTimer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

import stats.StatisticalSummary;

/**
 * User: Simon
 * Date: 03-Mar-2007
 * Time: 08:50:37
 *
 *  This class computes the distance to the nearest wall in each direction.
 *
 *  This allows the PacMan to see which way it could go -
 *  so if the distance from the pacman to the nearest wall in any direction was
 *  below a threshold, then that would not be a feasible direction.
 *
 *  This is done with a OneD array, is to have offsets of
 * +/- 1 and +/- width
 *  too close in any particular direction, then that would be detected
 */

public class MsPacInterface {
    // delay between each screen capture
    static int delay = 10;
    static boolean display = true;

    int left = 571;
    int top = 285;
    public static int width = 224;
    public static int height = 248;
    int[] pixels;
    Robot robot;
    SimpleExtractor se;
    SimpleDisplay sd;

    // these are the integers corresponding to the pixel colours for each game object
    static int blinky = -65536;
    static int pinky = -18689;
    static int inky = -16711681;
    static int sue = -18859;
    static int pacMan = -256;
    static int edible = -14408449;
    static int pill = -2434305;
    static int cherricita = -7252651;
    static int strawberricita = -16711936;

    static HashSet<Integer> colors = new HashSet<Integer>();

    static {
        colors.add(blinky);
        colors.add(pinky);
        colors.add(inky);
        colors.add(sue);
        colors.add(pacMan);
        colors.add(edible);
        colors.add(pill);
        colors.add(cherricita);
        colors.add(strawberricita);
    }

    public MsPacInterface() throws Exception {
        robot = new Robot();
        pixels = new int[width * height];
        se = new SimpleExtractor(width, height);
        sd = new SimpleDisplay(width, height);
        new JEasyFrame(sd, "Extracted", true);
    }

    public void analyseComponents(int[] pix) {
        se.gs.reset();
        ArrayList<Drawable> al = se.consume(pix, colors);
        // System.out.println("Components " + al);
        if (display) sd.updateObjects(al);
    }

    public int[] getPixels() {
        BufferedImage im = robot.createScreenCapture(new Rectangle(left, top, width, height));
        im.getRGB(0, 0, width, height, pixels, 0, width);
        return pixels;
    }

    public static void main(String[] args) throws Exception {
        MsPacInterface ms = new MsPacInterface();
        StatisticalSummary ss = new StatisticalSummary();       //Statistical Data
        PacMover pm = new PacMover();                           //Pacman mover
        DirectionComponent dc = DirectionComponent.easyUse();   //
        PacAgent pa = new LeftRight();

        TestMonitor tm = new TestMonitor();
        while(true) {
            ElapsedTimer t = new ElapsedTimer();                //Report of elapsed time

            int[] pix = ms.getPixels();

            ms.analyseComponents(pix);
            ss.add(t.elapsed());
            //int action = ms.ce.gs.agent.move(ms.ce.gs);
            int action = pa.move(ms.se.gs);
            System.out.println(action);
            pm.move(action);
            tm.log(action, ms.se.gs);
            if (display) dc.update(action);
            Thread.sleep(delay);
            // pm.randMove();
        }
        // System.out.println(ss);
    }
}
