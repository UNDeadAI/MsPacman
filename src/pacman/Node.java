package pacman;

class Node implements Comparable<Node>{

    int x;
    int y;
    int weight, dir;

    Node(int x, int y, int w){
        this.x = x;
        this.y = y;
        weight = w;
        dir = 0;
    }

    Node(int x, int y, int w, int d){
        this.x = x;
        this.y = y;
        weight = w;
        dir = d;
    }

    Node(int x, int y){
        this.x = x;
        this.y = y;
        weight = 0;
        dir = 0;
    }

    @Override
    public int compareTo(Node node)  {
        return node.weight - weight;
    }
}
