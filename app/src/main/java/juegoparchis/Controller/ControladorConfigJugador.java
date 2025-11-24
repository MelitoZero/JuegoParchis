package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import juegoparchis.Util.NavegacionPantallas;
import javafx.event.ActionEvent;

public class ControladorConfigJugador {

    @FXML
    private TextField txtJugador;

    @FXML
    private ComboBox<String> cboxColor;

    @FXML
    private ComboBox<String> cboxAvatar;

    @FXML
    private TextField txtCodigoSala;

    @FXML
    private Button btnUnirse;
    //Método para unirse a una partida
    @FXML
    protected void unirsePartida(ActionEvent event) {
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaEspera.fxml");
    }
    //Método para volver a la pantalla anterior
    @FXML
    protected void Volver(ActionEvent event) {
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaConfigPartida.fxml");
    }

}
