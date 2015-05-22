/**
 * @file Mounted.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Mounted todo
 */

package sample.unitats;

public class Mounted extends Unitat {

    public Mounted(String c, Integer atk, Integer def, Integer m, String bonus){
        super("Mounted", c, atk, def, m, 1, bonus);
    }


    public void mostrar() {
        Mostrar();
    }


}

