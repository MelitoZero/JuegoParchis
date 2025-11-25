package juegoparchis.Model;

import java.io.Serializable;
import juegoparchis.Model.Enum.Color;

public class Jugador implements Serializable {
   //Atributos
   private String nombre;
   private String avatar;
   private Color color;
   private int fichasEnCasa;

   //Constructor
   public Jugador(String nombre, String avatar, Color color) {
      this.nombre = nombre;
      this.avatar = avatar;
      this.color = color;
      this.fichasEnCasa = 4; // Inicialmente, todas las fichas están en casa
   }
   
   //Getters y Setters
   public String getNombre() {
      return nombre;
   }
   public void setNombre(String nombre) {
      this.nombre = nombre;
   }
   public String getAvatar() {
      return avatar;
   }
   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }
   public Color getColor() {
      return color;
   }
   public void setColor(Color color) {
      this.color = color;
   }
   public int getFichasEnCasa() {
      return fichasEnCasa;
   }
   public void setFichasEnCasa(int fichasEnCasa) {
      this.fichasEnCasa = fichasEnCasa;
   }
}