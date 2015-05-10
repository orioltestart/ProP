package sample.terrenys;

/**
 * Created by lluis on 24/04/15.
 */
public class River extends Terreny {

    public River(){
        super("River", -10, 3, true, false);
    }

    public River(int t){
        super("River"+t, -5, 3, true, false);
    }
}
