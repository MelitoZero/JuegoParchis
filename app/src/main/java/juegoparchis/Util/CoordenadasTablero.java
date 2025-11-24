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
        //Ejemplo de inicialización (deberías completar todas las coordenadas necesarias)
        recorrido.put(1, new Point2D(542.0, 876.0));
        recorrido.put(2, new Point2D(535.0, 829.0));
        recorrido.put(3, new Point2D(543.0, 783.0));
        recorrido.put(4, new Point2D(539.0, 743.0));
        recorrido.put(5, new Point2D(542.0, 696.0));
        recorrido.put(6, new Point2D(545.0, 654.0));
        recorrido.put(7, new Point2D(541.0, 608.0));
        recorrido.put(8, new Point2D(530.0, 560.0));
        recorrido.put(9, new Point2D(563.0, 522.0));
        recorrido.put(10, new Point2D(611.0, 540.0));
        recorrido.put(11, new Point2D(651.0, 540.0));
        recorrido.put(12, new Point2D(700.0, 542.0));
        recorrido.put(13, new Point2D(745.0, 540.0));
        recorrido.put(14, new Point2D(789.0, 540.0));
        recorrido.put(15, new Point2D(825.0, 552.0));
        recorrido.put(16, new Point2D(877.0, 536.0));
        recorrido.put(17, new Point2D(876.0, 446.0));
        recorrido.put(18, new Point2D(875.0, 353.0));
        recorrido.put(19, new Point2D(831.0, 350.0));
        recorrido.put(20, new Point2D(787.0, 350.0));
        recorrido.put(21, new Point2D(741.0, 356.0));
        recorrido.put(22, new Point2D(694.0, 356.0));
        recorrido.put(23, new Point2D(652.0, 356.0));
        recorrido.put(24, new Point2D(608.0, 356.0));
        recorrido.put(25, new Point2D(564.0, 366.0));
        recorrido.put(26, new Point2D(521.0, 342.0));
        recorrido.put(27, new Point2D(527.0, 288.0));
        recorrido.put(28, new Point2D(536.0, 248.0));
        recorrido.put(29, new Point2D(538.0, 202.0));
        recorrido.put(30, new Point2D(539.0, 156.0));
        recorrido.put(31, new Point2D(539.0, 108.0));
        recorrido.put(32, new Point2D(541.0, 67.0));
        recorrido.put(33, new Point2D(541.0, 19.0));
        recorrido.put(34, new Point2D(452.0, 18.0));
        recorrido.put(35, new Point2D(363.0, 19.0));
        recorrido.put(36, new Point2D(360.0, 63.0));
        recorrido.put(37, new Point2D(361.0, 111.0));
        recorrido.put(38, new Point2D(362.0, 154.0));
        recorrido.put(39, new Point2D(358.0, 196.0));
        recorrido.put(40, new Point2D(361.0, 245.0));
        recorrido.put(41, new Point2D(364.0, 285.0));
        recorrido.put(42, new Point2D(372.0, 331.0));
        recorrido.put(43, new Point2D(336.0, 366.0));
        recorrido.put(44, new Point2D(290.0, 355.0));
        recorrido.put(45, new Point2D(248.0, 354.0));
        recorrido.put(46, new Point2D(203.0, 355.0));
        recorrido.put(47, new Point2D(160.0, 353.0));
        recorrido.put(48, new Point2D(114.0, 356.0));
        recorrido.put(49, new Point2D(66.0, 357.0));
        recorrido.put(50, new Point2D(22.0, 353.0));
        recorrido.put(51, new Point2D(23.0, 445.0));
        recorrido.put(52, new Point2D(24.0, 541.0));
        recorrido.put(53, new Point2D(68.0, 539.0));
        recorrido.put(54, new Point2D(110.0, 539.0));
        recorrido.put(55, new Point2D(160.0, 543.0));
        recorrido.put(56, new Point2D(200.0, 545.0));
        recorrido.put(57, new Point2D(247.0, 539.0));
        recorrido.put(58, new Point2D(293.0, 539.0));
        recorrido.put(59, new Point2D(337.0, 527.0));
        recorrido.put(60, new Point2D(376.0, 557.0));
        recorrido.put(61, new Point2D(358.0, 605.0));
        recorrido.put(62, new Point2D(364.0, 647.0));
        recorrido.put(63, new Point2D(360.0, 695.0));
        recorrido.put(64, new Point2D(361.0, 739.0));
        recorrido.put(65, new Point2D(363.0, 789.0));
        recorrido.put(66, new Point2D(360.0, 832.0));
        recorrido.put(67, new Point2D(360.0, 878.0));
        recorrido.put(68, new Point2D(449.0, 875.0));

        //Pasillo Rojo
        List<Point2D> pasilloRojo = new ArrayList<>();
        pasilloRojo.add(new Point2D(448, 66));
        pasilloRojo.add(new Point2D(454, 106));
        pasilloRojo.add(new Point2D(458, 152));
        pasilloRojo.add(new Point2D(456, 196));
        pasilloRojo.add(new Point2D(456, 247));
        pasilloRojo.add(new Point2D(454, 290));
        pasilloRojo.add(new Point2D(448, 335));
        pasilloRojo.add(new Point2D(449, 394));
        //Guardamos la lista del pasillo rojo
        pasillos.put(Color.ROJO, pasilloRojo);
        //Pasillo amarillo
        List<Point2D> pasilloAmarillo = new ArrayList<>();
        pasilloAmarillo.add(new Point2D(450, 830));
        pasilloAmarillo.add(new Point2D(450, 784));
        pasilloAmarillo.add(new Point2D(450, 741));
        pasilloAmarillo.add(new Point2D(450, 694));
        pasilloAmarillo.add(new Point2D(450, 648));
        pasilloAmarillo.add(new Point2D(450, 606));
        pasilloAmarillo.add(new Point2D(450, 551));
        pasilloAmarillo.add(new Point2D(450, 492));
        //Guardamos la lista del pasillo amarillo
        pasillos.put(Color.AMARILLO, pasilloAmarillo);
        //Pasillo verde
        List<Point2D> pasilloVerde = new ArrayList<>();
        pasilloVerde.add(new Point2D(834, 448));
        pasilloVerde.add(new Point2D(789, 448));
        pasilloVerde.add(new Point2D(744, 448));
        pasilloVerde.add(new Point2D(698, 448));
        pasilloVerde.add(new Point2D(656, 448));
        pasilloVerde.add(new Point2D(608, 448));
        pasilloVerde.add(new Point2D(556, 448));
        pasilloVerde.add(new Point2D(500, 448));
        //Guardamos la lista del pasillo verde
        pasillos.put(Color.VERDE, pasilloVerde);
        //Pasillo azul
        List<Point2D> pasilloAzul = new ArrayList<>();
        pasilloAzul.add(new Point2D(65, 448));
        pasilloAzul.add(new Point2D(113, 448));
        pasilloAzul.add(new Point2D(159, 448));
        pasilloAzul.add(new Point2D(200, 448));
        pasilloAzul.add(new Point2D(248, 448));
        pasilloAzul.add(new Point2D(290, 448));
        pasilloAzul.add(new Point2D(338, 448));
        pasilloAzul.add(new Point2D(396, 448));
        //Guardamos la lista del pasillo azul
        pasillos.put(Color.AZUL, pasilloAzul);

        //Casas
        //Casa roja
        List<Point2D> casaRoja = new ArrayList<>();
        casaRoja.add(new Point2D(111, 106));
        casaRoja.add(new Point2D(209, 106));
        casaRoja.add(new Point2D(209, 200));
        casaRoja.add(new Point2D(111, 200));
        casas.put(Color.ROJO, casaRoja);
        //Casa amarilla
        List<Point2D> casaAmarilla = new ArrayList<>();
        casaAmarilla.add(new Point2D(692, 688));
        casaAmarilla.add(new Point2D(792, 688));
        casaAmarilla.add(new Point2D(692, 788));
        casaAmarilla.add(new Point2D(792, 788));
        casas.put(Color.AMARILLO, casaAmarilla);
        //Casa verde
        List<Point2D> casaVerde = new ArrayList<>();
        casaVerde.add(new Point2D(692, 105));
        casaVerde.add(new Point2D(792, 105));
        casaVerde.add(new Point2D(692, 205));
        casaVerde.add(new Point2D(792, 205));
        casas.put(Color.VERDE, casaVerde);
        //Casa azul
        List<Point2D> casaAzul = new ArrayList<>();
        casaAzul.add(new Point2D(111, 688));
        casaAzul.add(new Point2D(209, 688));
        casaAzul.add(new Point2D(111, 788));
        casaAzul.add(new Point2D(209, 788));
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
