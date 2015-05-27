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



    /**
     @pre --
     @post la maquina realitza les funcions de un jugador j
     @return void
     @param j es el jugador controlat per la maquina
     */
    public void ControlMaquina(Jugador j) throws InterruptedException {
        //iterador per totes les unitats del jugador
        Iterator itu = j.getExercit().iterator();
        while (itu.hasNext()) {
            Unitat agressor = (Unitat) itu.next();
            ArrayList<Posicio> rang = mapa.getRang(agressor.getPosAct(), agressor.getRang() + agressor.getMovTot());
            Iterator itp = rang.iterator();
            //iterador per totes les caselles dins del rang
            Integer millorDany = 0;
            Posicio Objectiu = new Posicio();
            //obtenir la unitat enemiga a la qual li farem mes punts de dany
            boolean mata = false;
            while (!mata && itp.hasNext()){
                Posicio pos = (Posicio) itp.next();
                if (pos.teUnitat() && pos.getUnitat().Enemiga(agressor) && agressor.calcularAtac(pos.getUnitat()) > millorDany){
                    millorDany = agressor.calcularAtac(pos.getUnitat());
                    Objectiu = pos;
                    if (millorDany >= Objectiu.getUnitat().getPV()){    //si matem una unitat no cal buscarne cap altra
                        mata = true;
                    }
                }
            }
            if (agressor.getPV()<35 && !mata){
                retirada(agressor);
            }

            else if (millorDany>0){
                //a partir de la posicio Objectiu, busquem la millor posicio per mourens
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
            j.enRepos(agressor);

        }
        //si no hi ha cap unitat al rang, no fa res

    }

    /**
     @pre --
     @post els jugadors j1 i j2 juguen l'un contra l'altre per torns: cada torn actua primer un i despres l'altre
     estrictament (quan un juga, l'altre ha d'esperar). Guanya el jugador que compleix l'objectiu primer
     @return void
     */
    public void jugar () throws InterruptedException {

        for (int i = 1; i<MaxTorns; i++){
            System.out.println("TORN "+i);
            j1.activaExercit();
            j2.activaExercit();
            // todo while (boto no pitjat)
                //juga
            Thread.sleep(2000);
            //torn jugador 1


            //bloquejar accions del ratoli


            ControlMaquina(j2);
        }
        //si arriba aqui vol dir que s'ha acabat la partida
        System.out.println("FI DE LA PARTIDA");

    }

    public void retirada(Unitat u){

        Posicio desti = new Posicio();
        ArrayList<Posicio> candidats = new ArrayList<>();

        for (Posicio p : mapa.getForts()){
            if (!p.teUnitat()) candidats.add(p);
        }
        //buscar cami minim per cada un
        //backtracking de la posicio on hi ha la fortalesa mes proxima
        // (si no esta ocupada) ens dirigirem cap alla
        //moures lo mes rapid possible cap alla
    }

}
