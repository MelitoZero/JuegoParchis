package juegoparchis.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import juegoparchis.Util.NavegacionPantallas;


public class ControladorMenu {
    
    @FXML
    private Button btnCrearSala;
    @FXML
    private Button btnUnirse;
    @FXML
    protected void crearPartida( ActionEvent event) {
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaConfigPartida.fxml");
    }
    @FXML
    protected void unirsePartida( ActionEvent event) {
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaConfigJugador.fxml");
    }

}