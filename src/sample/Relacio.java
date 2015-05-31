/**
 * @file Relacio.java
 * @author Lluis Ramon Armengol
 * @brief La classe Relacio equival a el cami entre un Vertex i un altre.
 */

package sample;


public class Relacio {

    private Vertex vert;    //cap a vert

    private Integer cost;


    /**
     @pre --
     @post crea una relacio amb vert v i cost c
     */
    public Relacio(Vertex v, Integer c) {
        vert = v; cost = c; }

    /**
     @pre --
     @post retorna la Relacio com a String
     @return String
     */
    public String toString(){
        return vert.toString() + " Dist: " + cost;
    }

    /**
     @pre --
     @post retorna el vertex que va la Relacio
     @return Vertex
     */
    public Vertex getVert() {
        return vert;
    }

    /**
     @pre --
     @post retorna el cost de la Relacio
     @return Integer
     */
    public Integer getCost() {
        return cost;
    }




}
