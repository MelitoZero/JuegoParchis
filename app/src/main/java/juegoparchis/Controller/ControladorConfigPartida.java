package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import juegoparchis.Util.NavegacionPantallas;
import juegoparchis.Util.Redes;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class ControladorConfigPartida {

    @FXML
    private TextField txtPartida, txtCodigo;
    @FXML
    private Button btnListo, btnVolver;
    @FXML
    public void initialize() {
        txtCodigo.setText("Cargando IP...");
        txtCodigo.setEditable(false);
        //Hilo secudario
        new Thread(() ->{
            //Obtener la IP pública
            String ipPublica = Redes.obtenerIpPublica();
            Platform.runLater(() -> {
                //Si no hay internet entonces muestra puro local
                if (ipPublica.equals("No disponible")) {
                    txtCodigo.setText(Redes.obtenerIpLocal());
                }else {
                    txtCodigo.setText(ipPublica);
                }
            });
        }).start();
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
                controlador.setModoHost(true, txtCodigo.getText());              
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
}
