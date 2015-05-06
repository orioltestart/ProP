/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="barraSuperior"
    private HBox barraSuperior; // Value injected by FXMLLoader

    @FXML // fx:id="contenidorMapa"
    private ScrollPane contenidorMapa; // Value injected by FXMLLoader

    @FXML // fx:id="mapa"
    private GridPane mapa; // Value injected by FXMLLoader

    @FXML // fx:id="barraInferior"
    private HBox barraInferior; // Value injected by FXMLLoader

    @FXML // fx:id="finestra"
    private BorderPane finestra; // Value injected by FXMLLoader

    @FXML // fx:id="barraLateral"
    private VBox barraLateral; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete

    private Posicio m[][];


    void initialize() {
        assert barraSuperior != null : "fx:id=\"barraSuperior\" was not injected: check your FXML file 'sample.fxml'.";
        assert contenidorMapa != null : "fx:id=\"contenidorMapa\" was not injected: check your FXML file 'sample.fxml'.";
        assert mapa != null : "fx:id=\"mapa\" was not injected: check your FXML file 'sample.fxml'.";
        assert barraInferior != null : "fx:id=\"barraInferior\" was not injected: check your FXML file 'sample.fxml'.";
        assert finestra != null : "fx:id=\"finestra\" was not injected: check your FXML file 'sample.fxml'.";
        assert barraLateral != null : "fx:id=\"barraLateral\" was not injected: check your FXML file 'sample.fxml'.";

        final int numCols = 50;
        final int numRows = 50;

        m = new Posicio[50][50];

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                Posicio p = new Posicio(i, j);
                m[i][j] = p;

                p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Posicio aux = (Posicio) mouseEvent.getSource();
                        m[aux.getX()][aux.getY()].setUnitat();
                    }
                });
                p.setTerreny();
                mapa.add(p, i, j);
            }
        }

    }
}
