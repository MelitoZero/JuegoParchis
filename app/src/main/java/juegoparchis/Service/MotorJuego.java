package juegoparchis.Service;

import java.util.List;
import java.util.ArrayList;
import juegoparchis.Model.Jugador;
import juegoparchis.Model.Ficha;
import juegoparchis.Model.Dado;

public class MotorJuego {

    private List<Jugador> jugadores;
    private int indiceTurnoActual;
    private Dado dado;

    //Constructor
    public MotorJuego(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.indiceTurnoActual = 0;
        this.dado = new Dado();        
    }
    //Método getJugadorActual
    public Jugador getJugadorActual() {
        return jugadores.get(indiceTurnoActual);
    }
    //Método lanzarDado
    public int lanzarDado() {
        return dado.lanzar();
    }
    //Método avanzarTurno
    public void avanzarTurno() {
        indiceTurnoActual++;
        if (indiceTurnoActual >= jugadores.size()) {
            indiceTurnoActual = 0;// Reiniciar al primer jugador
        }
    }
    /**
     * Método parra mover ficha segun el valor del dado
     */
    public List<Ficha> obtenerFichasMovibles(int valorDado){
        Jugador jugadorActual = getJugadorActual();
        List<Ficha> movibles = new ArrayList<>();
        for (Ficha ficha : jugadorActual.getFichas()) {
            //Regla para mover ficha fuera de la casa
            //Si la ficha está en casa (posición -1) y el dado muestra 5, puede moverse
            if (ficha.getPosicionActual() == -1) {
                if (valorDado == 5) {
                   movibles.add(ficha); 
                }
            }
            //Regla para mover ficha en el tablero
            else {
                //Ficha está en el tablero
                //Aquí puedes agregar reglas adicionales para mover fichas en el tablero
                movibles.add(ficha); //Por ahora, todas las fichas fuera de casa son movibles
            }
        }
        return movibles;
    }
}
