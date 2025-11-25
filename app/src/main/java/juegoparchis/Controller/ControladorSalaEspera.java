package juegoparchis.Controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import juegoparchis.Model.Enum.Color;
import juegoparchis.Service.ConexionP2P;
import juegoparchis.Util.NavegacionPantallas;

public class ControladorSalaEspera {
    
    private ConexionP2P conexion;
    private String miNombre;
    private boolean soyHost;
    @FXML
    private VBox vboxJugadores;
    @FXML
    private Button btnUnirse;
    //Método para unirse a la partida desde la sala de espera
    @FXML
    protected void iniciarPartida(ActionEvent event) {
        //Lógica para unirse a la partida
        //Cambiar a la pantalla del juego
        NavegacionPantallas.cambiarPantalla(event, "/View/VistaTablero.fxml");
    }

    /**
    * Método para inicializar la sala de espera desde los datos de la pantalla anterior
    */
    public void initData(String nombreJugador, boolean soyHost, String ipDestino, Color colorSeleccionado, String avatarSeleccionado) {
        this.miNombre = nombreJugador;
        this.soyHost = soyHost;
        this.conexion = new ConexionP2P();
        //Me agrego a la lista
        agregarJugador(miNombre + "(Yo)");
        //Si es el host, habilita el botón para iniciar la partida
        if (soyHost) {
            configurarHost();
        } else {
            configurarCliente(ipDestino);
        }

    }
    //Método para actualizar la lista de jugadores en la sala de espera
    public void agregarJugador(String nombre) {
        Platform.runLater(() -> {
            //Crea un label por cada jugador en la sala de espera
            Label jugadorLabel = new Label(nombre);
            //Establece el estilo del label
            jugadorLabel.getStyleClass().add("letras-jugadores-estilo");
                    //Agrega el label al VBox que contiene la lista de jugadores
        vboxJugadores.getChildren().add(jugadorLabel);
        });
    }
    //Método para configurar la conexión como host
    private void configurarHost() {
        btnUnirse.setVisible(true);
        btnUnirse.setDisable(false);
        conexion.iniciarServidor(mensaje -> procesarMensaje(mensaje));
    }
    //Método para configurar la conexión como cliente
    private void configurarCliente(String ipDestino) {
        btnUnirse.setVisible(false);
        btnUnirse.setDisable(true);
        //Conecta al host y establece el manejador de mensajes entrantes
        conexion.conectarAlServidor(ipDestino, mensaje -> procesarMensaje(mensaje));
        new Thread(() -> {
            try {
                Thread.sleep(500); // Espera medio segundo para asegurar la conexión
                conexion.enviarMensaje("Hola Nuevo Jugador: " + miNombre);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //Método para procesar los mensajes entrantes
    private void procesarMensaje(String mensaje) {
        System.out.println("Procesando mensaje: " + mensaje);//Para depuración
        if (mensaje.startsWith("Hola Nuevo Jugador: ")) {
            String nombreNuevoJugador = mensaje.split(":")[1];
            //Agrega el nuevo jugador a la lista
            agregarJugador(nombreNuevoJugador);
            //Si soy el host, notifico a los demás jugadores del nuevo jugador
            if (soyHost) {
                conexion.enviarMensaje("Hola Jugador: " + miNombre);
            }
        } else if (mensaje.startsWith("Hola Jugador: ")) {
            String nombreHost = mensaje.split(":")[1];
            agregarJugador(nombreHost + " (Host)");
        }
    }
}
