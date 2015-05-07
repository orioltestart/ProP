/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.terrenys.River;
import sample.terrenys.Terreny;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="barraSuperior"
    private HBox barraSuperior; // Value injected by FXMLLoader

    @FXML // fx:id="contenidorMapa"
    private ScrollPane contenidorMapa; // Value injected by FXMLLoader

    @FXML // fx:id="barraInferior"
    private HBox barraInferior; // Value injected by FXMLLoader

    @FXML // fx:id="finestra"
    private BorderPane finestra; // Value injected by FXMLLoader

    @FXML // fx:id="barraLateral"
    private VBox barraLateral; // Value injected by FXMLLoader

    @FXML
    private GridPane m;

    @FXML
    private TabPane tp;

    private Mapa mapa;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert barraSuperior != null : "fx:id=\"barraSuperior\" was not injected: check your FXML file 'sample.fxml'.";
        assert contenidorMapa != null : "fx:id=\"contenidorMapa\" was not injected: check your FXML file 'sample.fxml'.";
        assert barraInferior != null : "fx:id=\"barraInferior\" was not injected: check your FXML file 'sample.fxml'.";
        assert finestra != null : "fx:id=\"finestra\" was not injected: check your FXML file 'sample.fxml'.";
        assert barraLateral != null : "fx:id=\"barraLateral\" was not injected: check your FXML file 'sample.fxml'.";


        File f = new File("/Users/OriolTestart/IdeaProjects/Mapa/src/sample/mapa3");

        mapa = new Mapa(f);

        Terreny aux = new River();

        for (int i = 0; i < mapa.getMidaH(); i++) {
            for (int j = 0; j < mapa.getMidaV(); j++) {
                m.add(mapa.getPos(i, j), i, j);
            }
        }

    }
}
