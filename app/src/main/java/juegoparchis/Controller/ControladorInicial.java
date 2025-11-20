package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import juegoparchis.Util.NavegacionPantallas;
import javafx.event.ActionEvent;

public class ControladorInicial {
    
    @FXML //Permite a javaFX conectar el componente FXML con el controlador el componente fx:id="btnInicio"
    private Button btnInicio;
    @FXML //Permite a javaFX conectar el componente FXML con el método onAction="#iniciarJuego"
    protected void iniciarJuego( ActionEvent event) {
        NavegacionPantallas.cambiarPantalla(event, "/View/InicioView.fxml");
    }
}
