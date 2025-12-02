package juegoparchis.Service;

import java.util.List;
import java.util.ArrayList;
import juegoparchis.Model.Jugador;
import juegoparchis.Model.Tablero;
import juegoparchis.Model.Ficha;
import juegoparchis.Model.Casilla;
import juegoparchis.Model.Dado;
import juegoparchis.Model.Enum.Color;

public class MotorJuego {

    private List<Jugador> jugadores;
    private int indiceTurnoActual;
    private Dado dado;
    private Tablero tablero;

    //Constructor
    public MotorJuego(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.indiceTurnoActual = 0;
        this.dado = new Dado();
        this.tablero = new Tablero();       
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
            //Validaciones
            boolean puedeSalir = (ficha.isEnCasa() && valorDado == 5);
            boolean puedeMover = (!ficha.isEnCasa());
            //Si la ficha está en casa (posición -1) y el dado muestra 5, puede moverse
            if(puedeSalir || puedeMover){
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
            int posActual = ficha.getPosicionActual();
            int destino = posActual + pasos;
            moverEnPasillo(ficha, destino);
            return;
        }
        //Movimiento en tablero normal
        int actual = ficha.getPosicionActual();
        int nueva = actual + pasos;
        int entradaPasillo = obtenerEntradaPasillo(ficha.getColor());
        //Valida si entra al pasillo en color amarillo 
        if (ficha.getColor() == Color.AMARILLO) {
            // Si estaba antes del 68 y ahora pasa del 68
            if (actual <= 68 && nueva > 68) {
                int pasosEnPasillo = nueva - 68;
                entrarAPasillo(ficha, pasosEnPasillo);
                return; 
            }
        }else { //Caso de otros colores en pasillo
            // Si mi movimiento cruza mi entrada 
            if (actual <= entradaPasillo && nueva > entradaPasillo) {
                int pasosEnPasillo = nueva - entradaPasillo;
                entrarAPasillo(ficha, pasosEnPasillo);
                return;
            }
        }
        //Permite dar la vuelta al tablero
        if (nueva > 68) {
            nueva -= 68;
        }
        //Verificar si la casilla es segura
        Casilla casillaDestino = tablero.getCasilla(nueva);
        //Intentamos comer si no es segura la casilla o no es pasillo/meta
        if (casillaDestino != null && !casillaDestino.esSeguro()){
            verificarYComer(ficha, nueva);
        }
        //Finalmente se actualiza la posicion de la ficha local
        ficha.setPosicionActual(nueva);
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
    private void moverEnPasillo(Ficha ficha, int destinoCalculado) {
        //Lega a meta
        if (destinoCalculado == 8) {
           ficha.setPosicionActual(8);
            ficha.setEnMeta(true);
            System.out.println("¡Ficha llegó a la meta!"); 
        } else if (destinoCalculado > 8) { // Rebote, Se paso de la meta
            int rebote = 8 - (destinoCalculado - 8);
            ficha.setPosicionActual(rebote);
            System.out.println("Rebote en la meta de: " + destinoCalculado + " a " + rebote);
        } else { //Movimiento normal en pasillo
            ficha.setPosicionActual(destinoCalculado);
            if (destinoCalculado == 8) ficha.setEnMeta(true);
        } 
    }
    
    //Método que verifia y permite comer fichas
    public void verificarYComer(Ficha miFicha, int destino) {
        //Recorremos la lista de jugadores para ver quienes estan
        for (Jugador rival : jugadores){
            if (rival.getColor() == miFicha.getColor()) continue;
            for (Ficha fichaRival : rival.getFichas()) {
                //Verifica si el rival esta en la misma casilla que mi ficha
                if (fichaRival.getPosicionActual() == destino && !fichaRival.isEnCasa() && !fichaRival.isEnPasillo()) {
                    System.out.println("Captura de ficha, la ficha " + miFicha.getColor() + " se comio a la ficha de " + rival.getColor());
                    //Se le manda al rival su ficha a su casa
                    fichaRival.setEnCasa(true);
                    fichaRival.setPosicionActual(-1);
                    return;
                }
            }
        }
    }

    //Método para ver si entra a pasillo
    private void entrarAPasillo(Ficha ficha, int posicionPasillo){
        ficha.setEnPasillo(true); // Marcamos que ya no está en tablero
        moverEnPasillo(ficha, posicionPasillo);
   }
   
   //Método para verificar quien es el ganador
   public boolean verificarGanador(Jugador jugador) {
    int cuenta = 0;
    for (Ficha f : jugador.getFichas()) {
      if(f.isEnMeta()){
        cuenta++;
      }  
    }
    //Si hay 4 fichas en meta el jugador gana
    return cuenta == 4;
   }
}
