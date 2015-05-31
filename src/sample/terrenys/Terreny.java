/**
 * @file Terreny.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Terreny conte un tipus i pot contenir una unitat
 */

package sample.terrenys;

import javafx.scene.image.Image;

import java.io.InputStream;


public abstract class Terreny {

    private String Tipus; //fortress, plain, forest ...
    private Integer Id;     // castle, bridge1, River1, etc
    private Integer Defensa;    //bonificacio de defensa
    private Integer RedDespl;   //disminucio de desplaçament
    private Boolean Cura;
    private Image img;

    final static String terrains[] =
            {"Plain", "Forest", "Mountain", "Fortress",
                    "River", "River5" , "River6", "River7",
                    "River8", "Stone", "Road", "Floor",
                    "BridgeH", "BridgeV", "Wall", "Throne"};

    //Constructors

    public Terreny (String t, int i, Integer d, Integer r, Boolean c) {
        Tipus = t;
        Id = i;
        Defensa = d;
        RedDespl = r;
        Cura = c;
        InputStream is = Terreny.class.getResourceAsStream("imatges/" + terrains[i] + ".png");
        if (is == null) System.out.println("ES NULL");
        img = new Image(is);
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
     @post retorna la imatge d'aquest Terreny
     @return Image
     */
    public Image getImg(){
        return img;
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
