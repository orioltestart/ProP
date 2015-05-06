package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.terrenys.Terreny;
import sample.unitats.Unitat;

import java.util.EventObject;

/**
 * Created by OriolTestart on 18/4/15.
 */


public class Posicio extends Canvas {
    private Integer a_x;
    private Integer a_y;
    private Boolean seleccionada;
    private Unitat unitat;
    private Terreny terreny;

    @Override
    public String toString() {
        return "[" + a_x + "," + a_y + "]: " + unitat.toString() + " " + terreny.toString();
    }

    public Posicio() {
        super(40, 40);
        a_x = -1;
        a_y = -1;
        unitat = null;
        terreny = null;
        seleccionada = null;
    }

    public Posicio(Integer x, Integer y) {
        super(40, 40);
        a_x = x;
        a_y = y;
        unitat = null;
        terreny = null;
        seleccionada = false;
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

    public void setUnitat() {
        /*
        if (unitat != null) throw new IllegalArgumentException("Aquesta Posicio ja tenia una unitat");
        unitat = u;
        */
        super.getGraphicsContext2D().setFill(Color.RED);
        super.getGraphicsContext2D().fillText("U", 20, 20);
    }


    public void setTerreny(Terreny t) {
        terreny = t;
        super.getGraphicsContext2D().drawImage(terreny, 0, 0, 40, 40);
    }

    /*
    public void eliminaUnitat() {
        unitat = null;
        super.getGraphicsContext2D().restore();
        super.getGraphicsContext2D().drawImage(terreny, 0, 0, 40, 40);
    }
    */

    @Override
    public GraphicsContext getGraphicsContext2D() {
        return super.getGraphicsContext2D();
    }
}
