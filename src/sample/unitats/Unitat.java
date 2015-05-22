/**
 * @file Unitat.java
 * @author Lluís Ramon Armengol Xandri
 * @brief La classe Unitat todo
 */

package sample.unitats;

import javafx.scene.image.Image;
import sample.Posicio;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;

public abstract class Unitat {

    //atributs
    private static final AtomicInteger seq = new AtomicInteger(1);
    private int ID;  //unic per cada unitat
    private String Tipus;   //INF, CAV, RANG.
    private String Classe;  //Halberdier, Knight, Marksman, Bowknight, Paladin o Wyvernknight
    private Integer PV;     //Punts de vida; 0<PV<=100
    private Integer POW;    //Punts de poder
    private Integer DEF;    //Punts de defensa
    private Integer movActual;    //nombre de quadres que es pot moure tenint en compte el terreny. movActual<=movTotal
    private Integer movTotal;    //nombre de quadres que es pot moure com a màxim
    private String [] Bonificacio;  //Classes a les quals es té superioritat
    private Integer Rang;   //distancia (en quadres) que pot atacar la unitat
    private Image img;
    private Integer Propietari = 1; // 1 (blau) o 2(vermell)
    private Posicio PosAct;         //Posicio en la que es troba actualment
    private Boolean ready;          //controlador de si ja ha actuat o no

    //

    //constructors
    Unitat(){
        PV = 100;
        POW = 0;
        DEF = 0;
        movTotal = 0;
        movActual = 0;
    }

    Unitat(String t, String c,  Integer atac, Integer defensa, Integer moviment, Integer rang, String bonus){
        Random r = new Random();
        ID = seq.getAndIncrement();
        PV = 100;
        Tipus = t;
        Classe = c;
        //els atributs varien
        POW = atac + r.nextInt(30);
        DEF = defensa + r.nextInt(10);
        movTotal = moviment;
        movActual = movTotal;
        //pot haver-hi diverses bonificacions
        Bonificacio = bonus.split("-");
        Rang = rang;
        img = null;

    }


    //METODES DE LA CLASSE

    /**
     @pre --
     @post mostra el valor dels atributs d'aquesta unitat
     @return void
     */
    public void Mostrar(){
        System.out.println("ID: "+ID);
        System.out.println("Tipus: "+Tipus);
        System.out.println("Classe: "+Classe);
        System.out.println("PV: "+PV);
        System.out.println("POW: " + POW);
        System.out.println("DEF: "+DEF);
        System.out.println("movTotal: " + movTotal);
        System.out.println("Rang: "+Rang);
        System.out.println("Avantatge contra:");

        for (String i : Bonificacio){
            System.out.println("-"+i);
        }
        System.out.println();
    }

    /**
     @pre dany >=0
     @post aquesta unitat perd 'dany' punts de vida (PV>=0)
     @return void
     @param dany és un enter que conté el valor que es restarà a PV
     */
    public void reduirPV( Integer dany){
        PV-=dany;

        if (PV <= 0){
            PV = 0;
        }
    }

    /**
     @pre aquesta unitat està sobre una Posicio amb terreny Fortress
     @post aquesta unitat recupera 20 punts de vida, sense passar-se del limit
     @return void
     */
    public void recuperarPV () {
        PV+=20;
        if (PV > 100){
            PV=100;
        }
    }

    /**
     @pre --
     @post retorna cert si els propietaris d'aquesta unitat i la  unitat u són diferents; altrament fals
     @return boolea
     @param u és una unitat
     */
    public boolean potAtacar(Unitat u) {
        Posicio r = new Posicio(abs(PosAct.getX()-u.PosAct.getX()), abs(PosAct.getY()-u.PosAct.getY()));
        if (r.getX() <= Rang && r.getY() <= Rang) return true;
        return false;
    }

    /**
     @pre aquesta unitat i la unitat u pertanyen a diferents jugadors
     @post retorna la quantitat de dany que infligirà aquesta unitat contra la unitat u
     @return Integer
     @param u és la unitat agredida
     */
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

    /**
     @pre aquesta unitat i la unitat u pertanyen a diferents jugadors
     @post retorna la bonificacio d'atac d'aquesta unitat contr la unitat u
     @return Integer
     @param u és la unitat agredida
     */
    public Integer AplicarBonificacio (Unitat u){
        Integer bonusTotal = 1;
        if (Bonificacio[0].equals(u.Classe) || Bonificacio[1].equals(u.Classe)){
            bonusTotal*=3;
        }
        return bonusTotal;
    }

    /**
    @pre --
    @post resta n a moviment actual
    @return void
    @param n és un enter >=0
    */
    public void restaMov(Integer n) {
        movActual -= n;
        if (movActual<0) movActual=0;
    }

    /**
     @pre --
     @post retorna cert si aquesta unitat i la unitat u pertanyen a Jugadors diferents; altrament fals
     @return boolea
     @param u es una Unitat
     */
    public boolean Enemiga (Unitat u) {
        return !Propietari.equals(u.Propietari);
    }








    /**
     @pre --
     @post retorna el valor dels atributs d'aquesta unitat en forma d'String
     @return String
     */
    public String getAtributs() {
        String missatge = "";
        missatge += "Tipus : " + Tipus + "\nClasse: " + Classe + "\nVida: " + PV;
        missatge += " | Poder: " + POW + "\nDefensa: " + DEF + "\nMov: " + movTotal + " |  Rang: " + Rang;
        return missatge;
    }

    /**
     @pre --
     @post retorna aquesta com unitat com a String
     @return String
     */
    public String toString(){
        return ("ID: " + ID + " Classe: " + Classe + " Propietari: " + Propietari);
    }

    /**
     @pre --
     @post retorna la posicio en la que es troba aquesta unitat
     @return Posició
     */
    public Posicio getPosAct() { return PosAct; }

    /**
     @pre --
     @post retorna el valor de Punts de Vida d'aquesta unitat
     @return Integer
     */
    public Integer getPV() {
        return PV;
    }

    /**
     @pre --
     @post retorna el valor de Defensa d'aquesta unitat
     @return Integer
     */
    public Integer getDEF() {
        return DEF;
    }

    /**
    @pre --
    @post retorna el valor de Rang d'aquesta unitat
    @return Integer
    */
    public Integer getRang() {
        return Rang;
    }

    /**
     @pre --
     @post retorna la imatge d'aquesta unitat
     @return Imatge
     */
    public Image getImg() {
        return img;
    }

    /**
     @pre --
     @post retorna el valor del Moviment actual d'aquesta unitat
     @return Integer
     */
    public Integer getMovAct() {
        return movActual;
    }

    /**
     @pre --
     @post retorna el valor màxim de moviment d'aquesta unitat
     @return Integer
     */
    public Integer getMovTot() {
        return movTotal;
    }

    /**
     @pre --
     @post assigna fals a ready (descans)
     @return void
     */
    public void repos(){
        ready = false;
    }

    /**
     @pre --
     @post assigna l'atribut ready a cert (apunt)
     @return void
     */
    public void setReady (){
        ready = true;
    }

    /**
     @pre --
     @post assigna n a la posicio actual
     @return void
     @param n es una Posicio del mapa
     */
    public void setPosicio(Posicio n) {
        PosAct = n;
    }

    /**
     @pre --
     @post assigna a la unitat el seu propietari (1 o 2), i carrega la imatge
     @return void
     @param jugador és un enter que representa el propietari
     */
    public void setPropietari(Integer jugador) {
        Propietari = jugador;
        img = new Image("sample/Imatges/" + Classe + "v" + Propietari + ".png");
    }
}