package pacman;

import utilities.JEasyFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

public class MsPacInterface {
    // delay between each screen capture
    static private int delay = 4;

    //C#
    //public static int left = 575;
    //public static int top = 246;

    public static int left = 571;
    public static int top = 285;

    public static int width = 224;
    public static int height = 248;

    public static int[] pixels = new int[width*height];
    public static int[] searchPixels = new int[width*height];
    private Robot robot;
    private SimpleExtractor se;
    private SimpleDisplay sd;

//  CSharp app colors
//    static int pinky = -18210;
//    static int inky = -16711714;
//    static int sue = -18361;
//    static int edible = -14605858;
//    public static int pill = -2565892;

    static int pinky = -18689;
    static int inky = -16711681;
    static int sue = -18859;
    static int edible = -14408449;
    public static int pill = -2434305;

    public static int blinky = -65536;
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

    public void getPixels() {
        BufferedImage im = robot.createScreenCapture(new Rectangle(left, top, width, height));
        im.getRGB(0, 0, width, height, pixels, 0, width);
        System.arraycopy(pixels, 0, searchPixels, 0, width*height);
        //return pixels;
    }

    public static void main(String[] args) throws Exception {
        MsPacInterface ms = new MsPacInterface();
        PacMover pm = new PacMover();
        DirectionComponent dc = DirectionComponent.easyUse();

        while(true) {
            ms.getPixels();
            ms.analyseComponents(pixels);
            ms.se.gs.engage();
            int action = ms.se.gs.agent.move(ms.se.gs);
            pm.move(action);
            dc.update(action);
            ms.se.gs.reset();
            Thread.sleep(delay);
        }
    }
}
