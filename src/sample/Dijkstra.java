package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by lluis on 27/05/15.
 */
public class Dijkstra {

    private Matriu mapa;
    private Matriu solucio;
    private ArrayList<Vertex> verts;
    private List<Vertex> path;

    public Dijkstra(){
        //Dijkstra d = new Dijkstra("laberint.txt", "solucio.txt");

       // d.buscaCamiMinim();

       // d.mostraMapaISolucio();
    }

    public Dijkstra(String fitxerM, String fitxerS) throws IOException {
        mapa = new Matriu(fitxerM);
        solucio = new Matriu(fitxerS);
        verts = mapa.creaGraf();

       // Dijkstra d = new Dijkstra("laberint.txt", "solucio.txt");

        //buscaCamiMinim();

       // mostraMapaISolucio();
    }

    public void buscaCamiMinim(/*Vertex ori, Vertex desti*/){

        Vertex ori = getVertex(0,0);
        Vertex desti = getVertex(15,15);

        //ori = getVertex(ori);
        //desti = getVertex(desti);

        busca(ori);
        path = getCamiMin(desti);

        String c = "1";
        for (Vertex v : path) {
            String [] str = v.toString().split(" ");
            solucio.assignaPosicio(Integer.parseInt(str[0]), Integer.parseInt(str[1]), c);
        }

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
                    v.setValors(acumulat, u);
                    vertexQueue.add(v);
                }
            }
        }
    }


    public List<Vertex> getCamiMin(Vertex target) {
        List<Vertex> p = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.getAnterior()) {
            p.add(vertex);
        }
        Collections.reverse(p);
        return p;
    }

    public Vertex getVertex(int x, int y) {

        for (Vertex g : verts) {
            String[] str = g.toString().split(" ");
            if (Integer.parseInt(str[0]) == x && Integer.parseInt(str[1]) == y)
                return g;
        }
        return new Vertex("");   //todo tractar error
    }

    public void mostraMapaISolucio(){
        System.out.println("\nMapa");
        mapa.mostrar();
        System.out.println("\nSolucio");
        solucio.mostrar();
    }
}
