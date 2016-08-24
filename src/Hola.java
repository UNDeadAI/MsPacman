/**
 * Created by jccaleroe on 24/08/2016.
 */
public class Hola {

    public static void main(String[] args) throws Exception{
        System.out.println("Hola");
        Thread.sleep(1000);
        int blinky = -65536;
        System.out.println(blinky & 0xFFFFFF);
    }
}
