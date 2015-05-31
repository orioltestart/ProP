/**
 * @file Main.java
 * @author Oriol Testart
 * @brief Inicialitza l'escena d'inici
 */
package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent inici = FXMLLoader.load(getClass().getResource("controladors/inici.fxml"));
        primaryStage.setScene(new Scene(inici, 1280, 800));
        primaryStage.setTitle("CONQUER ARMY - INICI");
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
