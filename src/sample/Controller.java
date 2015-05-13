/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sample.terrenys.Plain;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="contenidorMapa"
    private ScrollPane contenidorMapa; // Value injected by FXMLLoader

    @FXML // fx:id="barraInferior"
    private HBox barraInferior; // Value injected by FXMLLoader

    @FXML // fx:id="finestra"
    private BorderPane finestra; // Value injected by FXMLLoader

    @FXML
    private GridPane m;


    private Mapa mapa;

    @FXML
    private Label text1;

    @FXML
    private Label text2;

    @FXML
    private TabPane tabPane;

    @FXML
    private VBox actualInf;

    private Posicio seleccionada;
    private Posicio actual;


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert contenidorMapa != null : "fx:id=\"contenidorMapa\" was not injected: check your FXML file 'sample.fxml'.";
        assert barraInferior != null : "fx:id=\"barraInferior\" was not injected: check your FXML file 'sample.fxml'.";
        assert finestra != null : "fx:id=\"finestra\" was not injected: check your FXML file 'sample.fxml'.";

        File terreny = new File("src/sample/mapes/mapa5");
        File unitats = new File("src/sample/mapes/unitats51");

        mapa = new Mapa(terreny.getAbsolutePath());
        mapa.llegirUnitats(unitats.getAbsolutePath());


        for (int i = 0; i < mapa.getMidaH(); i++) {
            for (int j = 0; j < mapa.getMidaV(); j++) {
                assignarHandlers(i, j);
                m.add(mapa.getPos(i, j), i, j);
            }
        }

    }

    private void assignarHandlers(int x, int y) {
        Posicio[][] pos = mapa.getMapa();

        pos[x][y].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                actual = (Posicio) mouseEvent.getTarget();
                if (actual.isMasked() && actual != seleccionada)
                    actual.setMasked(Color.GREEN);
                text2.setText(actual.toString());
            }
        });

        pos[x][y].setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Posicio aux = (Posicio) mouseEvent.getTarget();
                if (aux.isMasked() && aux != seleccionada)
                    aux.eliminaSeleccio();

            }
        });


        pos[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Posicio aux = (Posicio) mouseEvent.getTarget();
                ArrayList<Posicio> posicions;

                if (seleccionada != aux) { //Si no selecciono la que ja tinc seleccionada
                    if (seleccionada != null) { //Si ja en tenia una de seleccionada
                        seleccionada.unMask(); //La desenmascaro
                        if (seleccionada.teUnitat()) {
                            //todo Implementacio del moviment aqui
                            posicions = mapa.getRangMoviment(seleccionada); //Agafo totes les caselles per desenmascarar
                            for (Posicio i : posicions) i.unMask(); //Les desenmascaro
                        }
                    }

                    seleccionada = aux; //Si no en tenia cap de seleccionada poso l'actual
                    text1.setText(seleccionada.toString());
                    seleccionada.setMasked(Color.YELLOW);  //La pinto de groc
                    if (seleccionada.teUnitat()) { //Si te una unitat
                        posicions = mapa.getRangMoviment(seleccionada); //Agafo totes les caselles per enmascarar
                        for (Posicio i : posicions) i.setMasked(Color.RED); //Les pinto de vermell
                    }
                }
            }
        });
    }
}
