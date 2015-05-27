/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private VBox barraCursor;

    @FXML
    private VBox barraAccio;


    @FXML // fx:id="finestra"
    private BorderPane finestra; // Value injected by FXMLLoader

    @FXML
    private GridPane m;


    private Mapa mapa;

    @FXML
    private Label tipusInforme;

    @FXML
    private VBox posicioActual;

    @FXML
    private Button seg;

    @FXML
    private Button ant;

    @FXML
    private Button btAtacMoure;

    @FXML
    private Text textCursor;

    @FXML
    private Button btGuardar;

    @FXML
    private Button btSortir;

    @FXML
    private Button ferMovimentAtac;

    @FXML
    private VBox barraDesti;

    @FXML
    private VBox barraOrigen;

    private Integer index = 0;


    private ArrayList<Posicio> atacables = new ArrayList<Posicio>();

    private ArrayList<Posicio> pintades = new ArrayList<Posicio>();

    private Posicio seleccionada;
    private Posicio cursor;
    private Posicio actual;

    Jugador jugador1;
    Jugador jugador2;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws InterruptedException {
        assert contenidorMapa != null : "fx:id=\"contenidorMapa\" was not injected: check your FXML file 'sample.fxml'.";
        assert barraInferior != null : "fx:id=\"barraInferior\" was not injected: check your FXML file 'sample.fxml'.";
        assert finestra != null : "fx:id=\"finestra\" was not injected: check your FXML file 'sample.fxml'.";

        File terreny = new File("src/sample/mapes/mapa1");
        File unitats = new File("src/sample/mapes/unitats1");


        // BOTONS!!!
        assignarBotons();
        //Final de BOTONS

        jugador1 = new Jugador(1);
        jugador2 = new Jugador(2);

        mapa = new Mapa(terreny.getAbsolutePath());
        mapa.llegirUnitats(unitats.getAbsolutePath(), jugador1, jugador2);


        for (int i = 0; i < mapa.getMidaH(); i++) {
            for (int j = 0; j < mapa.getMidaV(); j++) {
                assignarHandlers(i, j);
                m.add(mapa.getPos(i, j), i, j);
            }
        }

        mostraExercitJugadors();

    }

    private void assignarBotons() {
        ant.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!atacables.isEmpty()) {
                    if (index == 0) index = atacables.size() - 1;
                    else index--;
                    if (actual != null) actual.pinta(Color.RED);
                    actual = atacables.get(index);
                    actual.pinta(Color.YELLOW);
                    actualitzaContenidor(actual, barraDesti);
                }
            }
        });

        seg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!atacables.isEmpty()) {
                    if (index == atacables.size() - 1) index = 0;
                    else index++;
                    if (actual != null) actual.pinta(Color.RED);
                    actual = atacables.get(index);
                    actual.pinta(Color.YELLOW);
                    actualitzaContenidor(actual, barraDesti);
                }
            }
        });


        ferMovimentAtac.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (btAtacMoure.getText().equals("Moure")) {
                    if (actual != null && !actual.teUnitat() && dinsRang(actual)) {
                        mapa.desplacar(seleccionada, actual);
                        actual.reset();
                        seleccionada = actual;
                        actual = null;
                        actualitzaContenidor(null, barraDesti);
                        actualitzaContenidor(seleccionada, posicioActual);
                        actualitzaContenidor(seleccionada, barraOrigen);
                        eliminaSeleccio();
                        pintaRang();
                        seleccionada.pinta(Color.BLUE);
                    }
                } else {
                    if (seleccionada != null && actual != null && seleccionada.teUnitat() && actual.teUnitat()) {
                        jugador1.atacar(seleccionada.getUnitat(), actual.getUnitat());
                        if (seleccionada.getUnitat().getPV() <= 0) { //Si la atacant es mor
                            seleccionada.eliminaUnitat();
                            actualitzaContenidor(null, posicioActual);
                            actualitzaContenidor(null, barraOrigen);
                            seleccionada.reset();
                            eliminaSeleccio();
                            seleccionada = null;
                        } else { //Si la atacant no es mor
                            actualitzaContenidor(seleccionada, posicioActual);
                            actualitzaContenidor(seleccionada, barraOrigen);
                            seleccionada.pinta(Color.BLUE);
                        }

                        if (actual.getUnitat().getPV() <= 0) { //Si la atacada es mor
                            atacables.remove(actual);
                            actual.eliminaUnitat();
                            actualitzaContenidor(null, barraDesti);
                            actual.reset();
                            if (seleccionada != null) actual.pinta(Color.RED);
                            if (atacables.isEmpty()) {
                                actual = null;
                                actualitzaContenidor(null, barraDesti);
                            } else {
                                actual = atacables.get(0);
                                actual.pinta(Color.YELLOW);
                                actualitzaContenidor(actual, barraDesti);
                            }
                        } else { //Si la atacada no es mor
                            if (seleccionada != null) {
                                actual.pinta(Color.YELLOW);
                                actualitzaContenidor(actual, barraDesti);
                            } else {
                                actualitzaContenidor(null, barraDesti);
                                actual.reset();
                            }
                        }
                    }
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
                    ferMovimentAtac.setText("Realitza Moviment");
                    ant.setVisible(false);
                    seg.setVisible(false);
                    tipusInforme.setText("Moviment");
                } else {
                    seg.setDisable(false);
                    ant.setDisable(false);
                    b.setText("Atac");
                    ferMovimentAtac.setText("Realitza Atac");
                    ant.setVisible(true);
                    seg.setVisible(true);
                    tipusInforme.setText("Atac");
                }

                if (seleccionada != null) {
                    pintaRang();
                    actualitzaContenidor(seleccionada, barraOrigen);
                    if (!atacables.isEmpty()) {
                        actual = atacables.get(index);
                        actual.pinta(Color.YELLOW);
                        actualitzaContenidor(actual, barraDesti);
                    }
                }

            }
        });
    }


    private void assignarHandlers(int x, int y) {
        Posicio[][] pos = mapa.getMapa();

        pos[x][y].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursor = (Posicio) mouseEvent.getTarget();

                actualitzaContenidor(cursor, barraCursor);

                if (cursor != seleccionada && cursor != actual) cursor.pinta(Color.GREEN);
            }
        });

        pos[x][y].setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Posicio aux = (Posicio) mouseEvent.getTarget();

                Boolean rang = dinsRang(aux);

                if (!rang && aux != seleccionada && aux != actual) aux.reset();
                else if (rang && aux != actual && aux != seleccionada) aux.pinta(Color.RED);

            }
        });


        pos[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Posicio clicada = (Posicio) mouseEvent.getTarget();

                if (seleccionada != clicada) {
                    if (seleccionada != null) { //Ja n'hi havia una de seleccionada
                        if (dinsRang(clicada) && !clicada.teUnitat()) { //Si he clicat dins d'una posicio dins del rang
                            if (actual != null && actual != seleccionada) actual.pinta(Color.RED);
                            actual = clicada;
                            actual.pinta(Color.YELLOW); //La pinto de groc

                            actualitzaContenidor(actual, barraDesti); //La poso a la barra d'informe
                            actualitzaContenidor(seleccionada, barraOrigen);
                        } else { //Si no estava dins del rang
                            eliminaSeleccio();
                            seleccionada.reset();
                            actual = null;
                            seleccionada = clicada; //Poso la seleccionada com a la clicada
                            pintaRang(); //Pinto el rang de la nova posicio

                            if (!atacables.isEmpty()) {
                                actual = atacables.get(index);
                                actual.pinta(Color.YELLOW);
                                actualitzaContenidor(actual, barraDesti);
                            } else actualitzaContenidor(null, barraDesti);
                            actualitzaContenidor(seleccionada, barraOrigen);

                            seleccionada.pinta(Color.BLUE);
                        }
                    } else {
                        seleccionada = clicada;
                        pintaRang();

                        actualitzaContenidor(seleccionada, posicioActual);
                        actualitzaContenidor(seleccionada, barraOrigen);

                        seleccionada.pinta(Color.BLUE);
                    }
                    actualitzaContenidor(seleccionada, posicioActual);
                    actualitzaContenidor(seleccionada, barraOrigen);
                }
            }
        });
    }


    private void pintaRang() {
        index = 0;
        atacables.clear();
        if (seleccionada.teUnitat()) {
            pintades = mapa.getRang(seleccionada, btAtacMoure.getText());
            for (Posicio i : pintades) {
                i.pinta(Color.RED);
                if (btAtacMoure.getText().equals("Atac") && i.teUnitat() && i.getUnitat().Enemiga(seleccionada.getUnitat()))
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
            t.setUnitat(aux.getUnitat(), false);

            p.getChildren().add(new Label("Posicio: [" + aux.getX() + "," + aux.getY() + "]"));
            p.getChildren().add(t);
            if (p == barraDesti) {
                if (btAtacMoure.getText().equals("Moure")) {
                    p.getChildren().addAll(
                            new Text("Distància recorreguda: "),
                            new Label(Mapa.distanciaRecorreguda(actual, seleccionada).toString() + " posicions")
                    );
                }
            }
            if (t.teUnitat()) p.getChildren().add(new Label(t.getUnitat().getAtributs()));
        }
    }


    private Boolean dinsRang(Posicio aux) {
        for (Posicio i : pintades) if (i == aux) return true;
        return false;
    }

    private void mostraExercitJugadors() {
        for (Unitat u : jugador1.getExercit()) System.out.println(u);

        for (Unitat u : jugador2.getExercit()) System.out.println(u);
    }

}
