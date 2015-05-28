/**
 * @file Partida.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Partida todo
 */

package sample;

import sample.unitats.Unitat;

import java.util.ArrayList;
import java.util.Iterator;

public class Partida {

    Integer comptadorTorns; // 0<comptadorTorns < MaxTorns
    Integer MaxTorns;
    Mapa mapa;
    Jugador j1, j2;
    Posicio fi;


    Partida (){
        comptadorTorns = 0;
        MaxTorns = 30;

    }

    public void afegirJugadors(Jugador a, Jugador b){
        j1 = a;
        j2 = b;
    }

    public void construirMapa (Mapa m){
        mapa = m;
    }


}
