package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import juegoparchis.Util.NavegacionPantallas;
import javafx.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ControladorConfigPartida {

    @FXML
    private TextField txtPartida, txtCodigo;
    @FXML
    private Button btnListo, btnVolver;
    @FXML
    public void initialize() {
        txtCodigo.setText("Cargando IP...");
        txtCodigo.setEditable(false);
        //Hilo para obtener la IP pública sin bloquear la interfaz
        new Thread(() ->{
            //Obtener la IP pública
            String ipPublica = obtenerIpPublica();
            //Obtener la IP local en caso de local
            @SuppressWarnings("unused")
            String ipLocal = "localhost";
            try {
                ipLocal = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {}
            //Actualizar el campo de texto en el hilo de la interfaz
            final String textoFinal = ipPublica;
            javafx.application.Platform.runLater(() ->{
                txtCodigo.setText(textoFinal);
            });
        }).start();
        try {
            //Obtenemos la IP del host para mostrarla en la configuración de la partida
            String ipHost = InetAddress.getLocalHost().getHostAddress();
            //Mostramos la IP en el txt
            txtCodigo.setText(ipHost);
            //Protegemos el campo de texto para que no se pueda editar
            txtCodigo.setEditable(false);
        } catch (UnknownHostException e) {
            txtCodigo.setText("Error al obtener IP");
            e.printStackTrace();
        }
    }

    //Función para configurar la partida y pasar a la configuración del jugador
    @FXML
    protected void configurarPartida(ActionEvent event) {
        try {
            //Obtener el controlador de la nueva pantalla
            ControladorConfigJugador controlador = NavegacionPantallas.cambiarPantallaControlador(event, "/View/VistaConfigJugador.fxml");
            //Inicializar datos en la configuración del jugador
            if (controlador != null) {
                //Obtenemos la ip
                String ipCompartir = txtCodigo.getText();
                controlador.setModoHost(true, ipCompartir); //El creador de la partida es el host                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Función para regresar a la pantalla principal
    @FXML
    protected void Volver(ActionEvent event) {
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaInicio.fxml");
    }
    //Obtener Ip de la partida
    private String obtenerIpPublica(){
        try {
            java.net.URL url = java.net.URI.create("http://checkip.amazonaws.com/").toURL();
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
            return br.readLine().trim(); // Devuelve la IP pública como una cadena
        } catch (Exception e) {
            return "Error al obtener IP";
        }
    }
}
