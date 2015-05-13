/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.unitats.Unitat;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

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
    private TabPane tabPane;

    @FXML
    private HBox targetaTerreny;

    @FXML
    private HBox targetaUnitat;

    @FXML
    private HBox targetaTerreny2;

    @FXML
    private HBox targetaUnitat2;

    @FXML
    private HBox targetaAtac;


    @FXML
    private StackPane pilaMapa;

    private Posicio seleccionada;
    private Posicio actual;
    private Posicio desti;

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

        pilaMapa.getChildren().add(new Rectangle(100, 100, Color.BLUE));


    }

    private void assignarHandlers(int x, int y) {
        Posicio[][] pos = mapa.getMapa();

        pos[x][y].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                actual = (Posicio) mouseEvent.getTarget();
                if (actual.isMasked() && actual != seleccionada)
                    actual.setMasked(Color.GREEN);
                text1.setText(actual.toString());
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
                            posicions = mapa.getRangMoviment(seleccionada); //Agafo totes les caselles per desenmascarar
                            for (Posicio i : posicions) {
                                i.unMask(); //Les desenmascaro
                            }
                        }
                    }

                    seleccionada = aux; //Si no en tenia cap de seleccionada poso l'actual
                    seleccionada.setMasked(Color.YELLOW);  //La pinto de groc
                    actualitzaColumnes(seleccionada);
                    if (seleccionada.teUnitat()) { //Si te una unitat
                        posicions = mapa.getRangMoviment(seleccionada); //Agafo totes les caselles per enmascarar
                        for (Posicio i : posicions) i.setMasked(Color.RED); //Les pinto de vermell
                    }
                }
            }
        });
    }

    private void actualitzaColumnes(Posicio aux) {
        //Construim targetaTerreny
        Posicio t = new Posicio(100);
        t.setTerreny(aux.getTerreny().copia());

        if (!targetaTerreny.getChildren().isEmpty()) targetaTerreny.getChildren().clear();
        if (!targetaUnitat.getChildren().isEmpty()) targetaUnitat.getChildren().clear();

        targetaTerreny.getChildren().addAll(t, new Label(t.getTerreny().getAtributs()));

        if (aux.teUnitat()) {
            Posicio u = new Posicio(100);
            u.setUnitat(aux.getUnitat().copia());
            targetaUnitat.getChildren().addAll(u, new Label(u.getUnitat().getAtributs()));
        }
    }
}
