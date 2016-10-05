package pacman;

public class Vector2d {
    public double x, y;
    public int w, dir;

    public Vector2d() {
        this(0, 0);
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
        w = 0;
        dir = -1;
    }

    public Vector2d(Vector2d v) {
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

    public Vector2d(double x, double y, int w) {
        this.x = x;
        this.y = y;
        this.w = w;
        dir = -1;
    }

    public void add(Vector2d v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void subtract(Vector2d v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void mul(double fac) {
        x *= fac;
        y *= fac;
    }

    public double scalarProduct(Vector2d v) {
        return x * v.x + y * v.y;
    }

    public void set(Vector2d v) {
        x = v.x;
        y = v.y;
        w = v.w;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return x + " : " + y;
    }

    public static double sqr(double x) {
        return x * x;
    }

    public double sqDist(Vector2d v) {
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

        if (Double.compare(vector2d.x, x) != 0) return false;
        return Double.compare(vector2d.y, y) == 0;
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
