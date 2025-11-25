package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import juegoparchis.Model.Enum.Color;
import juegoparchis.Util.NavegacionPantallas;
import javafx.event.ActionEvent;

public class ControladorConfigJugador {

    @FXML
    private TextField txtJugador, txtCodigoSala;

    @FXML
    private ComboBox<Color> cboxColor;

    @FXML
    private ComboBox<String> cboxAvatar;

    @FXML
    private Button btnUnirse, btnVolver;
    @FXML
    public void initialize() {
        // Inicializar ComboBox de colores
        cboxColor.getItems().setAll(Color.values());
        cboxColor.getSelectionModel().selectFirst();
        // Inicializar ComboBox de avatares
        cboxAvatar.getItems().addAll("Avatar1", "Avatar2", "Avatar3", "Avatar4");
        cboxAvatar.getSelectionModel().selectFirst();
    }
    //Método para unirse a una partida
    @FXML
    protected void unirsePartida(ActionEvent event) {
        String nombreJugador = txtJugador.getText();
        String ip = txtCodigoSala.getText();
        Color colorSeleccionado = cboxColor.getValue();
        String avatarSeleccionado = cboxAvatar.getValue();
        //validación simple
        if (nombreJugador.isEmpty()) {
            System.out.println("Por favor, ponga un nombre");
            return;
        }
        //Logica para determinar si es Host
        boolean esHost = ip.isEmpty(); //Si la IP está vacía, es host
        String ipDestino = esHost ? "localhost" : ip; //Si es host, la IP destino es localhost
        //Pasar datos
        try {
            //Obtener el controlador de la nueva pantalla
            ControladorSalaEspera controlador = NavegacionPantallas.cambiarPantallaControlador(event, "/View/VistaEspera.fxml");
            //Inicializar datos en la sala de espera
            controlador.initData(nombreJugador, esHost, ipDestino, colorSeleccionado, avatarSeleccionado);
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
    //Método para volver a la pantalla anterior
    @FXML
    protected void Volver(ActionEvent event) {
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaConfigPartida.fxml");
    }

}
