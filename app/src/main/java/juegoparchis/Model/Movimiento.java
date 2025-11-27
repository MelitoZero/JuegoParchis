package juegoparchis.Model;

import juegoparchis.Model.Enum.Color;

public class Movimiento {
    private Color color;
    private int idFicha;
    private int destino;
    private String siguienteJugador;
    private boolean enPasillo;
    //Constructor
    public Movimiento(Color color, int idFicha, int destino, String siguienteJugador, boolean enPasillo){
        this.color = color;
        this.idFicha = idFicha;
        this.destino = destino;
        this.siguienteJugador = siguienteJugador;
        this.enPasillo = enPasillo;
       
    }
    //Convierte el objeto a string para enviarlo por la conexion p2p
    public String toFormatoString() {
        return "Mover:" + color + ":" + idFicha + ":" + destino + ":" + siguienteJugador + ":" + enPasillo ;
    }
    //Crea un objeto desde el string recibido
    public static Movimiento desdeFormatoString(String mensaje){
       try {
            String[] partes = mensaje.split(":");
            Color c = Color.valueOf(partes[1]);
            int id = Integer.parseInt(partes[2]);
            int dest = Integer.parseInt(partes[3]);
            String sig = partes[4];
            boolean pas = Boolean.parseBoolean(partes[5]);
            return new Movimiento(c, id, dest, sig, pas);
        } catch (Exception e) {
            return null;
        } 
    }
    //Getters y setters
    public Color getColor() {
        return color;
    }
    public int getIdFicha() {
        return idFicha;
    }
    public int getDestino() {
        return destino;
    }
    public String getSiguienteJugador() {
        return siguienteJugador;
    }
    public boolean isEnPasillo() {
        return enPasillo;
    }
}
