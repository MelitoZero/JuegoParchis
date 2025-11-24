package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import juegoparchis.Util.NavegacionPantallas;
//import juegoparchis.Service.ConexionP2P;
import javafx.event.ActionEvent;

public class ControladorConfigPartida {
    //Añadimos el servicio de conexión
    //private ConexionP2P conexionP2P = new ConexionP2P();

    @FXML
    private VBox vBoxConfigurar;

    @FXML
    private TextField txtPartida;

    @FXML
    private TextField txtCodigo;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button btnListo;
    //Función para configurar la partida y pasar a la configuración del jugador
    @FXML
    protected void configurarPartida(ActionEvent event) {
        //Iniciamos el servidor y logica para esperar conexiones de otros jugadores 
        //Aun en desarrollo y prueba
        /*conexionP2P.iniciarServidor(() ->{
            System.out.println("Jugador conectado a la partida.");
        });*/
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaConfigJugador.fxml");
    }
    //Función para regresar a la pantalla principal
    @FXML
    protected void Volver(ActionEvent event) {
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaInicio.fxml");
    }

}
