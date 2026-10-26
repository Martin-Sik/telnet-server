package utb.fai;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class App {
    public static void main(String[] args) {
        int port = 12345;
        int max_threads = 10;

        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[0]);
                max_threads = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Chyba: Port a počet vláken musí být čísla.");
                return;
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(max_threads);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server naslouchá na portu " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                executor.execute(new ClientThread(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}