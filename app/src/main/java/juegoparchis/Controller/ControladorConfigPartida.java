package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import juegoparchis.Service.ConexionP2P;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
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

    @FXML
    protected void configurarPartida(ActionEvent event) {
        //Iniciamos el servidor y logica para esperar conexiones de otros jugadores 
        //Aun en desarrollo y prueba
        /*conexionP2P.iniciarServidor(() ->{
            System.out.println("Jugador conectado a la partida.");
        });*/
        try {
            // Cargar la nueva vista desde el archivo FXML
            URL fxmlURL = getClass().getResource("/View/ConfigurarJugador.fxml");
            Parent nuevaVista = FXMLLoader.load(fxmlURL);

            // Obtener la escena actual y establecer la nueva escena
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene nuevaEscena = new Scene(nuevaVista);

            // Configurar la nueva escena en la ventana actual
            stageActual.setScene(nuevaEscena);
            stageActual.show();

        } catch (IOException e) {
            System.out.println("Error al cargar la vista del juego:");
            System.out.println("Tipo de error: " + e.getClass().getName());
            System.out.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
