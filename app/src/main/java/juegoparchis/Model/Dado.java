package juegoparchis.Model;

import java.util.Random;

public class Dado {

    private int valor;
    private Random random;
    // Constructor
    public Dado() {
        this.random = new Random();
        this.valor = 1;
    }
    // Método para lanzar el dado
    public int lanzar() {
        this.valor = random.nextInt(6) + 1; // Genera un número entre 1 y 6
        return this.valor;
    }
    public int getValor() {
        return valor;
    }

}
