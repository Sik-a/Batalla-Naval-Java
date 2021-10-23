/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketservernaval;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author sicacha
 */
public class SocketServerNaval {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        int PUERTO = 6060;
        String HOST = "localhost";
        String msjServer;
        String[][] tablero = {{"O", "X", "X", "X", "O", "O", "O", "O", "O", "O"}, {"O", "O", "O", "O", "O", "O", "O", "O", "O", "O"},
        {"O", "O", "O", "O", "X", "O", "O", "X", "X", "X"}, {"O", "O", "O", "O", "X", "O", "O", "O", "O", "O"},
        {"O", "O", "O", "O", "X", "O", "O", "O", "O", "O"}, {"O", "O", "O", "O", "O", "O", "O", "O", "O", "O"},
        {"O", "O", "O", "O", "O", "O", "O", "X", "O", "O"}, {"X", "X", "X", "O", "O", "O", "O", "X", "O", "O"},
        {"O", "O", "O", "O", "O", "O", "O", "X", "O", "O"}, {"O", "O", "O", "O", "O", "O", "O", "O", "O", "O"}};
        int fila = 0, col = 0;
        boolean respTablero = false;

        ServerSocket conexionServer;
        Socket client;

        try {
            conexionServer = new ServerSocket(PUERTO);
            client = new Socket();

            System.out.println("Esperando cliente");
            client = conexionServer.accept();
            System.out.println("Conexión establecida" + "\n");

            // Lector
            DataInputStream lector = new DataInputStream(client.getInputStream());

            // Escritor
            DataOutputStream escritor = new DataOutputStream(client.getOutputStream());

            do {
                // Imprimimos el tablero en pantalla
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero.length; j++) {
                        System.out.print(tablero[i][j] + "\t");
                    }
                    System.out.println("\n");
                }

                fila = lector.readInt();
                col = lector.readInt();

                System.out.println(fila + "\n" + col);

                if (tablero[fila][col].equals("X")) {
                    tablero[fila][col] = "O";

                    respTablero = comprobar(tablero);

                    if (respTablero) {
                        escritor.writeInt(1);
                    } else {
                        escritor.writeInt(3);
                    }
                } else {
                    escritor.writeInt(0);
                    respTablero = comprobar(tablero);
                }
            } while (respTablero);

            System.out.println("Fin de la conexión");
            conexionServer.close();
        } catch (Exception e) {
            System.out.println("Error en: " + e.getMessage());
        }
    }

    public static boolean comprobar(String tablero[][]) {
        boolean bandera = false;

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j].equals("X")) {
                    bandera = true;
                }
            }
        }
        return bandera;
    }

}
