package pacman;

import javafx.stage.Screen;
import utilities.JEasyFrame;
import utilities.ElapsedTimer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    static int blinky = -65536;
    static int pinky = -18689;
    static int inky = -16711681;
    static int sue = -18859;
    static int pacMan = -256;
    static int edible = -14408449;
    static int pill = -2434305;
<<<<<<< HEAD
    static int cherricita = -7252651;
    static int strawberricita = -16711936;
=======
    //static int cherry = -7252651;
    //static int strawberry = -16711936;
>>>>>>> Camilo

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
        if (display) sd.updateObjects(al);
    }

    public int[] getPixels() {
        BufferedImage im = robot.createScreenCapture(new Rectangle(left, top, width, height));
        im.getRGB(0, 0, width, height, pixels, 0, width);
        return pixels;
    }

    public static void main(String[] args) throws Exception {
        MsPacInterface ms = new MsPacInterface();
<<<<<<< HEAD
        StatisticalSummary ss = new StatisticalSummary();       //Statistical Data
        PacMover pm = new PacMover();                           //Pacman mover
        DirectionComponent dc = DirectionComponent.easyUse();   //
=======
        //StatisticalSummary ss = new StatisticalSummary();
        PacMover pm = new PacMover();
        DirectionComponent dc = DirectionComponent.easyUse();
>>>>>>> Camilo
        PacAgent pa = new LeftRight();

        TestMonitor tm = new TestMonitor();
        while(true) {
<<<<<<< HEAD
            ElapsedTimer t = new ElapsedTimer();                //Report of elapsed time

=======
            //ElapsedTimer t = new ElapsedTimer();
>>>>>>> Camilo
            int[] pix = ms.getPixels();
            ms.analyseComponents(pix);
            //ss.add(t.elapsed());

            //int action = ms.se.gs.agent.move(ms.se.gs);
            int action = pa.move(ms.se.gs);
            //System.out.println(action);
            pm.move(action);
            //tm.log(action, ms.se.gs);
            if (display) dc.update(action);
            Thread.sleep(delay);
            // pm.randMove();
        }
    }
}
