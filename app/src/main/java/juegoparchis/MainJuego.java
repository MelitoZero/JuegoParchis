package juegoparchis;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainJuego extends Application {
    /**
     * Método start que se llama al iniciar la aplicación JavaFX.
     * @param args
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            //Carga el FXML inicial
            URL fxmlURL = getClass().getResource("/View/ComenzarView.fxml");
            Parent root = FXMLLoader.load(fxmlURL);
            //Crear la escena con el FXML cargado
            Scene scene = new Scene(root);
            //Se le pone un titulo a la ventana
            primaryStage.setTitle("Juego de Parchís");
            //Se establece la escena en la ventana
            primaryStage.setScene(scene);
            //Muestra la ventana
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error al iniciar la aplicación:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
