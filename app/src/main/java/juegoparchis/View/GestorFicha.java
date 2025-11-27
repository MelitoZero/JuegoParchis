package juegoparchis.View;

import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;
import java.util.List;
import juegoparchis.Model.Jugador;
import juegoparchis.Model.Ficha;
import juegoparchis.Model.Enum.Color;
import juegoparchis.Util.CoordenadasTablero;

public class GestorFicha {
    // Referencia al panel donde se dibujan las fichas
    private Pane panelFichas; 
    //Método constructor
    public GestorFicha(Pane panelFichas) {
        this.panelFichas = panelFichas;
    }
    //Método para inicializar
    public void dibujarFichasIniciales(List<Jugador> jugadores) {
        panelFichas.getChildren().clear();
        for (Jugador jugador : jugadores) {
            for (Ficha ficha : jugador.getFichas()) {
                Point2D pos = null;
                if (ficha.isEnCasa()) {
                    pos = CoordenadasTablero.getCoordenadaCasa(ficha.getColor(), ficha.getId());
                } else {
                    pos = CoordenadasTablero.getCoordenada(ficha.getPosicionActual());
                }
                if (pos != null) {
                    VistaFicha vista = new VistaFicha(ficha, 15);
                    vista.setLayoutX(pos.getX());
                    vista.setLayoutY(pos.getY());
                    panelFichas.getChildren().add(vista);
                }
            }
        }
    }

    // Método para buscar una ficha visual
    public VistaFicha buscarFichaVisual(Color color, int id) {
        for (javafx.scene.Node nodo : panelFichas.getChildren()) {
            if (nodo instanceof VistaFicha) {
                VistaFicha vista = (VistaFicha) nodo;
                Ficha modelo = vista.getFichaModelo();
                if (modelo.getColor() == color && modelo.getId() == id) {
                    return vista; // ¡La encontramos!
                }
            }
        }
        return null; // No existe
    }
}
