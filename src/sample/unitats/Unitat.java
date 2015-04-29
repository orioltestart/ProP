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
    private String Tipus;   //INF, CAV, ARQ.
    private String Classe;  //piquer, arquer, cavaller...
    private Integer PV;
    private Integer POW;
    private Integer DEF;
    private Integer MOV;
    private String[] Bonificacio;


    //constructors
    Unitat() {
        super("");
        PV = 100;
        POW = 0;
        DEF = 0;
        MOV = 0;
    }

    Unitat(String c, String t, Integer atac, Integer defensa, Integer moviment, String bonus) {
        super("");
        Random r = new Random();
        PV = 100;
        Tipus = t;
        Classe = c;
        ID = r.nextInt(1000);
        POW = atac + r.nextInt(20);
        DEF = defensa + r.nextInt(20);
        //random de 3 vol dir que agafa 0, 1 ,2.
        MOV = moviment;
        //pot haver-hi diverses bonificacions
        Bonificacio = bonus.split("-");

    }

    public void setBonificacio(String[] bonificacio) {
        Bonificacio = bonificacio;
    }

    public void Mostrar() {
        System.out.println("ID: " + ID);
        System.out.println("Tipus: " + Tipus);
        System.out.println("Classe: " + Classe);
        System.out.println("PV: " + PV);
        System.out.println("POW: " + POW);
        System.out.println("DEF: " + DEF);
        System.out.println("MOV: " + MOV);
        System.out.println("Avantatge contra:");

        for (String i : Bonificacio) {
            System.out.println("-" + i);
        }
        System.out.println();
    }


    public Integer getDEF() {
        return DEF;
    }

    public void reduirPV(Integer dany) {
        PV -= dany;
        if (PV <= 0) {
            PV = 0;
        }
    }

    public void recuperarPV() {
        PV += 20;
        if (PV > 100) {
            PV = 100;
        }
    }

    public Integer calcularAtac(Unitat u, Integer BonusT) {
        Integer defensaF = u.getDEF() + BonusT;
        Integer atacF = this.POW;
        Integer resultat = (atacF - defensaF) * this.AplicarBonificacio(u) * PV / 100;

        //System.out.println("ATAC: " + this.POW);
        //System.out.println("DEFENSA: " + defensaF);

        if (resultat < 0) {
            resultat = 0;
        }
        //todo
        return resultat;
    }

    public Integer AplicarBonificacio(Unitat u) {
        Integer bonusTotal = 1;
        for (String i : Bonificacio) {
            if (i.equals(u.Tipus)) {
                bonusTotal += 2;
            }
        }
        return bonusTotal;
    }

}