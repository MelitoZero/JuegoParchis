package juegoparchis.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Platform;
import java.util.function.Consumer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConexionP2P {
    //Para cliente
    private Socket socketCliente;
    private PrintWriter salidaCliente;
    private BufferedReader entradaCliente;
    //Para servidor
    private List<ClienteHandler> clientes = new CopyOnWriteArrayList<>();
    private boolean soyHost;
    private Consumer<String> listenerUI;
    private static final int PUERTO = 4770; //Asignación de puerto para la conexión P2P se tiene que añadir regla de firewall

    //Método que inicia el servidor para servidor
    public void iniciarServidor(Consumer<String> listener) {
        this.listenerUI = listener;
        this.soyHost = true;
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
                System.out.println("Servidor P2P iniciado en puerto " + PUERTO);
                //permite escuchar conexiones entrantes
                while (true) {
                    Socket socketNuevo = serverSocket.accept();
                    System.out.println("Nuevo jugador conectado: " + socketNuevo.getInetAddress());
                    ClienteHandler handler = new ClienteHandler(socketNuevo, this);
                    clientes.add(handler);
                    handler.start(); 
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //Método para procesar mensaje
    public synchronized void procesarMensajeDesdeCliente(String mensaje, ClienteHandler remitente) {
        notificarUI(mensaje);
        broadcast(mensaje);
    }
    //Método para eliminar jugador
    public void eliminarCliente(ClienteHandler cliente) {
        clientes.remove(cliente);
    }
    //Lógica de conexión al servidor P2P para el jugador de la partida
    public void conectarAlServidor(String ip, Consumer<String> listener) {
        this.listenerUI = listener;
        this.soyHost = false;
        new Thread(() ->{
            try {
                System.out.println("Conectando al Host: " + ip + "...");
                socketCliente = new Socket(ip, PUERTO);
                System.out.println("Conexión exitosa");
                // Configurar flujos
                salidaCliente = new PrintWriter(socketCliente.getOutputStream(), true);
                entradaCliente = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                escucharHost();
            } catch (IOException e) {
                System.out.println("Error al conectar al Host" + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    //Método para escuchar mensajes del host
    private void escucharHost(){
        try {
            String mensaje;
            while ((mensaje = entradaCliente.readLine()) != null){
                notificarUI(mensaje);
            }
        } catch (IOException e) { System.out.println("Error, no se pudo escuchar al host"); }
    }
    //Método de envio de mensajes
    public void enviarMensaje(String mensaje) {
        if (soyHost) {
            //Envia mensaje a todos los conectados
            broadcast(mensaje);
        } else { //Si no soy host 
            if (salidaCliente != null) salidaCliente.println(mensaje);
        }
    }
    //Método para enviar mensajes a todos los jugadores conectados
    private void broadcast(String mensaje){
        for (ClienteHandler c : clientes) {
            c.enviar(mensaje);
        }
    }
    //Método para subir a la interfaz el mensaje recibido
    private void notificarUI(String mensaje){
        Platform.runLater(() -> {
            if (listenerUI != null) listenerUI.accept(mensaje);
        });
    }
    //Setter del listener
    public void setListener(Consumer<String> l){ this.listenerUI = l; }
}
