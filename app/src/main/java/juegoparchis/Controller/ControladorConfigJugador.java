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
    private boolean soyHost = false;
    
    @FXML //Método para unirse a una partida
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
        boolean esHost = this.soyHost;
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

    //Método para establecer si el jugador es host o no
    public void setModoHost(boolean esHost, String ipHost) {
        this.soyHost = esHost;
        if (esHost) {
            //Mostras ip
            txtCodigoSala.setText(ipHost);
            //Si es host, deshabilitar el campo de texto del código de sala
            txtCodigoSala.setEditable(false);
            txtCodigoSala.setDisable(false);
        } else {
            //Si no es host, habilitar el campo de texto del código de sala
            txtCodigoSala.setText("");
            txtCodigoSala.setDisable(false);
            txtCodigoSala.setEditable(true);
            txtCodigoSala.setPromptText("Ingresa la Ip del Host");

        }
    }

    @FXML
    public void initialize() {
        cboxColor.getItems().clear();
        // Inicializar ComboBox de colores
        for (Color c : Color.values()) {
            if (c != Color.BLANCO) {
                cboxColor.getItems().add(c);
            }
        }
        //Seleccionamos el primero de la lista
        cboxColor.getSelectionModel().selectFirst();
        // Inicializar ComboBox de avatares
        cboxAvatar.getItems().addAll("Avatar1", "Avatar2", "Avatar3", "Avatar4");
        cboxAvatar.getSelectionModel().selectFirst();
        txtJugador.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) {
                txtJugador.setText(oldValue);
            }
        });
        //Copiar la Ip si es host
        txtCodigoSala.setOnMouseClicked(event ->{
            if (soyHost && !txtCodigoSala.getText().isEmpty()) {
                final javafx.scene.input.Clipboard copiado =javafx.scene.input.Clipboard.getSystemClipboard();
                final javafx.scene.input.ClipboardContent contenido = new javafx.scene.input.ClipboardContent();
                contenido.putString(txtCodigoSala.getText());
                copiado.setContent(contenido);
            }
        });
    }

    
    @FXML //Método para volver a la pantalla anterior
    protected void Volver(ActionEvent event) {
        if (soyHost) {
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaConfigPartida.fxml"); 
        } else {
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaInicio.fxml");
        }
    }
}
