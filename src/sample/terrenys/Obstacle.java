/**
 * @file Obstacle.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Obstacle es un tipus de Terreny
 */

package sample.terrenys;

public class Obstacle extends Terreny {

    public Obstacle(int i){
        super ("Obstacle", i, 0, 1000, false);
    }
}
