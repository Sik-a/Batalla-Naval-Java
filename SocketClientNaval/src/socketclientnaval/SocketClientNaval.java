/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketclientnaval;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author sicacha
 */
public class SocketClientNaval {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Socket scNaval;
        int PUERTO = 6060;
        String HOST = "localhost";
        int fila = 0, col = 0, respServer = 0;
        boolean over = false;
        
        Scanner teclado = new Scanner(System.in);
        
        try {
            System.out.println("Conectado con: " + HOST + ":" + PUERTO);
            scNaval = new Socket(HOST, PUERTO);
            
            // Escritor
            DataOutputStream escritor = new DataOutputStream(scNaval.getOutputStream());
            
            // Lector
            DataInputStream lector = new DataInputStream(scNaval.getInputStream());
            
            do {
                System.out.println("Ingrese posición en fila (0-9)");
                fila = teclado.nextInt();
                System.out.println("Ingrese posición en columna (0-9)");
                col = teclado.nextInt();
                
                escritor.writeInt(fila);
                escritor.writeInt(col);
                
                respServer = lector.readInt();
                
                if (respServer == 1) {
                    System.out.println("Acertó");
                } else if (respServer == 0) {
                    System.out.println("Sigue intentando");
                }
                else {
                    System.out.println("Ganaste");
                    over = true;
                }
            } while (!over);
            
        } catch (Exception e) {
            System.out.println("Error en " + e.getMessage());
        }
    }
    
}
