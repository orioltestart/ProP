package com.company;

import java.util.ArrayList;

/**
 * Created by lluis on 27/05/15.
 */
public class Vertex implements Comparable<Vertex> {

    private String codi;

    private ArrayList<Edge> adjacencies;

    private Integer distMin= 999999999;

    private Vertex anterior;



   public Vertex(){
       adjacencies = new ArrayList<Edge>();
   }


    public Vertex(String n) {
        codi = n;
        adjacencies = new ArrayList<Edge>();
    }

    public void setValors(Integer d, Vertex a){
        distMin = d;
        anterior = a;
    }

    public String toString() {
        return codi;
    }

    public int compareTo(Vertex v) {
        return Integer.compare(distMin, v.distMin);
    }

    public ArrayList<Edge> getAdjacencies() {
        return adjacencies;
    }

    public Vertex getAnterior() {
        return anterior;
    }

    public Integer getDistMin() {
        return distMin;
    }

    public void setDistMin(Integer distMin) {
        this.distMin = distMin;
    }

    public void iniciaAdjacencies() {
        adjacencies = new ArrayList<Edge>();
    }

    public void setAnterior(Vertex anterior) {
        this.anterior = anterior;
    }
}
