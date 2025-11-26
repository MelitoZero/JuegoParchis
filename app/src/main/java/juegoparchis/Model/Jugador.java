package juegoparchis.Model;

import java.io.Serializable;
import juegoparchis.Model.Enum.Color;

public class Jugador implements Serializable {
   //Atributos
   private String nombre;
   private String avatar;
   private Color color;
   private Ficha[] fichas;

   //Constructor
   public Jugador(String nombre, String avatar, Color color) {
      this.nombre = nombre;
      this.avatar = avatar;
      this.color = color;
      //Inicializar las 4 fichas del jugador
      this.fichas = new Ficha[4];
      for (int i = 0; i < 4; i++) {
         this.fichas[i] = new Ficha(i, color);
      }
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
   public Ficha[] getFichas() {
      return fichas;
   }
   public int getFichasEnCasa(){
      int contador = 0;
      for (Ficha f : fichas) {
         if (f.isEnCasa()) {
            contador++;
         }
      }
      return contador;
   }
}