package sample.terrenys;

import javafx.scene.image.Image;

/**
 * Created by lluis on 24/04/15.
 */


public class Terreny extends Image {

    private String Tipus; //fortress, plain ...
    private String Nom;     // castle, bridge1, etc
    private Integer Defensa;
    private Integer RedDespl;
    private Boolean Transitable;
    private Boolean Cura;

    final static String terrains[] =
            {"Plain", "Forest", "Mountain", "Fortress",
                    "River", "River5" , "River6", "River7", "River8",
                    "Stone", "Road", "Floor", "BridgeH", "BridgeV", "WallH", "WallV", "Goal"};

    public Terreny(){
        super("sample/Imatges/nothing.png");
        Defensa = 15;
        RedDespl = 0;
    }

    public Terreny (String t, int i, Integer d, Integer r, Boolean tr, Boolean c){
        super("sample/Imatges/" + terrains[i] + ".png");
        Tipus = t;
        Nom = terrains[i];
        Defensa = d;
        RedDespl = r;
        Transitable = tr;
        Cura = c;
    }

    public Boolean getTransitable() {
        return Transitable;
    }

    public Integer getDefensa() {
        return Defensa;
    }

    public Integer getRedDespl() {
        return RedDespl;
    }

    public String getTipus() {
        return Tipus;
    }

    public Boolean getCura() {
        return Cura;
    }

    public String toString(){
        return (Nom);
    }
}
