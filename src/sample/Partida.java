package sample;

/**
 * Created by lluis on 16/05/15.
 */
public class Partida {

    Integer comptadorTorns;
    Integer MaxTorns;
    Mapa mapa;
    Jugador j1, j2;


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
