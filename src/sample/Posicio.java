/**
 * @file Posicio.java
 * @author Oriol Testart
 * @brief La classe Posicio equival a una casella del mapa. Conté un terreny i pot contenir també una unitat
 */

package sample;


import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import sample.terrenys.*;
import sample.unitats.*;

public class Posicio extends Canvas {
    private Integer a_x;
    private Integer a_y;
    private Unitat unitat;
    private Terreny terreny;

    final static Integer a_mida = 80;
    private Integer midaQuadre;


    /**
     @pre cert
     @post Retorna un string amb les caracteristiques de la posicio
     */
    @Override
    public String toString() {
        String missatge = "";
        missatge += "[" + a_x + "," + a_y + "]";
        if (unitat != null) missatge += " U: " + unitat.toString();
        if (terreny != null) missatge += " T: " + terreny.toString();
        return missatge;
    }

    /**
     @pre cert
     @post Construeix una posicio buida
     */
    public Posicio() {
        super(a_mida, a_mida);
        a_x = -1;
        a_y = -1;
        unitat = null;
        terreny = null;
        midaQuadre = a_mida;
    }

    /**
     @pre cert
     @post Construeix una posicio buida de mida 'mida' (mida del llenç)
     */
    public Posicio(Integer mida) {
        super(mida, mida);
        a_x = -1;
        a_y = -1;
        unitat = null;
        terreny = new Obstacle(14);
        midaQuadre = mida;
    }

    /**
     @pre cert
     @post Construeix una posicio buida amb coordenades x, y
     */
    public Posicio(Integer x, Integer y) {
        super(a_mida, a_mida);
        a_x = x;
        a_y = y;
        unitat = null;
        terreny = null;
        midaQuadre = a_mida;
    }

    /**
     @pre cert
     @post retorna cert si la posició està dins les mides 'midaH' i 'midaV'
     @return Boolean
     */
    public Boolean esValida(Integer midaH, Integer midaV) {
        return (a_x >= 0 && a_x < midaH && a_y >= 0 && a_y < midaV);
    }

    /**
     @pre cert
     @post retorna la coordenada X de la posició actual
     @return Integer
     */
    public Integer getX() throws NullPointerException {
        if (a_x != null) return a_x;
        else throw new NullPointerException();
    }

    /**
     @pre cert
     @post retorna la coordenada Y de la posicio actual
     @return Integer
     */
    public Integer getY() throws NullPointerException {
        if (a_y != null) return a_y;
        else throw new NullPointerException();
    }

    /**
     @pre cert
     @post retorna la unitat de la posicio actual
     @return Unitat
     */
    public Unitat getUnitat() {
        return unitat;
    }


    /**
     @pre cert
     @post retorna el terreny de la posicio actual
     @return Terreny
     */
    public Terreny getTerreny() {
        return terreny;
    }

    /**
     @pre cert
     @post retorna cert si te unitat, altrament fals
     @return Boolean
     */
    public Boolean teUnitat() {
        return unitat != null;
    }

    /**
     @pre cert
     @post posa la unitat u a la posicio actual i la dibuixa. En cas de ser cert canviaPos la posició de la unitat també
     canviarà altrament no.
     @return void
     */
    public void setUnitat(Unitat u, Boolean canviaPos) {
        if (unitat != null) throw new IllegalArgumentException("Aquesta Posicio ja tenia una unitat");
        if (terreny == null) terreny = new Obstacle(14);
        unitat = u;

        if (unitat != null) {
            if (canviaPos) unitat.setPosicio(this);
            dibuixaUnitat();
        }
    }

    /**
     @pre cert
     @post elimina la unitat de la posicio actual, si no en tenia es quead igual i dibuixa el terreny de nou
     @return void
     */
    public void eliminaUnitat() {
        unitat = null;
        dibuixaTerreny();
    }


    /**
     @pre cert
     @post assigna el terreny 't' com a terreny de la posicio actual i el dibuixa
     @return void
     */
    public void setTerreny(Terreny t) {
        terreny = t;
        dibuixaTerreny();
    }

    /*  METODES DE SELECCIO  */

    /**
     @pre cert
     @post dibuixa un requadre de color 'c' a la posicio actual despres de resetejar-la
     @return void
     @param c és un color vàlid dins de la classe Color de JavaFX
     */
    public void pinta(Color c) {
        reset();
        dibuixaMascara(c);
    }

    /**
     @pre cert
     @post torna el llenç a la darrera posicio guardada i hi dibuixa a sobre el terreny i la unitat (si en te)
     @return void
     */
    public void reset() {
        super.getGraphicsContext2D().restore();
        dibuixaTerreny();
        dibuixaUnitat();
    }
    /**
     @pre cert
     @post reinicia el llenç i hi pinta a sobre la imatge de la unitat de la posicio actual així com una barra de vida.
     Guarda el llenç (important pel restore)
     @return void
     */
    private void dibuixaUnitat() {
        if (unitat != null) {
            super.getGraphicsContext2D().drawImage(unitat.getImg(), 0, 0, midaQuadre, midaQuadre);
            /* BARRA DE VIDA */
            super.getGraphicsContext2D().setFill(Color.BLACK);
            super.getGraphicsContext2D().fillRect(3, 3, midaQuadre - 6, midaQuadre / 8);
            super.getGraphicsContext2D().setFill(Color.GREENYELLOW);
            super.getGraphicsContext2D().fillRect(4, 4, (midaQuadre - 8) * unitat.getPV() / 100, midaQuadre / 10);
            /* GUARDEM EL RESULTAT */
            super.getGraphicsContext2D().save();
        }
    }

    /**
     @pre cert
     @post reinicia el llenç i hi pinta a sobre la imatge del terreny de la posicio actual. Guarda el llenç (important
     pel restore)
     @return void
     */
    private void dibuixaTerreny() {
        super.getGraphicsContext2D().restore();
        super.getGraphicsContext2D().drawImage(terreny.getImg(), 0, 0, midaQuadre, midaQuadre);
        super.getGraphicsContext2D().save();
    }

    /**
     @pre cert
     @post pinta un requadre de color 'c' a la posicio actual.
     @return void
     @param c és un color vàlid dins de la classe Color de JavaFX
     */
    private void dibuixaMascara(Color c) {
        super.getGraphicsContext2D().setFill(c);
        super.getGraphicsContext2D().setEffect(new BoxBlur(7, 7, 2));
        super.getGraphicsContext2D().fillRect(0, 0, midaQuadre, 4);
        super.getGraphicsContext2D().fillRect(0, 0, 4, midaQuadre);
        super.getGraphicsContext2D().fillRect(0, midaQuadre - 4, midaQuadre, midaQuadre - 4);
        super.getGraphicsContext2D().fillRect(midaQuadre - 4, 0, midaQuadre, midaQuadre);
    }

}
