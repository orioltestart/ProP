package sample.controladors;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Inici {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton niv1;

    @FXML
    private RadioButton niv2;

    @FXML
    private Button btInici;


    @FXML
    private BorderPane finestra;

    @FXML
    private VBox barraCentral;

    final ToggleGroup menu = new ToggleGroup();

    @FXML
    /**
     @pre cert
     @post Inicialitza l'interficie amb els mètodes corresponents als botons
     */
    void initialize() {
        assert niv1 != null : "fx:id=\"niv1\" was not injected: check your FXML file 'inici.fxml'.";
        assert niv2 != null : "fx:id=\"niv2\" was not injected: check your FXML file 'inici.fxml'.";
        assert btInici != null : "fx:id=\"btInici\" was not injected: check your FXML file 'inici.fxml'.";
        assert finestra != null : "fx:id=\"finestra\" was not injected: check your FXML file 'inici.fxml'.";
        assert barraCentral != null : "fx:id=\"barraCentral\" was not injected: check your FXML file 'inici.fxml'.";

        niv2.setToggleGroup(menu);
        niv1.setToggleGroup(menu);

        btInici.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /**
                 @brief classe interna que gestiona el comportament del botó per iniciar partida
                 @pre algun nivell seleccionat
                 @post s'iniciara la partida amb el mapa corresponent al nivell seleccionat. Canviant d'escena
                 */
                Button bt = (Button) actionEvent.getSource();
                Stage stage = (Stage) bt.getScene().getWindow();

                try {
                    if (niv1.isSelected()) {
                        Partida.terreny = "mapes/mapa3";
                        Partida.unitats = "mapes/unitats3";
                    } else if (niv2.isSelected()) {
                        Partida.terreny = "mapes/mapa1";
                        Partida.unitats = "mapes/unitats1";
                    }

                    Parent inici = FXMLLoader.load(getClass().getResource("partida.fxml"));

                    stage.setTitle("CONQUER ARMY - JOC");
                    stage.setScene(new Scene(inici, 1280, 800));

                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            System.exit(0);
                    }
                });

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });

    }
}
