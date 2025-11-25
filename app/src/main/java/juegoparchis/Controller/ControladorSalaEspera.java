package juegoparchis.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import juegoparchis.Model.Enum.Color;
import juegoparchis.Service.ConexionP2P;
import juegoparchis.Model.Jugador;

public class ControladorSalaEspera {
    //Atributos de la clase
    private Jugador yo;
    private List<Jugador> jugadoresConectados = new ArrayList<>();
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
        //Avisar a los jugadores que la partida va a iniciar
        conexion.enviarMensaje("IniciarPartida");
        irAlTablero();
    }

    /**
    * Método para inicializar la sala de espera desde los datos de la pantalla anterior
    */
    public void initData(String nombre, boolean esHost, String ip, Color color, String avatar) {
        this.miNombre = nombre;
        this.soyHost = esHost;
        this.yo = new Jugador(nombre, avatar, color);
        this.jugadoresConectados.add(yo);
        this.conexion = new ConexionP2P();
        //Mostrar mi nombre en la lista de jugadores
        agregarJugador(miNombre + " (Yo)");
        //Si es el host, habilita el botón para iniciar la partida
        if (soyHost) {
            configurarHost();
        } else {
            configurarCliente(ip);
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
        new Thread(() ->{
            try {
                Thread.sleep(500); // Espera medio segundo para asegurar la conexión
            } catch (InterruptedException e) { e.printStackTrace(); }
            conexion.enviarMensaje("Unirse:" + yo.getNombre() + ":" + yo.getColor());
        }).start();
    }
    //Método para procesar los mensajes entrantes
    private void procesarMensaje(String mensaje) {
        System.out.println("Procesando mensaje: " + mensaje);//Para depuración
        //En caso de que alguien se une
        if (mensaje.startsWith("Unirse:")) {
            String[] partes = mensaje.split(":");
            String nombreNuevoJugador = partes[1];
            Color colorNuevoJugador = Color.valueOf(partes[2]);
            //Luego vemos lo del avatar
            //Creamos el nuevo jugador y lo agregamos a la lista logica
            Jugador nuevoJugador = new Jugador(nombreNuevoJugador, "avatar1.jpg", colorNuevoJugador);
            jugadoresConectados.add(nuevoJugador);
            //Agrega el nuevo jugador a la lista visual
            agregarJugador(nombreNuevoJugador);
            //Si soy el host, notifico a jugadores(despues se implementará)
        }
        //En caso de que el host inicia la partida
        else if (mensaje.equals("IniciarPartida")) {
            Platform.runLater(this::irAlTablero);
        }
    }
    //Método para ir a la pantalla del tablero
    private void irAlTablero() {
        try {
            //Cambiar a la pantalla del tablero
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/View/VistaTablero.fxml"));
            javafx.scene.Parent root = loader.load();
            //Obtener el controlador de la pantalla del tablero
            ControladorPartida controladorPartida = loader.getController();
            //Inicializar los datos de la partida en el controlador
            controladorPartida.initData(jugadoresConectados, conexion, yo);
            javafx.stage.Stage stage = (javafx.stage.Stage) vboxJugadores.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
