/**
 * @file Posicio.java
 * @author Oriol Testart
 * @brief La classe Posicio todo
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


    @Override
    public String toString() {
        String missatge = "";
        missatge += "[" + a_x + "," + a_y + "]";
        if (unitat != null) missatge += " U: " + unitat.toString();
        if (terreny != null) missatge += " T: " + terreny.toString();
        return missatge;
    }

    public Posicio() {
        super(a_mida, a_mida);
        a_x = -1;
        a_y = -1;
        unitat = null;
        terreny = null;
        midaQuadre = a_mida;
    }

    public Posicio(Integer mida) {
        super(mida, mida);
        a_x = -1;
        a_y = -1;
        unitat = null;
        terreny = new Obstacle(14);
        midaQuadre = mida;
    }

    public Posicio(Integer x, Integer y) {
        super(a_mida, a_mida);
        a_x = x;
        a_y = y;
        unitat = null;
        terreny = null;
        midaQuadre = a_mida;
    }

    public Boolean esValida(Integer midaH, Integer midaV) {
        return (a_x >= 0 && a_x < midaH && a_y >= 0 && a_y < midaV);
    }

    public Integer getX() throws NullPointerException {
        if (a_x != null) return a_x;
        else throw new NullPointerException();
    }

    public Integer getY() throws NullPointerException {
        if (a_y != null) return a_y;
        else throw new NullPointerException();
    }

    public Unitat getUnitat() {
        return unitat;
    }

    public Terreny getTerreny() {
        return terreny;
    }

    public Boolean teUnitat() {
        return unitat != null;
    }

    public void setUnitat(Unitat u, Boolean canviaPos) {
        if (unitat != null) throw new IllegalArgumentException("Aquesta Posicio ja tenia una unitat");
        if (terreny == null) terreny = new Obstacle(14);
        unitat = u;

        if (unitat != null) {
            if (canviaPos) unitat.setPosicio(this);
            dibuixaUnitat();
        }
    }

    public void eliminaUnitat() {
        unitat = null;
        dibuixaTerreny();
    }

    public void setTerreny(Terreny t) {
        terreny = t;
        dibuixaTerreny();
    }

    /*  METODES DE SELECCIO  */

    public void pinta(Color c) {
        reset();
        dibuixaMascara(c);
    }

    public void reset() {
        super.getGraphicsContext2D().restore();
        dibuixaTerreny();
        dibuixaUnitat();
    }

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

    private void dibuixaTerreny() {
        super.getGraphicsContext2D().restore();
        super.getGraphicsContext2D().drawImage(terreny.getImg(), 0, 0, midaQuadre, midaQuadre);
        super.getGraphicsContext2D().save();
    }

    private void dibuixaMascara(Color c) {
        super.getGraphicsContext2D().setFill(c);
        super.getGraphicsContext2D().setEffect(new BoxBlur(7, 7, 2));
        super.getGraphicsContext2D().fillRect(0, 0, midaQuadre, 4);
        super.getGraphicsContext2D().fillRect(0, 0, 4, midaQuadre);
        super.getGraphicsContext2D().fillRect(0, midaQuadre - 4, midaQuadre, midaQuadre - 4);
        super.getGraphicsContext2D().fillRect(midaQuadre - 4, 0, midaQuadre, midaQuadre);
    }

}
