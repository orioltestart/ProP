/**
 * @file Mapa.java
 * @author Oriol Testart
 * @brief La classe Mapa todo
 */

package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import sample.terrenys.*;
import sample.unitats.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by OriolTestart on 18/4/15.
 */

public class Mapa {
    private String nom;
    private Posicio[][] mapa;
    private Integer MAXH;
    private Integer MAXV;
    private Posicio meta;
    private Boolean end = false;



    private ArrayList<Posicio> forts;//LLUIS
    private Integer [][] costosCamins;
    private ArrayList<Vertex> adj;


    public Mapa() {
        MAXH = 0;
        MAXV = 0;
        mapa = new Posicio[MAXH][MAXV];
        costosCamins = new Integer[MAXH][MAXV];
        adj = new ArrayList<>();
        nom = "Mapa no construit";
    }

    public Mapa(String f) {
        try {
            llegirMapa(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Integer getMidaH() {
        return MAXH;
    }

    public Integer getMidaV() {
        return MAXV;
    }

    public Posicio getPos(int x, int y) {
        return mapa[x][y];
    }

    public Posicio[][] getMapa() {
        return mapa;
    }

    //Pre: --
    //Post: Retorna un string amb la matriu del mapa a dins
    public String toString() {
        String aux = "Nom: " + nom + "\n";
        for (int i = 0; i < MAXH; i++) {
            for (int j = 0; j < MAXV; j++) {
                aux += mapa[i][j].toString() + " ";
            }
            aux += "\n";
        }
        return aux;
    }

    //FABRIQUES D'OBJECTES

    public static Terreny fabricaTerrenys(Integer i) {
        if (i.equals(0)) return new Plain(0);   //plain
        else if (i.equals(1)) return new Forest(1); //forest
        else if (i.equals(2)) return new Mountain(2);   //mountain
        else if (i.equals(3)) return new Fortress(3);   //fortress
        else if (i.equals(4)) return new River(4);  //
        else if (i.equals(5)) return new River(5);
        else if (i.equals(6)) return new River(6);
        else if (i.equals(7)) return new River(7);
        else if (i.equals(8)) return new River(8);
        else if (i.equals(9)) return new Obstacle(9);
        else if (i.equals(10)) return new Plain(10); //cami
        else if (i.equals(11)) return new Plain(11); //maó
        else if (i.equals(12)) return new Plain(12); //pontH
        else if (i.equals(13)) return new Plain(13); //pontV
        else if (i.equals(14)) return new Obstacle(14); //murH
        else if (i.equals(15)) return new Fortress(15); //meta
        else return new Terreny();
    }

    private Unitat fabricaUnitats(Integer i) {
        if (i.equals(1)) return new Halberdier();
        else if (i.equals(2)) return new Knight();
        else if (i.equals(3)) return new Bowknight();
        else if (i.equals(4)) return new Marksman();
        else if (i.equals(5)) return new Paladin();
        else if (i.equals(6)) return new Wyvernknight();
        else return null;
    }


    //Pre: --
    //Post: Trasllada la unitat de la Posició ori a la Posició fi
    //Excepcions: Desplaçar una unitat a una posició ja ocupada llança una IllegalArgumentException així que la posició ori no tingui cap unitat per desplaçar
    public void desplacar(Posicio ori, Posicio fi) {
        if (ori != fi && !mapa[fi.getX()][fi.getY()].teUnitat() && mapa[ori.getX()][ori.getY()].teUnitat()) {

            Unitat aux = mapa[ori.getX()][ori.getY()].getUnitat();

            aux.restaMov(distanciaRecorreguda(ori, fi));

            mapa[fi.getX()][fi.getY()].setUnitat(aux, true); //La coloquem al destí
            mapa[ori.getX()][ori.getY()].eliminaUnitat(); //Eliminem la unitat del origen

            mapa[fi.getX()][fi.getY()].reset();
            mapa[ori.getX()][ori.getY()].reset();

            if (fi.getX() == meta.getX() && fi.getY() == meta.getY()) end = true;
        }
    }

    public Boolean esFinal() {
        return end;
    }

    // CERCA DE LES CASELLES ON ES POT DESPLAÇAR LA UNITAT

    public ArrayList<Posicio> getRang(Posicio p, String s) {
        ArrayList<Posicio> posicions = new ArrayList<Posicio>();
        int x = p.getX();
        int y = p.getY();
        int mov = 0;
        if (s.equals("Atac")) mov = p.getUnitat().getRang();
        else if (s.equals("Moure")) mov = p.getUnitat().getMovAct();
        else if (s.equals("Total")) mov = p.getUnitat().getRang() + p.getUnitat().getMovAct();


        for (int i = 0; i <= mov; i++) {
            if ((y - mov + i) >= 0) {
                posicions.add(mapa[x][y - mov + i]);
                for (int j = 1; j <= i; j++) {
                    if ((x + j) < MAXH) posicions.add(mapa[x + j][y - mov + i]);
                    if ((x - j) >= 0) posicions.add(mapa[x - j][y - mov + i]);
                }
            }
            if ((y + mov - i) < MAXV && i < mov) {
                posicions.add(mapa[x][y + mov - i]);
                for (int j = 1; j <= i; j++) {
                    if ((x + j) < MAXH) posicions.add(mapa[x + j][y + mov - i]);
                    if ((x - j) >= 0) posicions.add(mapa[x - j][y + mov - i]);
                }
            }
        }

        posicions.remove(p);




        return posicions;
    }

    public ArrayList<Posicio> getRang(Posicio p, Integer mov){
        ArrayList<Posicio> posicions = new ArrayList<Posicio>();
        int x = p.getX();
        int y = p.getY();

        for (int i = 0; i <= mov; i++) {
            if ((y - mov + i) >= 0) {
                posicions.add(mapa[x][y - mov + i]);
                for (int j = 1; j <= i; j++) {
                    if ((x + j) < MAXH) posicions.add(mapa[x + j][y - mov + i]);
                    if ((x - j) >= 0) posicions.add(mapa[x - j][y - mov + i]);
                }
            }
            if ((y + mov - i) < MAXV && i < mov) {
                posicions.add(mapa[x][y + mov - i]);
                for (int j = 1; j <= i; j++) {
                    if ((x + j) < MAXH) posicions.add(mapa[x + j][y + mov - i]);
                    if ((x - j) >= 0) posicions.add(mapa[x - j][y + mov - i]);
                }
            }
        }

        return posicions;
    }


    public Boolean ComprovaObjectiu (Posicio o, Posicio p){
        return (mapa[o.getX()][o.getY()].teUnitat() && mapa[o.getX()][o.getY()].getUnitat().Enemiga(p.getUnitat()));

    }

    // METODES DE LECTURA DES DE FITXER

    private void llegirMapa(String f) throws IOException {

        forts = new ArrayList<>();//LLUIS
        BufferedReader br = new BufferedReader(new FileReader(f));
        //Llegim el nom del mapa
        nom = br.readLine().substring(4);

        //Llegim les mides X i Y del mapa
        String[] mida = br.readLine().substring(5).split("x");

        String [] o = br.readLine().substring(9).split("x");
        meta = new Posicio(Integer.parseInt(o[0]), Integer.parseInt(o[1]));

        MAXH = Integer.parseInt(mida[0]);
        MAXV = Integer.parseInt(mida[1]);

        mapa = new Posicio[MAXH][MAXV]; //Reservem memoria
        costosCamins = new Integer[MAXH][MAXV];

        String sCurrentLine;
        Integer j = 0;

        while ((sCurrentLine = br.readLine()) != null) { //Saltem les possibles linies en blanc
            if (!sCurrentLine.isEmpty()) {

                String[] pos = sCurrentLine.split(" "); //Agafem la linia en questió

                for (int i = 0; i < pos.length; i++) {
                    mapa[i][j] = new Posicio(i, j); //Creem la nova posició
                    mapa[i][j].setTerreny(fabricaTerrenys(Integer.parseInt(pos[i]))); //Inserim el terreny determinat a la posició recent creada.
                    if (mapa[i][j].getTerreny().toString().equals("Fortress")) forts.add(mapa[i][j]);   //LLUIS
                    costosCamins[i][j] = mapa[i][j].getTerreny().getRedDespl();
                   // System.out.print(costosCamins[i][j] + " ");
                }
                //System.out.println();
                j++;
            }
        }
        //FINS AQUI LA MATRIU DE PESOS ESTÀ BEN CREADA

        creaGraf();
        System.out.println("Lectura Correcte");
        //LA LLISTA DE VERTEXS AMB LES ADJACENCIES TAMBE ES CREA BE


        br.close();
    }

    private ArrayList<Vertex> creaGraf(){
        adj = new ArrayList<Vertex>();
        for (int i=0; i<MAXH; i++) {
            for (int j = 0; j < MAXV; j++) {
                adj.add(new Vertex(i + " " + j));
            }
        }
        // llista de tots els vertexs
        for (Vertex v1 : adj) {
            String[] s1 = v1.toString().split(" ");
            v1.iniciaAdjacencies();
            for (Vertex v2 : adj) {
                if (v1 != v2) {
                    String[] s2 = v2.toString().split(" ");
                    if (((Integer.parseInt(s2[0]) == Integer.parseInt(s1[0]) - 1 || Integer.parseInt(s2[0]) == Integer.parseInt(s1[0]) + 1) &&
                            Integer.parseInt(s2[1]) == Integer.parseInt(s1[1])) ||

                            ((Integer.parseInt(s2[1]) == Integer.parseInt(s1[1]) + 1 || Integer.parseInt(s2[1]) == Integer.parseInt(s1[1]) - 1) &&
                                    Integer.parseInt(s2[0]) == Integer.parseInt(s1[0]))) {

                        v1.getAdjacencies().add(new Edge(v2, (costosCamins[Integer.parseInt(s2[0])][Integer.parseInt(s2[1])])));
                    }
                }
            }
        }
        return adj;
    }


    public void buscaCamiMinim(Posicio p){

        Vertex ori = getVertex(p.getX(), p.getY());
        iniciarAnteriors();
        busca(ori);

    }

    public List<Vertex> getCamiMin(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.getAnterior()) {
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }

    private void iniciarAnteriors(){
         for (Vertex v : adj){
             v.setAnterior(null);
             v.setDistMin(999);
         }
    }

    public Integer ValorCamiMin(Posicio p) {
/*
        Integer dist = 0;
        //System.out.println("Posicio :" + p);
        List<Vertex> l = getCamiMin(getVertex(p.getX(),p.getY()));
        for (Vertex v : l){
            //System.out.print(v + " distmin es: " + v.getDistMin() + ",  ");
            //dist += v.getDistMin();
            dist = v.getDistMin();      //retrasito poderosito, mai acumular acumulacions
            //System.out.print("[" + v + "] : " + dist + ", ");

        }
        //System.out.println();
       // System.out.println(dist);
        return dist;
*/

        //Vertex desti = getVertex(p.getX(), p.getY());
        return getVertex(p.getX(),p.getY()).getDistMin();
    }

    public void busca(Vertex inici) {
        inici.setDistMin(0);
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(inici);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            for (Edge e : u.getAdjacencies())
            {
                Vertex v = e.getTarget();
                Integer pes = e.getCost();
                Integer acumulat = u.getDistMin() + pes;
                if (acumulat < v.getDistMin()) {
                    vertexQueue.remove(v);
                    v.setDistMin(acumulat);
                    v.setAnterior(u);
                    vertexQueue.add(v);
                }
            }
        }
    }

    public Vertex getVertex(int x, int y) {
        for (Vertex g : adj) {
            String[] str = g.toString().split(" ");
            if (Integer.parseInt(str[0]) == x && Integer.parseInt(str[1]) == y)
                return g;
        }
        return null;
    }

    public void llegirUnitats(String u, Jugador a, Jugador b) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(u));

            String sCurrentLine;
            Integer j = 0;
            Integer jugador = 1;
            Unitat aux;
            while ((sCurrentLine = br.readLine()) != null) { //Saltem les possibles linies en blanc
                if (sCurrentLine.isEmpty()) {
                    j = 0;
                    jugador++;
                }
                else {
                    String[] pos = sCurrentLine.split(" "); //Agafem la linia en questió
                    for (int i = 0; i < pos.length; i++) {
                        aux = fabricaUnitats(Integer.parseInt(pos[i]));
                        if (aux != null) {
                            aux.setPropietari(jugador);

                            if (jugador == 1) a.getExercit().add(aux);
                            else b.getExercit().add(aux);

                            mapa[i][j].setUnitat(aux, true); //Afegim les unitats corresponents a les posicions
                        }
                    }

                    j++;
                }
            }
            System.out.println("Lectura d'unitats correcte");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer distanciaRecorreguda(Posicio a, Posicio b) {
        return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
    }

    public ArrayList<Posicio> getForts() {
        return forts;
    }

    public boolean metaAconseguida() {
        if (mapa[meta.getX()][meta.getY()].teUnitat())
            return (mapa[meta.getX()][meta.getY()].getUnitat().getPropietari() == 2);
        return false;
    }
}
