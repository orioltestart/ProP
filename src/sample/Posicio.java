package sample;


import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import sample.terrenys.*;
import sample.unitats.*;

/**
 * Created by OriolTestart on 18/4/15.
 */

public class Posicio extends Canvas {
    private Integer a_x;
    private Integer a_y;
    private Unitat unitat;
    private Terreny terreny;

    private Boolean masked = false;
    final static Integer a_mida = 40;
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
        terreny = new Terreny();
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

    public Posicio(Posicio p) {
        this(p.getX(), p.getY());

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

    public void setUnitat(Unitat u) {
        if (unitat != null) throw new IllegalArgumentException("Aquesta Posicio ja tenia una unitat");
        if (terreny == null) terreny = new Terreny();
        unitat = u;
        if (unitat != null) {
            unitat.setImatge(1);
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

    public Boolean isMasked() {
        return masked;
    }

    public void setMasked(Color c) {
        dibuixaMascara(c);
        masked = true;
    }

    public void unMask() {
        dibuixaTerreny();
        dibuixaUnitat();
        masked = false;
    }

    public void eliminaSeleccio() {
        dibuixaTerreny();
        dibuixaUnitat();
        if (isMasked()) setMasked(Color.RED);
    }

    public void actualitzar() {
        dibuixaTerreny();
        dibuixaUnitat();
        if (isMasked()) dibuixaMascara(Color.RED);
    }

    private void dibuixaUnitat() {
        if (unitat != null) {
            super.getGraphicsContext2D().drawImage(unitat.getImg(), 0, 0, midaQuadre, midaQuadre);
            /* BARRA DE VIDA */
            super.getGraphicsContext2D().setFill(Color.BLACK);
            super.getGraphicsContext2D().fillRect(3, 3, midaQuadre - 6, midaQuadre / 8);
            super.getGraphicsContext2D().setFill(Color.GREENYELLOW);
            super.getGraphicsContext2D().fillRect(4, 4, midaQuadre - 8 * unitat.getPV() / 100, midaQuadre / 10);
            /* GUARDEM EL RESULTAT */
            super.getGraphicsContext2D().save();
        }
    }

    private void dibuixaTerreny() {
        super.getGraphicsContext2D().restore();
        super.getGraphicsContext2D().drawImage(terreny, 0, 0, midaQuadre, midaQuadre);
        super.getGraphicsContext2D().save();
    }

    private void dibuixaMascara(Color c) {
        super.getGraphicsContext2D().setFill(c);
        //todo revisar
        super.getGraphicsContext2D().setEffect(new BoxBlur(7, 7, 2));
        super.getGraphicsContext2D().fillRect(0, 0, midaQuadre, midaQuadre * 0.0625);
        super.getGraphicsContext2D().fillRect(0, 0, midaQuadre * 0.0625, midaQuadre);
        super.getGraphicsContext2D().fillRect(0, midaQuadre, midaQuadre - midaQuadre * 0.0625, midaQuadre * 0.0625);
        super.getGraphicsContext2D().fillRect(midaQuadre - midaQuadre * 0.0625, 0, midaQuadre * 0.0625, midaQuadre);

    }

}
