package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;

public class ControladorInicial {
    
    @FXML //Permite a javaFX conectar el componente FXML con el controlador el componente fx:id="btnInicio"
    private Button btnInicio;
    @FXML //Permite a javaFX conectar el componente FXML con el método onAction="#iniciarJuego"
    protected void iniciarJuego( ActionEvent event) {
        try {
            // Cargar la nueva vista desde el archivo FXML
            URL fxmlURL = getClass().getResource("/juegoparchis/View/InicioView.fxml");
            Parent nuevoVista = FXMLLoader.load(fxmlURL);
            // Obtener la escena actual y establecer la nueva escena
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene nuevaEscena = new Scene(nuevoVista);
            // Configurar la nueva escena en la ventana actual
            stageActual.setScene(nuevaEscena);
            //Muestra la nueva escena
            stageActual.show();
        } catch (IOException e) {
            System.out.println("Error al cargar la vista del juego:");
            e.printStackTrace();
        }
    }
}
