package pacman;

import utilities.JEasyFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

public class MsPacInterface {
    // delay between each screen capture
    static private int delay = 5;

    //VIDEOBEAM Windows
    //public static int left = 528;
    //public static int top = 261;

    //WINDOWS
    //public static int left = 571;
    //public static int top = 285;

    //ALVARO
    //public static int left = 67;
    //public static int top = 97;

    //ALVARO
    //public static int left = 288;
    //public static int top = 167;

    //UBUNTU
    public static int left = 571;
    public static int top = 311;

    //Ubuntu videobeam
    //public static int left = 571;
    //public static int top = 339;

    public static int width = 224;
    public static int height = 248;

    static int[] pixels = new int[width*height];
    static int[] searchPixels = new int[width*height];
    private Robot robot;
    private SimpleExtractor se;
    private SimpleDisplay sd;

    static int pinky = -18689;
    static int inky = -16711681;
    static int sue = -18859;
    static int edible = -14408449;
    static int pill = -2434305;
    static int edible2 = -2434305;

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

    private MsPacInterface() throws Exception {
        robot = new Robot();
        pixels = new int[width * height];
        se = new SimpleExtractor(width, height);
        sd = new SimpleDisplay(width, height);
        new JEasyFrame(sd, "Extracted", true);
    }

    private void analyseComponents(int[] pix) {
        ArrayList<Drawable> al = se.consume(pix, colors);
        sd.updateObjects(al);
    }

    private void getPixels() {
        BufferedImage im = robot.createScreenCapture(new Rectangle(left, top, width, height));
        im.getRGB(0, 0, width, height, pixels, 0, width);
        System.arraycopy(pixels, 0, searchPixels, 0, width*height);
    }

    public static void main(String[] args) throws Exception {
        MsPacInterface ms = new MsPacInterface();
        PacMover pm = new PacMover();
        DirectionComponent dc = DirectionComponent.easyUse();

        while(true) {
            ms.getPixels();
            ms.se.gs.setPath(MsPacInterface.searchPixels);
            ms.analyseComponents(pixels);
            ms.se.gs.initiateSearch();
            int action = ms.se.gs.agent.move(ms.se.gs);
            pm.move(action);
            dc.update(action);
            ms.se.gs.reset();
            Thread.sleep(delay);
        }
    }
}
