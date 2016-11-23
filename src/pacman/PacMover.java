package pacman;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class PacMover implements Constants {
    Robot robot;                                    //Robot to process pressed keys
    boolean keyPressed;                             //Verify whether a key is pressed or not
    int curKey;                                     //Current key pressed
    int curDir;                                     //Current direction of Ms Pacman
    // not used when waitForIdle isset to false
    static int autoDelay = 20;                      //Number of milliseconds that the robot sleeps
    static Random r = new Random();

    // these are related to the definitions in PacController
    static int[] keys = {-1, KeyEvent.VK_UP, KeyEvent.VK_RIGHT,         //Contants for numpads arrows
                         KeyEvent.VK_DOWN, KeyEvent.VK_LEFT};

    public PacMover() {
        keyPressed = false;                                             //No key pressed
        try {
            robot = new Robot();
            // prevent the robot from being flooded with too many events
            robot.setAutoWaitForIdle(false);                            //AutoWaitForIdle non activated
            robot.setAutoDelay(autoDelay);                              //Sets the number of milliseconds this Robot sleeps after generating an event
            //System.out.println(robot.getAutoDelay());
            //System.out.println(robot.isAutoWaitForIdle());
            curKey = -1;
            //robot.waitForIdle();
        } catch(Exception e) {}
    }

    public void move(int direction) {
        // release the current key if it is pressed
        if (keyPressed) {
            robot.keyRelease( curKey );                                 //Release the current key
            keyPressed = false;
        }

        // now work out the action
        if (direction > 0 && direction < keys.length) {

            curKey = keys[direction];                           //If direction is in range of keys, set current key in that direction
            robot.keyPress(curKey);                             //Press current key
            robot.keyRelease(curKey);                           //Release the current key
            robot.waitForIdle();                                //Waits until all events currently on the event queue have been processed
            keyPressed = true;                                  //A key has been pressed
        }
        curDir = direction;                                     //Current direction set to direction
    }

    public void randMove() {
        move(r.nextInt(keys.length));
    }
}
