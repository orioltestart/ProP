/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="contenidorMapa"
    private ScrollPane contenidorMapa; // Value injected by FXMLLoader

    @FXML // fx:id="esquema"
    private GridPane esquema; // Value injected by FXMLLoader

    @FXML // fx:id="mapa"
    private GridPane mapa; // Value injected by FXMLLoader

    @FXML // fx:id="barraInferior"
    private HBox barraInferior; // Value injected by FXMLLoader

    @FXML // fx:id="escenari"
    private AnchorPane escenari; // Value injected by FXMLLoader

    @FXML // fx:id="barraLateral"
    private VBox barraLateral; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert contenidorMapa != null : "fx:id=\"contenidorMapa\" was not injected: check your FXML file 'sample.fxml'.";
        assert esquema != null : "fx:id=\"esquema\" was not injected: check your FXML file 'sample.fxml'.";
        assert mapa != null : "fx:id=\"mapa\" was not injected: check your FXML file 'sample.fxml'.";
        assert barraInferior != null : "fx:id=\"barraInferior\" was not injected: check your FXML file 'sample.fxml'.";
        assert escenari != null : "fx:id=\"escenari\" was not injected: check your FXML file 'sample.fxml'.";
        assert barraLateral != null : "fx:id=\"barraLateral\" was not injected: check your FXML file 'sample.fxml'.";

        final int numCols = 50;
        final int numRows = 50;
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                Canvas c = new Canvas(40, 40);
                GraphicsContext gc = c.getGraphicsContext2D();
                gc.setFill(Color.BLUE);
                gc.fillRect(1, 1, 39, 39);
                mapa.add(c, i, j);
            }
        }

    }
}
