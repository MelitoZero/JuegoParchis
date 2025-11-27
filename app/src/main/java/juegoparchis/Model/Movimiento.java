package juegoparchis.Model;

import juegoparchis.Model.Enum.Color;

public class Movimiento {
    private Color color;
    private int idFicha;
    private int destino;
    //Constructor
    public Movimiento(Color color, int idFicha, int destino){
        this.color = color;
        this.idFicha = idFicha;
        this.destino = destino;
    }
    //Convierte el objeto a string para enviarlo por la conexion p2p
    public String toString() {
        return "Mover:" + color + ":" + idFicha + ":" + destino;
    }
    //Crea un objeto desde el string recibido
    public static Movimiento desdeString(String mensaje){
       try {
            String[] partes = mensaje.split(":");
            Color c = Color.valueOf(partes[1]);
            int id = Integer.parseInt(partes[2]);
            int dest = Integer.parseInt(partes[3]);
            return new Movimiento(c, id, dest);
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
}
