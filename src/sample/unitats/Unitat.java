package sample.unitats;


import javafx.scene.image.Image;

import java.util.Random;


/**
 * Created by lluis on 17/04/15.
 */
public abstract class Unitat extends Image {
    //constant es final

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
    private String imatge;


    //constructors
    Unitat(){
        super("");
        PV = 100;
        POW = 0;
        DEF = 0;
        MOV = 0;
    }

    Unitat(String t, String c,  Integer atac, Integer defensa, Integer moviment, Integer rang, String bonus){
        super("");
        Random r = new Random();
        PV = 100;
        Tipus = t;
        Classe = c;
        ID = r.nextInt(1000);
        POW = atac + r.nextInt(20);
        DEF = defensa + r.nextInt(10);
        MOV = moviment;
        //pot haver-hi diverses bonificacions
        Bonificacio = bonus.split("-");
        Rang = rang;

    }

    public void mostraPV(){
        System.out.println(PV);
    }

    public void setImatge() {

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

    public Integer calcularAtac(Unitat u, Integer BonusT){

        Integer defensaF = u.getDEF() + BonusT;
        Integer atacF = this.POW ;

        Integer resultat = (atacF - defensaF)* this.AplicarBonificacio(u);

        if (PV<50){
            resultat/=2;
        }

        //System.out.println("ATAC: " + this.POW);
        //System.out.println("DEFENSA: " + defensaF);

        if (resultat < 0){
            resultat = 0;
        }

        return resultat;
    }

    public Integer AplicarBonificacio (Unitat u){
        Integer bonusTotal = 1;

        if (Bonificacio[0].equals(u.Classe) || Bonificacio[1].equals(u.Classe)){
            bonusTotal+=2;
        }

        return bonusTotal;
    }

    public String toString(){
        return (Classe);
    }

}