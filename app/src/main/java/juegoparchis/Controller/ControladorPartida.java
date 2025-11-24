package juegoparchis.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ControladorPartida {
    
    @FXML
    private ImageView imgTablero;
    @FXML
    public void initialize() {
        // Aquí puedes agregar cualquier inicialización necesaria para el controlador
        imgTablero.setOnMouseClicked(event ->{
            double x = event.getX();
            double y = event.getY();
            System.out.println("recorrido.put(NUMERO, new Point2D(" + x + ", " + y + "));");    
        });
    }

}
