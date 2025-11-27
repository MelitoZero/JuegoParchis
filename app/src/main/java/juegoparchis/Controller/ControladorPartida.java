package juegoparchis.Controller;

import java.io.InputStream;
import java.util.List;
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
import juegoparchis.Model.Jugador;
import juegoparchis.Model.Enum.Color;
import juegoparchis.Service.ConexionP2P;
import juegoparchis.Service.MotorJuego;
import juegoparchis.Util.CoordenadasTablero;
import juegoparchis.View.VistaFicha;
import javafx.util.Duration;
import javafx.animation.PauseTransition; 
import juegoparchis.Model.Movimiento;   

public class ControladorPartida {
    
    private MotorJuego motorJuego;
    private ConexionP2P conexion;
    private Jugador miJugador;
    private Dado dadoModelo;
    @SuppressWarnings("unused")
    private List<Jugador> jugadores;
    @FXML
    private Label lblTurnoJugador;
    @FXML
    private Button btnTirarDado;
    @FXML
    private ImageView imgDado, imgTablero, imgJugador1, imgJugador2, imgJugador3, imgJugador4, imgPerfil1, imgPerfil2;
    @FXML
    private Pane panelFichas;
    //Método que inicializa el controlador
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
    }
    //Método para inicializar los datos de la partida
    public void initData(List<Jugador> jugadores, ConexionP2P conexion, Jugador yo) {
        this.jugadores = jugadores;
        this.conexion = conexion;
        this.miJugador = yo;
        //Inicializar el motor de juego
        this.motorJuego = new MotorJuego(jugadores);
        //Mostrar las fichas de los jugadores en el tablero
        dibujarFichasJugadores(jugadores);
        //Mostrar el turno del jugador
        actualizarInfoTurno();
        conexion.setListener(mensaje -> procesarMensajeRed(mensaje));
    }
    //Método para lanzar el dado
    @FXML
    protected void lanzarDado(ActionEvent event) {
        btnTirarDado.setDisable(true); // Deshabilitar el botón para evitar múltiples lanzamientos
        //Pedir al motor de juego que lance el dado
        int valor = motorJuego.lanzarDado();
        actualizarImgDado(valor);
        System.out.println("Dado lanzado: " + valor);//Para depuración
        //Enviar el valor del dado a los demas jugadores
        if (conexion != null) {
            conexion.enviarMensaje("Dado:" + valor);
        }
        //Pedirle al motor de juego las fichas movibles
        List<Ficha> fichasMovibles = motorJuego.obtenerFichasMovibles(valor);
        if (fichasMovibles.isEmpty()) {
            System.out.println("No hay movimientos posibles. Pasa el turno.");
            pausaYPasarTurno();
        } else {
            System.out.println("Selecciona una ficha para mover.");
            habilitarFichas(fichasMovibles, valor);
        }    
    }
    //Método para dibujar las fichas de los jugadores en el tablero
    private void dibujarFichasJugadores(List<Jugador> listaJugadores) {
        panelFichas.getChildren().clear(); // Limpiar fichas anteriores
        for (Jugador jugador : listaJugadores) {
            //Obtener las fichas del jugador
            Ficha[] mFichas = jugador.getFichas();
            Color color = jugador.getColor();
            for(int i = 0; i < 4; i++){
                //Usamos la ficha del array
                Ficha fichaLogica = mFichas[i];
                Point2D posicion = null;
                //Verificar que la ficha tiene posición de casa
                if (fichaLogica.isEnCasa()) {
                    posicion = CoordenadasTablero.getCoordenadaCasa(color, i);
                }//Si no está en casa, obtener la posición en el tablero
                else {
                    posicion = CoordenadasTablero.getCoordenada(fichaLogica.getPosicionActual());
                }
                //Dibujar la ficha en el tablero si hay posición valida
                if (posicion != null) {
                        VistaFicha vistaFicha = new VistaFicha(fichaLogica, 15);
                        vistaFicha.setLayoutX(posicion.getX());
                        vistaFicha.setLayoutY(posicion.getY());
                        panelFichas.getChildren().add(vistaFicha);
                    }
            }
        }
    }
    //Método para actualizar la imagen del dado
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
    //Método para actualizar la información del turno
    private void actualizarInfoTurno() {
        //Pregunta al motor del juego quien sigue
        Jugador jugadorActual = motorJuego.getJugadorActual();
        lblTurnoJugador.setText("Turno de: " + jugadorActual.getNombre().toUpperCase());
        //Valida que sea yo(jugador local)
        if (jugadorActual.equals(miJugador)) {
            //El turno mio(jugador local)
            btnTirarDado.setDisable(false);
        }else {
            //Es turno de otro jugador
            btnTirarDado.setDisable(true);
        }
    }
    //Método para habilitar las fichas movibles
    private void habilitarFichas(List<Ficha> fichasMovibles, int valorDado) {
        //Si no es mi turno(jugador local)
        if (!motorJuego.getJugadorActual().equals(miJugador)) {
            return;
        }
        //Recorrer las fichas en el panelFichas
        for (javafx.scene.Node nodo : panelFichas.getChildren()) {
            if (nodo instanceof VistaFicha) {
                VistaFicha vista = (VistaFicha) nodo;
                Ficha fichaModelo = vista.getFichaModelo();
                //Si la ficha es movible, habilitarla
                if (fichasMovibles.contains(fichaModelo)) {
                    vista.setEffect(new javafx.scene.effect.DropShadow(15, javafx.scene.paint.Color.CHARTREUSE));//Habilitar visualmente
                    vista.setCursor(javafx.scene.Cursor.HAND); //Cambiar cursor a mano
                    //Agregar evento de clic para mover la ficha
                    vista.setOnMouseClicked(e ->{
                        System.out.println("Clic en ficha " + fichaModelo.getId());//Para depuración
                        //Mover la ficha seleccionada
                        moverFicha(vista, valorDado);
                        //Desactiva todos los click despues de mover
                        desactivarFichas();
                        //Pausa y pasar turno
                        //Refla pendiente por si saca 6(repetir turno)
                        motorJuego.avanzarTurno();
                        actualizarInfoTurno();
                        btnTirarDado.setDisable(false); // Habilitar el botón para el siguiente turno
                    });
                } else {
                    //Deshabilitar visualmente
                    vista.setEffect(null);
                    vista.setCursor(javafx.scene.Cursor.DEFAULT); //Cambiar cursor a default
                    vista.setOnMouseClicked(null); //Eliminar evento de clic
                }
            }
        }
    }
    //Método para desactivar todas las fichas
    private void desactivarFichas(){
        for (javafx.scene.Node nodo : panelFichas.getChildren()) {
            nodo.setOpacity(1.0);//Habilitar visualmente
            nodo.setOnMouseClicked(null);//Eliminar evento de clic
        }
    }
    //Método para pausar y pasar turno
    private void pausaYPasarTurno(){
        // Crear una pausa de 2 segundos antes de pasar el turno
        PauseTransition pausa = new PauseTransition(Duration.seconds(2));
        pausa.setOnFinished(event -> {
            motorJuego.avanzarTurno();
            actualizarInfoTurno();
            btnTirarDado.setDisable(false); // Habilitar el botón para el siguiente turno
        });
        pausa.play();// Iniciar la pausa
    }
    //Método para mover la ficha seleccionada
    private void moverFicha(VistaFicha vista, int pasos) {
        Ficha ficha = vista.getFichaModelo();
        int posicionActual = ficha.getPosicionActual();
        int nuevaPosicion;
        //Lógica para mover la ficha si esta en casa
        if (ficha.isEnCasa()) {
            //Si sale de la casa, asignar la posición inicial según el color
            switch (ficha.getColor()) {
                case AMARILLO: nuevaPosicion = 5; break;
                case VERDE: nuevaPosicion = 22; break;
                case ROJO: nuevaPosicion = 39; break;
                case AZUL: nuevaPosicion = 56; break;
                default: nuevaPosicion = 1; break;
            }
            ficha.setEnCasa(false);
        } else { //Lógica  por si  la ficha ya esta en el Tablero 
            //Si ya está en el tablero, avanzar según los pasos
            nuevaPosicion = posicionActual + pasos;
            if (nuevaPosicion > 68) nuevaPosicion = nuevaPosicion - 68; //Permite dar la vuelta al tablero
        }
        //Actualizar la posición de la ficha en el modelo
        ficha.setPosicionActual(nuevaPosicion);
        System.out.println("Moviendo ficha "+ ficha.getId() + " a posición " + nuevaPosicion);//Para depuración
        //Mover visualmente la ficha
        Point2D destino = CoordenadasTablero.getCoordenada(nuevaPosicion);
        if (destino != null) {
            //Cambiar por una animacion despues
            vista.setLayoutX(destino.getX());
            vista.setLayoutY(destino.getY());
        }
        //Solo envia si soy yo(jugador local) el que esta moviendo(mi turno)
        if (motorJuego.getJugadorActual().getNombre().equals(miJugador.getNombre())) {
            //Mueve segun su color, el id de la ficha y su casilla destino
            Movimiento mov = new Movimiento(ficha.getColor(), ficha.getId(), nuevaPosicion);
            conexion.enviarMensaje(mov.toString());
        }
    }
    //Método para recibir ordenes de otro jugador
    private void procesarMensajeRed(String mensaje){
        System.out.println("Juego recibio: "+ mensaje);
        //permite mover ficha segun color y su id
        if (mensaje.startsWith("Mover:")) {
            Movimiento mov = Movimiento.desdeString(mensaje);
            if (mov != null) {
                //Hace el movimiento visual 
                moverFichaRemota(mov.getColor(), mov.getIdFicha(), mov.getDestino());
            }
        } else if (mensaje.startsWith("Dado:")) { //Permite ver el valor de dado que saco otro jugador
            try {
                int valor = Integer.parseInt(mensaje.split(":")[1]);
                actualizarImgDado(valor);
                System.out.println("El rival saco un: " + valor);
            } catch (Exception e) {
                System.out.println("Error al procesar dado remoto");
            }
        }
    }
    //Método para poder mover ficha remota de los demas jugadores
    private void moverFichaRemota(Color color, int id, int casillaDestino){
        //Busca la ficha visual correcta en el panel
        for(javafx.scene.Node nodo : panelFichas.getChildren()) {
            //Verifica si es una ficha
            if (nodo instanceof VistaFicha) {
                VistaFicha vista = (VistaFicha) nodo;
                Ficha modelo = vista.getFichaModelo();
                //Identificar si es la ficha que se seleccionó
                if (modelo.getColor() == color && modelo.getId() == id) {
                    //Se encontro la ficha, lo mueve
                    System.out.println("Moviendo ficha remota: " + color + " " + id + " a " + casillaDestino);
                    Point2D destino = CoordenadasTablero.getCoordenada(casillaDestino);
                    //Validamos si es pasillo o casa por seguridad
                    if (destino != null) {
                        vista.setLayoutX(destino.getX());
                        vista.setLayoutY(destino.getY());
                        //Actualizamos el modelo remotamente
                        modelo.setPosicionActual(casillaDestino);
                        modelo.setEnCasa(false);
                        //Sinconizar turno visualmente
                        motorJuego.avanzarTurno();
                        actualizarInfoTurno();
                    }
                    break; //Ya encontrado, dejamos de buscar
                }
            }
        }
    }
}
