package sample;

import sample.unitats.Unitat;

import java.util.ArrayList;
import java.util.Iterator;

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

    public void ControlMaquina(Jugador j) throws InterruptedException {
        //iterador per totes les unitats
        Iterator itu = j.getExercit().iterator();
        while (itu.hasNext()) {
            Unitat agressor = (Unitat) itu.next();
            ArrayList<Posicio> rang = mapa.getRang(agressor.getPosAct(), agressor.getRang() + agressor.getMov());
            Iterator itp = rang.iterator();
            //iterador per totes les caselles dins del rang
            Integer millorDany = 0;
            Posicio Objectiu = new Posicio();
            //obtenir la unitat enemiga a la qual li farem mes punts de dany
            while (itp.hasNext()){
                Posicio pos = (Posicio) itp.next();
                if (pos.teUnitat() && pos.getUnitat().Enemiga(agressor) && agressor.calcularAtac(pos.getUnitat()) > millorDany){
                    millorDany = agressor.calcularAtac(pos.getUnitat());
                    Objectiu = pos;
                }
            }
            if (millorDany>0){
                //a partir de la posicio Objectiu
                ArrayList<Posicio> area = mapa.getRang(Objectiu, agressor.getRang());
                area.retainAll(rang);
                Posicio desti= new Posicio();
                Integer millorDef = -20;
                for (Posicio h : area){
                    if (h.getTerreny().getDefensa() > millorDef)
                        desti = h;
                }

                Thread.sleep(2000);
                mapa.desplacar(agressor.getPosAct(), desti);
                Thread.sleep(2000);
                j.atacar(agressor, Objectiu.getUnitat());
                Thread.sleep(2000);
            }
        }
        //si no hi ha cap unitat al rang, no fa res

    }

}
