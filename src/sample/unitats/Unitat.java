/**
 * @file Unitat.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Unitat blablabla
 */

package sample.unitats;

import javafx.scene.image.Image;
import sample.Posicio;

import java.util.Random;

import static java.lang.Math.abs;

public abstract class Unitat {

    //atributs
    private Integer ID;
    private String Tipus;   //INF, CAV, RANG.
    private String Classe;  //piquer, arquer, cavaller...
    private Integer PV;
    private Integer POW;
    private Integer DEF;
    private Integer MOV;    //nombre de quadres que es pot moure
    private String [] Bonificacio;
    private Integer Rang;   //distancia (en quadres) que pot atacar la unitat
    private Image img;
    private Integer Propietari;
    private Posicio PosAct;

    //

    //constructors
    Unitat(){
        PV = 100;
        POW = 0;
        DEF = 0;
        MOV = 0;
    }

    Unitat(String t, String c,  Integer atac, Integer defensa, Integer moviment, Integer rang, String bonus){
        Random r = new Random();
        PV = 100;
        Tipus = t;
        Classe = c;
        //els atributs varien
        POW = atac + r.nextInt(30);
        DEF = defensa + r.nextInt(10);
        MOV = moviment;
        //pot haver-hi diverses bonificacions
        Bonificacio = bonus.split("-");
        Rang = rang;

    }

    public void AssignaValorsPersonals (Posicio ini, Integer i){
        Propietari = i;
        setImatge(i);
        SetPosicio(ini);
        String cadena = "PosAct.getX() + PosAct.getY()";
        ID = Integer.parseInt(cadena);
    }

    public String getAtributs() {
        String missatge = "";
        missatge += "Tipus : " + Tipus + " Classe: " + Classe + "\nVida: " + PV;
        missatge += " Poder: " + POW + "\nDefensa: " + DEF + "\nMov: " + MOV + "  Rang: " + Rang;
        return missatge;
    }

    public void Mostrar(){
        System.out.println("ID: "+ID);
        System.out.println("Tipus: "+Tipus);
        System.out.println("Classe: "+Classe);
        System.out.println("PV: "+PV);
        System.out.println("POW: " + POW);
        System.out.println("DEF: "+DEF);
        System.out.println("MOV: "+MOV);
        System.out.println("Rang: "+Rang);
        System.out.println("Avantatge contra:");

        for (String i : Bonificacio){
            System.out.println("-"+i);
        }
        System.out.println();
    }

    public void reduirPV( Integer dany){
        PV-=dany;

        if (PV <= 0){
            PV = 0;
        }
    }

    public void recuperarPV () {
        PV+=20;
        if (PV > 100){
            PV=100;
        }
    }

    public boolean potAtacar(Unitat u) {
        Posicio r = new Posicio(abs(PosAct.getX()-u.PosAct.getX()), abs(PosAct.getY()-u.PosAct.getY()));
        if (r.getX() <= Rang && r.getY() <= Rang) return true;
        return false;
    }

    public Integer calcularAtac(Unitat u){

        Integer BonusT = PosAct.getTerreny().getDefensa();
        Integer defensaF = u.getDEF() + BonusT;
        Integer atacF = this.POW ;

        Integer resultat = (atacF - defensaF)* this.AplicarBonificacio(u);

        if (PV<50){
            resultat/=2;
        }

        if (resultat < 0){
            resultat = 0;
        }

        return resultat;
    }

    public Integer AplicarBonificacio (Unitat u){
        Integer bonusTotal = 1;

        if (Bonificacio[0].equals(u.Classe) || Bonificacio[1].equals(u.Classe)){
            bonusTotal*=3;
        }

        return bonusTotal;
    }

    public String toString(){
        return (Classe);
    }

    public Unitat copia() {
        Unitat res;
        if (Classe.equals("Bowknight")) res = new Bowknight();
        else if (Classe.equals("Halberder")) res = new Halberdier();
        else if (Classe.equals("Knight")) res = new Knight();
        else if (Classe.equals("Marksman")) res = new Marksman();
        else if (Classe.equals("Paladin")) res = new Paladin();
        else res = new Wyvernknight();

        res.PV = PV;
        return res;
    }


    //Getter

    public Integer getRang() {
        return Rang;
    }

    public Image getImg() {
        return img;
    }

    public Integer getMOV() {
        return MOV;
    }

    public Integer getPV() {
        return PV;
    }

    public Integer getDEF() {
        return DEF;
    }

    public Posicio getPosAct() { return PosAct; }

    public boolean Enemiga (Unitat u) {
        return !Propietari.equals(u.Propietari);
    }

    //Setter

    public void setImatge(int i) {
        img = new Image("sample/Imatges/" + Classe + "v" + i + ".png");
    }

    public void SetPosicio (Posicio n){
        PosAct = n;
    }
}