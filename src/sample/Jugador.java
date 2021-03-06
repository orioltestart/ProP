/**
 * @file Jugador.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Jugador realitza accions sobre les unitats i el mapa.
 */

package sample;

import sample.unitats.Unitat;

import java.util.ArrayList;

public class Jugador {

    Integer Num;  //1 o 2 identificador de jugador
    ArrayList <Unitat> Exercit;

    //constructors

    /**
     @pre --
     @post crea un JUgador
     */
    public Jugador(){
        Num = 0;
    }

    /**
     @pre --
     @post crea un JUgador amb Num i
     */
    public Jugador (int i) {
        Num = i;
        Exercit = new ArrayList<>();
    }


    /**
     @pre --
     @post afegeix a la llista d'unitats d'aquest jugador la unitat u i modifica la puntuacio
     @return void
     @param u es una unitat
     */
    public void AfegirUnitat (Unitat u) {
        Exercit.add(u);
    }

    /**
     @pre punts de vida de u = 0 i u es propietat d'aquest jugador
     @post treu la unitat u de la llista d'unitats d'aquest jugador i modifica la puntuacio
     @return void
     @param u es una unitat
     */
    public void EliminaUnitat (Unitat u) {
        Exercit.remove(u);
    }

    /**
     @pre --
     @post realitza un enfrontament entre dues unitats: unitat u contra unitat u2 i modifica la puntuacio
     @return void
     @param u es una unitat propietat d'aquest jugador
     @param u2 es una unitat propietat d'un altre jugador diferent d'aquest
     */
    public void atacar (Unitat u, Unitat u2) {
        if (u.Enemiga(u2)) {
            Integer a = u.calcularAtac(u2);
            u2.reduirPV(a);
            if (u2.potAtacar(u)) {  //contraatac
                a = u2.calcularAtac(u);
                u.reduirPV(a);
            }

            u.getPosAct().reset();
            u2.getPosAct().reset();
        }
    }

    /**
     @pre inici del torn
     @post canvia l'estat de totes les unitats d'aquest jugador a apunt
     @return void
     */
    public void activaExercit(){
        for (Unitat u : Exercit){
            u.setReady();
        }
    }

    /**
     @pre --
     @post retorna el numero d'aquest jugador
     @return Integer
     */
    public Integer getNum (){
        return Num;
    }

    /**
     @pre --
     @post retorna el nombre d'unitats que té aquest jugador
     @return Integer
     */
    public Integer nombreUnitats() {
        return Exercit.size();
    }

    /**
     @pre --
     @post retorna la llista d'unitats d'aquest jugador
     @return ArrayList<Unitat>
     */
    public ArrayList<Unitat> getExercit() {
        return Exercit;
    }
}
