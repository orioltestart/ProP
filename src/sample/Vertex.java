/**
 * @file Relacio.java
 * @author Lluis Ramon Armengol
 * @brief La classe Vertex es un node del graf que conté la distancia minima per arribar-hi, el vertex pel que venir, i les Relacions a altres Vertexs .
 */

package sample;

import java.util.ArrayList;

/**
 * Created by lluis on 27/05/15.
 */
public class Vertex implements Comparable<Vertex> {

    private String codi;

    private ArrayList<Relacio> adjacencies;// 2,3 o 4 elements

    private Integer distMin = 999;  //valor per arribar a aquest vertex

    private Vertex anterior;    //vertex pel qual s'arriba


    /**
     @pre --
     @post crea un Vertex
     */
   public Vertex(){
       adjacencies = new ArrayList<Relacio>();
   }

    /**
     @pre --
     @post crea un Vertex amb codi n
     */
    public Vertex(String n) {
        codi = n;
        adjacencies = new ArrayList<Relacio>();
    }

    /**
     @pre --
     @post assigna d al camp distMin
     @return void
     @param d es un enter >0
     */
    public void setDistMin(Integer d){
        distMin = d;
    }

    /**
     @pre --
     @post assigna a al camp anterior
     @return void
     @param a es un vertex
     */
    public void setAnterior(Vertex a){
        anterior = a;
    }

    /**
     @pre --
     @post retorna el Vertex com a string
     @return String
     */
    public String toString() {
        return codi;
    }

    /**
     @pre --
     @post redefineix el metode comparTo comparant les distancies minimes s'aquest vertex i v
     @return Int
     @param v es un vertex
     */
    public int compareTo(Vertex v) {
        return Integer.compare(distMin, v.distMin);
    }

    /**
     @pre --
     @post retorna les adjacencies d'aquest Vertex
     @return Integer
     */
    public ArrayList<Relacio> getAdjacencies() {
        return adjacencies;
    }

    /**
     @pre --
     @post retorna el vertex pel qual s'arriba a aquest
     @return Integer
     */
    public Vertex getAnterior() {
        return anterior;
    }

    /**
     @pre --
     @post retorna la distancia minima
     @return Integer
     */
    public Integer getDistMin() {
        return distMin;
    }

    /**
     @pre --
     @post inicialtza la llista d'adjacencies
     @return Integer
     */
    public void iniciaAdjacencies() {
        adjacencies = new ArrayList<Relacio>();
    }
}
