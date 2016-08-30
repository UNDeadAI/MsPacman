package pacman;

import utilities.JEasyFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

import stats.StatisticalSummary;

public class MsPacInterface {
    // delay between each screen capture
    static int delay = 10;
    static boolean display = true;

    //Mis top y left
    //int left = 576;
    //int top = 246;

    int left = 571;
    int top = 285;
    public static int width = 224;
    public static int height = 248;
    int[] pixels;
    Robot robot;
    SimpleExtractor se;
    SimpleDisplay sd;

//  CSharp app colors
//    static int pinky = -18210;
//    static int inky = -16711714;
//    static int sue = -18361;
//    static int edible = -14605858;
//    static int pill = -2565892;

    static int pinky = -18689;
    static int inky = -16711681;
    static int sue = -18859;
    static int edible = -14408449;
    static int pill = -2434305;

    static int blinky = -65536;
    static int pacMan = -256;

    //static int cherry = -7252651;
    //static int strawberry = -16711936;

    static HashSet<Integer> colors = new HashSet<Integer>();

    static {
        colors.add(blinky);
        colors.add(pinky);
        colors.add(inky);
        colors.add(sue);
        colors.add(pacMan);
        colors.add(edible);
        colors.add(pill);
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
        PacMover pm = new PacMover();
        DirectionComponent dc = DirectionComponent.easyUse();

        while(true) {
            int[] pix = ms.getPixels();
            ms.analyseComponents(pix);
            int action = ms.se.gs.agent.move(ms.se.gs);
            pm.move(action);
            if (display) dc.update(action);
            Thread.sleep(delay);
        }
    }
}
