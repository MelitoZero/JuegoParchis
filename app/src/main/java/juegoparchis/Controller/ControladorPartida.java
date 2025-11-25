package juegoparchis.Controller;

import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;
import juegoparchis.Model.Dado;
import juegoparchis.Model.Ficha;
import juegoparchis.Model.Enum.Color;
import juegoparchis.Util.CoordenadasTablero;
import juegoparchis.View.VistaFicha;

public class ControladorPartida {
    
    private Dado dadoModelo;
    @FXML
    private Label lblTurnoJugador;
    @FXML
    private Button btnTirarDado;
    @FXML
    private ImageView imgDado;
    @FXML
    private ImageView imgJugador1, imgJugador2, imgJugador3, imgJugador4;
    @FXML
    private ImageView imgPerfil1, imgPerfil2;
    @FXML
    private Pane panelFichas;
    @FXML
    private ImageView imgTablero;
    @FXML
    public void initialize() {
        // Aquí puedes agregar cualquier inicialización necesaria para el controlador
        /*imgTablero.setOnMouseClicked(event ->{
            double x = event.getX();
            double y = event.getY();
            System.out.println("recorrido.put(NUMERO, new Point2D(" + x + ", " + y + "));");    
        });*/
        dadoModelo = new Dado();
        actualizarImgDado(dadoModelo.getValor());
        dibujarFichas();
    }
    @FXML
    protected void lanzarDado(ActionEvent event) {
        //Lógica para lanzar el dado
        btnTirarDado.setDisable(true); // Deshabilitar el botón para evitar múltiples lanzamientos
        // Simular el lanzamiento del dado
        int valorDado = dadoModelo.lanzar();
        System.out.println("Dado lanzado: " + valorDado);
        // Actualizar la imagen del dado en la interfaz
        actualizarImgDado(valorDado);
        //Reactivar el botón despues de terminar el turno
        //btnTirarDado.setDisable(false);
    }
    private void dibujarFichas() {
        // Lógica para dibujar las fichas en el panelFichas
        // Por ejemplo, podrías iterar sobre una lista de fichas y agregarlas al panel
        Color[] colores = {Color.ROJO, Color.AZUL, Color.VERDE, Color.AMARILLO};
        for (Color color : colores){
            for(int i = 0; i < 4; i++){
                // Crear la ficha lógica del modelo
                Ficha fichaLogica = new Ficha(i, color);
                // Obtener la posición de la casa
                Point2D posicion = CoordenadasTablero.getCoordenadaCasa(color, i);
                if (posicion != null) {
                    // Crear la vista de la ficha
                    VistaFicha vistaFicha = new VistaFicha(fichaLogica, 15);
                    // Establecer la posición de la vista de la ficha
                    vistaFicha.setLayoutX(posicion.getX());
                    vistaFicha.setLayoutY(posicion.getY());
                    // Agregar la vista de la ficha al panel de fichas
                    panelFichas.getChildren().add(vistaFicha);
                }
            }
        }
    }
    private void actualizarImgDado(int valor) {
        // Lógica para actualizar la imagen del dado en la interfaz
        try {
            String rutaImagen = "/imgs/Dado" + valor + ".png";
            InputStream flujo = getClass().getResourceAsStream(rutaImagen);
            if (flujo != null) {
                imgDado.setImage(new Image(flujo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
