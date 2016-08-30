import games.math.Vector2d;

/**
 * Created by jccaleroe on 24/08/2016.
 */
public class Hola {

    public static void main(String[] args) throws Exception{
        Vector2d cur = new Vector2d(110, 190), prev = new Vector2d(110, 38);
        System.out.println(prev.dist(cur));
    }
}
