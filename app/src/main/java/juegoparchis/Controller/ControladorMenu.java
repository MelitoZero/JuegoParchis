package juegoparchis.Controller;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class ControladorMenu {
    
    @FXML
    private Button btnCrearSala;
    @FXML
    private Button btnUnirse;
    @FXML
    protected void crearPartida( ActionEvent event) {
        try {
            // Cargar la nueva vista desde el archivo FXML
            URL fxmlURL = getClass().getResource("/View/ConfigurarPartida.fxml");
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
            System.out.println("Tipo de error: " + e.getClass().getName());
            System.out.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    protected void unirsePartida( ActionEvent event) {
        try {
            // Cargar la nueva vista desde el archivo FXML
            URL fxmlURL = getClass().getResource("/View/ConfigurarJugador.fxml");
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
            System.out.println("Tipo de error: " + e.getClass().getName());
            System.out.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

}