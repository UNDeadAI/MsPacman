package pacman;

/**
 * Created by oscar on 23/11/16.
 */
public class Pair<L,R> {
    public L l;
    public R r;

    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }

    public L getL() {
        return l;
    }

    public R getR() {
        return r;
    }

    public void setL(L l) {
        this.l = l;
    }

    public void setR(R r) {
        this.r = r;
    }
}
