package juegoparchis.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import juegoparchis.Util.NavegacionPantallas;

public class ControladorSalaEspera {
    @FXML
    private Button btnUnirse;
    //Método para unirse a la partida desde la sala de espera
    @FXML
    protected void iniciarPartida(ActionEvent event) {
        //Lógica para unirse a la partida
        //Cambiar a la pantalla del juego
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaTablero.fxml");
    }
}
