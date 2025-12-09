package juegoparchis.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread {
    
    private Socket socket;
    private ConexionP2P padre;
    private PrintWriter salida;
    private BufferedReader entrada;
    //Constructor
    public ClienteHandler(Socket socket, ConexionP2P padre) {
        this.socket = socket;
        this.padre = padre;
    }
    //Método run
    @Override
    public void run() {
        try {
            // Configurar flujos internos de este cliente
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                padre.procesarMensajeDesdeCliente(mensaje, this);
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado.");
            padre.eliminarCliente(this);
        }
    }
    // Método para enviarle un mensaje a este cliente específico
    public void enviar(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
        }
    }
}