package juegoparchis.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;

public class Redes {
    
    //Método para obterner la dirección IP del dispositivo
    public static String obtenerIpPublica() {
        try {
            URL url = URI.create("http://checkip.amazonaws.com/").toURL();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            return br.readLine().trim();  
        } catch (Exception e) {
            return "No disponible";
        }
    }
    //Método para obtener la dirección IP local
    public static String obtenerIpLocal() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1";
        }
    }

}
