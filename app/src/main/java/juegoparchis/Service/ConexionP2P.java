package juegoparchis.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Platform;
import java.util.function.Consumer;

public class ConexionP2P {

    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    @SuppressWarnings("unused")
    private boolean servidorHost;
    private Consumer<String> listenerActual;
    //Asignación de puerto para la conexión P2P
    private static final int PUERTO = 4770;

    //Método que inicia el servidor
    public void iniciarServidor(Consumer<String> recibirMensaje) {
        this.listenerActual = recibirMensaje;
        servidorHost = true;
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
                System.out.println("Servidor P2P iniciado, esperando conexiones en puerto " + PUERTO);
                    socket = serverSocket.accept();
                    System.out.println("Cliente conectado: " + socket.getInetAddress());
                    configurarFlujos();
                    escucharMensajes(listenerActual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //Lógica de conexión al servidor P2P para el jugador de la partida
    public void conectarAlServidor(String ip, Consumer<String> recibirMensaje) {
        this.listenerActual = recibirMensaje;
        servidorHost = false;
        new Thread(() ->{
            try {
                System.out.println("Intentando conectar al servidor P2P: " + ip + "...");
                socket = new Socket(ip, PUERTO);
                System.out.println("Conectado al servidor P2P: " + socket.getInetAddress());
                configurarFlujos();
                escucharMensajes(listenerActual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //Lógica de configuración de los flujos de entrada y salida
    private void configurarFlujos() throws IOException {
        //Canal para enviar mensajes
        salida = new PrintWriter(socket.getOutputStream(), true);
        //canal para recibir mensajes
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    //Lógica de envío de mensajes
    public void enviarMensaje(String mensaje) {
        if (salida != null){
            salida.println(mensaje);
            System.out.println("DEBUG: Enviando mensaje: " + mensaje);
        } else {
            System.out.println("DEBUG ERROR: 'salida' es NULL. No se pudo enviar: " + mensaje);
        }
    }
    //Lógica de escucha de mensajes entrantes
    private void escucharMensajes(Consumer<String> recibirMensajeOriginal) {
        try {
            String mensajeRecibido;
            while ((mensajeRecibido = entrada.readLine()) != null) {
                System.out.println("Mensaje recibido: " + mensajeRecibido);
                final String mensajeFinal = mensajeRecibido;
                //Logica del juego en el hilo de la interfaz gráfica
                Platform.runLater(() -> {
                    if (listenerActual != null) {
                        listenerActual.accept(mensajeFinal);
                    }
                });
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    //Setter del listener
    public void setListener(Consumer<String> nuevoListener){
        this.listenerActual = nuevoListener;
    }

}
