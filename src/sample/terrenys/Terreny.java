/**
 * @file Terreny.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Terreny todo
 */

package sample.terrenys;

import javafx.scene.image.Image;


public class Terreny extends Image {

    private String Tipus; //fortress, plain, forest ...
    private Integer Id;     // castle, bridge1, River1, etc
    private Integer Defensa;    //bonificacio de defensa
    private Integer RedDespl;   //disminucio de desplaçament
    private Boolean Transitable;
    private Boolean Cura;

    final static String terrains[] =
            {"Plain", "Forest", "Mountain", "Fortress",
                    "River", "River5" , "River6", "River7", "River8",
                    "Stone", "Road", "Floor", "BridgeH", "BridgeV",
                    "Wall", "Throne"};

    //Constructors

    public Terreny(){
        super("sample/Imatges/nothing.png");
        Defensa = 15;
        RedDespl = 0;
    }

    public Terreny (String t, int i, Integer d, Integer r, Boolean tr, Boolean c){
        super("sample/Imatges/" + terrains[i] + ".png");
        Tipus = t;
        Id = i;
        Defensa = d;
        RedDespl = r;
        Transitable = tr;
        Cura = c;
    }





    public Terreny copia() {
        return new Terreny(Tipus, Id, Defensa, RedDespl, Transitable, Cura);
    }

    //Getters

    /**
     @pre --
     @post retorna el valor dels atributs d'aquest terreny com a String
     @return String
     */
    public String getAtributs() {
        String missatge = "";
        missatge += "Defensa: " + Defensa + "\nReduc Despl: " + RedDespl;
        missatge += "\nCura: ";
        if (Cura) missatge += "Si";
        else missatge += "No";
        return missatge;
    }

    /**
     @pre --
     @post retorna el tipus de terreny
     @return String
     */
    public String toString(){
        return (terrains[Id]);
    }

    /**
     @pre --
     @post retorna el valor de Transitable
     @return boolea
     */
    public Boolean getTransitable() {
        return Transitable;
    }

    /**
     @pre --
     @post retorna el valor de bonificacio de defensa
     @return Integer
     */
    public Integer getDefensa() {
        return Defensa;
    }

    /**
     @pre --
     @post retorna el valor de reduccio de desplaçament
     @return Integer
     */
    public Integer getRedDespl() {
        return RedDespl;
    }

    /**
     @pre --
     @post retorna el tipus de terreny
     @return String
     */
    public String getTipus() {
        return Tipus;
    }

    /**
     @pre --
     @post retorna cert si aquest terreny pot recuperar punts de vida perduts; altrament fals
     @return boolea
     */
    public Boolean getCura() {
        return Cura;
    }
}
