/**
 * @file River.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe River es un tipus de Terreny
 */

package sample.terrenys;

public class River extends Terreny {

    public River(int t){
        super("River", t, -10, 3, false);
    }
}
