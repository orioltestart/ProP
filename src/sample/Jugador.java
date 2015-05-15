package sample;

import sample.unitats.Unitat;

import java.util.ArrayList;

/**
 * Created by lluis on 13/05/15.
 */
public class Jugador {

    Integer Num;  //1 o 2
    Integer Puntacio;
    ArrayList <Unitat> Exercit;



    public Jugador(){
        Puntacio = 0;
        Num = 0;
    }

    public Jugador (int i) {
        Puntacio = 0;
        Num = i;
    }

    public void AfegirUnitat (Unitat u) {
        Exercit.add(u);
        Puntacio +=1000;
    }

    public void EliminaUnitat (Unitat u) {
        Exercit.remove(u);
        Puntacio -=1000;
    }

    public void atacar (Unitat u, Unitat u2) {
            Integer a = u.calcularAtac(u2);
            u2.reduirPV(a);
            // tractar rang todo
            //tractar excepcio unitats aliades todo
            a = u2.calcularAtac(u);
            u.reduirPV(a);

            //puntuacions
            if (u.AplicarBonificacio(u2) == 3) Puntacio += 500;
            else Puntacio += 300;
    }

    public Integer getPuntacio() {
        return Puntacio;
    }
}
