package juegoparchis.Model;

import java.util.List;
import juegoparchis.Model.Enum.TipoCasilla;
import juegoparchis.Model.Enum.Color;
import java.util.ArrayList;

public class Casilla {
    private int numero; //número de la casilla
    private TipoCasilla tipo;
    private Color color; //color asociado a la casilla.
    private List<Ficha> fichasEnCasilla; //guarda las fichas que están en esta casilla
    
    //método constructor, si no se especifica el color, se asume que es blanco.
    public Casilla(int numero, TipoCasilla tipo){
        this(numero, tipo, Color.BLANCO);
    }
    //método constructor
    public Casilla(int numero, TipoCasilla tipo, Color color) {
        this.numero = numero;
        this.tipo = tipo;
        this.color = color; // Asignar color blanco por defecto
        this.fichasEnCasilla = new ArrayList<>();
    }
    // Métodos útiles para el Juego
    public void agregarFicha(Ficha ficha) {
        fichasEnCasilla.add(ficha);
    }

    public void removerFicha(Ficha ficha) {
        fichasEnCasilla.remove(ficha);
    }

    public boolean esSeguro() {
        return tipo == TipoCasilla.SEGURO || tipo == TipoCasilla.SALIDA;
    }

    // Esto facilita detectar bloqueos (barreras)
    public boolean estaBloqueada() {
        return fichasEnCasilla.size() >= 2;
    }
    
    // Getters 
    public List<Ficha> getFichas() { return fichasEnCasilla; }
    public Color getColor() { return color; }
    public TipoCasilla getTipo() { return tipo; }
    public int getNumero() { return numero; }
}
