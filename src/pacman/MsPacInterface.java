package pacman;

import utilities.JEasyFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

public class MsPacInterface {
    // delay between each screen capture
    static private int delay = 10;

    //C#
    public static int left = 575;
    public static int top = 246;

    //private static int left = 571;
    //private static int top = 285;

    public static int width = 224;
    public static int height = 248;
    //public static int width = 224;
    //public static int height = 248;

    public static int[] pixels;
    private Robot robot;
    private SimpleExtractor se;
    private SimpleDisplay sd;

//  CSharp app colors
    static int pinky = -18210;
    static int inky = -16711714;
    static int sue = -18361;
    static int edible = -14605858;
    static int pill = -2565892;

    //static int pinky = -18689;
    //static int inky = -16711681;
    //static int sue = -18859;
    //static int edible = -14408449;
    //static int pill = -2434305;

    static int blinky = -65536;
    static int pacMan = -256;

    //static int cherry = -7252651;
    //static int strawberry = -16711936;

    public static HashSet<Integer> colors = new HashSet<>();

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
        //se.gs.reset();
        ArrayList<Drawable> al = se.consume(pix, colors);
        sd.updateObjects(al);
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
            int action = ms.se.gs.agent.move2(ms.se.gs);
            pm.move(action);
            dc.update(action);
            Thread.sleep(delay);
        }
    }
}
