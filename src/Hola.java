import pacman.MsPacInterface;
import pacman.Node;
import pacman.Vector2d;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;

public class Hola {

    static int width = MsPacInterface.width;
    static int height = MsPacInterface.height;
    static int left = MsPacInterface.left;
    static int top = MsPacInterface.top;
    static int b = -16777216;
    static int pix[] = new int[width * height];

    private static int  xPills = 28, yPills = 29;
    static int matrix[][] = new int[yPills][xPills];

    private static int DR[] = {0, 1, 0, -1};
    private static int DC[] = {1, 0, -1, 0};

    static Vector2d blinkyPos = new Vector2d(),
            inkyPos = new Vector2d(), suePos = new Vector2d(), pinkyPos = new Vector2d(),
            currentPowerPillPos = new Vector2d(), currentPillPos = new Vector2d(),
            currentEdiblePos = new Vector2d();

    public static void search(int tXPos, int tYPos) {
        for (int i = 0; i < yPills; i++)
            for (int j = 0; j < xPills; j++)
                matrix[i][j] = 0;
        matrix[tYPos][tXPos] = 1;
        ArrayDeque<Node> queue = new ArrayDeque<>();
        Node u, child;
        Vector2d vector;
        queue.add(new Node(tYPos, tXPos, 0));
        int tmp1, tmp2;
        while (!queue.isEmpty()) {
            u = queue.poll();
            for (int i = 0; i < 4; i++) {
                tmp1 = DR[i] + u.x;
                tmp2 = DC[i] + u.y;
                if (tmp1 < yPills && tmp1 >= 0) {
                    if (tmp2 >= xPills)
                        tmp2 = 0;
                    if (tmp2 < 0)
                        tmp2 = xPills - 1;
                    if (matrix[tmp1][tmp2] == 0) {
                        matrix[tmp1][tmp2] = 2;
                        if (validatePos(tmp1, tmp2, pix) && validatePos2(tmp1, tmp2, pix)) {
                            matrix[tmp1][tmp2] = 1;
                            child = new Node(tmp1, tmp2, u.weight + 1);
                            if (u.dir == -1)
                                child.dir = i + 1;
                            else
                                child.dir = u.dir;
                            queue.add(child);
                            vector = new Vector2d(tmp2, tmp1, u.weight + 1, child.dir);
                            if (inkyPos.equals(vector)) {
                                inkyPos.w = vector.w;
                                System.out.println("inky encontrado en " + tmp1 + " " + tmp2);
                            } else if (suePos.equals(vector)) {
                                suePos.w = vector.w;
                                System.out.println("Sue encontrado en " + tmp1 + " " + tmp2);
                            } else if (blinkyPos.equals(vector)) {
                                blinkyPos.w = vector.w;
                                System.out.println("Blinky encontrado en " + tmp1 + " " + tmp2);
                            } else if (pinkyPos.equals(vector)) {
                                pinkyPos.w = vector.w;
                                System.out.println("Pinky encontrado en " + tmp1 + " " + tmp2);
                            } else if (currentPowerPillPos.equals(vector)) {
                                System.out.println("Power Pill encontrado en " + tmp1 + " " + tmp2);
                            } else if (currentPillPos.equals(vector)) {
                                System.out.println("pill encontrado en " + tmp1 + " " + tmp2);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean validatePos2(int x, int y, int[] pix){
        x = x*8+11;
        y = y*8+3;
        int a = x*width + y;
        if(pix[a] != b && (pix[a] == MsPacInterface.blinky || (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a]))))
            return false;
        x = x+8;
        if(x < height) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x - 14;
        if(x >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x + 6;
        y = y + 8;
        if(y < width) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        y = y - 14;
        if(y >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        return true;
    }

    private static boolean validatePos(int x, int y, int[] pix){
        x = x*8+12;
        y = y*8+4;
        int a = x*width + y;
        if(pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
            return false;
        x = x+7;
        if(x < height) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x - 14;
        if(x >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        x = x + 7;
        y = y + 7;
        if(y < width) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        y = y - 14;
        if(y >= 0) {
            a = x*width + y;
            if (pix[a] != b && (pix[a] == MsPacInterface.blinky || !MsPacInterface.colors.contains(pix[a])))
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception{

        Robot robot = new Robot();
        BufferedImage im = robot.createScreenCapture(new Rectangle(left, top, width, height));
        im.getRGB(0, 0, width, height, pix, 0, width);

        for(int i = 0; i < yPills; i++) {
            for (int j = 0; j < xPills; j++) {
                if(validatePos(i, j, pix) && validatePos2(i, j, pix))
                    matrix[i][j] = 1;
                //if(pixels[(i*8+11)*width + (j*8+3)] == MsPacInterface.pill)
                //matrix[i][j] = 1;
            }
        }

        for(int i = 0; i < yPills; i++) {
            for (int j = 0; j < xPills; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
        System.out.println("\n");

        blinkyPos.set((125-3)/8,(65-11)/8);
        inkyPos.set((193-3)/8,(164-11)/8);
        suePos.set((40-3)/8,(236-11)/8);
        pinkyPos.set((40-3)/8,(75-11)/8);
        currentPillPos.set((144-3)/8,(188-11)/8);
        currentPowerPillPos.set((144-3)/8,(188-11)/8);
        currentEdiblePos.set((144-3)/8,(188-11)/8);

        search((96-3)/8,(163-11)/8);

        for(int i = 0; i < yPills; i++) {
            for (int j = 0; j < xPills; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

//    private static boolean validatePos2(int x, int y, int[] pix){
//        x = x*8+11;
//        y = y*8+3;
//        int a = x*width + y;
//        if(pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//            return false;
//        x = x+8;
//        if(x < height) {
//            a = x*width + y;
//            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//                return false;
//        }
//        x = x - 14;
//        if(x >= 0) {
//            a = x*width + y;
//            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//                return false;
//        }
//        x = x + 6;
//        y = y + 8;
//        if(y < width) {
//            a = x*width + y;
//            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//                return false;
//        }
//        y = y - 14;
//        if(y >= 0) {
//            a = x*width + y;
//            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//                return false;
//        }
//        return true;
//    }
//
//    private static boolean validatePos(int x, int y, int[] pix){
//        x = x*8+12;
//        y = y*8+4;
//        int a = x*width + y;
//        if(pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//            return false;
//        x = x+7;
//        if(x < height) {
//            a = x*width + y;
//            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//                return false;
//        }
//        x = x - 14;
//        if(x >= 0) {
//            a = x*width + y;
//            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//                return false;
//        }
//        x = x + 7;
//        y = y + 7;
//        if(y < width) {
//            a = x*width + y;
//            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//                return false;
//        }
//        y = y - 14;
//        if(y >= 0) {
//            a = x*width + y;
//            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
//                return false;
//        }
//        return true;
//    }
}
