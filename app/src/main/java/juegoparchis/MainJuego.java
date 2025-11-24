package juegoparchis;

import javafx.application.Application;
import javafx.stage.Stage;
import juegoparchis.Util.NavegacionPantallas;

public class MainJuego extends Application {

    //Método start que se llama al iniciar la aplicación JavaFX.
    @Override
    public void start(Stage primaryStage) {
        //Cargamos la primera pantalla desde el archivo FXML.
        NavegacionPantallas.iniciarAplicacion(primaryStage, "/View/VistaInicio.fxml", "Juego Parchís");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
