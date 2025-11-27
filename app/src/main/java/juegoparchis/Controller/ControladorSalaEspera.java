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
    private Button btnIniciar;
    //Método para unirse a la partida desde la sala de espera
    @FXML
    protected void iniciarPartida(ActionEvent event) {
        //Hace una lista con todos los jugadores
        StringBuilder sb = new StringBuilder("Lista:");
        for (Jugador j : jugadoresConectados) {
            sb.append(j.getNombre()).append(":").append(j.getColor()).append(",");
        }
        //Envia la lista de jugadores a todos
        conexion.enviarMensaje(sb.toString());
        //Arranca el tablero
        new Thread(() ->{
           try { Thread.sleep(500); } catch (Exception e) {}
            conexion.enviarMensaje("IniciarPartida");
            Platform.runLater(this::irAlTablero); // El Host también va 
        }).start();
    }
    //Método para inicializar la sala de espera desde los datos de la pantalla anterior
    public void initData(String nombre, boolean esHost, String ip, Color color, String avatar) {
        System.out.println("DEBUG: initData iniciado. Host: " + esHost + ", IP: " + ip);
        this.miNombre = nombre;
        this.soyHost = esHost;
        this.yo = new Jugador(nombre, avatar, color);
        this.jugadoresConectados.add(yo);
        this.conexion = new ConexionP2P();
        if(conexion == null) System.out.println("ERROR FATAL: La conexión llegó NULL");
        else System.out.println("Conexión recibida correctamente");
        //Mostrar mi nombre en la lista de jugadores
        actualizarListaVisual();
        //Si es el host, habilita el botón para iniciar la partida
        if (soyHost) {
            configurarHost();
        } else {
            configurarCliente(ip);
        }
    }
    //Método para configurar la conexión como host
    private void configurarHost() {
        btnIniciar.setVisible(true);
        btnIniciar.setDisable(true);//modificado
        conexion.iniciarServidor(mensaje -> procesarMensaje(mensaje));
    }
    //Método para configurar la conexión como cliente
    private void configurarCliente(String ipDestino) {
        btnIniciar.setVisible(false);
        btnIniciar.setDisable(true);
        //Conecta al host y establece el manejador de mensajes entrantes
        conexion.conectarAlServidor(ipDestino, mensaje -> procesarMensaje(mensaje));
        new Thread(() ->{
            try {
                Thread.sleep(500); // Espera medio segundo para asegurar la conexión
            } catch (InterruptedException e) { e.printStackTrace(); }
            //Envia un saludo inicial
            conexion.enviarMensaje("Unirse:" + yo.getNombre() + ":" + yo.getColor());
        }).start();
    }
    //Método para procesar los mensajes entrantes
    private void procesarMensaje(String mensaje) {
        //En caso de que alguien se une
        if (mensaje.startsWith("Unirse:")) {
            String[] partes = mensaje.split(":");
            String nombreNuevoJugador = partes[1];
            Color colorNuevoJugador = Color.valueOf(partes[2]);
            //Luego vemos lo del avatar
            if (!nombreNuevoJugador.equals(miNombre)) {
                boolean yaExiste = jugadoresConectados.stream().anyMatch(j -> j.getNombre().equals(nombreNuevoJugador));
                if (!yaExiste) {
                    //Creamos el nuevo jugador y lo agregamos a la lista logica
                    Jugador nuevo = new Jugador(nombreNuevoJugador, "avatar1.jpg", colorNuevoJugador);
                    jugadoresConectados.add(nuevo);
                    actualizarListaVisual();
                    //Si soy el host, notifico a los demas
                    if (soyHost) {
                        Platform.runLater(() -> btnIniciar.setDisable(false));
                        //Envio datos a los demas para que me agreguen
                        conexion.enviarMensaje("Bienvenido:"+ yo.getNombre() + ":" + yo.getColor());
                        //Presenta los demas jugadores a los nuevos conectados
                        for (Jugador existente : jugadoresConectados){
                            //Evita que se repitan el nombre del host y suyo
                            if (!existente.getNombre().equals(nombreNuevoJugador) && !existente.getNombre().equals(miNombre)) {
                                try{ Thread.sleep(100);} catch (Exception e) {}
                                conexion.enviarMensaje("Unirse:" + existente.getNombre() + ":" + existente.getColor());
                            }
                        }
                    }
                }
            }
        }else if (mensaje.startsWith("Bienvenido:")){ //El host saluda
                String[] partes = mensaje.split(":");
                String nombreHost = partes[1];
                Color colorHost = Color.valueOf(partes[2]);
                Jugador hostJugador = new Jugador(nombreHost, "avatar1.jpg", colorHost);
                jugadoresConectados.add(0, hostJugador);
                actualizarListaVisual();
        }else if (mensaje.startsWith("Lista:")){
            // Limpiamos la lista desordenada local
            jugadoresConectados.clear();
            // Parseamos la lista del Host
            String data = mensaje.substring(6); // Quitar "Lista:"
            String[] jugadoresRaw = data.split(",");
            for (String jRaw : jugadoresRaw) {
                if (!jRaw.isEmpty()) {
                    String[] partes = jRaw.split(":");
                    String nombre = partes[0];
                    Color color = Color.valueOf(partes[1]);
                    // Reconstruimos el objeto Jugador
                    if (nombre.equals(miNombre)) {
                        jugadoresConectados.add(yo);
                    } else {
                        jugadoresConectados.add(new Jugador(nombre, "avatar", color));
                    }
                }
            }
            System.out.println("Lista sincronizada con el Host: " + jugadoresConectados.size());
            actualizarListaVisual();
        }else if (mensaje.equals("IniciarPartida")) { //En caso de que el host inicia la partida
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
    //Método para actualizar la lista visual de jugadores
    private void actualizarListaVisual() {
        Platform.runLater(() -> {
            vboxJugadores.getChildren().clear(); // Borra todo
            int i = 1;
            for (Jugador j : jugadoresConectados) {
                String texto = i + ": " + j.getNombre();
                if (j.getNombre().equals(miNombre)) {
                    texto += " (Yo)";
                }
                Label lbl = new Label(texto);
                lbl.getStyleClass().add("letras-jugadores-estilo");
                vboxJugadores.getChildren().add(lbl);
                i++;
            }
        });
    }
}
