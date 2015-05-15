/**
 * @file Unitat.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Unitat blablabla
 */

package sample.unitats;

import javafx.scene.image.Image;

import java.util.Random;

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

    //

    //constructors
    Unitat(){
        PV = 100;
        POW = 0;
        DEF = 0;
        MOV = 0;
    }

    Unitat(String t, String c,  Integer atac, Integer defensa, Integer moviment, Integer rang, String bonus){
        //falta el id todo
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

    /**
     * @pre --
     * @post
     */
    public void mostraPV(){
        System.out.println(PV);
    }

    public void setImatge(int i) {
        img = new Image("sample/Imatges/" + Classe + "v" + i + ".png");
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

    public String getAtributs() {
        String missatge = "";
        missatge += "Tipus : " + Tipus + "\nClasse: " + Classe + "\nVida: " + PV;
        missatge += " \nPoder: " + POW + "\nDefensa: " + DEF + "\nMov: " + MOV + "  Rang: " + Rang;
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


    public Integer getDEF() {
        return DEF;
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

    public boolean potContraatacar(Unitat u) {
        //comparar les dues posicions
        //todo
        return true;
    }

    public Integer calcularAtac(Unitat u){

        Integer BonusT = 0; //todo
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

    public Integer getRang() {
        return Rang;
    }
}