package sample;

/**
 * Created by lluis on 24/04/15.
 */
public class Terreny {

    private String NomTipus;
    private Integer Defensa;
    private Integer RedDespl;
    private Boolean Transitable;
    private Boolean Cura;

    public Terreny(){
        Defensa = 0;
        RedDespl = 0;
    }

    public Terreny (String n, Integer d, Integer r, Boolean t, Boolean c){
        NomTipus = n;
        Defensa = d;
        RedDespl = r;
        Transitable = t;
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

    public String getNomTipus() {
        return NomTipus;
    }

    public Boolean getCura() {
        return Cura;
    }

    public String toString(){
        return (NomTipus);
    }
}
