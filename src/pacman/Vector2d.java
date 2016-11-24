package pacman;

public class Vector2d {
    double x, y;
    int w, dir;

    public Vector2d() {
        this(0, 0);
    }

    Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
        w = 1000;
        dir = 0;
    }

    Vector2d(Vector2d v) {
        x = v.x;
        y = v.y;
        w = v.w;
        dir = v.dir;
    }

    public Vector2d(double x, double y, int w, int d) {
        this.x = x;
        this.y = y;
        this.w = w;
        dir = d;
    }

    void updateVector(Node n){
        w = n.weight;
        dir = n.dir;
    }

    void subtract(Vector2d v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void mul(double fac) {
        x *= fac;
        y *= fac;
    }

    double scalarProduct(Vector2d v) {
        return x * v.x + y * v.y;
    }

    void set(Vector2d v) {
        x = v.x;
        y = v.y;
        w = v.w;
    }

    void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return x + " : " + y;
    }

    static double sqr(double x) {
        return x * x;
    }

    double sqDist(Vector2d v) {
        return sqr(x - v.x) + sqr(y - v.y);
    }

    public double dist(Vector2d v) {
        return Math.sqrt(sqDist(v));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2d vector2d = (Vector2d) o;

        return Double.compare(vector2d.x, x) == 0 && Double.compare(vector2d.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
