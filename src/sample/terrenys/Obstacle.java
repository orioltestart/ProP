package sample.terrenys;

/**
 * Created by lluis on 24/04/15.
 */
public class Obstacle extends Terreny {

    public Obstacle (){
        super("Obstacle", 0, 10, false, false);
    }
    public Obstacle(int i){
        super ("Wall", 0, 10, false, false);
    }
}
