package sample;


import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.terrenys.Terreny;
import sample.unitats.Unitat;

/**
 * Created by OriolTestart on 18/4/15.
 */


public class Posicio extends Canvas {
    private Integer a_x;
    private Integer a_y;
    private Unitat unitat;
    private Terreny terreny;
    private Boolean masked = false;

    @Override
    public String toString() {
        String missatge = "";
        missatge += "[" + a_x + "," + a_y + "]";
        if (unitat != null) missatge += " U: " + unitat.toString();
        if (terreny != null) missatge += " T: " + terreny.toString();
        return missatge;
    }

    public Posicio() {
        super(80, 80);
        a_x = -1;
        a_y = -1;
        unitat = null;
        terreny = null;
    }

    public Posicio(Integer x, Integer y) {
        super(80, 80);
        a_x = x;
        a_y = y;
        unitat = null;
        terreny = null;
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

    public void setUnitat(Unitat u) {
        if (unitat != null) throw new IllegalArgumentException("Aquesta Posicio ja tenia una unitat");
        unitat = u;
        unitat.setImatge(1);
        dibuixaUnitat();
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

    public void setMasked() {
        System.out.println("Enmascarant -> " + this);
        dibuixaMascara(Color.RED);
        masked = true;
    }

    public void unMask() {
        dibuixaTerreny();
        dibuixaUnitat();
        masked = false;
    }

    public void setSeleccionat() {
        dibuixaMascara(Color.GREEN);
    }

    public void eliminaSeleccio() {
        dibuixaTerreny();
        dibuixaUnitat();
        if (isMasked()) setMasked();
    }

    private void dibuixaUnitat() {
        if (unitat != null) {
            super.getGraphicsContext2D().drawImage(unitat.getImg(), 0, 0, 80, 80);
            /* BARRA DE VIDA */
            super.getGraphicsContext2D().setFill(Color.BLACK);
            super.getGraphicsContext2D().fillRect(3, 3, 74, 10);
            super.getGraphicsContext2D().setFill(Color.GREENYELLOW);
            super.getGraphicsContext2D().fillRect(4, 4, 0.72 * unitat.getPV(), 8);
            /* GUARDEM EL RESULTAT */
            super.getGraphicsContext2D().save();
        }
    }

    private void dibuixaTerreny() {
        super.getGraphicsContext2D().restore();
        super.getGraphicsContext2D().drawImage(terreny, 0, 0, 80, 80);
        super.getGraphicsContext2D().save();
    }

    private void dibuixaMascara(Color c) {
        super.getGraphicsContext2D().setFill(c);
        super.getGraphicsContext2D().setGlobalAlpha(10);
        super.getGraphicsContext2D().setEffect(new GaussianBlur(100));
        super.getGraphicsContext2D().fillRect(0, 0, 80, 80);
    }

}
