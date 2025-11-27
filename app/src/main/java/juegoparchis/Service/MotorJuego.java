package juegoparchis.Service;

import java.util.List;
import java.util.ArrayList;
import juegoparchis.Model.Jugador;
import juegoparchis.Model.Ficha;
import juegoparchis.Model.Dado;
import juegoparchis.Model.Enum.Color;

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
    public Jugador getJugadorActual() { return jugadores.get(indiceTurnoActual); }
    //Método lanzarDado
    public int lanzarDado() { return dado.lanzar(); }
    //Método avanzarTurno
    public void avanzarTurno() {
        indiceTurnoActual++;
        if (indiceTurnoActual >= jugadores.size())  indiceTurnoActual = 0; // Reiniciar al primer jugador
    }
    //Método para dictar el turno de un jugador
    public void setTurno(String nombreJugador) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombre().equals(nombreJugador)) {
                this.indiceTurnoActual = i;
                System.out.println("DEBUG: Turno forzado a " + nombreJugador);
                return;
            }
        }
        System.out.println("Error: No se encontró al jugador " + nombreJugador);
    }

    //Método para saber que fichas se pueden mover
    public List<Ficha> obtenerFichasMovibles(int valorDado){
        Jugador jugadorActual = getJugadorActual();
        List<Ficha> movibles = new ArrayList<>();
        for (Ficha ficha : jugadorActual.getFichas()) {
            //Si la ficha está en casa (posición -1) y el dado muestra 5, puede moverse
            if (ficha.isEnCasa() && valorDado == 5 || !ficha.isEnCasa()) {
                   movibles.add(ficha); 
            }
        }
        return movibles;
    }
    
    //Método para mover una ficha
    public void realizarMovimiento(Ficha ficha, int pasos){
        //Sale de casa
        if (ficha.isEnCasa()) {
            int salida = 1;
            switch (ficha.getColor()) { //Por si da error en el futuro por no tener un default
                case AMARILLO: salida = 5; break;
                case VERDE:    salida = 22; break;
                case ROJO:     salida = 39; break;
                case AZUL:     salida = 56; break;
            }
            ficha.setPosicionActual(salida);
            ficha.setEnCasa(false);
            return;
        }
        //Mueve en pasillo
        if (ficha.isEnPasillo()) {
            moverEnPasillo(ficha, pasos);
            return;
        }
        //Movimiento en tablero
        int actual = ficha.getPosicionActual();
        int nueva = actual + pasos;
        int entrada = obtenerEntradaPasillo(ficha.getColor());
        // Caso especial Amarillo (68 -> 1)
        if (actual <= 68 && nueva > 68) {
            nueva -= 68;
            if (ficha.getColor() == Color.AMARILLO) {
                int pasosEnPasillo = (actual + pasos) - 68;
                moverEnPasillo(ficha, pasosEnPasillo);
                ficha.setEnPasillo(true);
                return;
            }
        }
        // Otros colores entrando al pasillo
        if (actual <= entrada && nueva > entrada && ficha.getColor() != Color.AMARILLO) {
            int pasosEnPasillo = nueva - entrada;
            moverEnPasillo(ficha, pasosEnPasillo);
            ficha.setEnPasillo(true);
        } else {
            ficha.setPosicionActual(nueva);
        }
    }

    //Método para mover una ficha en el pasillo
    private int obtenerEntradaPasillo(Color c) {
        switch (c) {
            case AMARILLO: return 68;
            case VERDE:    return 17;
            case ROJO:     return 34;
            case AZUL:     return 51;
            default: return 0;
        }
    }
    
    //Método para mover una ficha en el pasillo
    private void moverEnPasillo(Ficha ficha, int posicion) {
       if (posicion > 8) { // Rebote
            ficha.setPosicionActual(8 - (posicion - 8));
        } else {
            ficha.setPosicionActual(posicion);
            if (posicion == 8) ficha.setEnMeta(true);
        } 
    }
        
    
}
