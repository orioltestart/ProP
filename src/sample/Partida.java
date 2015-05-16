package sample;

import sample.unitats.Unitat;

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
            Unitat u = (Unitat) itu.next();
            Iterator itp = mapa.getRang(u.getPosAct(),"Total").iterator();
            Integer millorDany = 0;
            Integer millorDef = -20;
            Posicio millor = new Posicio();
            while (itp.hasNext()){
                Posicio pos = (Posicio) itp.next();
                if (pos.teUnitat() && pos.getUnitat().Enemiga(u) &&
                        u.calcularAtac(pos.getUnitat()) > millorDany &&
                        pos.getTerreny().getDefensa() > millorDef){
                    millorDany = u.calcularAtac(pos.getUnitat());
                    millor = pos;
                    millorDef = pos.getTerreny().getDefensa();
                }
            }
            if (millorDany>0){
                Thread.sleep(2000);
                mapa.desplacar(u.getPosAct(), millor);
                Thread.sleep(2000);
                j.atacar(u, millor.getUnitat());
                Thread.sleep(2000);
            }
            //si no hi ha cap unitat al rang, no fa res
        }
    }

    public void RealitzarDesplacament(Posicio o, Posicio f){
        mapa.desplacar(o,f);
    }

}
