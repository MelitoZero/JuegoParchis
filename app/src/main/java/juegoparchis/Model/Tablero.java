package juegoparchis.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import juegoparchis.Model.Enum.Color;
import juegoparchis.Model.Enum.TipoCasilla;

public class Tablero {
    //Lista de las 68 casillas del tablero que se pueden recorrer
    private List<Casilla> casillas;
    //Lista para los pasillo de meta de cada jugador
    private Map<Color, List<Casilla>> pasillosMeta;
    //Método constructor
    public Tablero(){
        casillas = new ArrayList<>();
        pasillosMeta = new HashMap<>();
        inicializarCasillas();
        inicializarPasillos();
    }
    //Método para inicializar las casillas del tablero
    private void inicializarCasillas(){
        //Lista de casillas seguras
        List<Integer> seguros = Arrays.asList(5, 12, 17, 22, 29, 34, 39, 46, 51, 56, 63, 68);
        //Lista de casillas de salida
        List<Integer> salidas = Arrays.asList(5, 22, 39, 56);
        //Crear las 68 casillas del tablero
        for(int i = 1; i <= 68; i++){
            TipoCasilla tipo = TipoCasilla.NORMAL;
            Color colorCasilla = Color.BLANCO;
            //Definir las casillas seguro
            if(salidas.contains(i)){
                tipo = TipoCasilla.SALIDA;
                if (i == 5) colorCasilla = Color.AMARILLO;
                else if (i == 22) colorCasilla = Color.VERDE;
                else if (i == 39) colorCasilla = Color.ROJO;
                else if (i == 56) colorCasilla = Color.AZUL;
            }else if (seguros.contains(i)){
                tipo = TipoCasilla.SEGURO;
            }
            casillas.add(new Casilla(i, tipo, colorCasilla));
        }
    }
    //Método para inicializar los pasillos de meta
    private void inicializarPasillos(){
        for(Color color : Color.values()){
            if (color == Color.BLANCO) continue; //El blanco no tiene pasillo de meta
            List<Casilla> pasilloColor = new ArrayList<>();
            //Crear 7 casillas de pasillo para cada color
            for(int i=1; i <= 8; i++){
                TipoCasilla tipo;
                if(i == 8){
                    tipo = TipoCasilla.META; //La última casilla es la meta
                }else{
                    tipo = TipoCasilla.PASILLO; //Las demás son pasillos
                }
                //Creamos la casila y la añadimos al pasillo del color correspondiente
                pasilloColor.add(new Casilla(i, tipo, color));
            }
            //Guardamos este pasillo en el mapa de pasillos de meta segun su color
            pasillosMeta.put(color, pasilloColor);
        }
    }
    //Getter de los pasillos y casillas
    public List<Casilla> getPasillo(Color color){
        return pasillosMeta.get(color);
    }
    public List<Casilla> getCasillas() {
        return casillas;
    }
    public Casilla getCasilla(int numero){
        if (numero < 1 || numero > casillas.size()) return null;
        return casillas.get(numero - 1);
    }
}