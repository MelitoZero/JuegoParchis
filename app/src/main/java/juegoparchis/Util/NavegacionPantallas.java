package juegoparchis.Util;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavegacionPantallas {
    //Método para iniciar la aplicación en la pantalla de inicio
    public static void iniciarAplicacion(Stage stage, String rutaFXML, String tituloVentana) {
        try {
            // Cargar la nueva vista desde el archivo FXML especificado
            URL fxmlURL = NavegacionPantallas.class.getResource(rutaFXML);
            // Verificar si el recurso FXML fue encontrado y no es nulo
            if (fxmlURL == null) {
                System.out.println("Error: No se pudo encontrar el archivo FXML en la ruta especificada: " + rutaFXML);
                return;
            }
            Parent nuevaVista = FXMLLoader.load(fxmlURL);
            Scene nuevaEscena = new Scene(nuevaVista);
            stage.setScene(nuevaEscena);
            stage.setTitle(tituloVentana);
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            //Maneja los errores y muestra mensajes en la consola
            System.out.println("Error al iniciar la Ventana del juego:");
            System.out.println("Tipo de error: " + e.getClass().getName());
            System.out.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }        
    //Métodos para la navegación entre pantallas 
    public static void cambiarPantalla(ActionEvent event, String rutaFXML) {
        try {
             // Cargar la nueva vista desde el archivo FXML especificado
            URL fxmlURL = NavegacionPantallas.class.getResource(rutaFXML);
            // Verificar si el recurso FXML fue encontrado y no es nulo
            if (fxmlURL == null) {
                System.out.println("Error: No se pudo encontrar el archivo FXML en la ruta especificada: " + rutaFXML);
                return;
            }
            // Carga la nueva vista
            Parent nuevaVista = FXMLLoader.load(fxmlURL);
            // Obtiene la escena actual y establece la nueva escena
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene nuevaEscena = new Scene(nuevaVista);
            // Configurar la nueva escena en la ventana actual
            stageActual.setScene(nuevaEscena);
            //Muestra la nueva escena
            stageActual.show();
            stageActual.centerOnScreen();
        } catch (IOException e) {
            //Maneja los errores y muestra mensajes en la consola
            System.out.println("Error al cargar la vista del juego:");
            System.out.println("Tipo de error: " + e.getClass().getName());
            System.out.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static <T> T cambiarPantallaControlador(ActionEvent event, String rutaFXML){
        try {
            FXMLLoader loader = new FXMLLoader(NavegacionPantallas.class.getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
