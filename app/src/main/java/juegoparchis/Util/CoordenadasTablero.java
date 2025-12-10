package juegoparchis.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import juegoparchis.Model.Enum.Color;

public class CoordenadasTablero {
    //Mapa de coordenadas para cada posición del tablero principal (1-68 casillas)
    private static final Map<Integer, Point2D> recorrido = new HashMap<>();
    //Mapa de coordenadas para los pasillos de cada color
    private static final Map<Color, List<Point2D>> pasillos = new HashMap<>();
    //Mapa para coordenadas de las casas
    private static final Map<Color, List<Point2D>> casas = new HashMap<>();

    //Bloque estático para inicializar las coordenadas
    static {
        cargarCoordenadas();
    }

    private static void cargarCoordenadas() {
        // Aquí se inicializarán las coordenadas para el recorrido y los pasillos
        //"Pasillo Amarillo"
        recorrido.put(1, new Point2D(555.0, 878.0));
        recorrido.put(2, new Point2D(555.0, 833.0));
        recorrido.put(3, new Point2D(555.0, 788.0));
        recorrido.put(4, new Point2D(555.0, 743.0));
        recorrido.put(5, new Point2D(555.0, 698.0));
        recorrido.put(6, new Point2D(555.0, 653.0));
        recorrido.put(7, new Point2D(555.0, 608.0));
        recorrido.put(8, new Point2D(525.0, 563.0));
        recorrido.put(9, new Point2D(565.0, 520.0));
        //"Pasillo Amarillo/verde"
        recorrido.put(10, new Point2D(611.0, 558.0));
        recorrido.put(11, new Point2D(656.0, 558.0));
        recorrido.put(12, new Point2D(701.0, 558.0));
        recorrido.put(13, new Point2D(746.0, 558.0));
        recorrido.put(14, new Point2D(791.0, 558.0));
        recorrido.put(15, new Point2D(836.0, 558.0));
        recorrido.put(16, new Point2D(880.0, 558.0));
        recorrido.put(17, new Point2D(880.0, 450.0));
        //"Pasillo Verde"
        recorrido.put(18, new Point2D(880.0, 338.0));
        recorrido.put(19, new Point2D(835.0, 338.0));
        recorrido.put(20, new Point2D(790.0, 338.0));
        recorrido.put(21, new Point2D(745.0, 338.0));
        recorrido.put(22, new Point2D(700.0, 338.0));
        recorrido.put(23, new Point2D(655.0, 338.0));
        recorrido.put(24, new Point2D(610.0, 338.0));
        recorrido.put(25, new Point2D(563.0, 375.0));
        //"Pasillo Verde/rojo"
        recorrido.put(26, new Point2D(520.0, 338.0));
        recorrido.put(27, new Point2D(560.0, 290.0));
        recorrido.put(28, new Point2D(542.0, 245.0));
        recorrido.put(29, new Point2D(542.0, 200.0));
        recorrido.put(30, new Point2D(542.0, 155.0));
        recorrido.put(31, new Point2D(542.0, 110.0));
        recorrido.put(32, new Point2D(542.0, 65.0));
        recorrido.put(33, new Point2D(542.0, 20.0));
        recorrido.put(34, new Point2D(452.0, 20.0));
        //"Pasillo Rojo"
        recorrido.put(35, new Point2D(342.0, 20.0));
        recorrido.put(36, new Point2D(342.0, 65.0));
        recorrido.put(37, new Point2D(342.0, 110.0));
        recorrido.put(38, new Point2D(342.0, 155.0));
        recorrido.put(39, new Point2D(342.0, 200.0));
        recorrido.put(40, new Point2D(342.0, 245.0));
        recorrido.put(41, new Point2D(342.0, 290.0));
        recorrido.put(42, new Point2D(380.0, 338.0));
        //"Pasillo Rojo/azul"
        recorrido.put(43, new Point2D(338.0, 378.0));
        recorrido.put(44, new Point2D(292.0, 338.0));
        recorrido.put(45, new Point2D(250.0, 338.0));
        recorrido.put(46, new Point2D(204.0, 338.0));
        recorrido.put(47, new Point2D(158.0, 338.0));
        recorrido.put(48, new Point2D(114.0, 338.0));
        recorrido.put(49, new Point2D(70.0, 338.0));
        recorrido.put(50, new Point2D(24.0, 338.0));
        recorrido.put(51, new Point2D(24.0, 450.0));
        //"Pasillo Azul"
        recorrido.put(52, new Point2D(24.0, 558.0));
        recorrido.put(53, new Point2D(68.0, 558.0));
        recorrido.put(54, new Point2D(114.0, 558.0));
        recorrido.put(55, new Point2D(158.0, 558.0));
        recorrido.put(56, new Point2D(205.0, 558.0));
        recorrido.put(57, new Point2D(248.0, 558.0));
        recorrido.put(58, new Point2D(292.0, 558.0));
        recorrido.put(59, new Point2D(332.0, 520.0));
        //"Pasillo Azul/amarillo"
        recorrido.put(60, new Point2D(370.0, 563.0));
        recorrido.put(61, new Point2D(340.0, 608.0));
        recorrido.put(62, new Point2D(340.0, 653.0));
        recorrido.put(63, new Point2D(340.0, 698.0));
        recorrido.put(64, new Point2D(340.0, 743.0));
        recorrido.put(65, new Point2D(340.0, 788.0));
        recorrido.put(66, new Point2D(340.0, 833.0));
        recorrido.put(67, new Point2D(340.0, 878.0));
        recorrido.put(68, new Point2D(450.0, 878.0));

        //Pasillo Rojo
        List<Point2D> pasilloRojo = new ArrayList<>();
        pasilloRojo.add(new Point2D(452, 63));
        pasilloRojo.add(new Point2D(452, 108));
        pasilloRojo.add(new Point2D(452, 153));
        pasilloRojo.add(new Point2D(452, 198));
        pasilloRojo.add(new Point2D(452, 243));
        pasilloRojo.add(new Point2D(452, 288));
        pasilloRojo.add(new Point2D(452, 342));
        pasilloRojo.add(new Point2D(452, 400));
        //Guardamos la lista del pasillo rojo
        pasillos.put(Color.ROJO, pasilloRojo);
        //Pasillo amarillo
        List<Point2D> pasilloAmarillo = new ArrayList<>();
        pasilloAmarillo.add(new Point2D(452, 833));
        pasilloAmarillo.add(new Point2D(452, 788));
        pasilloAmarillo.add(new Point2D(452, 743));
        pasilloAmarillo.add(new Point2D(452, 698));
        pasilloAmarillo.add(new Point2D(452, 653));
        pasilloAmarillo.add(new Point2D(452, 608));
        pasilloAmarillo.add(new Point2D(452, 553));
        pasilloAmarillo.add(new Point2D(452, 500));
        //Guardamos la lista del pasillo amarillo
        pasillos.put(Color.AMARILLO, pasilloAmarillo);
        //Pasillo verde
        List<Point2D> pasilloVerde = new ArrayList<>();
        pasilloVerde.add(new Point2D(836, 453));
        pasilloVerde.add(new Point2D(791, 453));
        pasilloVerde.add(new Point2D(746, 453));
        pasilloVerde.add(new Point2D(701, 453));
        pasilloVerde.add(new Point2D(656, 453));
        pasilloVerde.add(new Point2D(611, 453));
        pasilloVerde.add(new Point2D(563, 453));
        pasilloVerde.add(new Point2D(500, 453));
        //Guardamos la lista del pasillo verde
        pasillos.put(Color.VERDE, pasilloVerde);
        //Pasillo azul
        List<Point2D> pasilloAzul = new ArrayList<>();
        pasilloAzul.add(new Point2D(66, 453));
        pasilloAzul.add(new Point2D(111, 453));
        pasilloAzul.add(new Point2D(156, 453));
        pasilloAzul.add(new Point2D(201, 453));
        pasilloAzul.add(new Point2D(246, 453));
        pasilloAzul.add(new Point2D(291, 453));
        pasilloAzul.add(new Point2D(343, 453));
        pasilloAzul.add(new Point2D(403, 453));
        //Guardamos la lista del pasillo azul
        pasillos.put(Color.AZUL, pasilloAzul);

        //Casas
        //Casa roja
        List<Point2D> casaRoja = new ArrayList<>();
        casaRoja.add(new Point2D(99, 99));
        casaRoja.add(new Point2D(189, 99));
        casaRoja.add(new Point2D(189, 189));
        casaRoja.add(new Point2D(99, 189));
        casas.put(Color.ROJO, casaRoja);
        //Casa amarilla
        List<Point2D> casaAmarilla = new ArrayList<>();
        casaAmarilla.add(new Point2D(679, 687));
        casaAmarilla.add(new Point2D(772, 687));
        casaAmarilla.add(new Point2D(679, 767));
        casaAmarilla.add(new Point2D(772, 767));
        casas.put(Color.AMARILLO, casaAmarilla);
        //Casa verde
        List<Point2D> casaVerde = new ArrayList<>();
        casaVerde.add(new Point2D(679, 99));
        casaVerde.add(new Point2D(772, 99));
        casaVerde.add(new Point2D(679, 189));
        casaVerde.add(new Point2D(772, 189));
        casas.put(Color.VERDE, casaVerde);
        //Casa azul
        List<Point2D> casaAzul = new ArrayList<>();
        casaAzul.add(new Point2D(99, 687));
        casaAzul.add(new Point2D(185, 687));
        casaAzul.add(new Point2D(99, 767));
        casaAzul.add(new Point2D(185, 767));
        casas.put(Color.AZUL, casaAzul);
    }
    public static Point2D getCoordenada(int numeroCasilla) {
        return recorrido.get(numeroCasilla);
    }
    //Método para obtener la coordenada de una posición en el pasillo de un color específico
    public static Point2D getCoordenadaPasillo(Color color, int posicion) {
        List<Point2D> listaPasillo = pasillos.get(color);
        if (listaPasillo != null && posicion >= 0 && posicion < listaPasillo.size()) {
            return listaPasillo.get(posicion);
        }
        return null;
    }
    //Método para obtener la coordenada de una casa de un color específico
    public static Point2D getCoordenadaCasa(Color color, int posicion) {
        List<Point2D> listaCasa = casas.get(color);
        if (listaCasa != null && posicion >= 0 && posicion < listaCasa.size()) {
            return listaCasa.get(posicion);
        }
        return null;
    }

}
