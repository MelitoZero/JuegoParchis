package juegoparchis.View;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.effect.DropShadow;
import juegoparchis.Model.Ficha;


public class VistaFicha extends StackPane {
    //Guardamos la referencia a la ficha del modelo
    private Ficha fichaModelo;

    //Constructor
    public VistaFicha(Ficha ficha, double radio) {
        this.fichaModelo = ficha;
        //Se obteiene el color del modelo
        juegoparchis.Model.Enum.Color colorFicha = ficha.getColor();
        //Creamos la ficha visual en pantalla
        javafx.scene.paint.Color colorVista = convertirColor(colorFicha);
        //Creamos el circulo que representa la ficha
        Circle circuloFicha = new Circle(radio);
        circuloFicha.setFill(colorVista);//Color de la ficha
        circuloFicha.setStroke(javafx.scene.paint.Color.WHITE);//Borde blanco
        circuloFicha.setStrokeWidth(radio * 0.2);
        //Efecto de sombra
        DropShadow sombra = new DropShadow();
        sombra.setRadius(5.0);
        sombra.setOffsetX(2.0);
        sombra.setOffsetY(2.0);
        sombra.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.5));
        circuloFicha.setEffect(sombra);
        //Añadimos el circulo al StackPane
        this.getChildren().add(circuloFicha);
    }
    //Metodo para convertir el color del modelo al color de JavaFX
    private javafx.scene.paint.Color convertirColor(juegoparchis.Model.Enum.Color colorFicha) {
        if (colorFicha == null) return javafx.scene.paint.Color.GRAY;
        switch (colorFicha) {
            case ROJO:
                return javafx.scene.paint.Color.RED;
            case AZUL:
                return javafx.scene.paint.Color.BLUE;
            case VERDE:
                return javafx.scene.paint.Color.GREEN;
            case AMARILLO:
                return javafx.scene.paint.Color.YELLOW;
            default:
                return javafx.scene.paint.Color.GRAY; // Color por defecto
        }
    }

    //Getter para cuando se hace click
    public Ficha getFichaModelo() {
        return fichaModelo;
    }
}
