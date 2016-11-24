package pacman;


class Pair implements Comparable<Pair>{
    Node l;
    int r;

    Pair(Node l, int r){
        this.l = l;
        this.r = r;
    }

    @Override
    public int compareTo(Pair pair)  {
        return (r-pair.r);
    }
}
