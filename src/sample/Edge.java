package sample;

/**
 * Created by lluis on 27/05/15.
 */
public class Edge {

    private Vertex target;

    private Integer cost;



    public Edge(Vertex argTarget, Integer argWeight) {
        target = argTarget; cost = argWeight; }

    public String toString(){
        return target.toString() + " Dist: " + cost;
    }

    public Vertex getTarget() {
        return target;
    }

    public Integer getCost() {
        return cost;
    }




}
