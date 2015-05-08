package sample;



import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
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
        return "[" + a_x + "," + a_y + "]: " + unitat.toString() + " " + terreny.toString();
    }

    public Posicio() {
        super(40, 40);
        a_x = -1;
        a_y = -1;
        unitat = null;
        terreny = null;
    }

    public Posicio(Integer x, Integer y) {
        super(40, 40);
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
        super.getGraphicsContext2D().drawImage(unitat.getImg(), 0, 0, 40, 40);
        super.getGraphicsContext2D().save();
    }

    public void eliminaUnitat() {
        unitat = null;
        super.getGraphicsContext2D().restore();
        super.getGraphicsContext2D().drawImage(terreny, 0, 0, 40, 40);
        super.getGraphicsContext2D().save();
    }

    public void setTerreny(Terreny t) {
        terreny = t;
        super.getGraphicsContext2D().drawImage(terreny, 0, 0, 40, 40);
        super.getGraphicsContext2D().save();
    }

    public Boolean isMasked() {
        return masked;
    }

    public void setMasked() {
        super.getGraphicsContext2D().save();
        super.getGraphicsContext2D().setEffect(new BoxBlur(0, 80, 1));
        super.getGraphicsContext2D().setFill(Color.RED);
        super.getGraphicsContext2D().fillRect(0, 0, 40, 40);
        masked = true;
    }

    public void unMask() {
        super.getGraphicsContext2D().restore();
        super.getGraphicsContext2D().drawImage(terreny, 0, 0, 40, 40);
        if (unitat != null) super.getGraphicsContext2D().drawImage(unitat.getImg(), 0, 0, 40, 40);
        masked = false;
    }


}
