package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;

public class ControladorConfigJugador {


        @FXML
        private TextField txtJugador;

        @FXML
        private ComboBox<String> cboxColor;

        @FXML
        private ComboBox<String> cboxAvatar;

        @FXML
        private TextField txtCodigoSala;

        @FXML
        private Button btnUnirse;

        @FXML
        protected void unirsePartida() {

    }
}
