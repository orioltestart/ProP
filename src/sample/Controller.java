/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.unitats.Unitat;


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

    @FXML
    private VBox barraDesti;


    @FXML // fx:id="finestra"
    private BorderPane finestra; // Value injected by FXMLLoader

    @FXML
    private GridPane m;


    private Mapa mapa;

    @FXML
    private Label text1;

    @FXML
    private VBox posicioActual;

    @FXML
    private Button seg;

    @FXML
    private Button ant;

    @FXML
    private Button btAtacMoure;

    @FXML
    private Text textDesti;

    @FXML
    private Button btGuardar;

    @FXML
    private Button btSortir;

    @FXML
    private HBox barraInforme;

    private Integer index = 0;


    private ArrayList<Posicio> atacables = new ArrayList<Posicio>();

    private ArrayList<Posicio> pintades = new ArrayList<Posicio>();

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


        ant.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!atacables.isEmpty()) {
                    actualitzaContenidor(atacables.get(index), barraDesti);
                    if (index == 0) index = atacables.size() - 1;
                    else index--;
                }
            }
        });

        seg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!atacables.isEmpty()) {
                    actualitzaContenidor(atacables.get(index), barraDesti);
                    if (index == atacables.size() - 1) index = 0;
                    else index++;
                }
            }
        });


        btAtacMoure.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button b = (Button) actionEvent.getTarget();
                eliminaSeleccio();
                if (b.getText().equals("Atac")) {
                    seg.setDisable(true);
                    ant.setDisable(true);
                    b.setText("Moure");
                    textDesti.setText("Posicio Destí");
                } else {
                    seg.setDisable(false);
                    ant.setDisable(false);
                    b.setText("Atac");
                    textDesti.setText("Unitats Atacables");
                }

                pintaRang();
                if (!atacables.isEmpty())
                    actualitzaContenidor(atacables.get(index), barraDesti);
            }
        });

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
                text1.setText(actual.toString());

                if (btAtacMoure.getText().equals("Moure") && dinsRang(actual)) actualitzaContenidor(actual, barraDesti);

                if (actual != seleccionada) actual.pinta(Color.GREEN);
            }
        });

        pos[x][y].setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Posicio aux = (Posicio) mouseEvent.getTarget();

                if (btAtacMoure.getText().equals("Moure")) actualitzaContenidor(null, barraDesti);

                if (!dinsRang(aux) && aux != seleccionada) aux.reset();
                else if (aux != seleccionada) aux.pinta(Color.RED);
            }
        });


        pos[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }


    private void pintaRang() {
        index = 0;
        if (seleccionada.teUnitat()) {
            for (Posicio i : mapa.getRang(seleccionada, btAtacMoure.getText())) {
                i.pinta(Color.RED);
                pintades.add(i);
                if (btAtacMoure.getText().equals("Atac") && i.teUnitat())
                    atacables.add(i);
            }
        }
    }

    private void eliminaSeleccio() {
        for (Posicio i : pintades) i.reset();
        pintades.clear();
        atacables.clear();
    }

    private void actualitzaContenidor(Posicio aux, Pane p) {
        p.getChildren().clear();
        if (aux != null) {
            Posicio t = new Posicio(100);
            t.setTerreny(aux.getTerreny());
            t.setUnitat(aux.getUnitat());

            p.getChildren().add(t);
            if (t.teUnitat()) p.getChildren().add(new Label(t.getUnitat().getAtributs()));
        }
    }

    private Boolean dinsRang(Posicio aux) {
        for (Posicio i : pintades) if (i == aux) return true;
        return false;
    }

}
