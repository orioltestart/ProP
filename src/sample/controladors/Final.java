package sample.controladors;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Final {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane finestra;

    @FXML
    private VBox barraCentral;

    @FXML
    private Button finalitza;

    @FXML
    private Button tornaJugar;

    @FXML
    /**
     @pre cert
     @post Inicialitza l'interficie amb els mètodes corresponents als botons
     */
    void initialize() {
        assert finestra != null : "fx:id=\"finestra\" was not injected: check your FXML file 'finalVictoria.fxml'.";
        assert barraCentral != null : "fx:id=\"barraCentral\" was not injected: check your FXML file 'finalVictoria.fxml'.";
        assert finalitza != null : "fx:id=\"finalitza\" was not injected: check your FXML file 'finalVictoria.fxml'.";

        finalitza.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /**
                 @brief classe interna que gestiona el comportament del botó per finalitzar partida
                 @pre cert
                 @post es tallarà el programa
                 */
                System.exit(0);
            }
        });

        tornaJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /**
                 @brief classe interna que gestiona el comportament del botó per tornar a iniciar partida
                 @pre cert
                 @post Es canviarà l'escena a l'escena d'inici.
                 */
                try {
                    Button aux = (Button) actionEvent.getSource();
                    Stage stage = (Stage) aux.getScene().getWindow();
                    Parent root = (Parent) FXMLLoader.load(getClass().getResource("inici.fxml"));

                    stage.setTitle("CONQUER ARMY - INICI");
                    stage.setScene(new Scene(root, 1280, 800));

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
