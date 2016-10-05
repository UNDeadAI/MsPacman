import pacman.GameState;
import pacman.MsPacInterface;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hola {

    static int width = MsPacInterface.width;
    static int height = MsPacInterface.height;
    static int left = MsPacInterface.left;
    static int top = MsPacInterface.top;
    static int b = -16777216;

    private static int  xPills = 28, yPills = 29;
    static int matrix[][] = new int[yPills][xPills];

    private static boolean validatePos2(int x, int y, int[] pix){
        x = x*8+11;
        y = y*8+3;
        int a = x*MsPacInterface.width + y;
        if(pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
            return false;
        x = x+8;
        if(x < height) {
            a = x*MsPacInterface.width + y;
            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
                return false;
        }
        x = x - 14;
        if(x >= 0) {
            a = x*MsPacInterface.width + y;
            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
                return false;
        }
        x = x + 6;
        y = y + 8;
        if(y < width) {
            a = x*MsPacInterface.width + y;
            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
                return false;
        }
        y = y - 14;
        if(y >= 0) {
            a = x*MsPacInterface.width + y;
            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
                return false;
        }
        return true;
    }

    private static boolean validatePos(int x, int y, int[] pix){
        x = x*8+12;
        y = y*8+4;
        int a = x*MsPacInterface.width + y;
        if(pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
            return false;
        x = x+7;
        if(x < height) {
            a = x*MsPacInterface.width + y;
            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
                return false;
        }
        x = x - 14;
        if(x >= 0) {
            a = x*MsPacInterface.width + y;
            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
                return false;
        }
        x = x + 7;
        y = y + 7;
        if(y < width) {
            a = x*MsPacInterface.width + y;
            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
                return false;
        }
        y = y - 14;
        if(y >= 0) {
            a = x*MsPacInterface.width + y;
            if (pix[a] != b && !MsPacInterface.colors.contains(pix[a]))
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception{

        Robot robot = new Robot();
        int pixels[] = new int[width * height];
        BufferedImage im = robot.createScreenCapture(new Rectangle(left, top, width, height));
        im.getRGB(0, 0, width, height, pixels, 0, width);

        for(int i = 0; i < yPills; i++) {
            for (int j = 0; j < xPills; j++) {
                if(validatePos(i, j, pixels) && validatePos2(i, j, pixels))
                    matrix[i][j] = 1;
                //if(pixels[(i*8+12)*width + (j*8+4)] == -2565892)
                    //matrix[i][j] = 1;
            }
        }

        for(int i = 0; i < yPills; i++) {
            for (int j = 0; j < xPills; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
