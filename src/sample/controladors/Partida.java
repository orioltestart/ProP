/**
 * Sample Skeleton for 'partida.fxml' Partida Class
 */

package sample.controladors;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Jugador;
import sample.Mapa;
import sample.Posicio;
import sample.unitats.Unitat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class Partida {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="contenidorMapa"
    private ScrollPane contenidorMapa; // Value injected by FXMLLoader

    @FXML // fx:id="barraInferior"
    private TitledPane barraInferior; // Value injected by FXMLLoader

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
    private Button passarTorn;

    @FXML
    private Button btSortir;

    @FXML
    private Button ferMovimentAtac;

    @FXML
    private VBox barraDesti;

    @FXML
    private VBox barraOrigen;

    private Integer index = 0;

    public static String terreny;

    public static String unitats;


    private ArrayList<Posicio> atacables = new ArrayList<Posicio>();

    private ArrayList<Posicio> pintades = new ArrayList<Posicio>();


    private Posicio seleccionada;
    private Posicio cursor;
    private Posicio actual;

    Jugador jugador1;
    Jugador jugador2;

    Integer torn = 0;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws InterruptedException, IOException {
        assert contenidorMapa != null : "fx:id=\"contenidorMapa\" was not injected: check your FXML file 'partida.fxml'.";
        assert barraInferior != null : "fx:id=\"barraInferior\" was not injected: check your FXML file 'partida.fxml'.";
        assert finestra != null : "fx:id=\"finestra\" was not injected: check your FXML file 'partida.fxml'.";

        assignarBotons();

        jugador1 = new Jugador(1);
        jugador2 = new Jugador(2);

        //Abans de carregar el mapa

        mapa = new Mapa(terreny);
        mapa.llegirUnitats(unitats, jugador1, jugador2);

        for (int i = 0; i < mapa.getMidaH(); i++) {
            for (int j = 0; j < mapa.getMidaV(); j++) {
                assignarHandlers(i, j);
                m.add(mapa.getPos(i, j), i, j);
            }
        }
    }

    private void assignarBotons() {

        passarTorn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Passant torn: " + torn);
                barraInferior.setExpanded(false);
                try {
                    ControlMaquina(jugador1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Final Maquina");
                barraInferior.setExpanded(true);
                jugador1.activaExercit();
                jugador2.activaExercit();
                eliminaSeleccio();
                if (seleccionada != null) {
                    pintaRang();
                    seleccionada.pinta(Color.BLUE);
                }
                actualitzaContenidor(null, barraDesti);
                actualitzaContenidor(null, barraOrigen);
                actualitzaContenidor(null, posicioActual);

                if (jugador2.getExercit().isEmpty()) { //Final de torn i no et queden unitats
                    try {
                        Button aux = (Button) actionEvent.getTarget();
                        Stage s = (Stage) aux.getScene().getWindow();
                        Parent root = (Parent) FXMLLoader.load(getClass().getResource("finalDerrota.fxml"));

                        s.setTitle("CONQUEST ARMY - FINAL DERROTA");
                        s.setScene(new Scene(root, 1280, 800));
                        s.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (jugador1.getExercit().isEmpty()) {
                    try {
                        Button aux = (Button) actionEvent.getTarget();
                        Stage s = (Stage) aux.getScene().getWindow();
                        Parent root = (Parent) FXMLLoader.load(getClass().getResource("finalVictoria.fxml"));

                        s.setTitle("CONQUEST ARMY - FINAL VICTORIA");
                        s.setScene(new Scene(root, 1280, 800));
                        s.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                torn++;
            }
        });

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
                    actualitzaContenidor(null, barraAccio);
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
                    actualitzaContenidor(null, barraAccio);
                }
            }
        });


        btSortir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Button aux = (Button) actionEvent.getTarget();
                    Stage s = (Stage) aux.getScene().getWindow();
                    Parent root = (Parent) FXMLLoader.load(getClass().getResource("finalDerrota.fxml"));

                    s.setTitle("CONQUEST ARMY - FINAL DERROTA");
                    s.setScene(new Scene(root, 1280, 800));
                    s.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ferMovimentAtac.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (btAtacMoure.getText().equals("Moure")) {
                    if (actual != null && !actual.teUnitat() && dinsRang(actual)) {
                        mapa.desplacar(seleccionada, actual);
                        if (mapa.metaAconseguida()) {
                            try {
                                Button aux = (Button) actionEvent.getTarget();
                                Stage s = (Stage) aux.getScene().getWindow();
                                Parent root = (Parent) FXMLLoader.load(getClass().getResource("finalVictoria.fxml"));

                                s.setTitle("CONQUEST ARMY - FINAL VICTORIA");
                                s.setScene(new Scene(root, 1280, 800));
                                s.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        seleccionada = actual;
                        seleccionada.getUnitat().setMovActual(0);
                        actualitzaContenidor(null, barraDesti);
                        actualitzaContenidor(seleccionada, posicioActual);
                        actualitzaContenidor(seleccionada, barraOrigen);
                        eliminaSeleccio();
                        pintaRang();
                        seleccionada.pinta(Color.BLUE);
                    }
                } else {
                    if (seleccionada != null && actual != null && seleccionada.teUnitat() && actual.teUnitat()) {
                        jugador2.atacar(seleccionada.getUnitat(), actual.getUnitat());

                        if (jugador2.getExercit().isEmpty()) {
                            try {
                                Button aux = (Button) actionEvent.getTarget();
                                Stage s = (Stage) aux.getScene().getWindow();
                                Parent root = (Parent) FXMLLoader.load(getClass().getResource("finalDerrota.fxml"));

                                s.setTitle("CONQUEST ARMY - FINAL DERROTA");
                                s.setScene(new Scene(root, 1280, 800));
                                s.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (jugador1.getExercit().isEmpty()) {
                            try {
                                Button aux = (Button) actionEvent.getTarget();
                                Stage s = (Stage) aux.getScene().getWindow();
                                Parent root = (Parent) FXMLLoader.load(getClass().getResource("finalVictoria.fxml"));

                                s.setTitle("CONQUEST ARMY - FINAL VICTORIA");
                                s.setScene(new Scene(root, 1280, 800));
                                s.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (seleccionada.getUnitat().getPV() <= 0) { //Si la atacant es mor
                            jugador2.getExercit().remove(seleccionada);
                            seleccionada.eliminaUnitat();
                            actualitzaContenidor(null, posicioActual);
                            actualitzaContenidor(null, barraOrigen);
                            actualitzaContenidor(null, barraAccio);
                            seleccionada.reset();
                            eliminaSeleccio();
                            seleccionada = null;
                        } else { //Si la atacant no es mor
                            actualitzaContenidor(seleccionada, posicioActual);
                            actualitzaContenidor(seleccionada, barraOrigen);
                            seleccionada.getUnitat().acabaTorn();
                            pintaRang();
                            seleccionada.pinta(Color.BLUE);
                        }

                        if (actual.getUnitat().getPV() <= 0) { //Si la atacada es mor
                            jugador1.getExercit().remove(actual);
                            actual.eliminaUnitat();
                            actualitzaContenidor(null, barraDesti);
                            actualitzaContenidor(null, barraAccio);
                            actual.reset();
                            atacables.remove(actual);

                            if (seleccionada != null) actual.pinta(Color.RED);
                            if (atacables.isEmpty()) {
                                actual = null;
                                actualitzaContenidor(null, barraDesti);
                            } else {
                                actual = atacables.get(0);
                                actual.pinta(Color.YELLOW);
                                actualitzaContenidor(actual, barraDesti);
                                actualitzaContenidor(null, barraAccio);
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
                actualitzaContenidor(null, barraAccio);

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
                            if (actual != null && actual != seleccionada && dinsRang(actual)) actual.pinta(Color.RED);
                            actual = clicada;
                            actual.pinta(Color.YELLOW); //La pinto de groc

                            actualitzaContenidor(actual, barraDesti); //La poso a la barra d'informe
                            actualitzaContenidor(seleccionada, barraOrigen);
                        } else { //Si no estava dins del rang
                            eliminaSeleccio();
                            seleccionada.reset();
                            actual = null;
                            seleccionada = clicada; //Poso la seleccionada com a la clicada
                            pintaRang();
                            if (!atacables.isEmpty()) {
                                actual = atacables.get(index);
                                actual.pinta(Color.YELLOW);
                                actualitzaContenidor(actual, barraDesti);
                            } else actualitzaContenidor(null, barraDesti);
                            actualitzaContenidor(seleccionada, barraOrigen);
                        }
                    } else {
                        seleccionada = clicada;
                        pintaRang();
                    }
                    actualitzaContenidor(seleccionada, posicioActual);
                    actualitzaContenidor(seleccionada, barraOrigen);
                }
                actualitzaContenidor(null, barraAccio);
            }
        });
    }


    private void pintaRang() {
        index = 0;
        atacables.clear();
        if (seleccionada.teUnitat()) {
            if (seleccionada.getUnitat().getPropietari() == 2 && seleccionada.getUnitat().isReady()) {
                ferMovimentAtac.setDisable(false);
                ant.setDisable(false);
                seg.setDisable(false);

                seleccionada.pinta(Color.BLUE);
                pintades = mapa.getRang(seleccionada, btAtacMoure.getText());


                if (btAtacMoure.getText().equals("Moure")){
                    mapa.buscaCamiMinim(seleccionada);

                    ArrayList<Posicio> pintades2 = new ArrayList<Posicio>();
                    pintades2.clear();
                    for (Posicio p : pintades){
                       // System.out.println("posicio actual: " + seleccionada + " ->> " + mapa.ValorCamiMin(p));
                        Integer costMin = mapa.ValorCamiMin(p);
                        if (costMin <= seleccionada.getUnitat().getMovAct()) pintades2.add(p);
                    }
                    pintades = pintades2;
                }


                for (Posicio i : pintades) {
                    i.pinta(Color.RED);
                    if (btAtacMoure.getText().equals("Atac") && i.teUnitat() && i.getUnitat().Enemiga(seleccionada.getUnitat()))
                        atacables.add(i);
                }
            } else {
                ferMovimentAtac.setDisable(true);
                ant.setDisable(true);
                seg.setDisable(true);
            }
        } else {
            ferMovimentAtac.setDisable(true);
            ant.setDisable(true);
            seg.setDisable(true);
        }
    }

    private void eliminaSeleccio() {
        for (Posicio i : pintades) i.reset();
        pintades.clear();
        atacables.clear();
    }

    private void actualitzaContenidor(Posicio aux, Pane p) {
        p.getChildren().clear();
        if (p == barraAccio) {
            if (btAtacMoure.getText().equals("Moure")) {
                barraAccio.getChildren().addAll(new Label("es desplaça a "), new ImageView("sample/unitats/imatges/direction4.png"));
            } else {
                if (seleccionada != null && actual != null && seleccionada.teUnitat() && actual.teUnitat())
                    barraAccio.getChildren().add(new Label("Calcul del Atac: " + seleccionada.getUnitat().calcularAtac(actual.getUnitat())));
            }
        } else {
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
    }


    private Boolean dinsRang(Posicio aux) {
        for (Posicio i : pintades) if (i == aux) return true;
        return false;
    }

    /**
     * @param j es el jugador controlat per la maquina
     * @return void
     * @pre --
     * @post la maquina realitza les funcions de un jugador j
     */
    public void ControlMaquina(Jugador j) throws InterruptedException {
        //iterador per totes les unitats del jugador
        Iterator itu = j.getExercit().iterator();

        while (itu.hasNext()) {
            Unitat agressor = (Unitat) itu.next();
            ArrayList<Posicio> rang = mapa.getRang(agressor.getPosAct(), agressor.getRang() + agressor.getMovTot());
            Iterator itp = rang.iterator();
            //iterador per totes les caselles dins del rang
            Integer millorDany = 0;
            Posicio Objectiu = new Posicio();
            //obtenir la unitat enemiga a la qual li farem mes punts de dany
            boolean mata = false;
            while (!mata && itp.hasNext()) {
                Posicio pos = (Posicio) itp.next();
                if (pos.teUnitat() && pos.getUnitat().Enemiga(agressor) && agressor.calcularAtac(pos.getUnitat()) > millorDany) {
                    millorDany = agressor.calcularAtac(pos.getUnitat());
                    Objectiu = pos;
                    if (millorDany >= Objectiu.getUnitat().getPV()) {    //si matem una unitat no cal buscarne cap altra
                        mata = true;
                    }
                }
            }
            if (agressor.getPV() < 35 && !mata) {
                retirada(agressor);
            } else if (millorDany > 0) {
                //a partir de la posicio Objectiu, busquem la millor posicio per mourens
                ArrayList<Posicio> area = mapa.getRang(Objectiu, agressor.getRang());
                area.retainAll(rang);
                Posicio desti = new Posicio();
                Integer millorDef = -20;
                for (Posicio h : area) {
                    if (h.getTerreny().getDefensa() > millorDef)
                        desti = h;
                }

                //Passar temps

                mapa.desplacar(agressor.getPosAct(), desti);

                j.atacar(agressor, Objectiu.getUnitat());

                if (agressor.getPV() <= 0) {
                    j.getExercit().remove(agressor);
                    agressor.getPosAct().eliminaUnitat();
                    agressor.getPosAct().reset();
                    itu = j.getExercit().iterator();
                }
                if (Objectiu.getUnitat().getPV() <= 0) {
                    jugador2.getExercit().remove(Objectiu.getUnitat());
                    Objectiu.eliminaUnitat();
                    Objectiu.reset();
                }

            }
            j.enRepos(agressor);

        }
        //si no hi ha cap unitat al rang, no fa res
    }

    public void retirada(Unitat u) {

        Posicio desti = new Posicio();
        ArrayList<Posicio> candidats = new ArrayList<>();

        for (Posicio p : mapa.getForts()) {
            if (!p.teUnitat()) candidats.add(p);
        }
        //buscar cami minim per cada un
        //backtracking de la posicio on hi ha la fortalesa mes proxima
        // (si no esta ocupada) ens dirigirem cap alla
        //moures lo mes rapid possible cap alla
    }

}
