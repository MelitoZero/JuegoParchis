package juegoparchis.Model;

import juegoparchis.Model.Enum.Color;

public class Ficha {
    
    private int id; //Identificador unico de la ficha
    private Color color; //Color de la ficha
    private int posicionActual; //Desde 0 hasta 68, -1 es en casa
    private boolean enCasa;
    private boolean enMeta;

    public Ficha(int id, Color color) {
        this.id = id;
        this.color = color;
        this.posicionActual = -1; //Inicialmente en casa
        this.enCasa = true;
        this.enMeta = false;
    }
    public int getId() {
        return id;
    }
    public Color getColor() {
        return color;
    }
    public int getPosicionActual() {
        return posicionActual;
    }
    public void setPosicionActual(int posicionActual) {
        this.posicionActual = posicionActual;
    }
    public boolean isEnCasa() {
        return enCasa;
    }
    public void setEnCasa(boolean enCasa) {
        this.enCasa = enCasa;
        if (enCasa) {
            this.posicionActual = -1;
        }
    }
    public boolean isEnMeta() {
        return enMeta;
    }
    public void setEnMeta(boolean enMeta) {
        this.enMeta = enMeta;
    }
}
