package pacman;

public class Node {

    public int x;
    public int y;
    public int weight, dir;

    public Node(int x, int y, int w){
        this.x = x;
        this.y = y;
        weight = w;
        dir = 0;
    }

    public Node(int x, int y, int w, int d){
        this.x = x;
        this.y = y;
        weight = w;
        dir = d;
    }

    public Node(int x, int y){
        this.x = x;
        this.y = y;
        weight = 0;
        dir = 0;
    }
}
