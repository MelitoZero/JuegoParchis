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
import juegoparchis.Model.Dado;
import juegoparchis.Model.Ficha;
import juegoparchis.Model.Jugador;
import juegoparchis.Model.Enum.Color;
import juegoparchis.Service.ConexionP2P;
import juegoparchis.Service.MotorJuego;
import juegoparchis.Util.NavegacionPantallas;
import juegoparchis.View.GestorFicha;
import juegoparchis.View.VistaFicha;
import javafx.util.Duration;
import javafx.animation.PauseTransition; 
import juegoparchis.Model.Movimiento;   

public class ControladorPartida {
    
    private MotorJuego motorJuego;
    private ConexionP2P conexion;
    private Jugador miJugador;
    private Dado dadoModelo;
    private GestorFicha gestorFicha;
    private List<Jugador> jugadores;
    @FXML
    private Label lblTurnoJugador;
    @FXML
    private Button btnTirarDado;
    @FXML
    private ImageView imgDado, imgTablero, imgJugador1, imgJugador2, imgJugador3, imgJugador4, imgPerfil1, imgPerfil2;
    @FXML
    private Pane panelFichas;

    @FXML //Método que inicializa el controlador
    public void initialize() {
        dadoModelo = new Dado();
        actualizarImgDado(dadoModelo.getValor());
    }

    //Método para inicializar los datos de la partida
    public void initData(List<Jugador> jugadores, ConexionP2P conexion, Jugador yo, String jugadorElegido) {
        this.jugadores = jugadores;
        this.conexion = conexion;
        this.miJugador = yo;
        this.gestorFicha = new GestorFicha(panelFichas);
        this.motorJuego = new MotorJuego(jugadores); //Inicializar el motor de juego
        this.motorJuego.setTurno(jugadorElegido);
        //Mostrar el turno del jugador
        actualizarInfoTurno();
        //Mostrar las fichas de los jugadores en el tablero
        gestorFicha.dibujarFichasIniciales(jugadores);
        //Configurar la conexión con el servidor
        conexion.setListener(mensaje -> procesarMensajeRed(mensaje));
    }

    @FXML //Método para lanzar el dado
    protected void lanzarDado(ActionEvent event) {
        btnTirarDado.setDisable(true); // Deshabilitar el botón para evitar múltiples lanzamientos
        //Pedir al motor de juego que lance el dado
        int valor = motorJuego.lanzarDado();
        actualizarImgDado(valor);
        //Enviar el valor del dado a los demas jugadores
        if (conexion != null) {
            conexion.enviarMensaje("Dado:" + valor);
        }
        //Pedirle al motor de juego las fichas movibles
        List<Ficha> fichasMovibles = motorJuego.obtenerFichasMovibles(valor);
        if (fichasMovibles.isEmpty()) {
            pausaYPasarTurno();
        } else {
            habilitarFichas(fichasMovibles, valor);
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
        Jugador jugadorDelTurno = motorJuego.getJugadorActual();
        lblTurnoJugador.setText("Turno de: " + jugadorDelTurno.getNombre());
        //Valida que sea yo(jugador local)
        if (jugadorDelTurno.getNombre().equals(miJugador.getNombre())) {
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
        if (!motorJuego.getJugadorActual().equals(miJugador)) return;
        //Recorrer las fichas en el panelFichas
        for (javafx.scene.Node nodo : panelFichas.getChildren()) {
            if (nodo instanceof VistaFicha) {
                VistaFicha vista = (VistaFicha) nodo;
                Ficha fichaModelo = vista.getFichaModelo();
                //Verifica que la ficha sea de mi color
                if (fichaModelo.getColor() != miJugador.getColor()) {
                    continue;
                }
                //Si la ficha es movible, habilitarla
                if (fichasMovibles.contains(fichaModelo)) {
                    vista.setEffect(new javafx.scene.effect.DropShadow(15, javafx.scene.paint.Color.CHARTREUSE));//Habilitar visualmente
                    vista.setCursor(javafx.scene.Cursor.HAND); //Cambiar cursor a mano
                    //Agregar evento de clic para mover la ficha
                    vista.setOnMouseClicked(e ->{
                        //Mover la ficha seleccionada
                        moverFicha(vista, valorDado);
                        //Desactiva todos los click despues de mover
                        desactivarFichas();
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
            //Cambia el turno localmente
            motorJuego.avanzarTurno();
            actualizarInfoTurno();
            //Avisar  al otro jugador
            if (conexion != null) {
                String quienSigue = motorJuego.getJugadorActual().getNombre();
                conexion.enviarMensaje("Turno:" + quienSigue);
            }
        });
        pausa.play();// Iniciar la pausa
    }

    //Método para mover la ficha seleccionada
    private void moverFicha(VistaFicha vista, int pasos) {
        Ficha ficha = vista.getFichaModelo();
        //Logica para mover la ficha
        motorJuego.realizarMovimiento(ficha, pasos);
        //Actualizar tablero si se come ficha
        gestorFicha.dibujarFichasIniciales(jugadores);
        //Lógica de anuncio de victoria
        if(motorJuego.verificarGanador(miJugador)){
            System.out.println("Ganaste!!");
            String ganadorYo = miJugador.getNombre();
            Movimiento mov = new Movimiento(ficha.getColor(), ficha.getId(), ficha.getPosicionActual(), ganadorYo, ficha.isEnPasillo());
            if(conexion != null) conexion.enviarMensaje(mov.toFormatoString());
            mostrarMensajeGanador("Felicidades", "¡Ganastes el juego!");
            terminarJuego();
            return;
        }
        //Envia si soy yo(jugador local) el que esta moviendo(mi turno)
        if (motorJuego.getJugadorActual().getNombre().equals(miJugador.getNombre())) {
            if (pasos != 6) {
                motorJuego.avanzarTurno();
            }else {
                System.out.println("Sacastes 6, repites turno");
                //Reactivamos boton para volver a tirar
                btnTirarDado.setDisable(false);
            }
            actualizarInfoTurno(); //Se acaba mi turno
            String nombreSiguiente = motorJuego.getJugadorActual().getNombre();
            //Envia
            Movimiento mov = new Movimiento(ficha.getColor(), ficha.getId(), ficha.getPosicionActual(), nombreSiguiente, ficha.isEnPasillo());
            if (conexion != null) conexion.enviarMensaje(mov.toFormatoString());
        }
    }

    //Método para recibir ordenes de otro jugador
    private void procesarMensajeRed(String mensaje){
        javafx.application.Platform.runLater(() ->{
            //permite mover ficha segun color y su id
            if (mensaje.startsWith("Mover:")) {
                Movimiento mov = Movimiento.desdeFormatoString(mensaje);
                if (mov != null) {
                    //Hace el movimiento visual 
                    moverFichaRemota(mov);
                }
            }else if (mensaje.startsWith("Dado:")) { //Permite ver el valor de dado que saco otro jugador
                try {
                    int valor = Integer.parseInt(mensaje.split(":")[1]);
                    actualizarImgDado(valor);
                } catch (Exception e) {
                    System.out.println("Error al procesar dado remoto");
                }
            }else if(mensaje.startsWith("Turno:")){
                try {
                    String nombreSiguiente = mensaje.split(":")[1];
                    //Sincroniza el motor
                    motorJuego.setTurno(nombreSiguiente);
                    //actualiza visualmente
                    actualizarInfoTurno();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });  
    }

    //Método para poder mover ficha remota de los demas jugadores
    private void moverFichaRemota(Movimiento mov){
        Color colorMov = mov.getColor();
        int idFichaMov = mov.getIdFicha();
        int destinoMov = mov.getDestino();
        VistaFicha vista = gestorFicha.buscarFichaVisual(colorMov, idFichaMov);
        if (vista != null) {
            Ficha modelo = vista.getFichaModelo();
                //Se encontro la ficha, lo mueve
                modelo.setPosicionActual(destinoMov);
                modelo.setEnCasa(false);
                modelo.setEnPasillo(mov.isEnPasillo());
                //Verificamos si se come ficha
                motorJuego.verificarYComer(modelo, destinoMov);
                //Aseguramos dibujar denuevo la ficha
                gestorFicha.dibujarFichasIniciales(jugadores);
                Jugador rival = buscarJugadorColor(colorMov);
                if(rival != null && motorJuego.verificarGanador(rival)){
                    mostrarMensajeGanador("Fin de la partida", "El jugador " + rival.getNombre() + " ganó.");
                    terminarJuego();
                    return;
                }
                //Sinconizar turno visualmente
                String quienSigue = mov.getSiguienteJugador();
                motorJuego.setTurno(quienSigue);
                actualizarInfoTurno();
        }
    }

    //Método para mostrar mensaje de ganador
    private void mostrarMensajeGanador(String titulo, String mensaje) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Partida acabada");
            alert.setHeaderText(titulo);
            alert.setContentText(mensaje);
            alert.showAndWait();
            NavegacionPantallas.cambiarPantalla(null, "/View/VistaMenu.fxml");
        });
    }

    //Método para terminar el juego
    private void terminarJuego() {
        btnTirarDado.setDisable(true);
        panelFichas.setMouseTransparent(true);
    }

    //Método para buscar jugador por color
    private Jugador buscarJugadorColor(Color c){
        for (Jugador j : jugadores) {
            if (j.getColor() == c) return j;
        }
        return null;
    }

}