/**
 * @file Infantry.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Infantry es un tipus d'unitat
 */

package sample.unitats;

public class Infantry extends Unitat {

    public Infantry(String c, Integer atk, Integer def, Integer m, String bonus){
        super("Infantry", c, atk, def, m, 1, bonus);
    }




    public void mostrar() {
        Mostrar();
    }


}
